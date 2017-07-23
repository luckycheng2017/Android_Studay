#define LOG_TAG "HelloService"
//#define LOG_NDEBUG 0

#include <fcntl.h>
#include <sys/prctl.h>
#include <sys/wait.h>
#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <cutils/properties.h>
#include <utils/Log.h>

#include "IHelloService.h"
#include "IGoodbyeService.h"

using namespace android;

int main(void) {
    sp<ProcessState> proc(ProcessState::self());

    sp<IServiceManager> sm = defaultServiceManager();

    sm->addService(String16("hello"), new BnHelloService());
    sm->addService(String16("goodbye"), new BnGoodbyeService());

    ProcessState::self()->startThreadPool();
    IPCThreadState::self()->joinThreadPool();

	return 0;
}