#include <iostream>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <cstdlib>
#include <cstring>
#include <errno.h>

#define PORT 12345
#define BUFFER_SIZE 1024

class ReceiverDLL {
private:
    int server_fd, new_socket;
    struct sockaddr_in address;

    std::string ReceiveFrame() {
        char buffer[BUFFER_SIZE] = {0};
        int valread = read(new_socket, buffer, BUFFER_SIZE);
        if (valread < 0) {
            std::cerr << "Read error: " << strerror(errno) << std::endl;
            return "";
        }
        return std::string(buffer, valread);
    }

    std::string ExtractData(const std::string& frame) {
        return frame; // Data is the frame itself
    }

    void DeliverData(const std::string& data) {
        std::cout << "Delivered data: " << data << std::endl;
    }

public:
    ReceiverDLL() {
        int opt = 1;
        socklen_t addrlen = sizeof(address);
        
        // Create socket file descriptor
        if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
            std::cerr << "Socket creation failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
        
        // Set socket options
        if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))) {
            std::cerr << "Setsockopt failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
        
        address.sin_family = AF_INET;
        address.sin_addr.s_addr = INADDR_ANY;
        address.sin_port = htons(PORT);
        
        // Bind socket to port
        if (bind(server_fd, (struct sockaddr*)&address, sizeof(address)) < 0) {
            std::cerr << "Bind failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
        
        // Start listening
        if (listen(server_fd, 3) < 0) {
            std::cerr << "Listen failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
        
        std::cout << "Receiver listening on port " << PORT << "..." << std::endl;
        
        // Accept incoming connection
        if ((new_socket = accept(server_fd, (struct sockaddr*)&address, &addrlen)) < 0) {
            std::cerr << "Accept failed: " << strerror(errno) << std::endl;
            exit(EXIT_FAILURE);
        }
    }

    void run() {
        while (true) {
            std::string frame = ReceiveFrame();
            if (frame.empty()) break;
            
            std::string data = ExtractData(frame);
            DeliverData(data);
        }
        close(new_socket);
        close(server_fd);
        std::cout << "Connection closed" << std::endl;
    }
};

int main() {
    ReceiverDLL receiver;
    receiver.run();
    return 0;
}
