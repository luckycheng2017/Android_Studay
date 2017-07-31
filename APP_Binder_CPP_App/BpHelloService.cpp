#include "IHelloService.h"

namespace android {

class BpHelloService: public BpInterface<IHelloService>
{
public:
    BpHelloService(const sp<IBinder>& impl)
        : BpInterface<IHelloService>(impl) {
    }

    void sayhello(void) {
        Parcel data, reply;
        data.writeInt32(0);
        data.writeString16(String16("IHelloService"));

        remote()->transact(HELLO_SVR_CMD_SAYHELLO, data, &reply);
    }

    int sayhello_to(const char *name) {
        Parcel data, reply;
        int exception;

        data.writeInt32(0);
        data.writeString16(String16("IHelloService"));
        data.writeString16(String16(name));

        remote()->transact(HELLO_SVR_CMD_SAYHELLO_TO, data, &reply);

        exception = reply.readInt32();
        if (exception)
            return -1;
        else
            return reply.readInt32();
    }

    int get_fd(void) {
        Parcel data, reply;
        int exception;

        data.writeInt32(0);
        data.writeString16(String16("IHelloService"));

        remote()->transact(HELLO_SVR_CMD_GET_FD, data, &reply);

        exception = reply.readInt32();
        if (exception)
            return -1;
        else
        {
            int rawFd = reply.readFileDescriptor();
            return dup(rawFd);
        }
    }
};

IMPLEMENT_META_INTERFACE(HelloService, "android.media.IHelloService");

};
