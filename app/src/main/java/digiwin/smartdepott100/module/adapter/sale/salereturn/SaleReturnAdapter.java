package digiwin.smartdepott100.module.adapter.sale.salereturn;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des      销售退货 汇总adapter
 * @author  唐孟宇
 * @date    2017/3/09
 */

public class SaleReturnAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public SaleReturnAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_sale_return;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_return_order_no, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_item_custom,item.getCustomer_name());
        holder.setText(R.id.tv_apply_branch, item.getDepartment_name());
        holder.setText(R.id.tv_applicant, item.getEmployee_name());
    }
}
