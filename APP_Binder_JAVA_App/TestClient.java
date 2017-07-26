import android.provider.Settings;
import android.util.Slog;
import android.os.ServiceManager;
import android.os.IBinder;

public class TestClient {
    private static final String TAG = TestClient.class.getSimpleName();

    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("Usage: need parameter: <hello|goobye> [name]");
            return;
        }

        if (args[0].equals("hello")) {
            IBinder binder = ServiceManager.getService("hello");
            if (binder == null) {
                System.out.println("can not get hello service");
                Slog.i(TAG, "can not get hello service");
                return;
            }

            IHelloService svr = IHelloService.Stub.asInterface(binder);

            if (args.length == 1) {
                try {
                    svr.sayhello();
                    System.out.println("call sayhello");
                    Slog.i(TAG, "call sayhello");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    int cnt = svr.sayhello_to(args[1]);
                    System.out.println("call sayhello_to " + args[1]);
                    Slog.i(TAG, "call sayhello_to " + args[1] + " : cnt = " + cnt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}