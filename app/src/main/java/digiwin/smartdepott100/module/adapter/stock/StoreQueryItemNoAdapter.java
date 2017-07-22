package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;


/**
 * @des     库存查询--料件库存
 * @author  xiemeng
 * @date    2017/3/22
 */
public class StoreQueryItemNoAdapter extends BaseRecyclerAdapter<ListSumBean>{
    public StoreQueryItemNoAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_store_query_item_no;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_name_unit_no,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_store_locator,item.getWarehouse_storage());
        holder.setText(R.id.tv_num, StringUtils.deleteZero(item.getStock_qty()));


    }
}
