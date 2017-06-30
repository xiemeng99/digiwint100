package digiwin.smartdepott100.module.adapter.sale.pickupshipment;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 赵浩然
 * @module 捡料出货汇总页面
 * @date 2017/3/23
 */

public class PickUpShipmentSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public PickUpShipmentSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_pickupshipmentsum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());

        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_format, item.getItem_spec());
        holder.setText(R.id.tv_material_return, StringUtils.deleteZero(item.getApply_qty()));
        holder.setText(R.id.tv_material_return_big, StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_feeding_amount, StringUtils.deleteZero(item.getScan_sumqty()));

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_feeding_amount, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.orangeYellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.green1b));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.green1b));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.green1b));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.green1b));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.green1b));
        }
    }
}
