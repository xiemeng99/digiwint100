package digiwin.smartdepott100.module.activity.produce.endproductallot;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * 依成品调拨 条码扫描 对话框
 * Created by wangyu on 2017/6/14.
 *
 */

public class EndProductAllotDialog {
    private static CustomDialog dialog;
    private static final String TAG = "EndProductAllotDialog";

    /**
     * 显示依成品调拨的汇总的dialog 和适配器
     */
    public static void showSumDataDialog(Context mContext, ListSumBean item){
        try {
            if (mContext != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(mContext)
                        .view(R.layout.dialog_end_product_allot)
                        .style(R.style.CustomDialog)
                        .widthpx((int) (ViewUtils.getScreenWidth(mContext) * 0.9))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .cancelTouchout(false).backCancelTouchout(true)
                        .addViewOnclick(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                //判断申请量 和 匹配量
                float numb1 = StringUtils.string2Float(item.getApply_qty());
                float numb2 = StringUtils.string2Float(item.getScan_sumqty());
                if (numb2 == 0) {
                    builder.setViewTextColor(R.id.tv_item_name,item.getLow_order_item_name(), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_unit,item.getUnit_no(), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_item_format,item.getLow_order_item_spec(), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_item_no,item.getLow_order_item_no(), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_apply_number,StringUtils.deleteZero(item.getApply_qty()), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_stock_qty,StringUtils.deleteZero(item.getStock_qty()), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_match_number,StringUtils.deleteZero(item.getScan_sumqty()), mContext.getResources().getColor(R.color.red50));
                    builder.setViewTextColor(R.id.tv_line_store_qty,StringUtils.deleteZero(item.getW_stock_qty()), mContext.getResources().getColor(R.color.red50));
                } else if (numb1 > numb2) {
                    builder.setViewTextColor(R.id.tv_item_name,item.getLow_order_item_name(), mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_unit,item.getUnit_no(), mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_item_format,item.getLow_order_item_spec(), mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_item_no,item.getLow_order_item_no(), mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_apply_number, StringUtils.deleteZero(item.getApply_qty()),mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_stock_qty, StringUtils.deleteZero(item.getStock_qty()),mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_match_number,StringUtils.deleteZero(item.getScan_sumqty()), mContext.getResources().getColor(R.color.orangeYellow));
                    builder.setViewTextColor(R.id.tv_line_store_qty,StringUtils.deleteZero(item.getW_stock_qty()), mContext.getResources().getColor(R.color.orangeYellow));
                } else if (numb1 ==numb2) {
                    builder.setViewTextColor(R.id.tv_item_name,item.getLow_order_item_name(), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_unit,item.getUnit_no(), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_item_format,item.getLow_order_item_spec(), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_item_no, item.getLow_order_item_no(),mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_apply_number,StringUtils.deleteZero(item.getApply_qty()), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_stock_qty,StringUtils.deleteZero(item.getStock_qty()), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_match_number,StringUtils.deleteZero(item.getScan_sumqty()), mContext.getResources().getColor(R.color.green1b));
                    builder.setViewTextColor(R.id.tv_line_store_qty,StringUtils.deleteZero(item.getW_stock_qty()), mContext.getResources().getColor(R.color.green1b));
                }
                dialog = builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showBindSumDailog-----Error"+e);
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
