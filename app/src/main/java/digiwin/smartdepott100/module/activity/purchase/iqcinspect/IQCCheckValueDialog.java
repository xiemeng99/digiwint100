package digiwin.smartdepott100.module.activity.purchase.iqcinspect;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.dialog.CustomDialog;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.module.bean.purchase.CheckValueBean;
import digiwin.smartdepott100.module.bean.purchase.CheckValueCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by maoheng on 2017/8/15.
 */

public class IQCCheckValueDialog {
    private static String TAG = "IQCCheckValueDialog";

    private static CustomDialog dialog;

    private static TextView preTextView;
    private static EditText preEditText;
    private static View preView;

    private static GoBackListener listener;

    public static void setListener(GoBackListener mListener) {
        listener = mListener;
    }

    /**
     * 弹出测量值Dialog
     *
     * @Author 毛衡
     */
    public static void showBadResonDialog(final Activity context, final CheckValueBean checkValueBean, final IQCInspectLogic logic, final QCScanData qcData) {
        try {
            if (null != context) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                //Dialog
                int width = (int) (ViewUtils.getScreenWidth(context) * 0.6);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_checkvalue)
                        .style(digiwin.library.R.style.MyDialog)
                        .heightpx(height)
                        .widthpx(width)
                        .cancelTouchout(true)
                        .backCancelTouchout(true)
                        .setViewText(R.id.et_seq_no, checkValueBean.getOrder_seq())
                        .setViewText(R.id.et_check_value, checkValueBean.getMeasure_value());
                dialog = builder.build();
                dialog.show();
                /**
                 * 行序
                 */
                final TextView tv_seq_no = (TextView) builder.getView(R.id.tv_seq_no);
                final EditText et_seq_no = (EditText) builder.getView(R.id.et_seq_no);
                final View line_seq_no = builder.getView(R.id.line_seq_no);

                preTextView = tv_seq_no;
                preEditText = et_seq_no;
                preView = line_seq_no;

                et_seq_no.setKeyListener(null);

                et_seq_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_seq_no,et_seq_no,line_seq_no);
                    }
                });
                /**
                 * 测量值
                 */
                final TextView tv_check_value = (TextView) builder.getView(R.id.tv_check_value);
                final EditText et_check_value = (EditText) builder.getView(R.id.et_check_value);
                final View line_check_value = builder.getView(R.id.line_check_value);
                et_check_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_check_value,et_check_value,line_check_value);
                    }
                });
                /**
                 * 合格否
                 */
                final TextView tv_isOK = (TextView) builder.getView(R.id.tv_isOK);
                final CheckBox cb_isOK = (CheckBox) builder.getView(R.id.cb_isOK);
                final View line_isOK = builder.getView(R.id.line_isOK);
                if(AddressContants.Y.equals(checkValueBean.getResult_type())){
                    cb_isOK.setChecked(true);
                }else {
                    cb_isOK.setChecked(false);
                }

                /**
                 * 保存
                 */
                final Button save = (Button) builder.getView(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtils.isBlank(et_check_value.getText().toString().toString())){
                            AlertDialogUtils.showFailedDialog(context,R.string.checkValue_notBe_null);
                            return;
                        }

                        CheckValueCommitBean commitBean = new CheckValueCommitBean();
                        List<CheckValueBean> list = new ArrayList<CheckValueBean>();
                        checkValueBean.setDoc_no(qcData.getQc_no());
                        checkValueBean.setSeq(qcData.getSeq());
                        checkValueBean.setStatu("1");
                        checkValueBean.setOrder_seq(et_seq_no.getText().toString().trim());
                        checkValueBean.setMeasure_value(et_check_value.getText().toString().trim());
                        if(cb_isOK.isChecked()){
                            checkValueBean.setResult_type("Y");
                        }else {
                            checkValueBean.setResult_type("N");
                        }
                        list.add(checkValueBean);
                        commitBean.setData(list);
                        AlertDialogUtils.showLoadingDialog(context);
                        logic.updateIQCCheckValue(commitBean, new IQCInspectLogic.IQCSearchCheckValueListener() {
                            @Override
                            public void onSuccess(List<CheckValueBean> datas) {
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
        }catch(Exception e){
            e.printStackTrace();
            LogUtils.e(TAG, "IQCCheckValueDialog-----Error"+e);
        }

    }

    /**
     * 弹出不良原因Dialog
     *
     * @Author 毛衡
     */
    public static void showBadResonDialog(final Activity context, final IQCInspectLogic logic, final QCScanData qcData, int order_seq) {

        try {
            if (null != context) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }

                //Dialog
                int width = (int) (ViewUtils.getScreenWidth(context) * 0.6);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_checkvalue)
                        .style(digiwin.library.R.style.MyDialog)
                        .heightpx(height)
                        .widthpx(width)
                        .cancelTouchout(true)
                        .backCancelTouchout(true)
                        .setViewText(R.id.et_seq_no, StringUtils.objToString(order_seq));
                dialog = builder.build();
                dialog.show();

                final CheckValueBean checkValueBean = new CheckValueBean();

                /**
                 * 行序
                 */
                final TextView tv_seq_no = (TextView) builder.getView(R.id.tv_seq_no);
                final EditText et_seq_no = (EditText) builder.getView(R.id.et_seq_no);
                final View line_seq_no = builder.getView(R.id.line_seq_no);

                preTextView = tv_seq_no;
                preEditText = et_seq_no;
                preView = line_seq_no;

                et_seq_no.setKeyListener(null);

                et_seq_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_seq_no,et_seq_no,line_seq_no);
                    }
                });
                /**
                 * 测量值
                 */
                final TextView tv_check_value = (TextView) builder.getView(R.id.tv_check_value);
                final EditText et_check_value = (EditText) builder.getView(R.id.et_check_value);
                final View line_check_value = builder.getView(R.id.line_check_value);
                et_check_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        viewChange(context,tv_check_value,et_check_value,line_check_value);
                    }
                });
                /**
                 * 合格否
                 */
                final TextView tv_isOK = (TextView) builder.getView(R.id.tv_isOK);
                final CheckBox cb_isOK = (CheckBox) builder.getView(R.id.cb_isOK);
                final View line_isOK = builder.getView(R.id.line_isOK);

                /**
                 * 保存
                 */
                final Button save = (Button) builder.getView(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtils.isBlank(et_check_value.getText().toString().toString())){
                            AlertDialogUtils.showFailedDialog(context,R.string.checkValue_notBe_null);
                            return;
                        }

                        CheckValueCommitBean commitBean = new CheckValueCommitBean();
                        List<CheckValueBean> list = new ArrayList<CheckValueBean>();
                        checkValueBean.setDoc_no(qcData.getQc_no());
                        checkValueBean.setSeq(qcData.getSeq());
                        checkValueBean.setStatu("1");
                        checkValueBean.setOrder_seq(et_seq_no.getText().toString().trim());
                        checkValueBean.setMeasure_value(et_check_value.getText().toString().trim());
                        if(cb_isOK.isChecked()){
                            checkValueBean.setResult_type(AddressContants.Y);
                        }else {
                            checkValueBean.setResult_type(AddressContants.N);
                        }
                        list.add(checkValueBean);
                        commitBean.setData(list);
                        AlertDialogUtils.showLoadingDialog(context);
                        logic.updateIQCCheckValue(commitBean, new IQCInspectLogic.IQCSearchCheckValueListener() {
                            @Override
                            public void onSuccess(List<CheckValueBean> datas) {
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
            LogUtils.e(TAG, "IQCCheckValueDialog-----Error"+e);
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
