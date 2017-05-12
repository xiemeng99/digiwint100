package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * 筛选结果 待办事项Adapter
 * @author 唐孟宇
 */

public class PostMaterialOrderAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public PostMaterialOrderAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_post_material;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_post_material_order, item.getDoc_no());
        holder.setText(R.id.tv_item_plan_date,item.getCreate_date());
        holder.setText(R.id.tv_item_applicant, item.getEmployee_name());
        holder.setText(R.id.tv_item_department,item.getDepartment_name());
    }
}
