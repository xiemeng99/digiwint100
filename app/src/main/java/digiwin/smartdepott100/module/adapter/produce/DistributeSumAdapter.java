package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import java.util.List;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.produce.distribute.DistributeActivity;
import digiwin.smartdepott100.module.bean.produce.DistributeSumShowBean;

/**
 * @des      生产完配货汇总adapter
 * @author  唐孟宇
 * @date    2017/3/02
 */

public class DistributeSumAdapter extends BaseRecyclerAdapter<DistributeSumShowBean> {



    public DistributeSumAdapter(Context ctx, List<DistributeSumShowBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_distribute;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final DistributeSumShowBean item) {
        // 欠料量和库存量取值
        float numb3 = StringUtils.string2Float(item.getShortage_qty());
        float numb4 = StringUtils.string2Float(item.getStock_qty());

        float numb1 = StringUtils.string2Float(item.getUncheck_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        LogUtils.d("DistributeSumAdapter.bindData()","numb1 = "+numb1+",numb2 = "+numb2+",numb3 = "+numb3+",numb4 = "+numb4);
        holder.setClickListener(R.id.iv_detail_look, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到明细界面
                DistributeActivity ac= (DistributeActivity)mContext;
                ac.getDetail(item);
    }
});
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_model,item.getItem_spec());
        holder.setText(R.id.tv_material_numb, StringUtils.deleteZero(String.valueOf(numb3)));
        holder.setText(R.id.tv_locator_numb, StringUtils.deleteZero(String.valueOf(numb4)));
        holder.setText(R.id.tv_wait_recheck_numb, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_act_send_numb, StringUtils.deleteZero(String.valueOf(numb2)));

        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_model, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_material_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_locator_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_wait_recheck_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_act_send_numb, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_name,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_model, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_material_numb, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_locator_numb, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_wait_recheck_numb, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_act_send_numb, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_model, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_material_numb, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_locator_numb, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_wait_recheck_numb, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_act_send_numb, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
        }
    }
}
