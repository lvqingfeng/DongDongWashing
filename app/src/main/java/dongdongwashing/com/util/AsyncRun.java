package dongdongwashing.com.util;

import android.os.Handler;
import android.os.Looper;

/**
 * 异步运行
 * Created by star on 2017/2/28.
 */
public class AsyncRun {
    public static void run(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}
