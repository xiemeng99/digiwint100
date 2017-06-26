package digiwin.smartdepott100.module.adapter.sale;
import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;


/**
 * @des      销售出库汇总列表
 * @author  xiemeng
 * @date    2017/3/16
 */
public class SaleOutletSumAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public SaleOutletSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_saleoutlet_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_model,item.getItem_spec());
        holder.setText(R.id.tv_unit_no,item.getUnit_no());
        holder.setText(R.id.tv_in_apply_number, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(String.valueOf(numb2)));
        holder.setText(R.id.tv_locator_num,StringUtils.deleteZero(item.getStock_qty()));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_unit_no, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_in_apply_number, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.red50));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_unit_no, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_model,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_in_apply_number,mContext.getResources().getColor( R.color.orangeYellow));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_locator_num, mContext.getResources().getColor(R.color.orangeYellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_unit_no, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_in_apply_number, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_match_number,mContext.getResources().getColor( R.color.green1b));
            holder.setTextColor(R.id.tv_locator_num,mContext.getResources().getColor( R.color.green1b));
        }

    }


}
