package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

/**
 * @des      条码移库
 * @author  xiemeng
 * @date    2017/3/23
 */
public class MoveStoreAdapter extends BaseRecyclerAdapter<SumShowBean> {


    public MoveStoreAdapter(Context ctx, List<SumShowBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_movestore;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, SumShowBean item) {
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_allot_num, StringUtils.deleteZero(item.getScan_sumqty()));
        holder.setText(R.id.tv_unit, item.getUnit_no());
    }
}
