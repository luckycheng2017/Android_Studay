
#define LOG_TAG "TestService"
//#define LOG_NDEBUG 0

#include <fcntl.h>
#include <sys/prctl.h>
#include <sys/wait.h>
#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <cutils/properties.h>
#include <utils/Log.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/socket.h>

#include "IHelloService.h"
#include "IGoodbyeService.h"

#define SOCKET_BUFFER_SIZE      (32768U)

using namespace android;

class MyThread: public Thread {

private:
	int fd;
public:  
    MyThread() {}
    MyThread(int fd) { this->fd = fd; }
 
    bool threadLoop() {
		char buf[500];
		int len;
		int cnt = 0;
		
		while(1) {
			len = read(fd, buf, 500);
			buf[len] = '\0';
			ALOGI("%s\n", buf);
			
			len = sprintf(buf, "Hello, test_client, cnt = %d", cnt++);
			write(fd, buf, len);
		}
		
       	return true;  
    }
  
};  


int main(void) {

	int sockets[2];

	socketpair(AF_UNIX, SOCK_SEQPACKET, 0, sockets);

    int bufferSize = SOCKET_BUFFER_SIZE;
    setsockopt(sockets[0], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[0], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));

	sp<MyThread> th = new MyThread(sockets[0]);
	th->run();  

	sp<ProcessState> proc(ProcessState::self());

	sp<IServiceManager> sm = defaultServiceManager();

	sm->addService(String16("hello"), new BnHelloService(sockets[1]));
	sm->addService(String16("goodbye"), new BnGoodbyeService());

	ProcessState::self()->startThreadPool();
	IPCThreadState::self()->joinThreadPool();

	return 0;
}


