package digiwin.smartdepott100.module.adapter.stock.postallocate;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des     调拨过账  清单界面adapter
 * @author  唐孟宇
 * @date    2017/3/09
 */
public class PostAllocateOrderAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public PostAllocateOrderAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_post_allocate_order;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_post_material_order, item.getDoc_no());
        holder.setText(R.id.tv_item_plan_date,item.getCreate_date());
        holder.setText(R.id.tv_item_department,item.getDepartment_name());
        holder.setText(R.id.tv_item_applicant,item.getEmployee_name());
    }
}
