package digiwin.smartdepott100.module.adapter.board;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.module.bean.board.TctsBoardBean;

/**
 * @des      检验完成待入库看板
 * @author  xiemeng
 * @date    2017/3/9
 */

public class TctsBoardAdapter extends BaseRecyclerAdapter<TctsBoardBean> {
    public TctsBoardAdapter(Context ctx, List<TctsBoardBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_tctsboard;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, TctsBoardBean item) {
            holder.setText(R.id.tv_delivery_date,item.getReceipt_date());
            holder.setText(R.id.tv_item_no,item.getItem_no());
            holder.setText(R.id.tv_item_name,item.getItem_name());
            holder.setText(R.id.tv_statu,item.getStatu());
            holder.setText(R.id.tv_supplier,item.getSupplier_name());
            holder.setText(R.id.tv_waithour,item.getHours());
        if (AddressContants.Y.equals(item.getRush())){
            holder.setTextColor(R.id.tv_delivery_date,mContext.getResources().getColor(R.color.RED));
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor(R.color.RED));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor(R.color.RED));
            holder.setTextColor(R.id.tv_statu,mContext.getResources().getColor(R.color.RED));
            holder.setTextColor(R.id.tv_supplier,mContext.getResources().getColor(R.color.RED));
            holder.setTextColor(R.id.tv_waithour,mContext.getResources().getColor(R.color.RED));
        }else{
            holder.setTextColor(R.id.tv_delivery_date,mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_statu,mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_supplier,mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_waithour,mContext.getResources().getColor(R.color.Base_color));
        }


    }
}
