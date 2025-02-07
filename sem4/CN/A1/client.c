#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>

int main()
{
    int sockfd;
    sockfd = socket(PF_INET, SOCK_STREAM, 0);
    if (sockfd == -1) {
        perror("Socket creation failed");
        exit(1);
    }

    struct sockaddr_in servadd;
    servadd.sin_family = AF_INET;
    servadd.sin_port = htons(3003);
    servadd.sin_addr.s_addr = INADDR_ANY;

    int status = connect(sockfd, (struct sockaddr*)&servadd, sizeof(servadd));
    if (status == -1) {
        perror("Connection failed");
        close(sockfd);
        exit(1);
    }

    printf("\t---- Connection established---\n");

        char buffer[1024];
    int recv_len = recv(sockfd, buffer, sizeof(buffer)-1, 0);
    printf("Message from server: %s\n", buffer);
    close(sockfd);
    return 0;
}
