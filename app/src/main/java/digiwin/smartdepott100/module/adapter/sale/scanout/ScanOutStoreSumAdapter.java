package digiwin.smartdepott100.module.adapter.sale.scanout;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/4
 */

public class ScanOutStoreSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public ScanOutStoreSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_scanoutstoresum;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getStock_qty());
        float numb3 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_need_num, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_locator_num, StringUtils.deleteZero(String.valueOf(numb2)));
        holder.setText(R.id.tv_act_send_num, StringUtils.deleteZero(String.valueOf(numb3)));

        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

        if (numb3 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_spec, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_unit, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_need_num, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_locator_num, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_act_send_num, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
        } else if (numb1 > numb3) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_spec,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_no,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_unit,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_need_num, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_locator_num, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_act_send_num, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
        } else if (numb1 ==numb3) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_spec, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_unit, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_need_num,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_locator_num,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_act_send_num,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
        }
    }
}
