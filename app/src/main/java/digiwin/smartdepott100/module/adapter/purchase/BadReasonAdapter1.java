package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;

/**
 * @des     不良原因 adapter
 * @author  唐孟宇
 */

public class BadReasonAdapter1 extends BaseRecyclerAdapter<BadReasonBean> {

    RecyclerViewHolder viewHolder = null;

    public BadReasonAdapter1(Context ctx, List<BadReasonBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_bad_reason;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final BadReasonBean item) {
        viewHolder = holder;
        EditText et_bad_num = holder.getEditText(R.id.tv_bad_num);
        holder.setVisibility(R.id.tv_bad_num, View.GONE);
        holder.setText(R.id.tv_detail_reason, item.getDefect_reason_name());
    }

    public RecyclerViewHolder getHolder(){
        if(null != viewHolder){
            return viewHolder;
        }else{
            return null;
        }
    }



}
