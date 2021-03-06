package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

/**
 * @des      生产完工入库汇总adapter
 * @author  xiemeng
 * @date    2017/2/24
 */

public class FinishedStorageSumAdapter extends BaseRecyclerAdapter<SumShowBean> {

    public FinishedStorageSumAdapter(Context ctx, List<SumShowBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_fininshedstorage;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, SumShowBean item) {
        float numb1 = StringUtils.string2Float(item.getAvailable_in_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_item_danwei,item.getUnit_no());
        holder.setText(R.id.tv_plan_numb, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_scaned_numb, StringUtils.deleteZero(String.valueOf(numb2)));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_spec, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_scaned_numb, mContext.getResources().getColor(R.color.Base_color));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_danwei,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_item_spec, mContext.getResources().getColor(R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_plan_numb,mContext.getResources().getColor( R.color.textfous_yellow));
            holder.setTextColor(R.id.tv_scaned_numb, mContext.getResources().getColor(R.color.textfous_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_danwei, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_spec, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_scaned_numb,mContext.getResources().getColor( R.color.green1b));
        }

    }


}
