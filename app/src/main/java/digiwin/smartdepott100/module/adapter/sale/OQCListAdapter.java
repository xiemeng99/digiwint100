package digiwin.smartdepott100.module.adapter.sale;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;

/**
 * @author xiemeng
 * @des oqc带检验列表
 * @date 2017/10/3 11:37
 */

public class OQCListAdapter extends BaseRecyclerAdapter<QCScanData> {
    public OQCListAdapter(Context ctx, List<QCScanData> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_oqc_inspect;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, QCScanData item) {
        holder.setText(R.id.tv_delivery_note_no,item.getSource_no());
        holder.setText(R.id.tv_purchase_order,item.getRefer_no());
        holder.setText(R.id.tv_check_no,item.getQc_no());
        holder.setText(R.id.tv_supplier_no,item.getCustomer_no()+"  "+item.getCustomer_name());
        holder.setText(R.id.tv_create_date,item.getCreate_date());
        holder.setText(R.id.tv_material_date,item.getReceipt_date());
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_model,item.getItem_spec());
        holder.setText(R.id.tv_delivery_num,item.getQty());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_wait_min,item.getWait_min());
    }
}
