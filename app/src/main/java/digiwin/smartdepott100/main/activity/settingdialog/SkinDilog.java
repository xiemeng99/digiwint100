package digiwin.smartdepott100.main.activity.settingdialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import digiwin.library.constant.SharePreKey;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;

/**
 * @des 皮肤选择
 * Created by maoheng on 2017/9/28.
 */

public class SkinDilog {
    /**
     * 设备
     */
    public interface  SkinListener{
        public void onResult(String result);
    }
    private static CustomDialog dialog;
    private static final String TAG = "SkinDilog";
    /**
     * 弹出确定取消对话框
     */
    public static void showSkinDailog(Context context, final SkinListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_choose_skin)
                        .style(R.style.MyDialog);
                builder.addViewOnclick(R.id.iv_red, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            dialog.dismiss();
                        }
                        listener.onResult("red");
                    }
                }).addViewOnclick(R.id.iv_yellow, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            dialog.dismiss();
                        }
                        listener.onResult("yellow");
                    }
                }).addViewOnclick(R.id.iv_blue, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null){
                            dialog.dismiss();
                        }
                        listener.onResult("blue");
                    }
                }).addViewOnclick(R.id.iv_green, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            dialog.dismiss();
                        }
                        listener.onResult("green");
                    }
                }).cancelTouchout(true).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView ivRed = (ImageView) builder.getView(R.id.iv_red);
                ImageView ivYellow = (ImageView) builder.getView(R.id.iv_yellow);
                ImageView ivBlue = (ImageView) builder.getView(R.id.iv_blue);
                ImageView ivGreen = (ImageView) builder.getView(R.id.iv_green);
                String currentTheme = (String) SharedPreferencesUtils.get(context, SharePreKey.CURRENT_THEME,"red");
                switch (currentTheme){
                    case "red":
                        ivRed.setImageResource(R.mipmap.red_on);
                        break;
                    case "yellow":
                        ivYellow.setImageResource(R.mipmap.yellow_on);
                        break;
                    case "blue":
                        ivBlue.setImageResource(R.mipmap.blue_on);
                        break;
                    case "green":
                        ivGreen.setImageResource(R.mipmap.green_on);
                        break;
                }
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showUnBindStatuDailog-----Error"+e);
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
