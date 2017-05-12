package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @module 依成品调拨
 * @date 2017/4/13
 */
public class EndProductAllotFifoAdapter extends BaseRecyclerAdapter<FifoCheckBean> {

    public EndProductAllotFifoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_endproductallot_fifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode_no,item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_actual_yield, item.getScan_sumqty());
    }
}
