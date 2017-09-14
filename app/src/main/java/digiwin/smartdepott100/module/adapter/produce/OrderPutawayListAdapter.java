package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author xiemeng
 * @des 生产成套领料列表
 * @date 2017/5/28 16:00
 */

public class OrderPutawayListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public OrderPutawayListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_orderputaway_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_gongDan_no, item.getDoc_no());
        holder.setText(R.id.tv_gongDan_date,item.getCreate_date());
        holder.setText(R.id.tv_item_department,item.getDepartment_no());
        holder.setText(R.id.tv_produce_itemNo,item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
    }
}
