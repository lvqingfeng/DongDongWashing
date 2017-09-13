package dongdongwashing.com.util;

import android.hardware.Camera;

/**
 * Created by 沈 on 2017/5/3.
 */

public class IsCameraCanUse {

    public IsCameraCanUse() {

    }

    /**
     * 判断当前手机的摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
}
