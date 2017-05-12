package digiwin.smartdepott100.module.adapter.stock.storeallot;


import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;

/**
 * @des    无来源调拨
 * @author  xiemeng
 * @date    2017/3/10
 */
public class StoreAllotDetailAdapter extends BaseDetailRecyclerAdapter<DetailShowBean> {
    private static final String TAG = "StoreAllotDetailAdapter";

    public StoreAllotDetailAdapter(Context ctx, List<DetailShowBean> list) {
        super(ctx, list);
    }
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_storeallot_detail;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final DetailShowBean item) {
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_outlocator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_inlocator, item.getStorage_spaces_in_no());
        holder.setText(R.id.tv_number, StringUtils.deleteZero(item.getBarcode_qty()));
        holder.setClickListener(R.id.tv_number, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=listener){
                    listener.update(item,position,holder);
                }
            }
        });
        CheckBox cb = (CheckBox) holder.getView(R.id.cb_ischoose);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position,isChecked);
                isCheckAll();
            }
        });
        if (null != map.get(position)) {
            cb.setChecked(map.get(position));
        }else{
            cb.setChecked(false);
        }

    }
    @Override
    public CheckBox getPCheckBox() {
        CommonDetailActivity pactivity = (CommonDetailActivity) mContext;
        return pactivity.cbAll;
    }
}