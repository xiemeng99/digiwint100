package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @des 生产成套领料列表
 * @date 2017/5/28 16:00
 */

public class SuitPickingHalfListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public SuitPickingHalfListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_suitpickinghalf_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_picking_no, item.getIssuing_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_applicant,item.getEmployee_name());
        holder.setText(R.id.tv_depart,item.getDepartment_name());
    }
}
