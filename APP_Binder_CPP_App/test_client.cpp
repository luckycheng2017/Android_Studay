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

int main(int argc, char **argv) {
    int cnt;

    if (argc < 2) {
        ALOGI("Usage:\n");
        ALOGI("%s <hello|goodbye>\n", argv[0]);
        ALOGI("%s <hello|goodbye> <name>\n", argv[0]);
        return -1;
    }

    sp<ProcessState> proc(ProcessState::self());

    sp<IServiceManager> sm = defaultServiceManager();

    sp<IBinder> binder = sm->getService(String16("hello"));

    if (binder == 0) {
        ALOGI("can't get hello service\n");
        return -1;
    }

    sp<IHelloService> service = interface_cast<IHelloService> (binder);

    if (argc < 3) {
        service->sayhello();
        ALOGI("client call sayhello");
    } else {
        cnt = service->sayhello_to(argv[2]);
        ALOGI("client call sayhello_to, cnt = %d", cnt);
    }

    return 0;
}