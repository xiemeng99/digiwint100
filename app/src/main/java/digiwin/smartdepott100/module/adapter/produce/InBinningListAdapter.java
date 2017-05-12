package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 孙长权
 * @module 装箱入库列表
 * @date 2017/3/30
 */

public class InBinningListAdapter extends BaseRecyclerAdapter<ListSumBean> {
    /**
     * 是否比较（比较数值）
     */
    boolean isEquals;
    public InBinningListAdapter(Context ctx,boolean isEquals, List<ListSumBean> list) {
        super(ctx, list);
        this.isEquals=isEquals;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_inbinninglist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //已入库量
        float numb1 = StringUtils.string2Float(item.getStock_in_qty());
        //可入库量
        float numb2 = StringUtils.string2Float(item.getAvailable_in_qty());

        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_gongDan_no,item.getWo_no());
        holder.setText(R.id.tv_data, item.getCreate_date());//日期
        holder.setText(R.id.tv_department, item.getDepartment_name());//部门
        holder.setText(R.id.tv_plan_numb, item.getWo_qty());//计划量
        holder.setText(R.id.tv_fat_feed, item.getDistribute_qty());//已发料
        holder.setText(R.id.tv_yet_inwarehouse, item.getStock_in_qty());//已入库
        holder.setText(R.id.tv_inwarehouse, item.getAvailable_in_qty());//可入库

        if (isEquals) {
            holder.getView(R.id.ll_now_inwarehouse).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_now_inwarehouse, item.getScan_sumqty());//本次入
            if (numb2 == 0) {
                holder.setBackground(R.id.item_ll, R.drawable.red_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_gongDan_no, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_data, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_department, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_fat_feed, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_yet_inwarehouse, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_inwarehouse, mContext.getResources().getColor(R.color.red));
                holder.setTextColor(R.id.tv_now_inwarehouse, mContext.getResources().getColor(R.color.red));
            } else if (numb1 > numb2) {
                holder.setBackground(R.id.item_ll, R.drawable.yellow_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_gongDan_no, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_data, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_department, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_fat_feed, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_yet_inwarehouse, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_inwarehouse, mContext.getResources().getColor(R.color.outside_yellow));
                holder.setTextColor(R.id.tv_now_inwarehouse, mContext.getResources().getColor(R.color.outside_yellow));
            } else if (numb1 == numb2) {
                holder.setBackground(R.id.item_ll, R.drawable.green_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_gongDan_no, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_data, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_department, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_fat_feed, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_yet_inwarehouse, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_inwarehouse, mContext.getResources().getColor(R.color.Base_color));
                holder.setTextColor(R.id.tv_now_inwarehouse, mContext.getResources().getColor(R.color.Base_color));
            }
        }else{
            holder.getView(R.id.ll_now_inwarehouse).setVisibility(View.GONE);
        }
    }
}
