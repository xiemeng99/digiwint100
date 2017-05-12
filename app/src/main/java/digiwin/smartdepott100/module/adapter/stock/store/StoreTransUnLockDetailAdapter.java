package digiwin.smartdepott100.module.adapter.stock.store;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @des     库存交易解锁明细Adapter
 * @author  maoheng
 * @date    2017/3/4
 */

public class StoreTransUnLockDetailAdapter extends BaseRecyclerAdapter<ListSumBean>{

    public StoreTransUnLockDetailAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_storetrans_detail;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_model,item.getItem_spec());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_lot_no,item.getItem_lot_no());
        holder.setText(R.id.tv_storage,item.getWarehouse_no());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
    }
}
