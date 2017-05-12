package digiwin.smartdepott100.module.test.test_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public class TestTwoAdapter extends RecyclerView.Adapter<TestTwoAdapter.TestTwoViewHolder>{

    private List<TestTwoBean> list;
    private Context context;
    private LayoutInflater inflater;

    public TestTwoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<TestTwoBean> list) {
        this.list = list;
    }

    @Override
    public TestTwoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.testtwo_layout, null);
        TestTwoViewHolder holder = new TestTwoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestTwoViewHolder holder, int position) {
        TestTwoBean testTwoBean = list.get(position);
        if(null != testTwoBean){
//            if(position == list.size()-1){
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0,0,0,170);
//                holder.mzx_item_ll.setLayoutParams(layoutParams);
//            }else {
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0,0,0,0);
//                holder.mzx_item_ll.setLayoutParams(layoutParams);
//            }

            holder.mzx_liaohao_value.setText(testTwoBean.materialNo);
            holder.mzx_pinming_value.setText(testTwoBean.productName);
            holder.mzx_pipeiliang_value.setText(testTwoBean.matchingNo);
            holder.mzx_rukuliang_value.setText(testTwoBean.inWarehouse);
            int num = StringUtils.parseInt(testTwoBean.inWarehouse);
            int num1 = StringUtils.parseInt(testTwoBean.matchingNo);
            if(num1 == 0){
                //匹配量为0
                holder.mzx_item_ll.setBackgroundResource(R.drawable.red_scandetail_bg);
                holder.mzx_liaohao_value.setTextColor(context.getResources().getColor(R.color.red));
                holder.mzx_pinming_value.setTextColor(context.getResources().getColor(R.color.red));
                holder.mzx_pipeiliang_value.setTextColor(context.getResources().getColor(R.color.red));
                holder.mzx_rukuliang_value.setTextColor(context.getResources().getColor(R.color.red));
            }else if(num1<num){
                //匹配量小于入库量
                holder.mzx_item_ll.setBackgroundResource(R.drawable.yellow_scandetail_bg);
                holder.mzx_liaohao_value.setTextColor(context.getResources().getColor(R.color.outside_yellow));
                holder.mzx_pinming_value.setTextColor(context.getResources().getColor(R.color.outside_yellow));
                holder.mzx_pipeiliang_value.setTextColor(context.getResources().getColor(R.color.outside_yellow));
                holder.mzx_rukuliang_value.setTextColor(context.getResources().getColor(R.color.outside_yellow));
            }else {
                //匹配量等于入库量
                holder.mzx_item_ll.setBackgroundResource(R.drawable.green_scandetail_bg);
                holder.mzx_liaohao_value.setTextColor(context.getResources().getColor(R.color.Base_color));
                holder.mzx_pinming_value.setTextColor(context.getResources().getColor(R.color.Base_color));
                holder.mzx_pipeiliang_value.setTextColor(context.getResources().getColor(R.color.Base_color));
                holder.mzx_rukuliang_value.setTextColor(context.getResources().getColor(R.color.Base_color));
            }
        }
    }

    @Override
    public int getItemCount() {
        if(null != list){
            return list.size();
        }else {
            return 0;
        }
    }

    class TestTwoViewHolder extends RecyclerView.ViewHolder{

        /**
         * 料号
         */
        @BindView(R.id.mzx_liaohao)
        TextView mzx_liaohao;
        @BindView(R.id.mzx_liaohao_value)
        TextView mzx_liaohao_value;
        /**
         * 品名
         */
        @BindView(R.id.mzx_pinming)
        TextView mzx_pinming;
        @BindView(R.id.mzx_pinming_value)
        TextView mzx_pinming_value;
        /**
         * 入库量
         */
        @BindView(R.id.mzx_rukuliang)
        TextView mzx_rukuliang;
        @BindView(R.id.mzx_rukuliang_value)
        TextView mzx_rukuliang_value;
        /**
         * 匹配量
         */
        @BindView(R.id.mzx_pipeiliang)
        TextView mzx_pipeiliang;
        @BindView(R.id.mzx_pipeiliang_value)
        TextView mzx_pipeiliang_value;

        /**
         * 背景颜色
         */
        @BindView(R.id.mzx_item_ll)
        LinearLayout mzx_item_ll;
        public TestTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
