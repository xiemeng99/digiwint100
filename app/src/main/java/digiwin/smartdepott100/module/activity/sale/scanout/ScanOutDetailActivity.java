package digiwin.smartdepott100.module.activity.sale.scanout;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.ExpandAdapter;
import digiwin.smartdepott100.module.bean.sale.scanout.ScanOutDetailData;
import digiwin.smartdepott100.module.bean.sale.scanout.ScanOutDetailKeyBean;
import digiwin.smartdepott100.module.logic.sale.scanout.ScanOutLogic;
import digiwin.smartdepott100.module.logic.sale.scanoutstore.ScanOutStoreLogic;

/**
 * @author 毛衡
 * @des 扫码出货 明细页
 * @date 2017/4/5
 */

public class ScanOutDetailActivity extends BaseTitleActivity {

    private String itemNo;

    private ScanOutLogic storeLogic;

    private String mode;

    private LinkedHashMap<ScanOutDetailKeyBean, List<ScanOutDetailData>> dataMap;

    private ScanOutDetailAdapter adapter;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;
    @BindView(R.id.ll_group)
    LinearLayout ll_group;
    @BindView(R.id.tv_title_operation)
    TextView tvCheckAll;

    private boolean isChecked = false;

    @OnClick(R.id.tv_title_operation)
    void checkAll() {
        if (!isChecked) {
            adapter = new ScanOutDetailAdapter(dataMap, true);
            expandLv.setAdapter(adapter);
        } else {
            adapter = new ScanOutDetailAdapter(dataMap, false);
            expandLv.setAdapter(adapter);
        }
        isChecked = !isChecked;
    }

    @BindView(R.id.expand_lv)
    ExpandableListView expandLv;
    @BindView(R.id.delete)
    Button delete;

    @OnClick(R.id.delete)
    void delete() {
        showLoadingDialog();
        List<ScanOutDetailData> list = adapter.getCheckedList();
        List<String> cases = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!cases.contains(list.get(i).getPackage_no())) {
                cases.add(list.get(i).getPackage_no());
            }
        }
        dataMap = adapter.getAllData();
        List<Map<String, String>> maps = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put(AddressContants.PACKAGE_NO, cases.get(i));
            maps.add(map);
        }
        storeLogic.deleteScanOutDetailData(maps, new ScanOutLogic.DeleteScanOutDetailDataListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showToast(msg);
                getListData();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        mode = ModuleCode.SCANOUTSTORE;
        return mode;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_scanout_detail;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.inbinning_detail);
        ivScan.setVisibility(View.GONE);
        tvCheckAll.setText(R.string.check_all);
        tvCheckAll.setVisibility(View.VISIBLE);
    }

    @Override
    protected void doBusiness() {
        storeLogic = ScanOutLogic.getInstance(activity, mode, mTimestamp.toString());
        itemNo = getIntent().getExtras().getString(AddressContants.ITEM_NO);
        dataMap = new LinkedHashMap<>();
        adapter = new ScanOutDetailAdapter(dataMap, false);
        getListData();
    }


    private void getListData() {
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put(AddressContants.ITEM_NO, itemNo);
        storeLogic.getScanOutDetailData(map, new ScanOutLogic.GetScanOutDetailDataListener() {
            @Override
            public void onSuccess(List<ScanOutDetailData> datas) {
                dismissLoadingDialog();
                if (datas.size() > 0) {
                    ll_group.setVisibility(View.VISIBLE);
                    dataMap = new LinkedHashMap<>();
                    for (int i = 0; i < datas.size(); i++) {
                        ScanOutDetailData detailData = datas.get(i);
                        ScanOutDetailKeyBean keyBean = new ScanOutDetailKeyBean();
                        keyBean.setStorage_spaces_no(detailData.getStorage_spaces_no());
                        keyBean.setPackage_no(detailData.getPackage_no());
                        if (dataMap.get(keyBean) == null) {
                            ArrayList<ScanOutDetailData> tempList = new ArrayList<ScanOutDetailData>();
                            tempList.add(detailData);
                            dataMap.put(keyBean, tempList);
                        } else {
                            List<ScanOutDetailData> tempList = dataMap.get(keyBean);
                            tempList.add(detailData);
                            dataMap.put(keyBean, tempList);
                        }
                    }
                    adapter = new ScanOutDetailAdapter(dataMap, false);
                    expandLv.setAdapter(adapter);
                    expandLv.setGroupIndicator(null);
                } else {
                    ll_group.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                ll_group.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class ScanOutDetailAdapter extends ExpandAdapter<ScanOutDetailKeyBean, ScanOutDetailData> {


        public ScanOutDetailAdapter(LinkedHashMap<ScanOutDetailKeyBean, List<ScanOutDetailData>> data, boolean b) {
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
        public void editChildTextChanged(Editable arg0, EditText et, ScanOutDetailData t, int groupId, int childId) {

        }

        @Override
        public View setChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(activity).inflate(R.layout.expand_scanout_child, null);
            TextView tv_barcode = (TextView) view.findViewById(R.id.tv_barcode);
            EditText et_num = (EditText) view.findViewById(R.id.et_num);
            LinearLayout ll_child = (LinearLayout) view.findViewById(R.id.ll_child);
            ScanOutDetailKeyBean keyBean = (ScanOutDetailKeyBean) getGroup(groupPosition);
            if (childPosition == (dataMap.get(keyBean).size() - 1)) {
                ll_child.setBackgroundResource(R.drawable.expand_child_bottom);
            } else {
                ll_child.setBackgroundResource(R.drawable.expand_child_center);
            }
            if (groupPosition == dataMap.size() - 1) {
                if (childPosition == (dataMap.get(keyBean).size() - 1)) {
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.setMargins(0, 0, 0, 20);
                    ll_child.setLayoutParams(ll);
                }
            }
            et_num.setEnabled(false);
            ScanOutDetailData child = (ScanOutDetailData) getChild(groupPosition, childPosition);
            tv_barcode.setText(child.getPackage_no());
            et_num.setText(StringUtils.deleteZero(child.getScan_sumqty()));
            return view;
        }

        @Override
        public long setGroupId(int arg0) {
            return arg0;
        }

        @Override
        public View setGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(activity).inflate(
                    R.layout.expand_scanout_group, null);
            TextView tv_case_no = (TextView) view.findViewById(R.id.tv_case_no);
            TextView tv_locator = (TextView) view.findViewById(R.id.tv_locator);
            LinearLayout ll_group = (LinearLayout) view.findViewById(R.id.ll_group);
            final LinearLayout ll_child_top = (LinearLayout) view.findViewById(R.id.ll_child_top);

            ll_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandLv.isGroupExpanded(groupPosition)) {
                        expandLv.collapseGroup(groupPosition);
                    } else {
                        expandLv.expandGroup(groupPosition);
                    }
                }
            });
            if (expandLv.isGroupExpanded(groupPosition)) {
                ll_child_top.setVisibility(View.VISIBLE);
            } else {
                ll_child_top.setVisibility(View.GONE);
            }
            ScanOutDetailKeyBean group = (ScanOutDetailKeyBean) getGroup(groupPosition);
            tv_case_no.setText(group.getPackage_no());
            tv_locator.setText(group.getStorage_spaces_no());
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
