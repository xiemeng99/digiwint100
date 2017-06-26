package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.stock.PQCCheckOutBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author 孙长权
 * @module PQC检验 -- 清单 -- 适配器
 * @date 2017/5/29
 */

public class PQCCheckOutListAdapter extends BaseRecyclerAdapter<PQCCheckOutBean> {

    public PQCCheckOutListAdapter(Context ctx, List<PQCCheckOutBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_pqccheckout_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, PQCCheckOutBean item) {
        holder.setText(R.id.tv_worktime, item.getReport_date());//报工日期
        holder.setText(R.id.tv_gongDan_no,item.getWo_no());//工单单号
        holder.setText(R.id.tv_baogong_order,item.getReport_no());//报工单号
        holder.setText(R.id.tv_item_name, item.getItem_name());//品名
        holder.setText(R.id.tv_item_no, item.getItem_no());//料号
        holder.setText(R.id.tv_circulation_no, item.getPlot_no());//物料批号
        holder.setText(R.id.tv_pallet, item.getSubop_no());//工序
        holder.setText(R.id.tv_num, item.getUndefect_qty());//数量
    }
}
