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
 * @module 依工单补料 汇总页面
 * @date 2017/3/23
 */

public class WorkSupplementSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public WorkSupplementSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_worksupplementsum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 补料量
        float numb1 = StringUtils.string2Float(item.getReturn_qty());
        float numb2 = StringUtils.string2Float(item.getMatch_qty());

        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_material_return, StringUtils.deleteZero(item.getReturn_qty()));
        holder.setText(R.id.tv_material_return_big, StringUtils.deleteZero(item.getIssue_qty()));
        holder.setText(R.id.tv_feeding_amount, StringUtils.deleteZero(item.getMatch_qty()));

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_feeding_amount, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.Base_color));
        }
    }
}
