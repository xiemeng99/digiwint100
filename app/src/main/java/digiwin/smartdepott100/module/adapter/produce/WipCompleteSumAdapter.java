package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @des 完工入库
 * @date 2017/5/25
 * @author xiemeng
 */
public class WipCompleteSumAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public WipCompleteSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_wipcomplete;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_wo_no,item.getWo_no());
        holder.setText(R.id.tv_product_code,item.getProduct_no());
        holder.setText(R.id.tv_plan_numb, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_scaned_numb, StringUtils.deleteZero(String.valueOf(numb2)));

        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_wo_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_product_code, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_plan_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_scaned_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_name,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_wo_no, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_product_code, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_plan_numb,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_scaned_numb, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_wo_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_product_code, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_plan_numb, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_scaned_numb,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
        }

    }


}
