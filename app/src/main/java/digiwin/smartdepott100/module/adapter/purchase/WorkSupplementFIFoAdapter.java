package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;

/**
 * @author 赵浩然
 * @module 依工单补料 FIFO适配器
 * @date 2017/3/24
 */

public class WorkSupplementFIFoAdapter extends BaseRecyclerAdapter<FifoCheckBean> {

    private List<FifoCheckBean> fifoList;

    public WorkSupplementFIFoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
        fifoList = list;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_worksupplementfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());

        if(position == fifoList.size() - 1){
            holder.setVisibility(R.id.fifo_bottom_line, View.GONE);
        }
    }
}
