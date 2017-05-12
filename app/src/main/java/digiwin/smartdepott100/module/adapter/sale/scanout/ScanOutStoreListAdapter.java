package digiwin.smartdepott100.module.adapter.sale.scanout;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutStoreListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean>{
    public ScanOutStoreListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_scanoutstore;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_shipping_order, item.getDoc_no());
        holder.setText(R.id.tv_date, item.getCreate_date());
        holder.setText(R.id.tv_custom, item.getCustomer_name());
        holder.setText(R.id.tv_operating_department, item.getDepartment_name());
        holder.setText(R.id.tv_salesman, item.getEmployee_name());
    }
}
