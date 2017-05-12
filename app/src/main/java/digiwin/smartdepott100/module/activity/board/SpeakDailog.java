package digiwin.smartdepott100.module.activity.board;

import android.content.Context;
import android.view.ViewGroup;

import digiwin.library.constant.SystemConstant;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;

/**
 * @des      语音播放
 * @author  xiemeng
 * @date    2017/3/17
 */

public class SpeakDailog {
    private static CustomDialog dialog;
    private static final String TAG = "SpeakDailog";
    /**
     * 弹出确定取消对话框
     */
    public static void showChooseAllotDailog(Context context,String voicerType) {
        try {
            if (context != null) {
                CustomDialog.Builder builder = new CustomDialog.Builder(context);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
               int layoutID=0;
                if (SystemConstant.VIXF.equals(voicerType)){
                    layoutID=R.layout.dialog_speakboy;
                }else {
                    layoutID=R.layout.dialog_speakgirl;
                }
                dialog = builder.view(layoutID)
                        .style(R.style.CustomDialog)
                        .cancelTouchout(false)
                        .widthpx((int) (ViewUtils.getScreenWidth(context)))
                        .heightpx(ViewGroup.LayoutParams.MATCH_PARENT)
                        .startViewAnimdrawable(R.id.wait_dialog_iv)
                        .backCancelTouchout(false)
                        .build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showChooseAllotDailog-----Error"+e);
        }
    }

    /**
     * 隐藏等待dialog
     */
    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
