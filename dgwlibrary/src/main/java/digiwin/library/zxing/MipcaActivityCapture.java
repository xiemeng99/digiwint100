package digiwin.library.zxing;

import android.app.Activity;
import android.content.Intent;

import digiwin.library.zxing.camera.GetBarCodeListener;

/**
 * 二维码扫描
 * Initial the camera
 *
 * @author YunXiang.Tang
 */
public class MipcaActivityCapture {

    /**
     * 扫码监听
     */


    public static void startCameraActivity(Activity activity, GetBarCodeListener listener) {
        if (null != activity) {
            Intent intent = new Intent();
            intent.setClass(activity, CaptureScanActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            CaptureScanActivity.mBarcodeListener = listener;
            activity.startActivity(intent);
        }
    }


}