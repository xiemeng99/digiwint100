package digiwin.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import digiwin.library.R;

/**
 * Dialog弹出工具类
 * @Author 毛衡
 * Created by Administrator on 2017/1/11 0011.
 */

public class DialogUtils {

    private Activity context;
    private View dialogView;
    public  Dialog dialog;

    public DialogUtils(Activity context, View dialogView) {
        this.context = context;
        this.dialogView = dialogView;
    }



    /**
     * 显示Dialog
     */
    public void showDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.width = (int) (ViewUtils.getScreenWidth(context)*0.9); // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//高度
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    /**
     * 方法重载显示Dialog
     */
    public void showDialog(int width,int height){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.width = width; // 宽度
        lp.height = height;//高度
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    /**
     * 隐藏Dialog
     */
    public void dismissDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }
    /**
     * 设置dialog空白处不可点击
     */
    public void setCanceledOnTouchOutside(){
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keyListener);
    }

    private DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
    /**
     * 显示Dialog 背景透明
     */
    public void showDialogWithTransparent(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.width = (int) (ViewUtils.getScreenWidth(context)*0.9); // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//高度
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside();
        dialog.show();
    }
}
