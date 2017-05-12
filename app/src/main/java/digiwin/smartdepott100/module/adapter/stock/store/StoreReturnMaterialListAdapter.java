package digiwin.smartdepott100.module.adapter.stock.store;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author maoheng
 * @des 仓库退料清单
 * @date 2017/3/30
 */

public class StoreReturnMaterialListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean>{
    public StoreReturnMaterialListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_returnmaterial;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_return_material, item.getDoc_no());
        holder.setText(R.id.tv_date, item.getCreate_date());
        holder.setText(R.id.tv_supplier, item.getSupplier_name());
    }
}
