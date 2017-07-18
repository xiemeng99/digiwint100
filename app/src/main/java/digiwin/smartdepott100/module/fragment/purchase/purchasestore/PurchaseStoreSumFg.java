package digiwin.smartdepott100.module.fragment.purchase.purchasestore;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.purchase.purchasestore.PurchaseStoreActivity;
import digiwin.smartdepott100.module.adapter.stock.store.StoreReturnMaterialSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.purchase.PurchaseStoreLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

/**
 * sunchangquan
 * 采购仓库退料
 * 2017/5/30
 */

public class PurchaseStoreSumFg extends BaseFragment {
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_scan_order)
    TextView tvScanOrder;
    @BindView(R.id.ll_scan_order)
    LinearLayout llScanOrder;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    PurchaseStoreActivity rmActivity;

    boolean upDateFlag;

    PurchaseStoreLogic commonLogic;

    List<ListSumBean> list;

    StoreReturnMaterialSumAdapter adapter;

    private String order;

    /**
     * 单头数据
     */
    HashMap<String,String> headMap;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                sureCommit();
            }
            @Override
            public void onCallback2() {

            }
        });
    }

    /**
     * 确认提交
     */
    private void sureCommit() {
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        rmActivity.finish();
                    }
                });

            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_returnmaterial_sum;

    }


    @Override
    protected void doBusiness() {
        rmActivity = (PurchaseStoreActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }
    Bundle extras;
    private void initData() {
        extras= activity.getIntent().getExtras();
        list = new ArrayList<>();

        order = extras.getString(AddressContants.DOC_NO);
        tvScanOrder.setText(order);
        tvDate.setText(extras.getString(AddressContants.DATE));
        tvSupplier.setText(extras.getString(AddressContants.SUPPLIER));

        commonLogic = PurchaseStoreLogic.getInstance(activity,rmActivity.module,rmActivity.mTimestamp.toString());
    }

    /**
     * 滑动更新
     */
    public void upDateList() {
        try {
            list.clear();
            adapter = new StoreReturnMaterialSumAdapter(rmActivity,list);
            ryList.setAdapter(adapter);
            showLoadingDialog();
            headMap =new HashMap<>();
            headMap.put(AddressContants.DOC_NO,extras.getString(AddressContants.DOC_NO));
            headMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
            commonLogic.getSumDatas(headMap, new PurchaseStoreLogic.GetSumDataListener() {
                @Override
                public void onSuccess(List<ListSumBean> datas) {
                    dismissLoadingDialog();
                    list = datas;
                    adapter = new StoreReturnMaterialSumAdapter(rmActivity,list);
                    ryList.setAdapter(adapter);
                    if (null != list && list.size() > 0) {
                        upDateFlag = true;
                        toDetail();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    list.clear();
                    adapter = new StoreReturnMaterialSumAdapter(context, list);
                    ryList.setAdapter(adapter);
                    showFailedDialog(error);
                }
            });
        }catch (Exception e){
            LogUtils.e(TAG, "updateList--getSum--Exception" + e);
        }

    }
    /**
     * 查看单笔料明细
     */
    public void toDetail() {
        try {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int pos) {
                    getDetail(list.get(pos));
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "toDetail-->" + e);
        }
    }
    /**
     * 查看明细
     */
    private void getDetail(final ListSumBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setAvailable_in_qty(orderSumData.getReceipt_qty());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, rmActivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, rmActivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, rmActivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }


}
