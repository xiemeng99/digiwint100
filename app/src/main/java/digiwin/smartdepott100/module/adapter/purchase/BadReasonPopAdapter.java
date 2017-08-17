package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;

/**
 * Created by maoheng on 2017/8/15.
 */

public class BadReasonPopAdapter extends BaseRecyclerAdapter<BadReasonBean>{

    public BadReasonPopAdapter(Context ctx, List<BadReasonBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_badresonpop;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, BadReasonBean item) {
        holder.setText(R.id.tv_badReason,item.getDefect_reason_name());
    }
}
