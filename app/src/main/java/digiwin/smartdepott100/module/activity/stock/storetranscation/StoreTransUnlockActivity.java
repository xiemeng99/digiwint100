package digiwin.smartdepott100.module.activity.stock.storetranscation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.stock.store.StoreTransUnLockAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @des     库存交易解锁
 * @author  maoheng
 * @date    2017/3/28
 */

public class StoreTransUnlockActivity extends BaseTitleActivity{

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;
    /**
     * 作业编号
     */
    public String module;
    /**
     * 扫描界面
     */
    @BindView(R.id.input_top)
    LinearLayout input_top;
    /**
     * recyclerView
     */
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    /**
     * 筛选按钮
     */
    @BindView(R.id.iv_title_setting)
    ImageView search;

    private boolean isSearching;
    List<FilterResultOrderBean> list;
    StoreTransUnLockAdapter adapter;
    private String DOC_NO;

    @OnClick(R.id.iv_title_setting)
    void search(){
        if (isSearching) {
            isSearching=false;
            ryList.setVisibility(View.VISIBLE);
            input_top.setVisibility(View.GONE);
            unlock.setVisibility(View.VISIBLE);
            return;
        } else {
            isSearching=true;
            ryList.setVisibility(View.GONE);
            input_top.setVisibility(View.VISIBLE);
            unlock.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 仓库
     */
    @BindView(R.id.ll_storage)
    LinearLayout ll_storage;
    @BindView(R.id.et_storage)
    EditText et_storage;
    @BindView(R.id.tv_storage)
    TextView tv_storage;
    /**
     * 锁定单号
     */
    @BindView(R.id.ll_lock_no)
    LinearLayout ll_lock_no;
    @BindView(R.id.et_lock_no)
    EditText et_lock_no;
    @BindView(R.id.tv_lock_no)
    TextView tv_lock_no;
    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout ll_item_no;
    @BindView(R.id.et_item_no)
    EditText et_item_no;
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    /**
     * 批号
     */
    @BindView(R.id.ll_batch_no)
    LinearLayout ll_batch_no;
    @BindView(R.id.et_batch_no)
    EditText et_batch_no;
    @BindView(R.id.tv_batch_no)
    TextView tv_batch_no;
    /**
     * 库位
     */
    @BindView(R.id.ll_locator)
    LinearLayout ll_locator;
    @BindView(R.id.et_locator)
    EditText et_locator;
    @BindView(R.id.tv_locator)
    TextView tv_locator;
    /**
     * 日期
     */
//    @BindView(R.id.ll_date)
//    LinearLayout ll_date;
//    @BindView(R.id.et_date)
//    EditText et_date;
//    @BindView(R.id.tv_date)
//    TextView tv_date;
    /**
     * 锁定人
     */
    @BindView(R.id.ll_lock_people)
    LinearLayout ll_lock_people;
    @BindView(R.id.et_lock_people)
    EditText et_lock_people;
    @BindView(R.id.tv_lock_people)
    TextView tv_lock_people;
    /**
     * 锁定日期
     */
    @BindView(R.id.ll_lock_date)
    LinearLayout ll_lock_date;
    @BindView(R.id.et_lock_date)
    EditText et_lock_date;
    @OnTextChanged(value = R.id.et_lock_date, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void lockDateChange(CharSequence s) {
        if (StringUtils.isBlank(s.toString())) {
            startDate = "";
            endDate = "";
        }
    }
    @BindView(R.id.tv_lock_date)
    TextView tv_lock_date;
    @BindView(R.id.lock_date)
    ImageView lock_date;

    @BindViews({R.id.et_storage, R.id.et_lock_no, R.id.et_item_no, R.id.et_batch_no, R.id.et_locator, R.id.et_lock_people, R.id.et_lock_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_storage, R.id.ll_lock_no, R.id.ll_item_no, R.id.ll_batch_no, R.id.ll_locator, R.id.ll_lock_people, R.id.ll_lock_date})
    List<View> views;
    @BindViews({R.id.tv_storage, R.id.tv_lock_no, R.id.tv_item_no, R.id.tv_batch_no, R.id.tv_locator, R.id.tv_lock_people, R.id.tv_lock_date})
    List<TextView> textViews;

    private CommonLogic commonLogic;

    /**
     * 确定
     */
    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    /**
     * 删除
     */
    @BindView(R.id.unlock)
    Button unlock;
    @OnClick(R.id.unlock)
    void delete(){
        showLoadingDialog();
        List<FilterResultOrderBean> beanList = new ArrayList<>();
        List<FilterResultOrderBean> deleteList = new ArrayList<>();
        Map<Integer, Boolean> map = adapter.getMap();
        LogUtils.e(TAG,map.size()+"");
        for (int i = 0; i < map.size(); i++) {
            if(!map.get(i)&&map.get(i)!=null){
                beanList.add(list.get(i));
            }else{
                deleteList.add(list.get(i));
            }
        }
        if(deleteList.size() == 0){
            showFailedDialog(R.string.delete_choose);
        }else {
            final List<FilterResultOrderBean> resultList = beanList;
            List<Map<String,String>> maps = new ArrayList<>();
            for (int i = 0; i < deleteList.size(); i++) {
                Map<String,String> map1 = new HashMap<>();
                map1.put(AddressContants.DOC_NO,deleteList.get(i).getDoc_no());
                maps.add(map1);
            }
            commonLogic.commitList(maps, new CommonLogic.CommitListListener() {
                @Override
                public void onSuccess(String msg) {
                    dismissLoadingDialog();
                    list = resultList;
                    Map<Integer, Boolean> map2 = new HashMap<>();
                    updateUI(map2);
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    showFailedDialog(error);
                }
            });

        }
    }

    @OnClick(R.id.btn_search_sure)
    void searchSure(){
        showLoadingDialog();
        FilterBean filterBean = new FilterBean();
        filterBean.setWarehouse_no(et_storage.getText().toString().trim());
        filterBean.setDoc_no(et_lock_no.getText().toString().trim());
        filterBean.setBarcode_no(et_item_no.getText().toString().trim());
        filterBean.setLot_no(et_batch_no.getText().toString().trim());
        filterBean.setWarehouse_storage(et_locator.getText().toString().trim());
        filterBean.setEmployee_no(et_lock_people.getText().toString().trim());
        filterBean.setDate_begin(startDate);
        filterBean.setDate_end(endDate);
        commonLogic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
            @Override
            public void onSuccess(List<FilterResultOrderBean> masterDatas) {
                dismissLoadingDialog();
                list=masterDatas;
                showData();
                Map<Integer,Boolean> map = new HashMap<Integer, Boolean>();
                updateUI(map);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 展示信息
     */
    private void showData() {
        try {
            isSearching=true;
            unlock.setVisibility(View.VISIBLE);
            ryList.setVisibility(View.VISIBLE);
            input_top.setVisibility(View.GONE);
            adapter = new StoreTransUnLockAdapter(activity, list);
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    /**
     * 点击进入明细
     */
    private void itemClick() {

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(DOC_NO,list.get(position).getDoc_no());
                ActivityManagerUtils.startActivityForBundleData(activity,StoreTransUnLockDetailActivity.class,bundle);
            }
        });
    }

    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    @OnClick(R.id.lock_date)
    void getDate() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate,String mShowDate) {
                startDate=mStartDate;
                endDate=mEndDate;
                et_lock_date.setText(mShowDate);
                et_lock_date.requestFocus();
            }
        });
    }

