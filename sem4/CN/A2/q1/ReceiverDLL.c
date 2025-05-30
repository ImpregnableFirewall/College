#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <errno.h>

#define BUFFER_SIZE 1024

typedef struct {
    char *data;
    int length;
} Frame;

typedef struct {
    int server_fd;
    int new_socket;
    struct sockaddr_in address;
} ReceiverDLL;

void ReceiverDLL_Init(ReceiverDLL *receiver, const char *ip, int port) {
    int opt = 1;
    socklen_t addrlen = sizeof(receiver->address);
    
    if ((receiver->server_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }
    
    if (setsockopt(receiver->server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))) {
        perror("setsockopt");
        exit(EXIT_FAILURE);
    }
    
    receiver->address.sin_family = AF_INET;
    receiver->address.sin_port = htons(port);
    
    if (inet_pton(AF_INET, ip, &receiver->address.sin_addr) <= 0) {
        perror("invalid address");
        exit(EXIT_FAILURE);
    }
    
    if (bind(receiver->server_fd, (struct sockaddr*)&receiver->address, sizeof(receiver->address)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
    
    if (listen(receiver->server_fd, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }
    
    printf("Receiver listening on %s:%d...\n", ip, port);
    
    if ((receiver->new_socket = accept(receiver->server_fd, (struct sockaddr*)&receiver->address, &addrlen)) < 0) {
        perror("accept");
        exit(EXIT_FAILURE);
    }
}

Frame ReceiveFrame(ReceiverDLL *receiver) {
    Frame frame = {NULL, 0};
    char buffer[BUFFER_SIZE];
    int valread = read(receiver->new_socket, buffer, BUFFER_SIZE);
    
    if (valread < 0) {
        perror("read");
        return frame;
    } else if (valread == 0) {
        return frame;
    }
    
    frame.data = malloc(valread);
    if (!frame.data) {
        perror("malloc");
        return frame;
    }
    memcpy(frame.data, buffer, valread);
    frame.length = valread;
    return frame;
}

Frame ExtractData(Frame frame) {
    return frame;
}

void DeliverData(Frame data) {
    printf("Delivered data: ");
    fwrite(data.data, 1, data.length, stdout);
    printf("\n");
}

void ReceiverDLL_Run(ReceiverDLL *receiver) {
    while (1) {
        Frame frame = ReceiveFrame(receiver);
        if (frame.length <= 0) {
            if (frame.data) free(frame.data);
            break;
        }
        Frame data = ExtractData(frame);
        DeliverData(data);
        free(frame.data);
    }
    close(receiver->new_socket);
    close(receiver->server_fd);
    printf("Connection closed\n");
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s <IP> <Port>\n", argv[0]);
        return 1;
    }
    const char *ip = argv[1];
    int port = atoi(argv[2]);

    ReceiverDLL receiver;
    ReceiverDLL_Init(&receiver, ip, port);
    ReceiverDLL_Run(&receiver);
    return 0;
}
