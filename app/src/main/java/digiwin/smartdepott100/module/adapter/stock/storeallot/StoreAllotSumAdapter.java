package digiwin.smartdepott100.module.adapter.stock.storeallot;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
/**
 * @des      无来源调拨
 * @author  xiemeng
 * @date    2017/3/9
 */
public class StoreAllotSumAdapter extends BaseRecyclerAdapter<SumShowBean> {


    public StoreAllotSumAdapter(Context ctx, List<SumShowBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_storeallot;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, SumShowBean item) {
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_scaned_numb, StringUtils.deleteZero(item.getScan_sumqty()));
        holder.setText(R.id.tv_unit, item.getUnit_no());
    }
}
