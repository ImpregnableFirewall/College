#include <iostream>
#include <string>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <cstdlib>
#include <cstring>
#include <errno.h>

#define PORT 12345

class SenderDLL {
private:
    int sock;
    struct sockaddr_in serv_addr;

    void WaitForEvent() {
        // Simulate waiting for user input event
        std::cout << "Waiting for user input..." << std::endl;
    }

    std::string GetData() {
        std::string data;
        std::cout << "Enter message to send (type 'exit' to quit): ";
        std::getline(std::cin, data);
        return data;
    }

    std::string MakeFrame(const std::string& data) {
        return data; // Simplest frame (no encapsulation)
    }

    void SendFrame(const std::string& frame) {
        if (send(sock, frame.c_str(), frame.size(), 0) < 0) {
            std::cerr << "Send failed: " << strerror(errno) << std::endl;
        }
    }

public:
    SenderDLL() {
        // Create socket
        if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
            std::cerr << "Socket creation error: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }

        serv_addr.sin_family = AF_INET;
        serv_addr.sin_port = htons(PORT);
        
        // Convert IPv4 address from text to binary
        if (inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0) {
            std::cerr << "Invalid address: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }

        // Connect to receiver
        if (connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
            std::cerr << "Connection failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
    }

    void run() {
        while (true) {
            WaitForEvent();
            std::string data = GetData();
            
            if (data == "exit") {
                std::cout << "Closing connection..." << std::endl;
                break;
            }
            
            std::string frame = MakeFrame(data);
            SendFrame(frame);
            std::cout << "Sent frame: " << frame << std::endl;
        }
        close(sock);
    }
};

int main() {
    SenderDLL sender;
    sender.run();
    return 0;
}
