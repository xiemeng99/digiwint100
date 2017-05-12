package digiwin.smartdepott100.module.adapter.stock.store;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des     库存交易解锁适配器
 * @author  maoheng
 * @date    2017/3/4
 */

public class StoreTransUnLockAdapter extends BaseDetailRecyclerAdapter<FilterResultOrderBean> {

    public StoreTransUnLockAdapter(Context ctx, List list) {
        super(ctx, list);
    }

    @Override
    public CheckBox getPCheckBox() {
        return null;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_storetransunlock;
    }

    @Override protected void bindData(final RecyclerViewHolder holder, final int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_lock_no, item.getDoc_no());
        holder.setText(R.id.tv_lock_date, item.getCreate_date());
        holder.setText(R.id.tv_lock_people, item.getEmployee_name());
        holder.setText(R.id.tv_lock_reason, item.getLock_reason());
        map.put(position,false);
        CheckBox cb = (CheckBox) holder.getView(R.id.cb_ischoose);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position,isChecked);
            }
        });
        if (null != map.get(position)) {
            cb.setChecked(map.get(position));
        }else{
            cb.setChecked(false);
        }
    }
}
