package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;

/**
 * @author 赵浩然
 * @module 依成品发料扫描
 * @date 2017/3/3
 */

public class AccordingMaterialFiFo_Adapter extends BaseRecyclerAdapter<FifoCheckBean>{

    public AccordingMaterialFiFo_Adapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_accordingmaterial_fifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_batch_date,item.getLot_date());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}
