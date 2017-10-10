package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 孙长权
 * @module 装箱入库汇总
 * @date 2017/3/30
 */

public class InBinningSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public InBinningSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_inbinningsum;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //已入库量
        float numb1 = StringUtils.string2Float(item.getStock_in_qty());
        //可入库量
        float numb2 = StringUtils.string2Float(item.getAvailable_in_qty());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_gongDan_no,item.getDoc_no());
        holder.setText(R.id.tv_data, item.getCreate_date());//日期
        holder.setText(R.id.tv_department,item.getDepartment_name());
        holder.setText(R.id.tv_inwarehouse, StringUtils.deleteZero(item.getAvailable_in_qty()));//可入库
        holder.setText(R.id.tv_yet_inwarehouse, StringUtils.deleteZero(item.getScan_sumqty()));//已入库
        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

            if (numb2 == 0) {
                holder.setBackground(R.id.item_ll, R.drawable.red_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_gongDan_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_data, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_department, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_yet_inwarehouse, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_inwarehouse, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            } else if (numb1 > numb2) {
                holder.setBackground(R.id.item_ll, R.drawable.yellow_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_gongDan_no, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_data, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_department, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_yet_inwarehouse, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_inwarehouse, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            } else if (numb1 == numb2) {
                holder.setBackground(R.id.item_ll, R.drawable.green_scandetail_bg);
                holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_gongDan_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_data, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_department, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_yet_inwarehouse, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_inwarehouse, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            }
    }
}
