package digiwin.smartdepott100.module.fragment.sale.traceproduct;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.sale.tranceproduct.TraceProductActivity;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.ProcuctProcessBean;
import digiwin.smartdepott100.module.logic.sale.traceproduct.TraceProductLogic;

/**
 * @author maoheng
 * @des 产品质量追溯 生产过程
 * @date 2017/4/6
 */

public class TraceProductionProcessFg extends BaseFragment {

    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.rc_list)
    RecyclerView rcList;

    private TraceProductLogic productLogic;

    private TraceProductActivity tactivity;

    private ProductProcessAdapter adapter;

    private List<ProcuctProcessBean> list;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_trace_product_process;
    }

    @Override
    protected void doBusiness() {
        prePosition = -1;
        tactivity = (TraceProductActivity) activity;
        productLogic = TraceProductLogic.getInstance(tactivity,tactivity.module,tactivity.mTimestamp.toString());
        LinearLayoutManager manager = new LinearLayoutManager(tactivity);
        rcList.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ProductProcessAdapter(tactivity,list);
    }

    public void initData(){
        ScanBarcodeBackBean backBean = TraceProductActivity.backBean;
        if(null != backBean){
            tvItemNo.setText(backBean.getItem_no());
            tvItemName.setText(backBean.getItem_name());
            tvModel.setText(backBean.getItem_spec());
            prePosition = -1;
            upDateList();
        }
    }

    public void upDateList(){
        list.clear();
        adapter = new ProductProcessAdapter(tactivity,list);
        rcList.setAdapter(adapter);
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.BARCODE_NO,TraceProductActivity.backBean.getBarcode_no());
        map.put(AddressContants.ITEM_NO,TraceProductActivity.backBean.getItem_no());
        productLogic.productProcessGet(map, new TraceProductLogic.ProductProcessGetListener() {
            @Override
            public void onSuccess(List<ProcuctProcessBean> datas) {
                dismissLoadingDialog();
                list = datas;
                adapter = new ProductProcessAdapter(tactivity,list);
                rcList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    private ProcuctProcessBean selectedData;

    public ProcuctProcessBean getSelectedData() {
        if (null == selectedData){
            selectedData = new ProcuctProcessBean();
        }
        return selectedData;
    }

    public static int prePosition = -1;

    class ProductProcessAdapter extends BaseRecyclerAdapter<ProcuctProcessBean> {

        public ProductProcessAdapter(Context ctx, List<ProcuctProcessBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_trace_product_process;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder, final int position, ProcuctProcessBean item) {
            holder.setText(R.id.tv_order_no,item.getWo_no());
            holder.setText(R.id.tv_card_no,item.getProcess_card());
            holder.setText(R.id.tv_time,item.getReport_datetime());
            holder.setText(R.id.tv_line_no,item.getLine_no());
            holder.setText(R.id.tv_gongxu,item.getSubop_no());
            holder.setText(R.id.tv_device,item.getMachine_no());
            holder.setText(R.id.tv_is_feeding,item.getItem_in_status());
            holder.setText(R.id.tv_employee_group,item.getWorkgroup_in_status());
            holder.setText(R.id.tv_is_check,item.getQc_check_status());

            View view = holder.getView(R.id.item_ll);

            if(position == prePosition){
                view.setBackgroundColor(tactivity.getResources().getColor(R.color.gray_f0));
            }else {
                view.setBackground(null);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prePosition = position;
                    selectedData = list.get(position);
                    notifyDataSetChanged();
                    view.setBackgroundColor(tactivity.getResources().getColor(R.color.gray_f0));
                }
            });
        }
    }
}
