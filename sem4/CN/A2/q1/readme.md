gcc ReceiverDLL.c -o receiver
gcc SenderDLL.c -o sender

./receiver 127.0.0.1 12345
Receiver listening on 127.0.0.1:12345...
./sender 127.0.0.1 12345
