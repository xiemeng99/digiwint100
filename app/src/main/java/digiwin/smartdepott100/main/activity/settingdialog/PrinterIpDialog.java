package digiwin.smartdepott100.main.activity.settingdialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.SharePreferenceKey;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ViewUtils;

/**
 * sunchangquan
 * 打印机ip
 * 2017/5/28 13:14
 */

public class PrinterIpDialog {
    /**
     * 回调接口
     */
    public interface  PrinterBindIpListener{
        public void bindByDevice(String ip);
    }
    private static CustomDialog dialog;
    private static final String TAG = "showPrinterDailog";
    /**
     * 弹出确定取消对话框
     */
    public static void showPrinterDailog(Context context, final PrinterBindIpListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
               final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                    .view(R.layout.dialog_printer_ip)
                        .style(R.style.CustomDialog);
               final String ip = (String) SharedPreferencesUtils.get(context, SharePreferenceKey.PRINTER_IP, "");
                EditText view = (EditText) builder.getView(R.id.et_psw);
                view.setText(ip);
                builder.addViewOnclick(R.id.tv_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                final String editText = builder.getViewText(R.id.et_psw);

                                listener.bindByDevice(editText);
                            }
                        }).cancelTouchout(true).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showPrinterDailog-----Error"+e);
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
