package digiwin.smartdepott100.module.activity.stock.printlabel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import digiwin.smartdepott100.R;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;

/**
 * sunchangquan
 * 打印机弹框
 * 2017/5/28 13:14
 */

public class PrintLableFlowDialog {
    /**
     * 回调接口
     */
    public interface  PrinterFlowListener{
        public void bindByDevice(String lableNumber,String printNumber);
    }
    private static CustomDialog dialog;
    private static final String TAG = "PrintLableFlowDialog";


    /**
     * 原材料标签打印
     */
    public static void showRawMaterialPrint(Context context,String string,final PrinterFlowListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_printer_rawmaterial)
                        .style(R.style.CustomDialog);
                EditText view = (EditText) builder.getView(R.id.et_print_number);
                view.setText("1");
                builder.addViewOnclick(R.id.tv_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                            dialog.dismiss();

                        final String lableNumber = builder.getViewText(R.id.et_weight);
                        //打印张数
                        final String printNumber = builder.getViewText(R.id.et_print_number);
                        listener.bindByDevice(lableNumber,printNumber);
                    }
                }).setViewText(R.id.tv_material,string)
                        .cancelTouchout(true).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "PrintLableFlowDialog-----Error"+e);
        }
    }


    /**
     *厂内流转标签
     */
    public static void showPrinterDailog(Context context,String string,final PrinterFlowListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
               final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                    .view(R.layout.dialog_printer_flow)
                        .style(R.style.CustomDialog);
                EditText view = (EditText) builder.getView(R.id.et_print_number);
                view.setText("1");
                builder.addViewOnclick(R.id.tv_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                //标签数量
                                final String lableNumber = builder.getViewText(R.id.et_lable_number);
                                //打印张数
                                final String printNumber = builder.getViewText(R.id.et_print_number);
                                listener.bindByDevice(lableNumber,printNumber);
                            }
                        }).setViewText(R.id.tv_material,string)
                        .cancelTouchout(true).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "PrintLableFlowDialog-----Error"+e);
        }
    }
    /**
     * 标签补打
     */
    public static void showRePrinterDailog(Context context,final PrinterFlowListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
               final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                    .view(R.layout.dialog_printer_reprint)
                        .style(R.style.CustomDialog);
                builder.addViewOnclick(R.id.tv_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                //标签数量
                                final String lableNumber = builder.getViewText(R.id.et_lable_number);
                                //打印张数
                                final String printNumber = builder.getViewText(R.id.et_print_number);
                                listener.bindByDevice(lableNumber,printNumber);
                            }
                        })
                        .cancelTouchout(true).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "PrintLableFlowDialog-----Error"+e);
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
