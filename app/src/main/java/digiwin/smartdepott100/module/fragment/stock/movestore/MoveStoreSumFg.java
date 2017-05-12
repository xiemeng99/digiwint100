package digiwin.smartdepott100.module.fragment.stock.movestore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.stock.movestore.MoveStoreActivity;
import digiwin.smartdepott100.module.adapter.stock.MoveStoreAdapter;
import digiwin.smartdepott100.module.adapter.stock.storeallot.StoreAllotSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @des  条码移库扫描
 * @author  xiemeng
 * @date    2017/3/23
 */
public class MoveStoreSumFg extends BaseFragment {
    @BindViews({ R.id.et_scan_moveinlocator})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_inlocator})
    List<View> views;
    @BindViews({R.id.tv_moveinlocator})
    List<TextView> textViews;

    @BindView(R.id.tv_moveinlocator)
    TextView tvMoveinlocator;
    @BindView(R.id.et_scan_moveinlocator)
    EditText etScanMoveinlocator;
    @BindView(R.id.ll_scan_inlocator)
    LinearLayout llScanInlocator;
    @BindView(R.id.tv_storage)
    TextView tvStorage;
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @OnTextChanged(value = R.id.et_scan_moveinlocator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
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
   boolean locatorFlag;
    MoveStoreActivity pactivity;


    private boolean upDateFlag;

    BaseRecyclerAdapter adapter;

    List<SumShowBean> sumShowBeanList;

    CommonLogic commonLogic;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            tvLocator.setText(locatorBackBean.getStorage_spaces_name());
                            tvStorage.setText(locatorBackBean.getWarehouse_name());
                            locatorFlag=true;
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanMoveinlocator.setText("");
                                }
                            });
                            locatorFlag = false;
                        }
                    });
                    break;
            }
            return false;
        }
    });
    /**
     * 库位
     */
    final int LOCATORWHAT = 1003;
    @Override
    protected int bindLayoutId() {
        return R.layout.fg_movestore_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (MoveStoreActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }
    /**
     * 汇总展示
     */
    public void updateList() {
        try {
            sumShowBeanList.clear();
            adapter = new MoveStoreAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            Map<String, String> map = new HashMap<>();
            showLoadingDialog();
            commonLogic.getSum(map, new CommonLogic.GetSumListener() {
                @Override
                public void onSuccess(List<SumShowBean> list) {
                    sumShowBeanList = list;
                    adapter = new MoveStoreAdapter(activity, sumShowBeanList);
                    ryList.setAdapter(adapter);
                    upDateFlag = true;
                    toDetail();
                    dismissLoadingDialog();
                }

                @Override
                public void onFailed(String error) {
                    upDateFlag = false;
                    try {
                        dismissLoadingDialog();
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<SumShowBean>();
                        adapter = new StoreAllotSumAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });
        } catch (Exception e) {
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
                    getDetail(sumShowBeanList.get(pos));
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "toDetail-->" + e);
        }
    }


    /**
     * 查看明细
     */
    private void getDetail(final SumShowBean sumShowBean) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, sumShowBean.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE,pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    /**
     * 提交
     */
    private void sureCommit(){
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_in_movelocator);
            return;
        }
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("storage_spaces_in_no",tvLocator.getText().toString());
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.createNewModuleId(pactivity.module);
                        pactivity.moduleVp.setCurrentItem(0);
                        pactivity.scanFg.initData();
                        initData();
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

    private void initData(){
        sumShowBeanList=new ArrayList<>();
        commonLogic = CommonLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        locatorFlag=false;
        upDateFlag = false;
        etScanMoveinlocator.setText("");
        tvLocator.setText("");
        tvStorage.setText("");
    }
}
