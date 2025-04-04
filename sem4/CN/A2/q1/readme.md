# Simplest Protocol Implementation for Sender/Receiver DLL

## Description
This project implements the simplest protocol for Data Link Layer (DLL) communication between a sender and receiver using C++. The implementation demonstrates basic network communication using TCP sockets on port 12345.

## Key Features
- **Sender Site DLL**:
  - `WaitForEvent()`: Waits for user input
  - `GetData()`: Reads data from standard input
  - `MakeFrame()`: Creates network frames (simple data encapsulation)
  - `SendFrame()`: Transmits frames over TCP

- **Receiver Site DLL**:
  - `ReceiveFrame()`: Receives frames from network
  - `ExtractData()`: Retrieves data from frames
  - `DeliverData()`: Outputs received data to console

- Separate executable programs for sender and receiver
- Basic error handling and connection management
- TCP communication using port 12345

## Requirements
- C++ compiler (g++ recommended)
- POSIX-compliant operating system (Linux/macOS)
- Network stack supporting TCP sockets
- Port 12345 available (or modify code for different port)

## How to Execute

### 1. Compile Programs
Compile both programs separately:
```bash
g++ senderDLL.cpp -o sender -std=c++11
g++ receiverDLL.cpp -o receiver -std=c++11

First start the receiver in one terminal:
./receiver
                        
Then start the sender in another terminal:
./sender
