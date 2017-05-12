package digiwin.smartdepott100.module.activity.purchase.pqccheck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.constant.SharePreKey;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.library.zxing.MipcaActivityCapture;
import digiwin.library.zxing.camera.GetBarCodeListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.module.activity.purchase.purchasecheck.BadReasonActivity;
import digiwin.smartdepott100.module.adapter.ExpandAdapter;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.PQCValueBean;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckBean;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckDetailBean;
import digiwin.smartdepott100.module.logic.purchase.PQCLogic;
import digiwin.smartdepott100.module.logic.purchase.PurcahseCheckLogic;

/**
 * PQC检验
 *
 * @author maoheng
 * @date 2017/4/26
 */

public class PQCCheckActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @OnClick(R.id.iv_back)
    public void ivBack(){
        activity.finish();
    }
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @OnClick(R.id.tv_title_name)
    public void tvBack(){
        activity.finish();
    }
    @BindView(R.id.et_input)
    EditText etInput;
    @OnTextChanged(value = R.id.et_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void scanBarcode(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }

    }
    @BindView(R.id.iv_input)
    ImageView ivInput;
    @BindView(R.id.rl_iv_input_scan)
    RelativeLayout rlIvInputScan;
    @OnClick(R.id.rl_iv_input_scan)
    void ScanCamara() {
        MipcaActivityCapture.startCameraActivity(activity, new GetBarCodeListener() {
            @Override
            public void onSuccess(String msg) {
                View focusView = ViewUtils.getFocusView(activity);
                if (focusView instanceof EditText) {
                    EditText et = (EditText) focusView;
                    et.setText(msg);
                    et.setSelection(msg.length());
                }
            }
        });
    }
    @BindView(R.id.ll_et_input)
    LinearLayout llEtInput;
    @BindView(R.id.iv_value_check)
    ImageView ivValueCheck;

    private Map<PurchaseCheckDetailBean,List<PQCValueBean>> valueMap;

    private List<PQCValueBean> values;

    private final int VALUESEND = 1234;

    private List<PurchaseCheckDetailBean> detailBean;

    @OnClick(R.id.iv_value_check)
    void goValueCheck(){
        if(selectedDetailBean == null){
            showToast(R.string.please_select_one_data);
            return;
        }else {
            Bundle bundle = new Bundle();
            bundle.putString(AddressContants.DOC_NO, selectBean.getDoc_no());
            bundle.putString(AddressContants.SEQ, selectedDetailBean.getSeq());
            bundle.putString(AddressContants.MODULEID_INTENT,module);
            if(null!=valueMap&&valueMap.size()>0){
                List<PQCValueBean> pqcValueBeen = valueMap.get(selectedDetailBean);
                if(null != pqcValueBeen&&pqcValueBeen.size()>0){
                    bundle.putSerializable("valueList", (Serializable) pqcValueBeen);
                }
            }
            ActivityManagerUtils.startActivityBundleForResult(activity,PQCValueActivity.class,bundle,VALUESEND);
        }
    }
    @BindView(R.id.iv_bad_reason)
    ImageView ivBadReason;
    /**
     * 来自不良原因界面的不良数量
     */
    float defect_num = 0;

    Map<PurchaseCheckDetailBean,List<BadReasonBean>> badReasonMap = new HashMap<>();

    private List<BadReasonBean> badReasonList = new ArrayList<>();

    /**
     * 不良原因
     */
    @OnClick(R.id.iv_bad_reason)
    void lookBadReason(){
        if(selectedDetailBean == null){
            showToast(R.string.please_select_one_data);
            return;
        }else{
            //TODO 跳转到不良原因界面
            Bundle bundle = new Bundle();
            selectedDetailBean.setItem_no(selectBean.getItem_no());
            bundle.putSerializable("purchaseCheckBean",selectBean);
            bundle.putSerializable("purchaseCheckDetailBean",selectedDetailBean);
            if(null != badReasonMap && badReasonMap.size()>0){
                List<BadReasonBean> list = badReasonMap.get(selectedDetailBean);
                if(null != list && list.size()>0){
                    bundle.putSerializable("badReasonList", (Serializable) list);
                }
            }
            bundle.putString(AddressContants.MODULEID_INTENT,module);
            ActivityManagerUtils.startActivityForBundleData(activity,BadReasonActivity.class,bundle);
        }
    }
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_work_group)
    TextView tvWorkGroup;
    @BindView(R.id.tv_pallet)
    TextView tvPallet;
    @BindView(R.id.ll_order_head)
    LinearLayout llOrderHead;
    @BindView(R.id.commit)
    Button commit;

    private PQCLogic pqcLogic;
    @OnClick(R.id.commit)
    public void commit(){
        final List<Map<String,String>> maps = new ArrayList<>();
        final List<Map<String,String>> details = new ArrayList<>();
        if(null!=detailBean&&detailBean.size()>0){
            for (int i = 0; i < detailBean.size(); i++) {
                PurchaseCheckDetailBean purchaseCheckDetailBean = detailBean.get(i);
                Map<String,String> map1 = new HashMap<>();
                map1.put(AddressContants.DOC_NO,purchaseCheckDetailBean.getDoc_no());
                map1.put("order_no",purchaseCheckDetailBean.getSeq());
                map1.put("defect_qty",purchaseCheckDetailBean.getDefect_qty());
                String okString = activity.getResources().getString(R.string.OK);
                if(okString.equals(purchaseCheckDetailBean.getItem_deter())){
                    map1.put("item_deter","1");
                }else {
                    map1.put("item_deter","2");
                }
                maps.add(map1);
                List<BadReasonBean> list = badReasonMap.get(detailBean.get(i));
                if(null!=list&&list.size()>0){
                    for (int j = 0; j < list.size(); j++) {
                        Map<String, String> valueMap1 = ObjectAndMapUtils.getValueMap(list.get(j));
                        details.add(valueMap1);
                    }
                }
            }
        }else {
            return;
        }
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                pqcLogic.postPQCData(maps, details, new PQCLogic.postPQCListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg);
                        createNewModuleId(module);
                        clearData();
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

    /**
     * 清空数据
     */
    private void clearData() {
        dataMap = new LinkedHashMap<>();
        valueMap = new HashMap<>();
        values = new ArrayList<>();
        detailBean = new ArrayList<>();
        logic = PurcahseCheckLogic.getInstance(activity,module,mTimestamp.toString());
        pqcLogic = PQCLogic.getInstance(activity,module,mTimestamp.toString());
        selectBean = null;
        selectedDetailBean = null;
        prePosition = -1;
        tvPallet.setText("");
        tvWorkGroup.setText("");
        tvOrderNumber.setText("");
        adapter = new PQCExpandAdapter(dataMap,false);
        expandLv.setAdapter(adapter);

    }

    @BindView(R.id.expand_lv)
    ExpandableListView expandLv;

    private LinkedHashMap<PurchaseCheckBean,List<PurchaseCheckDetailBean>> dataMap;

    PurcahseCheckLogic logic;

    private final int BARCODEWHAT = 100;

    private PQCExpandAdapter adapter;

    private PurchaseCheckBean selectBean;

    private PurchaseCheckDetailBean selectedDetailBean;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BARCODEWHAT:
                    showLoadingDialog();
                    Map<String,String> map = new HashMap<>();
                    map.put(AddressContants.BARCODE_NO,msg.obj.toString().trim());
                    map.put(AddressContants.PAGESIZE, (String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING,AddressContants.PAGE_NUM));
                    showLoadingDialog();
                    logic.getMaterialToCheck(map, new PurcahseCheckLogic.GetMaterialToCheckListener() {
                        @Override
                        public void onSuccess(List<PurchaseCheckBean> purchaseCheckBean) {
                            dataMap = new LinkedHashMap<PurchaseCheckBean, List<PurchaseCheckDetailBean>>();
                            etInput.setText("");
                            if(purchaseCheckBean.size()>0){
                                for (int i = 0; i < purchaseCheckBean.size(); i++) {
                                    purchaseCheckBean.get(i).setSeq(StringUtils.objToString(i+1));
                                }
                                PurchaseCheckBean checkBean = purchaseCheckBean.get(0);
                                tvOrderNumber.setText(checkBean.getWo_no());
                                tvWorkGroup.setText(checkBean.getWorkstation_no());
                                tvPallet.setText(checkBean.getSubop_no());
                                getAllDetail(purchaseCheckBean);
                            }else{
                                adapter = new PQCExpandAdapter(dataMap,false);
                                expandLv.setAdapter(adapter);
                                expandLv.setGroupIndicator(null);
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error);
                            etInput.setText("");
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取所有详细信息
     */
    private void getAllDetail(final List<PurchaseCheckBean> purchaseCheckBean) {
        for (int i = 0; i < purchaseCheckBean.size(); i++) {
            final PurchaseCheckBean bean = purchaseCheckBean.get(i);
            Map<String,String> map = new HashMap<>();
            map.put(AddressContants.DOC_NO,bean.getDoc_no());
            final int finalI = i;
            logic.getIQCDetailList(map, new PurcahseCheckLogic.GetIQCListListener() {
                @Override
                public void onSuccess(List<PurchaseCheckDetailBean> purchaseCheckDetailBean) {
                    for (int j = 0; j < purchaseCheckDetailBean.size(); j++) {
                        purchaseCheckDetailBean.get(j).setDoc_no(bean.getDoc_no());
                    }
                    detailBean.addAll(purchaseCheckDetailBean);
                    dataMap.put(purchaseCheckBean.get(finalI),purchaseCheckDetailBean);
                    adapter = new PQCExpandAdapter(dataMap,false);
                    expandLv.setAdapter(adapter);
                    expandLv.setGroupIndicator(null);
                    if(finalI == purchaseCheckBean.size()-1){
                        dismissLoadingDialog();
                    }
                }

                @Override
                public void onFailed(String error) {
                    if(finalI == purchaseCheckBean.size()-1){
                        dismissLoadingDialog();
                    }
                }
            });
        }
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PQCCHECK;
        return module;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收来自不良原因界面的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainT(List<BadReasonBean> badReasonBeanList){
        if(badReasonBeanList.size()>0){
            badReasonList = new ArrayList<>();
            badReasonList.addAll(badReasonBeanList);
            badReasonMap.put(selectedDetailBean,badReasonList);
//            for (int i = 0; i < badReasonBeanList.size(); i++) {
//                defect_num += StringUtils.string2Float(badReasonBeanList.get(i).getDefect_qty());
//            }
//            for (int i = 0; i < dataMap.size(); i++) {
//                selectedDetailBean.setDefect_qty(StringUtils.objToString(defect_num));
//                List<PurchaseCheckDetailBean> list = dataMap.get(selectBean);
//                if(list.get(i).getSeq().equals(selectedDetailBean.getSeq())){
//                    list.remove(i);
//                    list.add(prePosition,selectedDetailBean);
//                    dataMap.put(selectBean,list);
//                }
//            }
//            defect_num = 0;
//            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode!=VALUESEND){
            return;
        }else {
            if(null!=data){
                List<PQCValueBean> valueBeen = (List<PQCValueBean>) data.getExtras().getSerializable("PQCValue");
                if(valueBeen.size()>0){
                    values = new ArrayList<>();
                    values.addAll(valueBeen);
                    valueMap.put(selectedDetailBean,valueBeen);
                    for (int i = 0; i < valueBeen.size(); i++) {
                        defect_num += StringUtils.string2Float(valueBeen.get(i).getQty());
                    }
                    for (int i = 0; i < dataMap.size(); i++) {
                        selectedDetailBean.setDefect_qty(StringUtils.objToString(defect_num));
                        if(StringUtils.string2Float(selectedDetailBean.getAc_qty())<defect_num){
                            selectedDetailBean.setItem_deter(activity.getResources().getString(R.string.unOK));
                        }else {
                            selectedDetailBean.setItem_deter(activity.getResources().getString(R.string.OK));
                        }
                        List<PurchaseCheckDetailBean> list = dataMap.get(selectBean);
                        if(list.get(i).getSeq().equals(selectedDetailBean.getSeq())){
                            list.remove(i);
                            list.add(prePosition,selectedDetailBean);
                            dataMap.put(selectBean,list);
                        }
                    }
                    defect_num = 0;
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pqc_check;
    }

    @Override
    protected void initNavigationTitle() {

    }

    private int prePosition;

    @Override
    protected void doBusiness() {
        tvTitleName.setText(R.string.pqc_check);
        dataMap = new LinkedHashMap<>();
        valueMap = new HashMap<>();
        values = new ArrayList<>();
        detailBean = new ArrayList<>();
        logic = PurcahseCheckLogic.getInstance(activity,module,mTimestamp.toString());
        pqcLogic = PQCLogic.getInstance(activity,module,mTimestamp.toString());
        selectBean = null;
        selectedDetailBean = null;
        prePosition = -1;
    }

    public class PQCExpandAdapter extends ExpandAdapter<PurchaseCheckBean, PurchaseCheckDetailBean> {


        public PQCExpandAdapter(LinkedHashMap<PurchaseCheckBean, List<PurchaseCheckDetailBean>> data, boolean b) {
            super(data, b);
        }

        @Override
        public int getChildEditTextLayoutId() {
            return R.id.et_num;
        }

        @Override
        public int getGroupCheckBoxId() {
            return R.id.cb_group;
        }

        @Override
        public int getChildCheckBoxId() {
            return R.id.cb_child;
        }

        @Override
        public long setChildId(int parentId, int childId) {
            return childId;
        }

        @Override
        public void editChildTextChanged(Editable arg0, EditText et, PurchaseCheckDetailBean t, int groupId, int childId) {

        }

        @Override
        public View setChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(activity).inflate(R.layout.expand_pqc_child, null);
            //seq
            TextView tv_seq = (TextView) view.findViewById(R.id.tv_seq);
            //检验项目
            TextView tv_project_check = (TextView) view.findViewById(R.id.tv_project_check);
            //aql
            TextView tv_aql = (TextView) view.findViewById(R.id.tv_aql);
            //抽检量
            TextView tv_check_num = (TextView) view.findViewById(R.id.tv_check_num);
            //ac
            TextView tv_ac = (TextView) view.findViewById(R.id.tv_ac);
            //re
            TextView tv_re = (TextView) view.findViewById(R.id.tv_re);
            //项目判定
            TextView tv_project_get = (TextView) view.findViewById(R.id.tv_project_get);
            //缺点数
            TextView tv_defect_num = (TextView) view.findViewById(R.id.tv_defect_num);

            final PurchaseCheckDetailBean child = (PurchaseCheckDetailBean) getChild(groupPosition,childPosition);

            tv_seq.setText(child.getSeq());
            tv_project_check.setText(child.getInspection_item());
            tv_aql.setText(StringUtils.deleteZero(child.getAql()));
            tv_check_num.setText(StringUtils.deleteZero(child.getSample_qty()));
            tv_ac.setText(StringUtils.deleteZero(child.getAc_qty()));
            tv_re.setText(StringUtils.deleteZero(child.getRe_qty()));
            tv_project_get.setText(child.getItem_deter());
            tv_defect_num.setText(StringUtils.deleteZero(child.getDefect_qty()));
            final LinearLayout ll_child = (LinearLayout) view.findViewById(R.id.ll_child);
            PurchaseCheckBean keyBean = (PurchaseCheckBean) getGroup(groupPosition);
            if(groupPosition == dataMap.size()-1){
                if(childPosition == (dataMap.get(keyBean).size()-1)){
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.setMargins(0,0,0,20);
                    ll_child.setLayoutParams(ll);
                }
            }
            if(childPosition == prePosition){
                ll_child.setBackgroundColor(activity.getResources().getColor(R.color.Base_color));
            }else {
                ll_child.setBackgroundColor(activity.getResources().getColor(R.color.gray_f0));
            }
            ll_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedDetailBean = child;
                    prePosition = childPosition;
                    ll_child.setBackgroundColor(activity.getResources().getColor(R.color.Base_color));
                    notifyDataSetChanged();
                }
            });

            return view;
        }

        @Override
        public long setGroupId(int arg0) {
            return arg0;
        }

        @Override
        public View setGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(activity).inflate(
                    R.layout.expand_pqc_group, null);

            //项次
            TextView tv_seq = (TextView) view.findViewById(R.id.tv_seq);
            //品名
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            //规格
            TextView tv_item_spec = (TextView) view.findViewById(R.id.tv_item_spec);
            //图号
            TextView tv_drawing_no = (TextView) view.findViewById(R.id.tv_drawing_no);
            //检验量
            TextView tv_sendCheck_num = (TextView) view.findViewById(R.id.tv_sendCheck_num);
            //合格量
            final TextView et_OK_num = (TextView) view.findViewById(R.id.et_OK_num);
            //判定状态
            final TextView tv_state = (TextView) view.findViewById(R.id.tv_state);

            et_OK_num.setTag(groupPosition);

            final PurchaseCheckBean group = (PurchaseCheckBean) getGroup(groupPosition);
            // 合格量栏位：如果返回的QC单检验否=N，则该栏位可以输入，
            // 如果不是，则该栏位不能输入；该栏位需大于等于0；
            // 如果合格量=0，则判定状态图标为P，否则为N
            if(!"N".equals(group.getQc_state())){
                et_OK_num.setOnKeyListener(null);
            }
            et_OK_num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String str = editable.toString();
                    PurchaseCheckBean myGroup = (PurchaseCheckBean) getGroup((Integer) et_OK_num.getTag());
                    if(!StringUtils.isBlank(str)){
                        if("0".equals(str)){
                            tv_state.setText("Y");
                            myGroup.setQc_state("Y");
                        }else {
                            tv_state.setText("N");
                            myGroup.setQc_state("N");
                        }
                        myGroup.setOk_qty(str);
                    }
                }
            });
            if(!StringUtils.isBlank(group.getOk_qty())){
                et_OK_num.setText(group.getOk_qty());
            }
            tv_seq.setText(group.getSeq());
            tv_item_name.setText(group.getItem_name());
            tv_item_spec.setText(group.getItem_spec());
            tv_drawing_no.setText(group.getDrawing_no());
            tv_sendCheck_num.setText(StringUtils.deleteZero(group.getQty()));
            tv_state.setText(group.getQc_state());

            LinearLayout ll_group = (LinearLayout) view.findViewById(R.id.ll_group);
            // 多出的头布局
            final LinearLayout ll_child_top = (LinearLayout) view.findViewById(R.id.ll_child_top);
            ll_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectBean = group;
                            selectedDetailBean = null;
                    if(expandLv.isGroupExpanded(groupPosition)){
                        expandLv.collapseGroup(groupPosition);
                        prePosition = -1;
                    }else{
                        expandLv.expandGroup(groupPosition);
                    }
                }
            });
            if (expandLv.isGroupExpanded(groupPosition)){
                ll_child_top.setVisibility(View.VISIBLE);
            }else{
                ll_child_top.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public boolean setStableIds() {
            return false;
        }

        @Override
        public boolean setChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}
