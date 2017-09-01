package digiwin.smartdepott100.module.adapter.produce;

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
 * @des  完工入库明细
 * @date 2017/8/29
 * @author xiemeng
 */
public class WipStorageDetailAdapter extends BaseDetailRecyclerAdapter<DetailShowBean> {
    private static final String TAG = "WipStorageDetailAdapter";

    private List<DetailShowBean> detailList;
    public WipStorageDetailAdapter(Context ctx, List<DetailShowBean> list) {
        super(ctx, list);
        detailList = list;
    }
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_wipstorage_detail;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final DetailShowBean item) {
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
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
        if(position == detailList.size()-1){
            holder.setVisibility(R.id.bottom_line,View.GONE);
        }

    }
    @Override
    public  CheckBox getPCheckBox() {
        CommonDetailActivity pactivity = (CommonDetailActivity) mContext;
        return pactivity.cbAll;
    }
}
