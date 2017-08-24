package digiwin.smartdepott100.module.adapter.stock;


import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;

/**
 * @des    产品装箱明细
 * @author  孙长权
 */
public abstract class ProductBinningDetailAdapter extends BaseDetailRecyclerAdapter<ProductBinningBean> {
    private static final String TAG = "ProductBinningDetailAdapter";

    public ProductBinningDetailAdapter(Context ctx, List<ProductBinningBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_productbinning_detail;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final ProductBinningBean item) {
        //项次
        holder.setText(R.id.tv_item_seq, item.getSeq());
        //数量
        holder.setText(R.id.tv_number, StringUtils.deleteZero(item.getItem_qty()));
        //条码
        holder.setText(R.id.tv_product_no, item.getBarcode_no());
        //品名
        holder.setText(R.id.tv_item_name,item.getItem_name());
        //料号
      //  holder.setText(R.id.tv_item_no,item.getItem_no());

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

}