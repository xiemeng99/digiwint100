package digiwin.smartdepott100.module.activity.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogClickgetTextListener;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.UpdateNumListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.common.CommonDetailAdapter;
import digiwin.smartdepott100.module.adapter.purchase.PurchaseGoodsDetailAdapter;
import digiwin.smartdepott100.module.adapter.stock.storeallot.StoreAllotDetailAdapter;
import digiwin.smartdepott100.module.bean.common.DeleteUpdateBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author ChangQuanSun
 * @des 通用明细 不同的明细样式使用不同adapter即可
 * @date 2017/2/24
 */
public class CommonDetailActivity extends BaseTitleActivity {
    /**
     * 页面传输明细
     */
    public static final String DETAIL = "detail";
    /**
     * 页面传输头部数据
     */
    public static final String ONESUM = "onesum";
    /**
     * 模组名
     */
    public static final String MODULECODE = "code";

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.cb_all)
    public CheckBox cbAll;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.delete)
    void delete() {
        Map<Integer, Boolean> map = adapter.getMap();
        Set<Map.Entry<Integer, Boolean>> sets = map.entrySet();
        List<DetailShowBean> deletelist = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : sets) {
            Integer key = entry.getKey();
            Boolean val = entry.getValue();
            if (null != val && val) {
                deletelist.add(mDetailShowBeen.get(key));
            }
        }
        if (deletelist.size() == 0) {
            showFailedDialog(R.string.delete_choose);
            return;
        }
        toDelete(deletelist, AddressContants.DELETETPYE);
    }

    @OnClick(R.id.cb_all)
    void onCheckChange() {
        boolean checked = cbAll.isChecked();
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int i = 0; i < mDetailShowBeen.size(); i++) {
            map.put(i, checked);
        }
        updateUI(map);
    }

    public CommonLogic commonLogic;

    BaseDetailRecyclerAdapter adapter = null;
    public List<DetailShowBean> mDetailShowBeen;
    public SumShowBean sumBean;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.scandetail);
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module = bundle.getString(MODULECODE, ModuleCode.OTHER);
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_common_detail;
    }

    @Override
    protected void doBusiness() {
        Bundle bundle = getIntent().getExtras();
        mDetailShowBeen = (List<DetailShowBean>) bundle.getSerializable(DETAIL);
        sumBean = (SumShowBean) bundle.getSerializable(ONESUM);
        commonLogic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        if (null != sumBean) {
            tvItemNo.setText(sumBean.getItem_no());
        }
        Map<Integer, Boolean> map = new HashMap<>();
        updateUI(map);
        updateNum();
    }

    /**
     * 更新界面
     * TODO:可能会存在不同的明细页面使用不同的adapter
     */
    private void updateUI(Map<Integer, Boolean> map) {
        switch (module) {
            case ModuleCode.NOCOMESTOREALLOT:
                adapter = new StoreAllotDetailAdapter(activity, mDetailShowBeen);
                break;
            case ModuleCode.PURCHASEGOODSSCAN:
                adapter = new PurchaseGoodsDetailAdapter(activity, mDetailShowBeen);
                break;
            case ModuleCode.POSTALLOCATE:
                adapter = new StoreAllotDetailAdapter(activity, mDetailShowBeen);
                break;
            default:
                adapter = new CommonDetailAdapter(activity, mDetailShowBeen);
                break;
        }

        adapter.setMap(map);
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        ryList.setAdapter(adapter);
    }

    /**
     * 修改...操作后重新setlistener
     */
    private void updateNum() {
        try {
            adapter.setListener(new UpdateNumListener<DetailShowBean>() {
                @Override
                public void update(final DetailShowBean item, int pos, RecyclerViewHolder holder) {
                    AlertDialogUtils.showEditDialogAndCall(StringUtils.deleteZero(item.getBarcode_qty()), context, new OnDialogClickgetTextListener() {
                        @Override
                        public void onCallback(CustomDialog dialog, String text) {
                            DeleteUpdateBean updateBean = new DeleteUpdateBean();
                            updateBean.setAvailable_in_qty(sumBean.getAvailable_in_qty());
                            updateBean.setBarcode_qty(text);
                            updateBean.setScandetail_upd_type(AddressContants.UPDATETPYE);
                            updateBean.setApp_reqseq(item.getApp_reqseq());
                            List<DeleteUpdateBean> list = new ArrayList<>();
                            list.add(updateBean);
                            deleteAndUpdate(list, dialog);
                        }
                    });
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "updateNum---" + e);
        }
    }

    /**
     * 删除
     */
    private void toDelete(List<DetailShowBean> deletelist, String type) {
        List<DeleteUpdateBean> list = new ArrayList<>();
        try {
            for (DetailShowBean detail : deletelist) {
                DeleteUpdateBean deleteUpdateBean = new DeleteUpdateBean();
                deleteUpdateBean.setApp_reqseq(detail.getApp_reqseq());
                deleteUpdateBean.setScandetail_upd_type(type);
                deleteUpdateBean.setBarcode_qty(detail.getBarcode_qty());
                deleteUpdateBean.setAvailable_in_qty(sumBean.getAvailable_in_qty());
                list.add(deleteUpdateBean);
            }
            deleteAndUpdate(list, null);
        } catch (Exception e) {
            LogUtils.e(TAG, "toDelete---" + e);
        }

    }

    /**
     * 修改删除
     *
     * @param list
     */
    private void deleteAndUpdate(List<DeleteUpdateBean> list, CustomDialog editdialog) {
        showLoadingDialog();
        commonLogic.upDateAndDelete(ObjectAndMapUtils.getListMap(list), new CommonLogic.UpdateAndDeleteListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                dismissLoadingDialog();
                AlertDialogUtils.dismissEditDialog();
                mDetailShowBeen = detailShowBeen;
                Map<Integer, Boolean> map = new HashMap<>();
                updateUI(map);
                updateNum();
                cbAll.setChecked(false);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }


}
