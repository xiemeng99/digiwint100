package digiwin.smartdepott100.module.adapter.sale;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;

/**
 * @des      销售出库fifo
 * @author  xiemeng
 * @date    2017/3/16
 */

public class SaleOutletFiFoAdapter extends BaseRecyclerAdapter<FifoCheckBean>{

    private List<FifoCheckBean> fifoList;

    public SaleOutletFiFoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
        fifoList = list;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_saleoutletfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_no,item.getLot_no());
        holder.setText(R.id.tv_barcode,item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());

        if(position == fifoList.size() -1){
            holder.setVisibility(R.id.fifo_bottom_line, View.GONE);
        }
    }
}
