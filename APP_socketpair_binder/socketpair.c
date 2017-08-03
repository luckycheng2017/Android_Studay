#include <pthread.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>

#define SOCKET_BUFFER_SIZE      (32768U)

void *function_thread1 (void *arg) {
    int fd = (int)arg;
    char buf[500];
    int len;
    int cnt = 0;

    while (1) {
        len = sprintf (buf, "Hello, main thread, cnt = %d", cnt++);
        write(fd, buf, len);

        len = read(fd, buf, 500);
        buf[len] = '\0';
        printf("%s\n", buf);

        sleep(5);
    }

    return NULL;
}

int main(int argc, char **argv) {
    int sockets[2];

    socketpair(AF_UNIX, SOCK_SEQPACKET, 0, sockets);

    int bufferSize = SOCKET_BUFFER_SIZE;
    setsockopt(sockets[0], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[0], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));

    pthread_t threadID;
    pthread_create(&threadID, NULL, function_thread1, (void *) sockets[1]);

    char buf[500];
    int len;
    int cnt = 0;
    int fd = sockets[0];

    while(1) {
        len = read(fd, buf, 500);
        buf[len] = '\0';
        printf("%s\n", buf);

        len = sprintf(buf, "Hello, thread1, cnt = %d", cnt++);
        write(fd, buf, len);
    }
}