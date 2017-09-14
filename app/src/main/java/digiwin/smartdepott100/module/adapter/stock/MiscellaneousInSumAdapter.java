package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;

/**
 * @des      杂项收料 汇总adapter
 * @author  唐孟宇
 * @date    2017/3/02
 */

public class MiscellaneousInSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public MiscellaneousInSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_miscellaneous_in_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_danwei, item.getUnit_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_model,item.getItem_spec());
        holder.setText(R.id.tv_signcode,item.getSigncode()+"    "+item.getSigncode_instruction());
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(String.valueOf(numb2)));
        holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(String.valueOf(numb1)));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_signcode, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.Base_color));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_danwei,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_signcode, mContext.getResources().getColor(R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.textfous_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_signcode, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_in_storage_number, mContext.getResources().getColor(R.color.green1b));
        }
    }
}
