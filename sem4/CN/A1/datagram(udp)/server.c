#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define BUF_SIZE 1024

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <port>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int sockfd;
    char buffer[BUF_SIZE];
    struct sockaddr_in server_addr, client_addr;
    socklen_t addr_len = sizeof(client_addr);
    int port = atoi(argv[1]); // Convert port argument to integer

    // Create socket
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Fill server information
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(port);

    // Bind the socket
    if (bind(sockfd, (const struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Bind failed");
        close(sockfd);
        exit(EXIT_FAILURE);
    }

    printf("Server listening on port %d\n", port);

    while (1) {
        // Receive message from client
        int n = recvfrom(sockfd, buffer, BUF_SIZE, MSG_WAITALL,
                         (struct sockaddr *) &client_addr, &addr_len);
        buffer[n] = '\0';
        printf("Client: %s\n", buffer);

        // Check for exit condition
        if (strcmp(buffer, "bye\n") == 0) {
            printf("Client has ended the chat.\n");
            break;
        }

        // Send reply to client
        printf("You: ");
        fgets(buffer, BUF_SIZE, stdin);
        sendto(sockfd, buffer, strlen(buffer), MSG_CONFIRM,
               (const struct sockaddr *) &client_addr, addr_len);

        // Check for exit condition
        if (strcmp(buffer, "bye\n") == 0) {
            printf("You have ended the chat.\n");
            break;
        }
    }

    close(sockfd);
    return 0;
}
