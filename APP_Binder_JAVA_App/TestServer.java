import android.util.Slog;
import android.os.ServiceManager;

public class TestServer {
    private static final String TAG = TestServer.class.getSimpleName();

    public static void main(String args[]) {
        Slog.i(TAG, "add hello service");
        ServiceManager.addService("hello", new HelloService());

        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}