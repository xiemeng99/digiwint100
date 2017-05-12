package digiwin.smartdepott100.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.sale.traceproduct.CurrentInventoryBean;

/**
 * @author maoheng
 * @des 产品质量追溯 当前库存adapter
 * @date 2017/4/6
 */

public class CurrentInventoryAdapter extends BaseRecyclerAdapter<CurrentInventoryBean> {

    public CurrentInventoryAdapter(Context ctx, List<CurrentInventoryBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_current_inventory;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, CurrentInventoryBean item) {
        holder.setText(R.id.tv_store_locator,item.getWarehouse_no()+"/"+item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_no,item.getLot_no());
        holder.setText(R.id.tv_qty, StringUtils.deleteZero(item.getQty()));
    }
}
