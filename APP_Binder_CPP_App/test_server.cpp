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


using namespace android;

void main(void) {
    sp<ProcessState> proc(ProcessState::self());

    sp<IServiceManager> sm = defaultServiceManager();

    sm->addService(String16("hello"), new BnHelloService());

    ProcessState::self()->startThreadPool();
    IPCThreadState::self()->joinThreadPool();
}