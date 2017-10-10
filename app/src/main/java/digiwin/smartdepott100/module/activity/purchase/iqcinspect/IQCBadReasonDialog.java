package digiwin.smartdepott100.module.activity.purchase.iqcinspect;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.popupwindow.CustomPopWindow;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.adapter.purchase.BadReasonPopAdapter;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.BadReasonCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by maoheng on 2017/8/15.
 */

public class IQCBadReasonDialog {
    private static String TAG = "IQCBadReasonDialog";
    private static CustomDialog dialog;

    private static TextView preTextView;
    private static EditText preEditText;
    private static View preView;

    private static GoBackListener listener;

    public static void setListener(GoBackListener mListener) {
        listener = mListener;
    }

    /**
     * 弹出不良原因Dialog
     *
     * @Author 毛衡
     */
    public static void showBadResonDialog(final Activity context, final BadReasonBean badReasonBean, final IQCInspectLogic logic, final QCScanData qcData) {
        try {
            if(null!=context){
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                //Dialog
                int width = (int) (ViewUtils.getScreenWidth(context) * 0.6);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_badreason)
                        .style(digiwin.library.R.style.MyDialog)
                        .heightpx(height)
                        .widthpx(width)
                        .cancelTouchout(true)
                        .backCancelTouchout(true)
                        .setViewText(R.id.et_defectrea, badReasonBean.getDefect_reason_name())
                        .setViewText(R.id.et_bad_num, badReasonBean.getDefect_reason_qty())
                        .setViewText(R.id.et_remark, badReasonBean.getRemark())
                        .setViewText(R.id.et_defect_num, badReasonBean.getQty());
                dialog = builder.build();
                dialog.show();
                //缺点原因
                final TextView tv_defectrea = (TextView) builder.getView(R.id.tv_defectrea);
                final EditText et_defectrea = (EditText) builder.getView(R.id.et_defectrea);
                final View line_defectrea = builder.getView(R.id.line_defectrea);

                final View imgDelete=  builder.getView(R.id.img_delete);
                imgDelete.setVisibility(View.GONE);
                preTextView = tv_defectrea;
                preEditText = et_defectrea;
                preView = line_defectrea;

                et_defectrea.setKeyListener(null);


                et_defectrea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_defectrea,et_defectrea,line_defectrea);
                    }
                });
                //缺点数量
                final TextView tv_defect_num = (TextView) builder.getView(R.id.tv_defect_num);
                final EditText et_defect_num = (EditText) builder.getView(R.id.et_defect_num);
                final View line_defect_num = builder.getView(R.id.line_defect_num);
                et_defect_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_defect_num,et_defect_num,line_defect_num);
                    }
                });
                //不良数量
                final TextView tv_bad_num = (TextView) builder.getView(R.id.tv_bad_num);
                final EditText et_bad_num = (EditText) builder.getView(R.id.et_bad_num);
                final View line_bad_num = builder.getView(R.id.line_bad_num);
                et_bad_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_bad_num,et_bad_num,line_bad_num);
                    }
                });
                //备注
                final TextView tv_remark = (TextView) builder.getView(R.id.tv_remark);
                final EditText et_remark = (EditText) builder.getView(R.id.et_remark);
                final View line_remark = builder.getView(R.id.line_remark);
                et_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_remark,et_remark,line_remark);
                    }
                });

                //保存
                final Button save = (Button) builder.getView(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtils.isBlank(et_defect_num.getText().toString())||"0".equals(et_defect_num.getText().toString().trim())){
                            AlertDialogUtils.showFailedDialog(context,R.string.defectNum_not_zero);
                            return;
                        }
                        if(StringUtils.string2Float(qcData.getSample_qty())<StringUtils.string2Float(et_bad_num.getText().toString().trim())){
                            AlertDialogUtils.showFailedDialog(context,R.string.badNum_big_sampleNum);
                            return;
                        }
                        BadReasonCommitBean commitBean = new BadReasonCommitBean();
                        List<BadReasonBean> list = new ArrayList<BadReasonBean>();
                        badReasonBean.setDoc_no(qcData.getQc_no());
                        badReasonBean.setSeq(qcData.getSeq());
                        badReasonBean.setStatu("1");
                        badReasonBean.setQty(et_defect_num.getText().toString().trim());
                        badReasonBean.setRemark(et_remark.getText().toString().trim());
                        badReasonBean.setDefect_reason_qty(et_bad_num.getText().toString().trim());
                        list.add(badReasonBean);
                        commitBean.setData(list);
                        AlertDialogUtils.showLoadingDialog(context);
                        logic.upDateIQCBadReason(commitBean, new IQCInspectLogic.IQCUpDateBadReasonListener() {
                            @Override
                            public void onSuccess(List<BadReasonBean> datas) {
                                AlertDialogUtils.dismissDialog();
                                dialog.dismiss();
                                if (listener!=null){
                                    listener.goBack();
                                }
                            }

                            @Override
                            public void onFailed(String error) {
                                AlertDialogUtils.dismissDialog();
                                AlertDialogUtils.showFailedDialog(context,error);
                            }
                        });
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(TAG, "IQCBadReasonDialog-----Error"+e);
        }
    }

    /**
     * 弹出不良原因Dialog
     *
     * @Author 毛衡
     */
    public static void showBadResonDialog(final Activity context, final IQCInspectLogic logic, final QCScanData qcData) {
        try {
            if(null!=context){
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                final BadReasonBean badReasonBean = new BadReasonBean();

                //弹出Dialog
                int width = (int) (ViewUtils.getScreenWidth(context) * 0.6);
                int height =WindowManager.LayoutParams.WRAP_CONTENT;

                CustomDialog.Builder dialogBuilder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_badreason)
                        .style(digiwin.library.R.style.MyDialog)
                        .heightpx(height)
                        .widthpx(width)
                        .cancelTouchout(true)
                        .backCancelTouchout(true);
                dialog = dialogBuilder.build();
                dialog.show();
                //缺点原因
                final TextView tv_defectrea = (TextView) dialogBuilder.getView(R.id.tv_defectrea);
                final EditText et_defectrea = (EditText) dialogBuilder.getView(R.id.et_defectrea);
                final View line_defectrea = dialogBuilder.getView(R.id.line_defectrea);
                final View imgDelete=  dialogBuilder.getView(R.id.img_delete);
                //缺点数量
                final TextView tv_defect_num = (TextView) dialogBuilder.getView(R.id.tv_defect_num);
                final EditText et_defect_num = (EditText) dialogBuilder.getView(R.id.et_defect_num);
                final View line_defect_num = dialogBuilder.getView(R.id.line_defect_num);
                et_defect_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_defect_num,et_defect_num,line_defect_num);
                    }
                });
                preTextView = tv_defectrea;
                preEditText = et_defectrea;
                preView = line_defectrea;

                CustomPopWindow.PopupWindowBuilder builder = new CustomPopWindow.PopupWindowBuilder(context);
                final CustomPopWindow customPopWindow = builder
                        .setView(R.layout.popwindow_iqcbadreason)
                        .setFocusable(false)
                        .size((int) (ViewUtils.getScreenWidth(context) * 0.45),WindowManager.LayoutParams.WRAP_CONTENT)
                        .create();

                View view2 = customPopWindow.getmPopupWindow().getContentView();
                final RecyclerView ry_list = (RecyclerView) view2.findViewById(R.id.ry_list);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                ry_list.setLayoutManager(manager);
                final List<BadReasonBean> badReasonBeanList = new ArrayList<>();

                et_defectrea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_defectrea,et_defectrea,line_defectrea);
                    }
                });
                et_defectrea.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!StringUtils.isBlank(editable.toString())){
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("defect_reason_zm",editable.toString());
                            logic.searchIQCBadReason(map, new IQCInspectLogic.IQCSearchBadReasonListener() {
                                @Override
                                public void onSuccess(List<BadReasonBean> datas) {
                                    badReasonBeanList.clear();
                                    badReasonBeanList.addAll(datas);
                                    BadReasonPopAdapter adapter = new BadReasonPopAdapter(context,badReasonBeanList);
                                    ry_list.setAdapter(adapter);
//                                    et_defectrea.requestFocus();
                                    adapter.setOnItemClickListener(new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View itemView, int position) {
                                            et_defectrea.setText(badReasonBeanList.get(position).getDefect_reason_name());
                                            et_defectrea.setKeyListener(null);
                                            badReasonBean.setDefect_reason(badReasonBeanList.get(position).getDefect_reason());
                                            badReasonBean.setDefect_reason_name(badReasonBeanList.get(position).getDefect_reason_name());
                                            customPopWindow.dissmiss();
                                            et_defect_num.requestFocus();
                                        }
                                    });
                                }

                                @Override
                                public void onFailed(String error) {
                                    badReasonBeanList.clear();
                                    BadReasonPopAdapter adapter = new BadReasonPopAdapter(context,badReasonBeanList);
                                    ry_list.setAdapter(adapter);
                                }
                            });
                            customPopWindow.showAsDropDown(et_defectrea,0,10);
                        }
                    }
                });
                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_defectrea.setText("");
                        et_defectrea.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                        et_defectrea.requestFocus();
                    }
                });

                //不良数量
                final TextView tv_bad_num = (TextView) dialogBuilder.getView(R.id.tv_bad_num);
                final EditText et_bad_num = (EditText) dialogBuilder.getView(R.id.et_bad_num);
                final View line_bad_num = dialogBuilder.getView(R.id.line_bad_num);
                et_bad_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_bad_num,et_bad_num,line_bad_num);
                    }
                });
                //备注
                final TextView tv_remark = (TextView) dialogBuilder.getView(R.id.tv_remark);
                final EditText et_remark = (EditText) dialogBuilder.getView(R.id.et_remark);
                final View line_remark = dialogBuilder.getView(R.id.line_remark);
                et_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_remark,et_remark,line_remark);
                    }
                });

                //保存
                final Button save = (Button) dialogBuilder.getView(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtils.isBlank(badReasonBean.getDefect_reason())){
                            AlertDialogUtils.showFailedDialog(context,R.string.choose_defectrea_first);
                            return;
                        }
                        if(StringUtils.isBlank(et_defect_num.getText().toString())||"0".equals(et_defect_num.getText().toString().trim())){
                            AlertDialogUtils.showFailedDialog(context,R.string.defectNum_not_zero);
                            return;
                        }
                        if(StringUtils.string2Float(qcData.getSample_qty())<StringUtils.string2Float(et_bad_num.getText().toString().trim())){
                            AlertDialogUtils.showFailedDialog(context,R.string.badNum_big_sampleNum);
                            return;
                        }
                        BadReasonCommitBean commitBean = new BadReasonCommitBean();
                        List<BadReasonBean> list = new ArrayList<BadReasonBean>();
                        badReasonBean.setDoc_no(qcData.getQc_no());
                        badReasonBean.setSeq(qcData.getSeq());
                        badReasonBean.setStatu("1");
                        badReasonBean.setQty(et_defect_num.getText().toString().trim());
                        badReasonBean.setRemark(et_remark.getText().toString().trim());
                        badReasonBean.setDefect_reason_qty(et_bad_num.getText().toString().trim());
                        list.add(badReasonBean);
                        commitBean.setData(list);
                        AlertDialogUtils.showLoadingDialog(context);
                        logic.upDateIQCBadReason(commitBean, new IQCInspectLogic.IQCUpDateBadReasonListener() {
                            @Override
                            public void onSuccess(List<BadReasonBean> datas) {
                                AlertDialogUtils.dismissDialog();
                                dialog.dismiss();
                                if (listener!=null){
                                    listener.goBack();
                                }
                            }

                            @Override
                            public void onFailed(String error) {
                                AlertDialogUtils.dismissDialog();
                                AlertDialogUtils.showFailedDialog(context,error);
                            }
                        });
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(TAG, "IQCBadReasonDialog-----Error"+e);
        }
    }

    private static void viewChange(Activity context, TextView textView, EditText editText, View view){
        preTextView.setTextColor(context.getResources().getColor(R.color.black32));
        preEditText.setTextColor(context.getResources().getColor(R.color.black32));
        preView.setBackgroundColor(context.getResources().getColor(R.color.BG_ALL));

        textView.setTextColor(context.getResources().getColor(R.color.light_orangeYellow));
        editText.setTextColor(context.getResources().getColor(R.color.light_orangeYellow));
        view.setBackgroundColor(context.getResources().getColor(R.color.light_orangeYellow));

        preTextView = textView;
        preEditText = editText;
        preView = view;
    }

    public interface GoBackListener{
        void goBack();
    }
}
