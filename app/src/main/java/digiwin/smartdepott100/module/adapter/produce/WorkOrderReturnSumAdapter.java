package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
/**
 * @des      工单退料，数据汇总
 * @author  xiemeng
 * @date    2017/3/24
 */

public class WorkOrderReturnSumAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public WorkOrderReturnSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_workorderreturn_sum;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getLow_order_item_no());
        holder.setText(R.id.tv_item_name,item.getLow_order_item_name());
        holder.setText(R.id.tv_item_model,item.getLow_order_item_spec());
        holder.setText(R.id.tv_unit_no,item.getUnit_no());
        holder.setText(R.id.tv_product_code,item.getProduct_no());
        holder.setText(R.id.tv_can_return_num, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_actual_return_num, StringUtils.deleteZero(String.valueOf(numb2)));

        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_unit_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_model, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_product_code, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_can_return_num, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_actual_return_num, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_unit_no, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_name,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_model,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_product_code,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_can_return_num,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_actual_return_num, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_unit_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_model,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_product_code,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_can_return_num, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_actual_return_num,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
        }

    }


}