package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckBean;

/**
 * @des     收获检验 主页面 adapter
 * @author  唐孟宇
 */

public class PurchaseCheckAdapter extends BaseRecyclerAdapter<PurchaseCheckBean> {

    RecyclerViewHolder viewHolder = null;

    public PurchaseCheckAdapter(Context ctx, List<PurchaseCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_check_form1;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final PurchaseCheckBean item) {
        viewHolder = holder;
        EditText et_ok_num = holder.getEditText(R.id.et_ok_num);
        if(!item.getQc_state().equals("N")){
            et_ok_num.setOnKeyListener(null);
        }
        holder.setText(R.id.tv_item_seq, item.getSeq());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_model,item.getItem_spec());
        holder.setText(R.id.tv_picture_no,item.getDrawing_no());
        holder.setText(R.id.tv_send_check_num,item.getQty());
        holder.setText(R.id.tv_check_state,item.getQc_state());
    }

    public RecyclerViewHolder getHolder(){
        if(null != viewHolder){
            return viewHolder;
        }else{
            return null;
        }
    }

}
