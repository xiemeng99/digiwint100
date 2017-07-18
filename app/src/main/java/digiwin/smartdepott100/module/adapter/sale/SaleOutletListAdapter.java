package digiwin.smartdepott100.module.adapter.sale;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des    销售出库
 * @author  xiemeng
 * @date    2017/3/19
 */
public class SaleOutletListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public SaleOutletListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_saleoutletlist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_general_number, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_item_custom,item.getCustomer_name());
//        holder.setText(R.id.tv_item_applicant,item.getEmployee_name());
//        holder.setText(R.id.tv_item_department,item.getDepartment_name());
    }
}
