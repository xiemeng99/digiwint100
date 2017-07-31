package digiwin.smartdepott100.module.adapter.stock;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @author 孙长权
 * @module 库存盘点列表
 * @date 2017/4/18
 */

public class StockCheckListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public StockCheckListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_stockchecklist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_check_order, item.getDoc_no());//盘点单号
        holder.setText(R.id.tv_plan_data, item.getCreate_date());//计划日期
        holder.setText(R.id.tv_chcek_warehouse,item.getWarehouse_no());//盘点仓库
        holder.setText(R.id.tv_person, item.getEmployee_name());//人员
        holder.setText(R.id.tv_remark, item.getRemark());//备注

    }
}
