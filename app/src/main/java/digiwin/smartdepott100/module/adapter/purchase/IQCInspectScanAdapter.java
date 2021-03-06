package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;

/**
 * Created by maoheng on 2017/8/11.
 */

public class IQCInspectScanAdapter extends BaseRecyclerAdapter<QCScanData>{
    public IQCInspectScanAdapter(Context ctx, List<QCScanData> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_iqc_inspect;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, QCScanData item) {
        holder.setText(R.id.tv_delivery_note_no,item.getDelivery_bill_no());
        holder.setText(R.id.tv_purchase_order,item.getReceipt_no());
        holder.setText(R.id.tv_check_no,item.getQc_no());
        holder.setText(R.id.tv_supplier_no,item.getSupplier_no()+"  "+item.getSupplier_name());
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
