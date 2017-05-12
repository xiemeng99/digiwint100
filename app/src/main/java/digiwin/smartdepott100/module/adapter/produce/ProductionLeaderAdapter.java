package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 赵浩然
 * @module 生产超领 汇总页面
 * @date 2017/3/30
 */

public class ProductionLeaderAdapter  extends BaseRecyclerAdapter<ListSumBean> {

    public ProductionLeaderAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_productionleader;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        float numb1 = StringUtils.string2Float(item.getReceipt_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());

        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_model, item.getItem_spec());
        holder.setText(R.id.tv_apply_number, StringUtils.deleteZero(item.getReceipt_qty()));
        holder.setText(R.id.tv_swept_volume, StringUtils.deleteZero(item.getScan_sumqty()));

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_model, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_apply_number, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_swept_volume, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_model, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_apply_number,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_swept_volume, mContext.getResources().getColor(R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_model, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_apply_number,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_swept_volume, mContext.getResources().getColor(R.color.Base_color));
        }
    }
}
