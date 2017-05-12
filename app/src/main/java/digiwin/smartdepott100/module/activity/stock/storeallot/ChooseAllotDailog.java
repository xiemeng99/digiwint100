package digiwin.smartdepott100.module.activity.stock.storeallot;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;

/**
 * @des      选择拨入拨出
 * @author  xiemeng
 * @date    2017/3/10
 */
public class ChooseAllotDailog {
    private static CustomDialog dialog;
    private static final String TAG = "ChooseAllotDailog";
    /**
     * 弹出确定取消对话框
     */
    public static void showChooseAllotDailog(Context context, final OnDialogTwoListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_nocome_chooseallot)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.tv_allotin, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                listener.onCallback1();
                            }
                        }).addViewOnclick(R.id.tvallot_out, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                listener.onCallback2();
                            }
                        }).cancelTouchout(false).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
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
