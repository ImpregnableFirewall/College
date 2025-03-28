#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define BUF_SIZE 1024

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s <server_ip> <port>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int sockfd;
    char buffer[BUF_SIZE];
    struct sockaddr_in server_addr;
    int port = atoi(argv[2]); // Convert port argument to integer

    // Create socket
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Fill server information
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);
    server_addr.sin_addr.s_addr = inet_addr(argv[1]); // Server IP from command-line argument

    while (1) {
        // Send message to server
        printf("You: ");
        fgets(buffer, BUF_SIZE, stdin);
        sendto(sockfd, buffer, strlen(buffer), MSG_CONFIRM,
               (const struct sockaddr *) &server_addr, sizeof(server_addr));

        // Check for exit condition
        if (strcmp(buffer, "bye\n") == 0) {
            printf("You have ended the chat.\n");
            break;
        }

        // Receive reply from server
        int n = recvfrom(sockfd, buffer, BUF_SIZE, MSG_WAITALL,
                         NULL, NULL);
        buffer[n] = '\0';
        printf("Server: %s\n", buffer);

        // Check for exit condition
        if (strcmp(buffer, "bye\n") == 0) {
            printf("Server has ended the chat.\n");
            break;
        }
    }

    close(sockfd);
    return 0;
}
