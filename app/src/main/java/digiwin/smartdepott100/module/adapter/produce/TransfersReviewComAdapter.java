package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @des      调拨复核
 * @author  xiemeng
 * @date    2017/3/6
 */
public class TransfersReviewComAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public TransfersReviewComAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_tranreview_commit;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, final int position,final ListSumBean item) {
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_seq,item.getReceipt_seq());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_itemspec,item.getItem_spec());
        holder.setText(R.id.req_qty, StringUtils.deleteZero(item.getReq_qty()));
        holder.setText(R.id.tv_distribute_qty,StringUtils.deleteZero(item.getDistribute_qty()));
        holder.setText(R.id.et_accept_qty,StringUtils.deleteZero(item.getAccept_qty()));
        holder.getEditText(R.id.et_accept_qty).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                item.setAccept_qty(s.toString());
            }
        });
    }


}
