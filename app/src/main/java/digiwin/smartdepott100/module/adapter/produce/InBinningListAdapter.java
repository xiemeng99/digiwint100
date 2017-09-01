package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 孙长权
 * @module 装箱入库列表
 * @date 2017/3/30
 */

public class InBinningListAdapter extends BaseRecyclerAdapter<ListSumBean> {
    /**
     * 是否比较（比较数值）
     */
    boolean isEquals;
    public InBinningListAdapter(Context ctx, boolean isEquals, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_inbinninglist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_gongDan_no,item.getDoc_no());
        holder.setText(R.id.tv_data, item.getCreate_date());//日期
        holder.setText(R.id.tv_department, item.getDepartment_name());//部门
        holder.setText(R.id.tv_inwarehouse, StringUtils.deleteZero(item.getAvailable_in_qty()));//可入库
    }
}
