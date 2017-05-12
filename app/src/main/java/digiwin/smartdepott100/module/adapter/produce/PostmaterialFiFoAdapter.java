package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;

/**
 * @des 领料过账 FIFO adapter
 * @author  唐孟宇
 */
public class PostmaterialFiFoAdapter extends BaseRecyclerAdapter<FifoCheckBean>{

    public PostmaterialFiFoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_postmaterialfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_no,item.getLot_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}