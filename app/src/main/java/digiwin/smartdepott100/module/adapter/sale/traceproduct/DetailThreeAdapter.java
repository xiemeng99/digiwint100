package digiwin.smartdepott100.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;

/**
 * @author maoheng
 * @des 产品质量追溯
 * @date 2017/4/6
 */

public class DetailThreeAdapter extends BaseRecyclerAdapter<TraceProductDetailBean> {

    public DetailThreeAdapter(Context ctx, List<TraceProductDetailBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_detail_three;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, TraceProductDetailBean item) {
        holder.setText(R.id.tv_bad_no,item.getDefect_reason());
        holder.setText(R.id.tv_bad_string,item.getDefect_reason_name());
        holder.setText(R.id.tv_bad_qty,StringUtils.deleteZero(item.getDefect_reason_qty()));
        holder.setText(R.id.tv_employee,item.getEmployee_name());
    }
}
