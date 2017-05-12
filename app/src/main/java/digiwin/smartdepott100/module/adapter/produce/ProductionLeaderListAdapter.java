package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author 赵浩然
 * @module 生产超领 清单列表 适配器
 * @date 2017/3/30
 */

public class ProductionLeaderListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public ProductionLeaderListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_productionleaderlist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FilterResultOrderBean item) {
        holder.setText(R.id.tv_super_number, item.getDoc_no());
        holder.setText(R.id.tv_date,item.getCreate_date());
        holder.setText(R.id.tv_department, item.getDepartment_name());
        holder.setText(R.id.tv_applicant, item.getEmployee_name());
    }
}
