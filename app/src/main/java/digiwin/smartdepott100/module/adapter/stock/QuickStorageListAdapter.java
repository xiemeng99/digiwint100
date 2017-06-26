package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author 孙长权
 * @module 快速入库 -- 清单 -- 适配器
 * @date 2017/6/15
 */

public class QuickStorageListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public QuickStorageListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_qucikstoragelist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_purchase_order, item.getDoc_no());
        holder.setText(R.id.tv_date,item.getReceipt_date());
        holder.setText(R.id.tv_supplier, item.getSupplier_name());
    }
}
