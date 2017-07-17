
#define LOG_TAG "HelloService"

#include "IHelloService.h"


namespace android {


status_t BnHelloService::onTransact(
    uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags)
{
    switch (code) {
        case HELLO_SVR_CMD_SAYHELLO: {
            sayhello();
            return NO_ERROR;
        } break;
        case HELLO_SVR_CMD_SAYHELLO_TO: {
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

void BnHelloService::sayhello(void) {
	static int cnt = 0;
	ALOGI("say hello : %d\n", cnt++);
}

int BnHelloService::sayhello_to(const char *name) {
    static int cnt = 0;
    ALOGI("say hello to %s : %d\n", name, cnt++);
    return cnt;
}

}; // namespace android
