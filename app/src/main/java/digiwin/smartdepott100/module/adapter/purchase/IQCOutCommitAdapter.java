package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @des iqc委外提交
 * @date 2017/5/30 10:19
 */

public class IQCOutCommitAdapter extends BaseRecyclerAdapter<ListSumBean> {
    private Map<Integer, Boolean> mFlagMap;
    public IQCOutCommitAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
        mFlagMap=new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if ("1".equals(list.get(i).getResult_type())) {
                mFlagMap.put(i, true);
            } else {
                mFlagMap.put(i, false);
            }
        }
    }

    @Override
    protected int getItemLayout(int i) {
        return R.layout.ryitem_iqcoutcommit_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder,final int i, final ListSumBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_model, item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_circulation_no, item.getPlot_no());
        holder.setText(R.id.tv_number, StringUtils.deleteZero(item.getReceipt_qty()));
        final CheckBox cbOK = (CheckBox) holder.getView(R.id.cb_OK);
        final CheckBox cbNG = (CheckBox) holder.getView(R.id.cb_seletor_NG);
        cbOK.setChecked(mFlagMap.get(i));
        cbNG.setChecked(!mFlagMap.get(i));
        cbOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlagMap.put(i, cbOK.isChecked());
                cbOK.setChecked(mFlagMap.get(i));
                cbNG.setChecked(!mFlagMap.get(i));
            }
        });
        cbNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlagMap.put(i, !cbNG.isChecked());
                cbOK.setChecked(mFlagMap.get(i));
                cbNG.setChecked(!mFlagMap.get(i));
            }
        });

    }

    public Map<Integer, Boolean> getmFlagMap() {
        return mFlagMap;
    }

    public void setmFlagMap(Map<Integer, Boolean> mFlagMap) {
        this.mFlagMap = mFlagMap;
    }
}
