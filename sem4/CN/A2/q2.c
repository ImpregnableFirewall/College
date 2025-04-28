#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <sys/time.h>

enum FrameType { DATA, ACK, NAK };

typedef struct {
    enum FrameType type;
    int seqNum;
    char data[100];
    int corrupted; // 0 for not corrupted, 1 for corrupted
} Frame;

typedef struct {
    Frame frames[100];
    int front;
    int count;
    int noisy;
    double lossProbability;
    double corruptionProbability;
} Channel;

typedef struct {
    Channel channel;
    int nextSeqNum;
    int lastAckNum;
    int waitingForAck;
    int timeoutMs;
} StopAndWaitProtocol;

long long currentTimeMillis() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (long long)tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

void initChannel(Channel *channel, int isNoisy, double lossProb, double corruptProb) {
    channel->front = 0;
    channel->count = 0;
    channel->noisy = isNoisy;
    channel->lossProbability = lossProb;
    channel->corruptionProbability = corruptProb;
}

void sendFrame(Channel *channel, Frame frame) {
    if (channel->noisy) {
        double rand_val = (double)rand() / RAND_MAX;

        if (rand_val < channel->lossProbability) {
            printf("Channel: Frame %d LOST!\n", frame.seqNum);
            return;
        }

        if (rand_val < channel->lossProbability + channel->corruptionProbability) {
            frame.corrupted = 1;
            printf("Channel: Frame %d CORRUPTED!\n", frame.seqNum);
        }
    }

    if (channel->count < 100) {
        int rear = (channel->front + channel->count) % 100;
        channel->frames[rear] = frame;
        channel->count++;
    } else {
        printf("Channel: Queue full, frame dropped\n");
    }
}

int hasFrame(Channel *channel) {
    return channel->count > 0;
}

Frame receiveFrame(Channel *channel) {
    Frame frame = channel->frames[channel->front];
    channel->front = (channel->front + 1) % 100;
    channel->count--;
    return frame;
}

void initProtocol(StopAndWaitProtocol *protocol, int isNoisy) {
    initChannel(&protocol->channel, isNoisy, 0.3, 0.2);
    protocol->nextSeqNum = 0;
    protocol->lastAckNum = -1;
    protocol->waitingForAck = 0;
    protocol->timeoutMs = 2000;
}

int main() {
    srand(time(NULL));

    printf("Choose transmission type:\n");
    printf("1. Noiseless\n2. Noisy\n");
    int choice;
    scanf("%d", &choice);

    StopAndWaitProtocol protocol;
    initProtocol(&protocol, choice == 2);

    printf("Starting Stop-and-Wait Protocol (%s mode)\n",
           protocol.channel.noisy ? "Noisy" : "Noiseless");

    for (int i = 0; i < 5; i++) {
        Frame frame;
        frame.type = DATA;
        frame.seqNum = protocol.nextSeqNum;
        snprintf(frame.data, sizeof(frame.data), "Data%d", i);
        frame.corrupted = 0;

        printf("Sender: Sending frame %d - %s\n", protocol.nextSeqNum, frame.data);
        sendFrame(&protocol.channel, frame);
        protocol.waitingForAck = 1;

        long long start = currentTimeMillis();
        while (protocol.waitingForAck) {
            long long now = currentTimeMillis();
            long long elapsed = now - start;

            if (elapsed > protocol.timeoutMs) {
                printf("Sender: Timeout! Resending frame %d\n", protocol.nextSeqNum);
                sendFrame(&protocol.channel, frame);
                start = currentTimeMillis();
            }

            if (hasFrame(&protocol.channel)) {
                Frame received = receiveFrame(&protocol.channel);

                if (received.type == DATA) {
                    if (received.corrupted) {
                        printf("Receiver: Corrupted frame received\n");
                        Frame nakFrame = { NAK, protocol.lastAckNum, "", 0 };
                        sendFrame(&protocol.channel, nakFrame);
                    } else if (received.seqNum == (protocol.lastAckNum + 1) % 2) {
                        printf("Receiver: Received NEW frame %d - %s\n", received.seqNum, received.data);
                        protocol.lastAckNum = received.seqNum;
                        Frame ackFrame = { ACK, received.seqNum, "", 0 };
                        sendFrame(&protocol.channel, ackFrame);
                    } else {
                        printf("Receiver: Received DUPLICATE frame %d\n", received.seqNum);
                        Frame ackFrame = { ACK, received.seqNum, "", 0 };
                        sendFrame(&protocol.channel, ackFrame);
                    }
                } else if (received.type == ACK || received.type == NAK) {
                    if (received.corrupted) {
                        printf("Sender: Corrupted ACK received\n");
                    } else if (received.type == ACK && received.seqNum == protocol.nextSeqNum) {
                        printf("Sender: Received ACK for frame %d\n", protocol.nextSeqNum);
                        protocol.nextSeqNum = 1 - protocol.nextSeqNum;
                        protocol.waitingForAck = 0;
                    } else if (received.type == NAK) {
                        printf("Sender: Received NAK for frame %d\n", received.seqNum);
                        sendFrame(&protocol.channel, frame);
                        start = currentTimeMillis();
                    }
                }
            }

            usleep(100000); // Sleep for 100 milliseconds
        }
    }

    return 0;
}
