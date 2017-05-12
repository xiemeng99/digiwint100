package digiwin.smartdepott100.module.adapter.sale.pickupshipment;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;

/**
 * @author 赵浩然
 * @module 检料出货 FIFO适配器
 * @date 2017/3/24
 */

public class PickUpShipmentFIFoAdapter extends BaseRecyclerAdapter<FifoCheckBean> {

    public PickUpShipmentFIFoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_pickupshipmentfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}
