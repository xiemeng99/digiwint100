package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.purchase.RawMaterialPrintBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @des  原材料标签打印
 * @date 2017/6/8  
 * @author xiemeng
 */
public class RawMaterialAdapter extends BaseRecyclerAdapter<RawMaterialPrintBean> {
    private static final String TAG = "RawMaterialAdapter";
    List<RawMaterialPrintBean> list;
    List<RawMaterialPrintBean> seletorList = new ArrayList<>();
    List<Integer> seletorPosition = new ArrayList<>();

    public RawMaterialAdapter(Context ctx, List<RawMaterialPrintBean> list) {
        super(ctx, list);
        this.list=list;
    }

    public void addData(RawMaterialPrintBean bean){
        if(list!=null) {
            list.add(list.size(), bean);
            notifyItemInserted(list.size());
        }
    }
    public void removeData(int position){
        if(list!=null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<RawMaterialPrintBean> getSeletor(){
        return seletorList;
    }
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_rawmaterialprint;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final RawMaterialPrintBean item) {
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_material_num,item.getReceipt_qty());
        holder.setText(R.id.tv_luhao,item.getFurnace_no());
        //物料条码
        holder.setText(R.id.tv_material_number, item.getLot_no());
        final CheckBox cb = (CheckBox) holder.getView(R.id.cb_seletor);
        cb.setTag(position);
        if (seletorPosition != null) {
            cb.setChecked(seletorPosition.contains(position) ? true : false);
        } else {
            cb.setChecked(false);
        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!seletorPosition.contains(cb.getTag())) {
                        seletorPosition.add((Integer) cb.getTag());
                        seletorList.add(item);
                    }
                } else {
                    if (seletorPosition.contains(cb.getTag())) {
                        seletorPosition.remove(cb.getTag());
                        seletorList.remove(item);
                    }
                }
            }
        });

    }

}
