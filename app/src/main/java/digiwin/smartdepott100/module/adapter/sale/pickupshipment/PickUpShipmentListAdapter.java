package digiwin.smartdepott100.module.adapter.sale.pickupshipment;

import android.content.Context;
import java.util.List;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author 赵浩然
 * @module 捡料出货清单 适配器
 * @date 2017/3/23
 */

public class PickUpShipmentListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public PickUpShipmentListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_pickupshipmentlist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_shipping_order, item.getDoc_no());
        holder.setText(R.id.tv_shipping_date,item.getCreate_date());
        holder.setText(R.id.tv_custom,item.getCustomer_name());
        holder.setText(R.id.tv_apply_branch, item.getDepartment_name());
        holder.setText(R.id.tv_applicant, item.getEmployee_name());
    }
}
