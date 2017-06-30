package digiwin.smartdepott100.core.coreutil;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * @author xiemeng
 * @des  6.0以上权限申请
 * @date 2017/6/14 19:58
 */

public class PermissionUtils {



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};


    public static void verifyStoragePermissions(Activity activity) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            try {
                //检测是否有写的权限
                int permission = ActivityCompat.checkSelfPermission(activity,
                        "android.permission.WRITE_EXTERNAL_STORAGE");
                //检测是否有照相机权限
                int permission2 = ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA);
                //检测是否有麦克风权限
                int permission3 = ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.RECORD_AUDIO);
                if (permission != PackageManager.PERMISSION_GRANTED||permission2 != PackageManager.PERMISSION_GRANTED||permission3 != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
