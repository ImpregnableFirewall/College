#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<string.h>
#include<unistd.h>

int main()
{
    int sockfd, newsockfd;
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == -1) {
        perror("Socket creation failed");
        exit(1);
    }

    struct sockaddr_in servaddr, clientaddr;
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(3003);

    if (bind(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr)) == -1) {
        perror("Binding failed");
        close(sockfd);
        exit(1);
    }

    if (listen(sockfd, 5) == -1) {
        perror("Listening failed");
        close(sockfd);
        exit(1);
    }
    printf("Server listening on port 3003...\n");

    socklen_t clientlen = sizeof(clientaddr);
    newsockfd = accept(sockfd, (struct sockaddr*)&clientaddr, &clientlen);
    if (newsockfd == -1) {
        perror("Accept failed");
        close(sockfd);
        exit(1);
    }

    printf("Connection established with client: %s\n", inet_ntoa(clientaddr.sin_addr));

    char message[1000];
    printf("Enter message: ");
    scanf("%[^\n]%*c", message);
    if (send(newsockfd, message, sizeof(message), 0) == -1) {
        perror("Send failed");
        close(newsockfd);
        close(sockfd);
        exit(1);
    }

    printf("Message sent to client.\n");

    close(newsockfd);
    close(sockfd);
    return 0;
}
