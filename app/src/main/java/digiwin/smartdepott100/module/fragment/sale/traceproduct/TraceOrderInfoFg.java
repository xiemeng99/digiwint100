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
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.sale.tranceproduct.TraceProductActivity;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.OrderInfoBean;
import digiwin.smartdepott100.module.logic.sale.traceproduct.TraceProductLogic;

/**
 * @author maoheng
 * @des 产品质量追溯 工单信息
 * @date 2017/4/6
 */

public class TraceOrderInfoFg extends BaseFragment {
//    @BindView(R.id.tv_item_no)
//    TextView tvItemNo;
//    @BindView(R.id.tv_item_name)
//    TextView tvItemName;
//    @BindView(R.id.tv_model)
//    TextView tvModel;
    @BindView(R.id.tv_gongDan_no)
    TextView tvGongDanNo;
    @BindView(R.id.tv_make_department)
    TextView tvMakeDepartment;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_product_draw_no)
    TextView tvProductDrawNo;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_production)
    TextView tvProduction;
    @BindView(R.id.tv_craft_route)
    TextView tvCraftRoute;
    @BindView(R.id.rc_list)
    RecyclerView rcList;

    private TraceProductLogic productLogic;

    private TraceProductActivity tactivity;

    private OrderInfoAdapter adapter;

    private List<OrderInfoBean> list;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_trace_order_info;
    }

    @Override
    protected void doBusiness() {
        tactivity = (TraceProductActivity) activity;
        productLogic = TraceProductLogic.getInstance(tactivity,tactivity.module,tactivity.mTimestamp.toString());
        LinearLayoutManager manager = new LinearLayoutManager(tactivity);
        rcList.setLayoutManager(manager);
        list = new ArrayList<>();
    }
    public void initData(){
        ScanBarcodeBackBean backBean = TraceProductActivity.backBean;
        if(null != backBean){
            upDateList();
        }
    }
    public void upDateList(){
        list.clear();
        adapter = new OrderInfoAdapter(tactivity,list);
        rcList.setAdapter(adapter);
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.BARCODE_NO,TraceProductActivity.backBean.getBarcode_no());
        map.put(AddressContants.ITEM_NO,TraceProductActivity.backBean.getItem_no());
        productLogic.orderInfoGet(map, new TraceProductLogic.OrderInfoGetListener() {
            @Override
            public void onSuccess(List<OrderInfoBean> datas) {
                dismissLoadingDialog();
                list = datas;
                adapter = new OrderInfoAdapter(tactivity,list);
                rcList.setAdapter(adapter);
                OrderInfoBean bean = datas.get(prePosition);
                upDateText(bean);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    public static int prePosition = 0;

    /**
     * 适配器
     */
    class OrderInfoAdapter extends BaseRecyclerAdapter<OrderInfoBean> {

        public OrderInfoAdapter(Context ctx, List<OrderInfoBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_trace_order_info;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder, final int position, OrderInfoBean item) {
            holder.setText(R.id.tv_item_no,item.getItem_no());
            holder.setText(R.id.tv_item_name,item.getItem_name());
            holder.setText(R.id.tv_item_spec,item.getItem_spec());
            holder.setText(R.id.tv_unit,item.getUnit_no());
            holder.setText(R.id.tv_biaozhun_use, StringUtils.deleteZero(item.getStandard_qpa()));
            holder.setText(R.id.tv_relatity_use,StringUtils.deleteZero(item.getActual_qpa()));
            holder.setText(R.id.tv_is_should,StringUtils.deleteZero(item.getRecommended_qty()));
            holder.setText(R.id.tv_relatity,StringUtils.deleteZero(item.getQty()));

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
                    upDateText(list.get(position));
                    notifyDataSetChanged();
                    view.setBackgroundColor(tactivity.getResources().getColor(R.color.gray_f0));
                }
            });
        }
    }

    /**
     * 更新TextView
     * @param bean
     */
    private void upDateText(OrderInfoBean bean){
        tvGongDanNo.setText(bean.getWo_no());
        tvCraftRoute.setText(bean.getWo_process());
        tvMakeDepartment.setText(bean.getDepartment_name());
        tvVersion.setText(bean.getWo_ver());
        tvOrderNo.setText(bean.getCustomer_po_no());
        tvProductDrawNo.setText(bean.getDrawing_no());
        tvProduction.setText(StringUtils.deleteZero(bean.getWo_qty()));
    }

}
