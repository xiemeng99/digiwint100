package digiwin.smartdepott100.module.activity.purchase.pqccheckout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.purchase.PQCCheckOutExpandableAdapter;
import digiwin.smartdepott100.module.bean.stock.PQCCheckOutBean;
import digiwin.smartdepott100.module.logic.purchase.PQCCheckOutLogic;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;

/**
 * Created by qGod on 2017/5/29
 * Thank you for watching my code
 * PQC检验列表
 */

public class PQCCheckOutListActivity extends BaseTitleActivity {
    private PQCCheckOutListActivity activity;

    PQCCheckOutExpandableAdapter adapter;

    PQCCheckOutLogic commonLogic;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar_title;

    @BindView(R.id.expand_lv)
    ExpandableListView expandableListView;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;

    @BindViews({R.id.ll_provider_code, R.id.ll_barcode_no, R.id.ll_item_name, R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_provider_code, R.id.tv_barcode_no, R.id.tv_item_name, R.id.tv_plan_date})
    List<TextView> textViews;
    @BindViews({R.id.et_provider_code, R.id.et_barcode_no, R.id.et_item_name, R.id.et_plan_date})
    List<EditText> editTexts;

    /**
     * 工单单号
     */
    @BindView(R.id.ll_provider_code)
    LinearLayout ll_search_workordernumber;
    @BindView(R.id.tv_provider_code)
    TextView tv_search_wrokordernumber;
    @BindView(R.id.et_provider_code)
    EditText et_search_workordernumber;

    @OnFocusChange(R.id.et_provider_code)
    void provider_codeFocusChanage() {
        ModuleUtils.viewChange(ll_search_workordernumber, views);
        ModuleUtils.tvChange(activity, tv_search_wrokordernumber, textViews);
        ModuleUtils.etChange(activity, et_search_workordernumber, editTexts);
    }

    /**
     * 料号
     */
    @BindView(R.id.ll_barcode_no)
    LinearLayout ll_item_no;
    @BindView(R.id.tv_barcode_no)
    TextView tv_item_no;
    @BindView(R.id.et_barcode_no)
    EditText et_item_no;

    @OnFocusChange(R.id.et_barcode_no)
    void barcode_noFocusChanage() {
        ModuleUtils.viewChange(ll_item_no, views);
        ModuleUtils.tvChange(activity, tv_item_no, textViews);
        ModuleUtils.etChange(activity, et_item_no, editTexts);
    }

    /**
     * 物料批号
     */
    @BindView(R.id.ll_item_name)
    LinearLayout ll_circultion_no;
    @BindView(R.id.tv_item_name)
    TextView tv_circultion_no;
    @BindView(R.id.et_item_name)
    EditText et_circultion_no;

    @OnFocusChange(R.id.et_item_name)
    void customFocusChanage() {
        ModuleUtils.viewChange(ll_circultion_no, views);
        ModuleUtils.tvChange(activity, tv_circultion_no, textViews);
        ModuleUtils.etChange(activity, et_circultion_no, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.ll_plan_date)
    LinearLayout ll_plan_date;

    @BindView(R.id.iv_plan_date)
    ImageView iv_plan_date;
    @BindView(R.id.tv_plan_date)
    TextView tv_plan_date;
    @BindView(R.id.et_plan_date)
    EditText et_plan_date;

    @OnFocusChange(R.id.et_plan_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(ll_plan_date, views);
        ModuleUtils.tvChange(activity, tv_plan_date, textViews);
        ModuleUtils.etChange(activity, et_plan_date, editTexts);
    }

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_plan_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_plan_date.setText(showDate);
            }
        });
    }

    private final int SCANCODE = 1234;

    public static final String DATA = "data";

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    /**
     * 清空dialog条件
     */
    public void clearDialogText() {
        et_search_workordernumber.setText("");
        et_item_no.setText("");
        et_circultion_no.setText("");
        et_plan_date.setText("");
    }
    private LinkedHashMap<String,List<PQCCheckOutBean>> hashMap;
    @OnClick(R.id.btn_search_sure)
    void search() {
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put(AddressContants.WO_NO, et_search_workordernumber.getText().toString().trim());//工单单号
        map.put(AddressContants.ITEM_NO, et_item_no.getText().toString().trim());//料号
        map.put(AddressContants.PLOTNO, et_circultion_no.getText().toString().trim());//物料批号
        map.put(AddressContants.DATEBEGIN, startDate);//起始日期
        map.put(AddressContants.DATEEND, endDate);//结束日期
        map.put(AddressContants.PAGESIZE, (String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING,AddressContants.PAGE_NUM));
        commonLogic.getFiltrateDatas(map, new PQCCheckOutLogic.FiltrateListener() {
            @Override
            public void onSuccess(final List<PQCCheckOutBean> list) {
                dismissLoadingDialog();
                if (list.size() > 0) {
                    ll_search_dialog.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                    hashMap=new LinkedHashMap<String, List<PQCCheckOutBean>>();
                    for (PQCCheckOutBean bean : list) {
                        if(hashMap.containsKey(bean.getQc_type())){//包含
                            List<PQCCheckOutBean> beanlist = hashMap.get(bean.getQc_type());
                            beanlist.add(bean);
                            hashMap.put(bean.getQc_type(),beanlist);
                        }else{//不包含
                            LinkedList<PQCCheckOutBean> beanlist = new LinkedList<PQCCheckOutBean>();
                            beanlist.add(bean);
                            hashMap.put(bean.getQc_type(),beanlist);
                        }
                    }
                    adapter = new PQCCheckOutExpandableAdapter(activity,hashMap);
                    expandableListView.setAdapter(adapter);
                    clearDialogText();
                    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                            Bundle bundle = new Bundle();
                            PQCCheckOutBean data = (PQCCheckOutBean) adapter.getChild(i,i1);
                            bundle.putSerializable(DATA, data);
                            ActivityManagerUtils.startActivityBundleForResult(activity, PQCCheckOutActivity.class, bundle, SCANCODE);
                            return false;
                        }
                    });
                } else {
                    showFailedDialog(getResources().getString(R.string.nodate));
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                hashMap=new LinkedHashMap<String, List<PQCCheckOutBean>>();
                adapter = new PQCCheckOutExpandableAdapter(activity,hashMap);
                expandableListView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void doBusiness() {
        et_plan_date.setKeyListener(null);
        commonLogic = PQCCheckOutLogic.getInstance(activity, module, mTimestamp.toString());
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
                ll_search_dialog.setVisibility(View.GONE);
                expandableListView.setVisibility(View.VISIBLE);
        } else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SCANCODE) {
                search();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pqc_checkout_list;
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar_title;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PQCCHECKOUT;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.pqc_check)+getString(R.string.list));
        activity = this;
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }
}
