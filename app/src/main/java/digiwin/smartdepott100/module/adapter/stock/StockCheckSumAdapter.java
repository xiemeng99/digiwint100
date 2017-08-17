package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @des  库存盘点汇总列表
 * @date 2017/8/12
 * @author xiemeng
 */

public class StockCheckSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public StockCheckSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_stockchecksum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_unit_no,item.getUnit_no());
        holder.setText(R.id.tv_item_model, item.getItem_spec());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_scan_number, item.getScan_sumqty());

    }
}
