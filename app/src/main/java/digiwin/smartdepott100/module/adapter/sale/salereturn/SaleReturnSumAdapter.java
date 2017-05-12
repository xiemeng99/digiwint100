package digiwin.smartdepott100.module.adapter.sale.salereturn;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @des      销售退货 汇总adapter
 * @author  唐孟宇
 * @date    2017/3/02
 */

public class SaleReturnSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public SaleReturnSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_in_store_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getReceipt_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_danwei, item.getUnit_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_model,item.getItem_spec());
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(String.valueOf(numb2)));
        holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_wait_recheck_numb_string, mContext.getResources().getString(R.string.apply_number));
        holder.setText(R.id.tv_act_send_numb_string, mContext.getResources().getString(R.string.scaned_numb));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_danwei,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.Base_color));
        }
    }
}
