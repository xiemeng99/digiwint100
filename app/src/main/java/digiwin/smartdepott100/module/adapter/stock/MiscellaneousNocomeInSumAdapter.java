package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

/**
 * @des      杂项发料 汇总adapter
 * @author  唐孟宇
 * @date    2017/3/02
 */

public class MiscellaneousNocomeInSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public
    MiscellaneousNocomeInSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_miscellaneousnocome_in_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_model,item.getItem_spec());
        holder.setText(R.id.tv_item_danwei,item.getUnit_no());
        holder.setText(R.id.tv_product_code,item.getProduct_no());
       // holder.setText(R.id.tv_stock_qty, StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getScan_sumqty()));
    }
}
