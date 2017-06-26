package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des  供应商扫码收货
 * @date 2017/6/13
 * @author xiemeng
 */
public class PurchaseSupplierAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public PurchaseSupplierAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchasesupplier_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_delivery_note_no, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_delivery_address,item.getDelivery_address());
        holder.setText(R.id.tv_supplier,item.getSupplier_name());
    }
}
