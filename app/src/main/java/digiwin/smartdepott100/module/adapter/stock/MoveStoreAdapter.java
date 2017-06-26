package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;

/**
 * @des      条码移库
 * @author  xiemeng
 * @date    2017/3/23
 */
public class MoveStoreAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public MoveStoreAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_movestore;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float num1=StringUtils.string2Float(item.getApply_qty());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_unit, item.getUnit_no());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_allot_num, StringUtils.deleteZero(String.valueOf(num1)));


    }
}
