#include "IHelloService.h"

#define LOG_TAG "HelloService"

#include <stdint.h>
#include <sys/types.h>

#include <binder/Parcel.h>
#include <binder/IMemory.h>

#include <utils/Errors.h>  // for status_t
#include <utils/String8.h>

namespace android {


status_t BnHelloService::onTransact(
    uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags)
{
    switch (code) {
        case HELLO_SVR_CMD_SAYHELLO: {
            sayhello();
            return NO_ERROR;
        } break;
        case HELLO_SVR_CMD_SAYHELLOTO: {
            int32_t policy = data.readInt32();
            String16 name16 = data.readString16();
            String8 name8(name16);

            int cnt = sayhello_to(name8.string());

            reply->writeInt32(cnt);

            return NO_ERROR;
        } break;
        default:
            return BBinder::onTransact(code, data, reply, flags);
    }
}

int BnHelloService::sayhello(const char *name) {
    static int cnt = 0;
    ALOGI("say hello : %d\n", cnt++);
}

int BnHelloService::sayhello_to(const char *name) {
    static int cnt = 0;
    ALOGI("say hello to %s : %d\n", name, cnt++);
    return cnt;
}

}; // namespace android
