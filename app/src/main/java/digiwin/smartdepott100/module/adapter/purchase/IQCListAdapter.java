package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @des iqc列表
 * @date 2017/5/30 09:43
 */

public class IQCListAdapter   extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public IQCListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_iqc_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_check_no, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_check_employee,item.getEmployee_name());
        holder.setText(R.id.tv_check_depart,item.getDepartment_name());
    }
}
