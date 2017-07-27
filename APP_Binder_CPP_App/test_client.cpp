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

#include "IHelloService.h"
#include "IGoodbyeService.h"

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

    if (strcmp(argv[1], "hello") == 0) {
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
            ALOGI("client call sayhello_to, cnt = %d\n", cnt);
        }
    } else {
        sp<IBinder> binder = sm->getService(String16("goodbye"));

        if (binder == 0) {
            ALOGI("can't get goodbye service\n");
            return -1;
        }

        sp<IGoodbyeService> service = interface_cast<IGoodbyeService> (binder);

        if (argc < 3) {
            service->saygoodbye();
            ALOGI("client call saygoodbye");
        } else {
            cnt = service->saygoodbye_to(argv[2]);
            ALOGI("client call saygoodbye_to, cnt = %d\n", cnt);
        }
    }

    return 0;
}
