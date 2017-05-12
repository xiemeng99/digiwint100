package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
/**
 * @des      工单退料
 * @author  xiemeng
 * @date    2017/3/24
 */
public class WorkOrderReturnListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean>{
    public WorkOrderReturnListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_workorderreturn_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_order_number,item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_depart_supplier,item.getDepartment_name());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
    }
}
