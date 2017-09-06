package digiwin.smartdepott100.module.adapter.sale.scanout;

import android.content.Context;

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
        if (numb3 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_spec, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_need_num, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_act_send_num, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb3) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_spec,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_need_num, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_act_send_num, mContext.getResources().getColor(R.color.outside_yellow));
        } else if (numb1 ==numb3) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_spec, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_need_num,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_locator_num,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_act_send_num,mContext.getResources().getColor( R.color.Base_color));
        }
    }
}
