package digiwin.smartdepott100.module.adapter.purchase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.adapter.ExpandNoCheckBoxAdapter;
import digiwin.smartdepott100.module.bean.stock.PQCCheckOutBean;

/**
 * Created by qGod on 2017/6/5
 * Thank you for watching my code
 */

public class PQCCheckOutExpandableAdapter extends ExpandNoCheckBoxAdapter<String,PQCCheckOutBean> {
    private Context context;

    public PQCCheckOutExpandableAdapter(Context context,LinkedHashMap<String, List<PQCCheckOutBean>> data) {
        super(data);
        this.context=context;
    }

    @Override
    public View setChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PQCCheckOutBean childBean= (PQCCheckOutBean) getChild(groupPosition,childPosition);
        View view=null;
        if (isLastChild){
            view= LayoutInflater.from(context).inflate(R.layout.epitem_pqccheckout_child_last, null);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.epitem_pqccheckout_child_no_last, null);
        }
        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.ll_child);
        if(isLastChild){
            linearLayout.setBackgroundResource(R.drawable.red_child_last_expand_bg);
        }else{
            linearLayout.setBackgroundResource(R.drawable.red_child_expand_bg);
        }
        //报工日期
        ((TextView)view.findViewById(R.id.tv_worktime)).setText(childBean.getReport_date());
        //工单单号
        ((TextView)view.findViewById(R.id.tv_gongDan_no)).setText(childBean.getWo_no());
        //报工单号
        ((TextView)view.findViewById(R.id.tv_baogong_order)).setText(childBean.getReport_no());
        //品名
        ((TextView)view.findViewById(R.id.tv_item_name)).setText(childBean.getItem_name());
        //料号
        ((TextView)view.findViewById(R.id.tv_item_no)).setText(childBean.getItem_no());
        //物料批号
        ((TextView)view.findViewById(R.id.tv_circulation_no)).setText(childBean.getPlot_no());
        //工序
        ((TextView)view.findViewById(R.id.tv_pallet)).setText(childBean.getSubop_no());
        //数量
        ((TextView)view.findViewById(R.id.tv_num)).setText(childBean.getUndefect_qty());

        return view;
    }

    @Override
    public View setGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String qcType= (String) getGroup(groupPosition);
        View view=null;
        if (isExpanded){
            view= LayoutInflater.from(context).inflate(R.layout.epitem_pqccheckout_header_expand_list, null);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.epitem_pqccheckout_header_unexpand_list, null);
        }
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_jiantou);
        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.ll_group);
        if(isExpanded){
            imageView.setImageResource(R.drawable.jt_down);
            linearLayout.setBackgroundResource(R.drawable.red_group_expand_bg);
        }else{
            imageView.setImageResource(R.drawable.jt_up);
            linearLayout.setBackgroundResource(R.drawable.red_group_unexpand_bg);
        }
        //检验类型
        ((TextView)view.findViewById(R.id.tv_check_mold)).setText(qcType);
        //笔数
        ((TextView)view.findViewById(R.id.tv_number)).setText(String.valueOf(parentList.size()));

        return view;
    }

    @Override
    public long setGroupId(int arg0) {
        return arg0;
    }

    @Override
    public boolean setStableIds() {
        return false;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
}