    @OnFocusChange(R.id.et_storage)
    void storeFocusChange() {
        ModuleUtils.viewChange(ll_storage, views);
        ModuleUtils.etChange(activity, et_storage, editTexts);
        ModuleUtils.tvChange(activity, tv_storage, textViews);
    }
    @OnFocusChange(R.id.et_lock_no)
    void lockNoFocusChange() {
        ModuleUtils.viewChange(ll_lock_no, views);
        ModuleUtils.etChange(activity, et_lock_no, editTexts);
        ModuleUtils.tvChange(activity, tv_lock_no, textViews);
    }
    @OnFocusChange(R.id.et_item_no)
    void itemNoFocusChange() {
        ModuleUtils.viewChange(ll_item_no, views);
        ModuleUtils.etChange(activity, et_item_no, editTexts);
        ModuleUtils.tvChange(activity, tv_item_no, textViews);
    }
    @OnFocusChange(R.id.et_batch_no)
    void batchNoFocusChange() {
        ModuleUtils.viewChange(ll_batch_no, views);
        ModuleUtils.etChange(activity, et_batch_no, editTexts);
        ModuleUtils.tvChange(activity, tv_batch_no, textViews);
    }
    @OnFocusChange(R.id.et_locator)
    void locatorFocusChange() {
        ModuleUtils.viewChange(ll_locator, views);
        ModuleUtils.etChange(activity, et_locator, editTexts);
        ModuleUtils.tvChange(activity, tv_locator, textViews);
    }
//    @OnFocusChange(R.id.et_date)
//    void dateFocusChange() {
//        ModuleUtils.viewChange(ll_date, views);
//        ModuleUtils.etChange(activity, et_date, editTexts);
//        ModuleUtils.tvChange(activity, tv_date, textViews);
//    }
    @OnFocusChange(R.id.et_lock_people)
    void lockPeopleFocusChange() {
        ModuleUtils.viewChange(ll_lock_people, views);
        ModuleUtils.etChange(activity, et_lock_people, editTexts);
        ModuleUtils.tvChange(activity, tv_lock_people, textViews);
    }
    @OnFocusChange(R.id.et_lock_date)
    void lockDateFocusChange() {
        ModuleUtils.viewChange(ll_lock_date, views);
        ModuleUtils.etChange(activity, et_lock_date, editTexts);
        ModuleUtils.tvChange(activity, tv_lock_date, textViews);
    }



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
        return R.layout.activity_storetransunlock_list;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.store_trans_unlock);
        ivScan.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching=true;
        unlock.setVisibility(View.GONE);
    }

    @Override
    protected void doBusiness() {
        et_lock_date.setKeyListener(null);
        DOC_NO = AddressContants.DOC_NO;
        commonLogic = CommonLogic.getInstance(activity,module,mTimestamp.toString());
        list=new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        Map<Integer,Boolean> map = new LinkedHashMap<>();
        updateUI(map);
    }
    /**
     * 更新界面
     */
    private void updateUI(Map<Integer, Boolean> map) {
        adapter = new StoreTransUnLockAdapter(activity,list);
        adapter.setMap(map);
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        ryList.setAdapter(adapter);
        itemClick();
    }
}
