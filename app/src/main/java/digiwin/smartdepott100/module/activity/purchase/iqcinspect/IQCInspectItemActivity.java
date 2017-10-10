package digiwin.smartdepott100.module.activity.purchase.iqcinspect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeHActivity;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.module.bean.purchase.IQCCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by maoheng on 2017/8/11.
 */

public class IQCInspectItemActivity extends BaseFirstModuldeHActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_delivery_num)
    TextView tvDeliveryNum;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.commit)
    Button commit;

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

    private void sureCommit() {
        IQCCommitBean commitBean = new IQCCommitBean();
        List<IQCCommitBean.IQCCommitItemBean> itemList = new ArrayList<>();
        for (int i = 0; i < qcList.size(); i++) {
            if (StringUtils.isBlank(qcList.get(i).getDefect_qty())){
                showCommitFailDialog(qcList.get(i).getInspection_item()+getString(R.string.defect_nonull));
                return;
            }
            if (StringUtils.isBlank(qcList.get(i).getDefect_reason_qty())){
                showCommitFailDialog(qcList.get(i).getInspection_item()+getString(R.string.defect_qty_nonull));
                return;
            }
            IQCCommitBean.IQCCommitItemBean itemBean = new IQCCommitBean.IQCCommitItemBean();
            itemBean.setDoc_no(scanData.getQc_no());
            itemBean.setSeq(qcList.get(i).getSeq());
            itemBean.setRemark(qcList.get(i).getRemark());
            itemBean.setDefect_qty(qcList.get(i).getDefect_qty());
            itemBean.setDefect_reason_qty(qcList.get(i).getDefect_reason_qty());
            itemBean.setQc_result(qcList.get(i).getQc_result());
            itemList.add(itemBean);
        }
        commitBean.setData(itemList);
        showLoadingDialog();
        logic.commitIQC(commitBean, new IQCInspectLogic.IQCCommitListener() {
            @Override
            public void onSuccess(String result) {
                dismissLoadingDialog();
                showCommitSuccessDialog(result, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        activity.finish();
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

    private IQCInspectLogic logic;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_iqc_inspectitem;
    }

    private QCScanData scanData;

    private List<QCScanData> qcList;

    private IQCInspectItemAdapter adapter;

    @Override
    protected void doBusiness() {
        Bundle extras = getIntent().getExtras();
        scanData = (QCScanData) extras.getSerializable(IQCInspectListActivity.DATA);
        tvSupplier.setText(scanData.getSupplier_no() + "  " + scanData.getSupplier_name());
        tvItemNo.setText(scanData.getItem_no());
        tvItemName.setText(scanData.getItem_name());
        tvDeliveryNum.setText(scanData.getQty());
        logic = IQCInspectLogic.getInstance(activity, module, mTimestamp.toString());
        qcList = new ArrayList<>();
        adapter = new IQCInspectItemAdapter(activity, qcList);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(manager);
        ryList.setAdapter(adapter);
        mHandler.removeMessages(GETLISTWHAT);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(GETLISTWHAT), AddressContants.DELAYTIME);
    }

    private final int GETLISTWHAT = 1234;

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GETLISTWHAT:
                    showLoadingDialog();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("qc_no", scanData.getQc_no());
                    logic.getIQCInspectDatas(map, new IQCInspectLogic.IQCInspectListener() {
                        @Override
                        public void onSuccess(List<QCScanData> datas) {
                            dismissLoadingDialog();
                            for (int i = 0; i < datas.size(); i++) {
                                datas.get(i).setItem_no(scanData.getItem_no());
                                datas.get(i).setItem_name(scanData.getItem_name());
                            }
                            qcList.clear();
                            qcList.addAll(datas);
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View itemView, int position) {
                                    clickItem(qcList.get(position));
                                }
                            });
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            qcList = new ArrayList<QCScanData>();
                            adapter = new IQCInspectItemAdapter(activity, qcList);
                            ryList.setAdapter(adapter);
                            showFailedDialog(error);
                        }
                    });
                    break;
            }
            return false;
        }
    };

    public static final String INTENTTAG = "initTag";

    public static final String TITLE = "title";

    private static final int REQUSETQODE = 1234;

    private void clickItem(QCScanData qcData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENTTAG, qcData);
        bundle.putString("code", module);
        bundle.putString(TITLE, getString(R.string.iqc_change));
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        ActivityManagerUtils.startActivityBundleForResult(activity, IQCInspectActivity.class, bundle, REQUSETQODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSETQODE) {
            qcList = new ArrayList<QCScanData>();
            adapter = new IQCInspectItemAdapter(activity, qcList);
            ryList.setAdapter(adapter);
            mHandler.removeMessages(GETLISTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(GETLISTWHAT), AddressContants.DELAYTIME);
        }
    }

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.iqc_check_pad);
        toolbarTitle.setBackgroundResource(R.drawable.title_bg);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.IQCINSPECT;
        return module;
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    class IQCInspectItemAdapter extends BaseRecyclerAdapter<QCScanData> {

        public IQCInspectItemAdapter(Context ctx, List<QCScanData> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_iqcinspectitem;
        }

        @Override
        protected void bindData(final RecyclerViewHolder holder, final int position, QCScanData item) {
            holder.setText(R.id.tv_lineSequence, item.getSeq());
            holder.setText(R.id.tv_check_item, item.getInspection_item());
            holder.setText(R.id.tv_check_item_instruction, item.getInspection_item_name());
            holder.setText(R.id.tv_allow_num, StringUtils.deleteZero(item.getAc_qty()));
            holder.setText(R.id.tv_reject_num, StringUtils.deleteZero(item.getRe_qty()));
            holder.setText(R.id.tv_sample_num, StringUtils.deleteZero(item.getSample_qty()));
//            holder.setText(R.id.et_defect_num,item.getDefect_qty());
//            holder.setText(R.id.et_bad_num,item.getDefect_reason_qty());
            if (holder.getEditText(R.id.et_defect_num).getTag() instanceof TextWatcher) {
                holder.getEditText(R.id.et_defect_num).removeTextChangedListener((TextWatcher) holder.getEditText(R.id.et_defect_num).getTag());
            }
            holder.setText(R.id.et_defect_num, StringUtils.deleteZero(item.getDefect_qty()));
            TextWatcher etDefectTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    qcList.get(position).setDefect_qty(editable.toString());
                }
            };
            holder.getEditText(R.id.et_defect_num).addTextChangedListener(etDefectTextWatcher);
            holder.getEditText(R.id.et_defect_num).setTag(etDefectTextWatcher);

            if (holder.getEditText(R.id.et_bad_num).getTag() instanceof TextWatcher) {
                holder.getEditText(R.id.et_bad_num).removeTextChangedListener((TextWatcher) holder.getEditText(R.id.et_bad_num).getTag());
            }
            holder.setText(R.id.et_bad_num, StringUtils.deleteZero(item.getDefect_reason_qty()));
            TextWatcher etBadNumTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    qcList.get(position).setDefect_reason_qty(editable.toString());
                    if (!StringUtils.isBlank(qcList.get(position).getRe_qty())) {
                        if (StringUtils.string2Float(editable.toString()) > StringUtils.string2Float(qcList.get(position).getRe_qty())) {
                            qcList.get(position).setQc_result("2");
                            holder.setText(R.id.tv_project_check, mContext.getResources().getString(R.string.unOK));
                            holder.getTextView(R.id.tv_project_check).setTextColor(mContext.getResources().getColor(R.color.Base_color));
                        } else {
                            qcList.get(position).setQc_result("1");
                            holder.setText(R.id.tv_project_check, mContext.getResources().getString(R.string.OK));
                            holder.getTextView(R.id.tv_project_check).setTextColor(mContext.getResources().getColor(R.color.result_points));
                        }
                    }
                }
            };
            holder.getEditText(R.id.et_bad_num).addTextChangedListener(etBadNumTextWatcher);
            holder.getEditText(R.id.et_bad_num).setTag(etBadNumTextWatcher);


            String qc_result = item.getQc_result();
            if ("1".equals(qc_result)) {
                holder.setText(R.id.tv_project_check, mContext.getResources().getString(R.string.OK));
                holder.getTextView(R.id.tv_project_check).setTextColor(mContext.getResources().getColor(R.color.result_points));
            } else {
                holder.setText(R.id.tv_project_check, mContext.getResources().getString(R.string.unOK));
                holder.getTextView(R.id.tv_project_check).setTextColor(mContext.getResources().getColor(R.color.Base_color));
            }

        }
    }

}
