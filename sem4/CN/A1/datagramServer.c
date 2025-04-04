#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>

#define BUFFER_SIZE 256

void error(const char *msg) {
    perror(msg);
    exit(1);
}

int main(int argc, char* argv[]) {
    if(argc < 2) {
        fprintf(stderr, "Port No. not provided. Program terminated\n");
        exit(1);
    }

    int sockfd, n;
    char buffer[BUFFER_SIZE];
    struct sockaddr_in serv_addr, cli_addr;
    socklen_t clilen = sizeof(cli_addr);

    // Create UDP socket (changed to SOCK_DGRAM)
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockfd < 0) {
        error("Error opening socket");
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    int portno = atoi(argv[1]);
    
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);
    
    if(bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        error("Binding failed");
    }

    printf("UDP Server listening on port %d...\n", portno);

    while(1) {
        // Receive message from client
        bzero(buffer, BUFFER_SIZE);
        n = recvfrom(sockfd, buffer, BUFFER_SIZE, 0,
                    (struct sockaddr *) &cli_addr, &clilen);
        if(n < 0) {
            error("Error receiving data");
        }
        printf("Client: %s", buffer);

        // Prepare response
        bzero(buffer, BUFFER_SIZE);
        printf("Server: ");
        fgets(buffer, BUFFER_SIZE-1, stdin);
        
        // Send response to client
        n = sendto(sockfd, buffer, strlen(buffer), 0,
                  (struct sockaddr *) &cli_addr, clilen);
        if(n < 0) {
            error("Error sending data");
        }

        if(strncmp("bye", buffer, 3) == 0) {
            break;
        }
    }

    close(sockfd);
    return 0;
}
