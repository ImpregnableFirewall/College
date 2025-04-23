#include <iostream>
#include <string>
#include <queue>
#include <random>
#include <chrono>
#include <thread>  // Required for std::this_thread::sleep_for()

enum FrameType { DATA, ACK, NAK };

struct Frame {
    FrameType type;
    int seqNum;
    std::string data;
    bool corrupted = false;
};

class Channel {
private:
    std::queue<Frame> queue;
    bool noisy;
    double lossProbability;
    double corruptionProbability;
    std::default_random_engine generator;
   
public:
    Channel(bool isNoisy = false, double lossProb = 0.3, double corruptProb = 0.2) :
        noisy(isNoisy),
        lossProbability(lossProb),
        corruptionProbability(corruptProb),
        generator(std::chrono::system_clock::now().time_since_epoch().count()) {}
       
    void sendFrame(Frame frame) {
        if (noisy) {
            std::uniform_real_distribution<double> dist(0.0, 1.0);
            double rand = dist(generator);
           
            if (rand < lossProbability) {
                std::cout << "Channel: Frame " << frame.seqNum << " LOST!\n";
                return;
            }
           
            if (rand < lossProbability + corruptionProbability) {
                frame.corrupted = true;
                std::cout << "Channel: Frame " << frame.seqNum << " CORRUPTED!\n";
            }
        }
        queue.push(frame);
    }

    bool hasFrame() const {
        return !queue.empty();
    }

    Frame receiveFrame() {
        Frame frame = queue.front();
        queue.pop();
        return frame;
    }
};

class StopAndWaitProtocol {
private:
    Channel channel;
    int nextSeqNum = 0;
    int lastAckNum = -1;
    bool waitingForAck = false;
    const int timeoutMs = 2000;
   
public:
    StopAndWaitProtocol(bool isNoisy) : channel(isNoisy) {}
   
    void run() {
        std::cout << "Starting Stop-and-Wait Protocol ("
                  << (channel.hasFrame() ? "Noisy" : "Noiseless") << " mode)\n";
       
        for (int i = 0; i < 5; i++) {
            // Sender side
            {
                Frame frame;
                frame.type = DATA;
                frame.seqNum = nextSeqNum;
                frame.data = "Data" + std::to_string(i);
               
                std::cout << "Sender: Sending frame " << nextSeqNum
                          << " - " << frame.data << "\n";
                channel.sendFrame(frame);
                waitingForAck = true;
               
                auto start = std::chrono::steady_clock::now();
                while (waitingForAck) {
                    // Check for timeout
                    auto now = std::chrono::steady_clock::now();
                    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>(now - start).count();
                   
                    if (elapsed > timeoutMs) {
                        std::cout << "Sender: Timeout! Resending frame " << nextSeqNum << "\n";
                        channel.sendFrame(frame);
                        start = now;
                    }
                   
                    // Receiver side (simulated)
                    if (channel.hasFrame()) {
                        Frame received = channel.receiveFrame();
                       
                        if (received.type == DATA) {
                            // Process data frame
                            if (received.corrupted) {
                                std::cout << "Receiver: Corrupted frame received\n";
                                Frame nakFrame;
                                nakFrame.type = NAK;
                                nakFrame.seqNum = lastAckNum;
                                channel.sendFrame(nakFrame);
                            }
                            else if (received.seqNum == (lastAckNum + 1) % 2) {
                                std::cout << "Receiver: Received NEW frame " << received.seqNum
                                          << " - " << received.data << "\n";
                                lastAckNum = received.seqNum;
                                Frame ackFrame;
                                ackFrame.type = ACK;
                                ackFrame.seqNum = received.seqNum;
                                channel.sendFrame(ackFrame);
                            }
                            else {
                                std::cout << "Receiver: Received DUPLICATE frame " << received.seqNum << "\n";
                                Frame ackFrame;
                                ackFrame.type = ACK;
                                ackFrame.seqNum = received.seqNum;
                                channel.sendFrame(ackFrame);
                            }
                        }
                        else if (received.type == ACK || received.type == NAK) {
                            // Process ACK/NAK
                            if (received.corrupted) {
                                std::cout << "Sender: Corrupted ACK received\n";
                            }
                            else if (received.type == ACK && received.seqNum == nextSeqNum) {
                                std::cout << "Sender: Received ACK for frame " << nextSeqNum << "\n";
                                nextSeqNum = 1 - nextSeqNum;
                                waitingForAck = false;
                            }
                            else if (received.type == NAK) {
                                std::cout << "Sender: Received NAK for frame " << received.seqNum << "\n";
                                channel.sendFrame(frame);
                                start = std::chrono::steady_clock::now();
                            }
                        }
                    }
                   
                    // Small delay to prevent CPU overuse
                    std::this_thread::sleep_for(std::chrono::milliseconds(100));
                }
            }
        }
    }
};

int main() {
    std::cout << "Choose transmission type:\n";
    std::cout << "1. Noiseless\n2. Noisy\n";
    int choice;
    std::cin >> choice;
   
    StopAndWaitProtocol protocol(choice == 2);
    protocol.run();
   
    return 0;
}
