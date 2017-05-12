package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des      采购入库汇总adapter
 * @author  唐孟宇
 * @date    2017/3/09
 */

public class PurchaseInStorageAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public PurchaseInStorageAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_in_storage_order;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_purchase_order, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_item_supplier,item.getSupplier_name());
    }
}
