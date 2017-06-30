package digiwin.smartdepott100.module.fragment.sale.pickupshipment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.sale.pickupshipment.PickUpShipmentActivity;
import digiwin.smartdepott100.module.adapter.sale.pickupshipment.PickUpShipmentSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.pickupshipment.PickUpShipmentLogic;

/**
 * @author 赵浩然
 * @des 捡料出货汇总页
 * @date 2017/3/23
 */
public class PickUpShipmentSumFg extends BaseFragment {

    /**
     * 出货单号
     */
    @BindView(R.id.tv_shipping_order)
    TextView tv_shipping_order;

    /**
     * 申请日期
     */
    @BindView(R.id.tv_apply_date)
    TextView tv_apply_date;

    /**
     * 客户
     */
    @BindView(R.id.tv_custom)
    TextView tv_custom;

    PickUpShipmentSumAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.commit)
    void commit() {
        sureCommit();
    }

    FilterResultOrderBean localData = new FilterResultOrderBean();

    PickUpShipmentActivity pactivity;
    PickUpShipmentLogic logic;

    boolean upDateFlag;

    List<ListSumBean>  sumBeanList;

    @Override
    protected void doBusiness() {
        pactivity = (PickUpShipmentActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        localData = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable("data");
        //获取单号 日期 人员参数
        tv_shipping_order.setText(localData.getDoc_no());
        tv_apply_date.setText(localData.getShipment_date());
        tv_custom.setText(localData.getCustomer_name());

    }

    public void upDateList() {
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put(AddressContants.DOC_NO,localData.getDoc_no());
            map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getUserInfo().getWare());
            logic = PickUpShipmentLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
            showLoadingDialog();
            logic.getSOLSumData(map, new PickUpShipmentLogic.GetSumDataListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    if(list.size() > 0){
                        upDateFlag = true;
                        adapter = new PickUpShipmentSumAdapter(getActivity(),list);
                        ryList.setAdapter(adapter);
                        sumBeanList = new ArrayList<ListSumBean>();
                        sumBeanList = list;
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                getDetail(sumBeanList.get(position));
                            }
                        });
                    }
                }

                @Override
                public void onFailed(String errmsg) {
                    dismissLoadingDialog();
                    showFailedDialog(errmsg, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查看明细
     */
    public void getDetail(final ListSumBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        float numb1 = StringUtils.string2Float(orderSumData.getReq_qty());
        float numb2 = StringUtils.string2Float(orderSumData.getStock_qty());
        if(numb1 > numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getStock_qty());
        }else if(numb1 < numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getReq_qty());
        }else if(numb1 == numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getReq_qty());
        }

        logic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    private void sureCommit(){
        if (!upDateFlag){
            showFailedDialog(R.string.nodate);
            return;
        }
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                HashMap<String, String> map = new HashMap<>();
                logic.commit(map, new CommonLogic.CommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                activity.finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showCommitFailDialog(error);
                    }
                });
            }
            @Override
            public void onCallback2() {

            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_pickupshipment_sum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
