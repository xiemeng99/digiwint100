package digiwin.smartdepott100.main.activity.settingdialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;

/**
 * @author xiemeng
 * @des 解绑相关操作
 * @date 2017/4/10 13:14
 */

public class DeviceDialog {
    /**
     * 设备
     */
    public interface  DeviceInfoListener{
        public void unBindByDevice(String psw);
        public void unBindByUse(String psw);
    }
    private static CustomDialog dialog;
    private static final String TAG = "DeviceDialog";
    /**
     * 弹出确定取消对话框
     */
    public static void showUnBindStatuDailog(Context context, final DeviceInfoListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
               final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                    .view(R.layout.dialog_device_unbind)
                        .style(R.style.CustomDialog);
                builder.addViewOnclick(R.id.tv_unbinddevice, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                final String editText = builder.getViewText(R.id.et_psw);
                                if (!StringUtils.isBlank(editText)){
                                    listener.unBindByUse(editText);
                                }
                            }
                        })
                        .addViewOnclick(R.id.tv_unbinduser, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                final String editText = builder.getViewText(R.id.et_psw);
                                if (!StringUtils.isBlank(editText)){
                                    listener.unBindByUse(editText);
                                }

                            }
                        }).cancelTouchout(true).backCancelTouchout(true)
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
