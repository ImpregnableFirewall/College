#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <errno.h>

#define PORT 12345
#define BUFFER_SIZE 1024

typedef struct {
    char *data;
    int length;
} Frame;

typedef struct {
    int sock;
    struct sockaddr_in serv_addr;
} SenderDLL;

void SenderDLL_Init(SenderDLL *sender) {
    if ((sender->sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket creation error");
        exit(EXIT_FAILURE);
    }
    
    sender->serv_addr.sin_family = AF_INET;
    sender->serv_addr.sin_port = htons(PORT);
    
    if (inet_pton(AF_INET, "127.0.0.1", &sender->serv_addr.sin_addr) <= 0) {
        perror("invalid address");
        exit(EXIT_FAILURE);
    }
    
    if (connect(sender->sock, (struct sockaddr*)&sender->serv_addr, sizeof(sender->serv_addr)) < 0) {
        perror("connection failed");
        exit(EXIT_FAILURE);
    }
}

void WaitForEvent() {
    printf("Waiting for user input...\n");
}

Frame GetData() {
    Frame data = {NULL, 0};
    char buffer[BUFFER_SIZE];
    
    printf("Enter message to send (type 'exit' to quit): ");
    fflush(stdout);
    
    if (fgets(buffer, BUFFER_SIZE, stdin) == NULL) {
        return data;
    }
    
    size_t len = strlen(buffer);
    if (len > 0 && buffer[len-1] == '\n') {
        buffer[len-1] = '\0';
        len--;
    }
    
    data.data = malloc(len);
    if (!data.data) {
        perror("malloc");
        return data;
    }
    memcpy(data.data, buffer, len);
    data.length = len;
    return data;
}

Frame MakeFrame(Frame data) {
    return data;
}

void SendFrame(SenderDLL *sender, Frame frame) {
    if (send(sender->sock, frame.data, frame.length, 0) < 0) {
        perror("send failed");
    }
}

void SenderDLL_Run(SenderDLL *sender) {
    while (1) {
        WaitForEvent();
        Frame data = GetData();
        if (data.data == NULL) {
            continue;
        }
        if (data.length == 4 && memcmp(data.data, "exit", 4) == 0) {
            free(data.data);
            printf("Closing connection...\n");
            break;
        }
        Frame frame = MakeFrame(data);
        SendFrame(sender, frame);
        printf("Sent frame: ");
        fwrite(frame.data, 1, frame.length, stdout);
        printf("\n");
        free(data.data);
    }
    close(sender->sock);
}

int main() {
    SenderDLL sender;
    SenderDLL_Init(&sender);
    SenderDLL_Run(&sender);
    return 0;
}
