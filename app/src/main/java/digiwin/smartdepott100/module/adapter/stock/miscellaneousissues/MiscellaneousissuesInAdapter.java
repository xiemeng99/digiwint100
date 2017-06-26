package digiwin.smartdepott100.module.adapter.stock.miscellaneousissues;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * 杂项收料  清单adapter
 * Created by wangyu on 2017/6/5.
 */

public class MiscellaneousissuesInAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {
    public MiscellaneousissuesInAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int i) {
        return R.layout.ryitem_miscellaneousissues_in_order;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int i, FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_miscellaneous_in_no, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_item_person,item.getEmployee_name());
        holder.setText(R.id.tv_item_department,item.getDepartment_name());
    }
}
