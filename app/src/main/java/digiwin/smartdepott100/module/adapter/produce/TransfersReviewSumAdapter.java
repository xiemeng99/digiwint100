package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des      调拨复核
 * @author  xiemeng
 * @date    2017/3/6
 */
public class TransfersReviewSumAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {


    public TransfersReviewSumAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_transferssum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_receipt_no,item.getDoc_no());
        holder.setText(R.id.tv_date,item.getCreate_date());
    }


}
