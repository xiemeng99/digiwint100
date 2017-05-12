package digiwin.smartdepott100.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.sale.traceproduct.ShipmentToBean;

/**
 * @author maoheng
 * @des 产品质量追溯 生产过程adapter
 * @date 2017/4/6
 */

public class ShipmentToAdapter extends BaseRecyclerAdapter<ShipmentToBean> {

    public ShipmentToAdapter(Context ctx, List<ShipmentToBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_shipment_to;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ShipmentToBean item) {
        holder.setText(R.id.tv_order_date,item.getReceipt_date());
        holder.setText(R.id.tv_custom,item.getCustomer_name());
        holder.setText(R.id.tv_order_no,item.getReceipt_no());
        holder.setText(R.id.tv_custom_no,item.getCustomer_po_no());
        holder.setText(R.id.tv_warehouse, item.getWarehouse_no());
        holder.setText(R.id.tv_num,StringUtils.deleteZero(item.getQty()));
    }
}
