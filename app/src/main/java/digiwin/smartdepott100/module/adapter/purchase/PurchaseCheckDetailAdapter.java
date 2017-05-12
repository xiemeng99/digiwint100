package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckDetailBean;

/**
 * @des     收获检验 IQC adapter
 * @author  唐孟宇
 */

public class PurchaseCheckDetailAdapter extends BaseRecyclerAdapter<PurchaseCheckDetailBean> {

    public PurchaseCheckDetailAdapter(Context ctx, List<PurchaseCheckDetailBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_check_form_detail;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final PurchaseCheckDetailBean item) {
        EditText et_defect_num = holder.getEditText(R.id.et_defect_num);
        et_defect_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.setText(R.id.tv_item_seq, item.getSeq());
        holder.setText(R.id.tv_check_item,item.getInspection_item());
        holder.setText(R.id.tv_aql,item.getAql());
        holder.setText(R.id.tv_select_check_num,item.getSample_qty());
        holder.setText(R.id.tv_ac,item.getAc_qty());
        holder.setText(R.id.tv_re,item.getRe_qty());
        holder.setText(R.id.tv_project_check,item.getItem_deter());
    }
}
