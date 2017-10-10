package digiwin.smartdepott100.module.adapter.produce;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.activity.produce.accordingmaterial.AccordingMaterialActivity;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

/**
 * @author 赵浩然
 * @module 依成品发料 汇总数据
 * @date 2017/3/2
 */

public class AccordingMaterialSumAdapter extends BaseRecyclerAdapter<ListSumBean>{

    public AccordingMaterialSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_accodingmaterial;
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        final float numb1 = StringUtils.string2Float(item.getApply_qty());
        final float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        final float numb3 = StringUtils.string2Float(item.getStock_qty());
        holder.setText(R.id.tv_item_no, item.getLow_order_item_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name, item.getLow_order_item_name());
        holder.setText(R.id.tv_item_format, item.getLow_order_item_spec());
        holder.setText(R.id.tv_material_return, StringUtils.deleteZero(item.getApply_qty()));
        holder.setText(R.id.tv_feeding_amount, StringUtils.deleteZero(item.getScan_sumqty()));
        holder.setText(R.id.tv_material_return_big, StringUtils.deleteZero(item.getStock_qty()));

        //跳转到明细
        holder.setClickListener(R.id.img_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumShowBean bean = new SumShowBean();
                bean.setItem_no(item.getLow_order_item_no());
                bean.setItem_name(item.getLow_order_item_name());
                bean.setAvailable_in_qty(item.getApply_qty());
                AccordingMaterialActivity activity = (AccordingMaterialActivity) mContext;
                activity.ToDetailAct(bean);
            }
        });

        TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});

        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_unit, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_item_format, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_material_return, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_material_return_big, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
            holder.setTextColor(R.id.tv_feeding_amount, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_unit,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_name,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_item_format, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_material_return,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_material_return_big, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
            holder.setTextColor(R.id.tv_feeding_amount,a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_unit,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_name,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_item_format, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_material_return,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_material_return_big, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
            holder.setTextColor(R.id.tv_feeding_amount,a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
        }
    }
}
