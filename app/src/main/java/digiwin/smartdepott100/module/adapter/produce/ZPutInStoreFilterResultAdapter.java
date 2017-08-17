package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
/**
 * @des  富钛入库上架
 * @date 2017/5/26  
 * @author xiemeng
 */
public class ZPutInStoreFilterResultAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public ZPutInStoreFilterResultAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_zput_in_store;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_post_material_order, item.getDoc_no());
        holder.setText(R.id.tv_item_plan_date,item.getCreate_date());
        holder.setText(R.id.tv_item_person, item.getEmployee_name());
        holder.setText(R.id.tv_item_department,item.getDepartment_name());
    }
}
