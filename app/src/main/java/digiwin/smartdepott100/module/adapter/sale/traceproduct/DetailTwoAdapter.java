package digiwin.smartdepott100.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;

/**
 * @author maoheng
 * @des 产品质量追溯
 * @date 2017/4/6
 */

public class DetailTwoAdapter extends BaseRecyclerAdapter<TraceProductDetailBean> {

    public DetailTwoAdapter(Context ctx, List<TraceProductDetailBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_detail_two;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, TraceProductDetailBean item) {
        holder.setText(R.id.tv_employee_no,item.getEmployee_no());
        holder.setText(R.id.tv_employee_name,item.getEmployee_name());
        holder.setText(R.id.tv_department, item.getDepartment_name());
    }
}
