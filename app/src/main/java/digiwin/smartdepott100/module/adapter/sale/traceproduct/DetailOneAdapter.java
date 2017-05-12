package digiwin.smartdepott100.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;

/**
 * @author maoheng
 * @des 产品质量追溯
 * @date 2017/4/6
 */

public class DetailOneAdapter extends BaseRecyclerAdapter<TraceProductDetailBean> {

    public DetailOneAdapter(Context ctx, List<TraceProductDetailBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_detail_one;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, TraceProductDetailBean item) {
        holder.setText(R.id.tv_barcode_no,item.getBarcode_no());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_spec, item.getItem_spec());
    }
}
