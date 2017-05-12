package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;


/**
 * @author 唐孟宇
 * @des 采购入库明细Adapter
 * @date 2017/03/09
 */
public class PurchaseInStoreDetailAdapter extends BaseRecyclerAdapter<DetailShowBean> {
    private static final String TAG = "FinishedStorageDetailAd";
    private Map<Integer, Boolean> map;

    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Boolean> map) {
        this.map = map;
    }

    public UpdateNumListener listener;

    public PurchaseInStoreDetailAdapter(Context ctx, List<DetailShowBean> list) {
        super(ctx, list);
    }

    public UpdateNumListener getListener() {
        return listener;
    }

    public void setListener(UpdateNumListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_goods_detail;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final DetailShowBean item) {
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
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


    /**
     * 修改数量
     */
    public interface UpdateNumListener{
        public void update(DetailShowBean item, int pos, RecyclerViewHolder holder);
    }


    /**
     * 是否全选
     */
    private void isCheckAll(){
        Set<Map.Entry<Integer, Boolean>> sets = map.entrySet();
        List<Object> deletelist = new ArrayList<>();
        int i=0;
        for (Map.Entry<Integer, Boolean> entry : sets) {
            Boolean val = entry.getValue();
            if (null != val&&val) {
                i++;
            }
        }
        CommonDetailActivity activity= (CommonDetailActivity) mContext;
        if (i==mItems.size()){
            activity.cbAll.setChecked(true);
        }else{
            activity.cbAll.setChecked(false);
        }

    }


}
