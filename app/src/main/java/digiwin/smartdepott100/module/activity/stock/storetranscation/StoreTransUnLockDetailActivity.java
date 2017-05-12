package digiwin.smartdepott100.module.activity.stock.storetranscation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.stock.store.StoreTransUnLockDetailAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @des     库存交易解锁明细
 * @author  maoheng
 * @date    2017/3/4
 */

public class StoreTransUnLockDetailActivity extends BaseTitleActivity{

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * recyclerView
     */
    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    /**
     * 作业编号
     */
    public String module;

    /**
     * 单号
     */
    private String doc_no;

    private CommonLogic commonLogic;

    private StoreTransUnLockDetailAdapter adapter;

    private List<ListSumBean> list;

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module= ModuleCode.STORETRANSUNLOCK;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_storetransunlock_detail;
    }
    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.store_trans_unlock_detail);
        ivScan.setVisibility(View.GONE);
    }
//    private RecyclerView ry_list;
    @Override
    protected void doBusiness() {
//        ry_list = (RecyclerView) findViewById(R.id.ry_list);
        ry_list.setLayoutManager(new LinearLayoutManager(activity));
        Bundle extras = getIntent().getExtras();
        doc_no = extras.getString(AddressContants.DOC_NO);
        commonLogic = CommonLogic.getInstance(activity,module,mTimestamp.toString());
        list = new ArrayList<>();
        ClickItemPutBean bean = new ClickItemPutBean();
        bean.setDoc_no(doc_no);
        showLoadingDialog();
        commonLogic.getOrderSumData(bean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> datas) {
                dismissLoadingDialog();
                LogUtils.e(TAG,datas.size());
                list = datas;
                updateUI();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }
    /**
     * 更新界面
     */
    private void updateUI() {
        adapter = new StoreTransUnLockDetailAdapter(activity,list);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        ry_list.setLayoutManager(manager);
        ry_list.setAdapter(adapter);
    }
}
