package digiwin.smartdepott100.module.adapter.stock.printlable;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.stock.PrintLabelFlowBean;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * sunchangquan
 * 流转标签打印adapter
 * 2017/5/28
 */
public class PrintLableFlowAdapter extends BaseRecyclerAdapter<PrintLabelFlowBean> {
    private static final String TAG = "OrderDailyWorkPeopleAdapter";
    List<PrintLabelFlowBean> list;
    List<PrintLabelFlowBean> seletorList = new ArrayList<>();
    List<Integer> seletorPosition = new ArrayList<>();

    public PrintLableFlowAdapter(Context ctx, List<PrintLabelFlowBean> list) {
        super(ctx, list);
        this.list=list;
    }

    public void addData(PrintLabelFlowBean bean){
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

    public List<PrintLabelFlowBean> getSeletor(){
        return seletorList;
    }
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_printlable_flow_scan;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final PrintLabelFlowBean item) {
        //物料条码
        holder.setText(R.id.tv_material, item.getItem_no());
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
