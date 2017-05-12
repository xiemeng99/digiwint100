package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.produce.workorder.WorkOrderActivity;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

/**
 * @author 赵浩然
 * @module 依工单发料 汇总数据
 * @date 2017/3/10
 */

public class WorkOrderSumAdapter extends BaseRecyclerAdapter<ListSumBean>{

    public WorkOrderSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_workorder;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        final float numb1 = StringUtils.string2Float(item.getShortage_qty());
        final float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        final float numb3 = StringUtils.string2Float(item.getStock_qty());

        holder.setText(R.id.tv_item_name, item.getLow_order_item_name());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_format, item.getLow_order_item_no());
        holder.setText(R.id.tv_item_no, item.getLow_order_item_no());
        holder.setText(R.id.tv_locator_num, StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_material_return, StringUtils.deleteZero(item.getShortage_qty()));
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getScan_sumqty()));

        //跳转到明细
        holder.setClickListener(R.id.img_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumShowBean bean = new SumShowBean();
                bean.setItem_no(item.getLow_order_item_no());
                bean.setItem_name(item.getLow_order_item_name());
                bean.setAvailable_in_qty(StringUtils.getMinQty(item.getShortage_qty(),item.getStock_qty()));
                WorkOrderActivity activity = (WorkOrderActivity) mContext;
                activity.ToDetailAct(bean);
            }
        });


        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_locator_num,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_match_number,mContext.getResources().getColor( R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_match_number,mContext.getResources().getColor( R.color.Base_color));
        }
    }
}
