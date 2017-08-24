package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.produce.endproductallot.EndProductAllotActivity;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @module 依成品调拨 汇总适配器
 * @date 2017/4/13
 */
public class EndProductAllotSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public EndProductAllotSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_endproductallot_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断申请量 和 匹配量
        float numb1 = StringUtils.string2Float(item.getApply_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_name, item.getLow_order_item_name());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_format, item.getLow_order_item_spec());
        holder.setText(R.id.tv_item_no, item.getLow_order_item_no());
        holder.setText(R.id.tv_apply_number,StringUtils.deleteZero(item.getApply_qty()));
        holder.setText(R.id.tv_stock_qty,StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_match_number,StringUtils.deleteZero(item.getScan_sumqty()));
        holder.setText(R.id.tv_line_store_qty,StringUtils.deleteZero(item.getW_stock_qty()));

        //点击图标 跳转到明细
        holder.setClickListener(R.id.img_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumShowBean bean = new SumShowBean();
                bean.setItem_no(item.getLow_order_item_no());
                bean.setItem_name(item.getLow_order_item_name());
                bean.setAvailable_in_qty(item.getApply_qty());
                EndProductAllotActivity activity = (EndProductAllotActivity) mContext;
                activity.ToDetailAct(bean);
            }
        });
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_apply_number, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_stock_qty, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.red50));
            holder.setTextColor(R.id.tv_line_store_qty, mContext.getResources().getColor(R.color.red50));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_apply_number, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_stock_qty, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.orangeYellow));
            holder.setTextColor(R.id.tv_line_store_qty, mContext.getResources().getColor(R.color.orangeYellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_apply_number, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_stock_qty, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_match_number, mContext.getResources().getColor(R.color.green1b));
            holder.setTextColor(R.id.tv_line_store_qty, mContext.getResources().getColor(R.color.green1b));
        }
    }
}
