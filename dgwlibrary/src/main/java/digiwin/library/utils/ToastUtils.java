package digiwin.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by qGod
 * 2016/12/12
 * Toast工具类
 */

public class ToastUtils {

    private static Toast mToast;

    /**
     * 底部Toast(直接输入string)
     */
    public static void showToastByString(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 底部Toast(根据字符串id显示)
     */
    public static void showToastByInt(Context context, int stringid) {
        if (mToast == null) {
            mToast = Toast.makeText(context, stringid, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(stringid);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * Toast居中显示(根据字符串id显示)
     */
    public static void showCenterToastByInt(Context context, int stringid) {
        if (mToast == null) {
            mToast = Toast.makeText(context, stringid, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(stringid);
            mToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * Toast居中显示(直接输入string)
     */
    public static void showCenterToastByString(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 取消Toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
