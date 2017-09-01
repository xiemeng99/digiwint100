package digiwin.library.utils;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import digiwin.library.R;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogClickgetTextListener;
import digiwin.library.dialog.OnDialogTwoListener;


/**
 * Created by ChangquanSun
 * 2017/1/24
 */

public class AlertDialogUtils {

    private static CustomDialog dialog;
    private static final String TAG = "AlertDialogUtils";

    /**
     * 显示提交成功的dialog,没有点击按钮
     */
    public static void showCommitSuccessNoClickDialog(Context context, Object content) {
        try {
            if (context != null) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_commit_success_noclick)
                        .style(R.style.CustomDialog)
                        .cancelTouchout(false);
                ((TextView) builder.getView(R.id.tv_commit_success_content)).setMovementMethod(ScrollingMovementMethod.getInstance());
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showCommitSuccessDialog-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showCommitSuccessDialog-----Error");
        }
    }
    /**
     * 显示提交成功的dialog
     */
    public static void showCommitSuccessDialog(Context context, Object content) {
        try {
            if (context != null) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_commit_success)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                ((TextView) builder.getView(R.id.tv_commit_success_content)).setMovementMethod(ScrollingMovementMethod.getInstance());
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showCommitSuccessDialog-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showCommitSuccessDialog-----Error");
        }
    }

    /**
     * 显示提交成功的dialog(有回调)
     */
    public static void showCommitSuccessDialogAndCall(Context context, Object content, final OnDialogClickListener listener) {
        try {
            if (context != null) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_commit_success)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null) listener.onCallback();
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                ((TextView) builder.getView(R.id.tv_commit_success_content)).setMovementMethod(ScrollingMovementMethod.getInstance());
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showCommitSuccessDialogAndCall-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showCommitSuccessDialogAndCall-----Error");
        }
    }

    /**
     * 显示提交失败的dialog
     */
    public static void showCommitFailDialog(Context context, Object content) {
        try {
            if (context != null) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_commit_fail)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                ((TextView) builder.getView(R.id.tv_commit_success_content)).setMovementMethod(ScrollingMovementMethod.getInstance());
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showCommitFailDialog-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showCommitFailDialog-----Error");
        }
    }

    /**
     * 显示提交失败的dialog(有回调)
     */
    public static void showCommitFailDialogAndCall(Context context, Object content, final OnDialogClickListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_commit_fail)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null) listener.onCallback();
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                ((TextView) builder.getView(R.id.tv_commit_success_content)).setMovementMethod(ScrollingMovementMethod.getInstance());
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showCommitFailDialogAndCall-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showCommitFailDialogAndCall-----Error");
        }
    }

    /**
     * 显示退出界面的dialog
     */
    public static void showExitDialog(final Context context, final Object content) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_exit)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.btn_ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((Activity) context).finish();
                                dialog.dismiss();
                            }
                        }).addViewOnclick(R.id.btn_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        }).cancelTouchout(false)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.6))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_title, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_title, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showExitDialog-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showExitDialog-----Error");
        }
    }

    /**
     * 弹出确定取消对话框
     */
    public static void showSureOrQuitDialogAndCall(Context context, Object content, final OnDialogTwoListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_exit)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.btn_ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                    listener.onCallback1();
                            }
                        }).addViewOnclick(R.id.btn_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.cancel();
                                    listener.onCallback2();
                            }
                        }).cancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);

                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_title, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_title, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showSureOrQuitDialogAndCall-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showSureOrQuitDialogAndCall-----Error");
        }
    }

    public static CustomDialog editDialog;

    /**
     * 获取dialog上的文字(有回调)
     */
    public static void showEditDialogAndCall(String text, Context context, final OnDialogClickgetTextListener listener) {
        try {
            if (context != null) {
                if (editDialog != null) {
                    editDialog.dismiss();
                    editDialog = null;
                }
                final CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_updatenumber)
                        .style(R.style.CustomDialog)
                        .setViewText(R.id.et_text, text);
                String editText = builder.getViewText(R.id.et_text);
                builder.addViewOnclick(R.id.btn_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String editText = builder.getViewText(R.id.et_text);
                        if (listener != null) listener.onCallback(editDialog, editText);
                    }
                }).addViewOnclick(R.id.add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editText = builder.getViewText(R.id.et_text);
                        float v1 = StringUtils.string2Float(editText) + 1;
                        builder.setViewText(R.id.et_text, StringUtils.deleteZero(String.valueOf(v1)));
                    }
                }).addViewOnclick(R.id.minus, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editText = builder.getViewText(R.id.et_text);
                        float v1 = StringUtils.string2Float(editText) - 1;
                        if (v1 > 0) {
                            builder.setViewText(R.id.et_text, StringUtils.deleteZero(String.valueOf(v1)));
                        }
                    }
                }).cancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.6))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                editDialog = builder.build();
                editDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showSureOrQuitDialogAndCall-----Error");
        }
    }
    /**
     * 显示火箭加载的dialog
     */
    public static void showRocketLoadingDialog(Context context) {
        try {
            if (context != null) {
                CustomDialog.Builder builder = new CustomDialog.Builder(context);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                dialog = builder.view(R.layout.dialog_rocket_loading)
                        .style(R.style.RocketDialog)
                        .cancelTouchout(false)
                        .widthpx((int) (ViewUtils.getScreenWidth(context)))
                        .heightpx(ViewGroup.LayoutParams.MATCH_PARENT)
                        .startViewAnimdrawable(R.id.iv_loading)
                        .backCancelTouchout(false)
                        .build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showLoadingDialog-----Error");
        }
    }

    /**
     * 显示加载等待的dialog
     */
    public static void showLoadingDialog(Context context) {
        try {
            if (context != null) {
                CustomDialog.Builder builder = new CustomDialog.Builder(context);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                dialog = builder.view(R.layout.dialog_wait)
                        .style(R.style.CustomDialog)
                        .cancelTouchout(false)
                        .widthpx((int) (ViewUtils.getScreenWidth(context)))
                        .heightpx(ViewGroup.LayoutParams.MATCH_PARENT)
                        .startViewAnim(R.id.iv_wait_dialog_circle)
                        .startViewAnimdrawable(R.id.wait_dialog_iv)
                        .backCancelTouchout(false)
                        .build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showLoadingDialog-----Error");
        }
    }

    /**
     * 显示网络连接成功的dialog,Object需是string或int型
     */
    public static void showNetWorkSuccessDialog(Context context, final Object content) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_network)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showNetWorkSuccessDialog-----content类型Error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showNetWorkSuccessDialog-----Error");
        }
    }

    /**
     * 显示失败的dialog
     */
    public static void showFailedDialog(Context context, final Object content) {

        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_network_error)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                TextView mTv_text = (TextView) builder.getView(R.id.tv_commit_success_content);
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showFailedDialog-----content类型Error");
                }
                String s = mTv_text.getText().toString();
                if (null!=s&&(s.contains("!")||s.contains("！"))){
                    mTv_text.setTextColor(context.getResources().getColor(R.color.red50));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            LogUtils.e(TAG, "showFailedDialog-----Error");

        }
    }

    /**
     * 显示失败的dialog
     */
    public static void showFailedDialog(Context context, final Object content, final OnDialogClickListener listener) {

        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_network_error)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.button_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (null != listener) {
                                    listener.onCallback();
                                }
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false);
                TextView mTv_text = (TextView) builder.getView(R.id.tv_commit_success_content);
                if (content instanceof String) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (String) content)
                            .build();
                    dialog.show();
                } else if (content instanceof Integer) {
                    dialog = builder.setViewText(R.id.tv_commit_success_content, (Integer) content)
                            .build();
                    dialog.show();
                } else {
                    LogUtils.e(TAG, "showFailedDialog-----content类型Error");
                }
                String s = mTv_text.getText().toString();
                if (null!=s&&(s.contains("!")||s.contains("！"))){
                    mTv_text.setTextColor(context.getResources().getColor(R.color.red50));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            LogUtils.e(TAG, "showFailedDialog-----Error");

        }
    }


    /**
     * 账号管理用户注销确定弹框dialog
     */
    public static void showSettingExitDialog(Context context, final OnDialogClickListener listener) {
        try {
            if (context != null) {
                CustomDialog.Builder builder = new CustomDialog.Builder(context);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                dialog = builder.view(R.layout.dialog_userexit)
                        .style(R.style.MyDialog)
                        .addViewOnclick(R.id.rl_userInfo_exit, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.tv_userInfo_yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null) {
                                    listener.onCallback();
                                }
                                dialog.dismiss();
                            }
                        }).cancelTouchout(false)
                        .build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showSettingExitDialog-----Error");
        }
    }

    /**
     * 数值选择器
     */
    public static void showNumDialog(Context context, int mini, int max, String oldnum, final OnDialogClickgetTextListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.numberpickerlayout)
                        .style(R.style.MyDialog);
                final NumberPicker numPicker = (NumberPicker) builder.getView(R.id.num_picker);
                numPicker.setMinValue(mini);
                numPicker.setMaxValue(max);
                numPicker.setValue(Integer.valueOf(oldnum));
                numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                builder.addViewOnclick(R.id.tv_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            String newnum = String.valueOf(numPicker.getValue());
                            listener.onCallback(dialog, newnum);
                        }
                        dialog.dismiss();
                    }
                }).cancelTouchout(true);
                dialog = builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showSettingExitDialog-----Error" + e);
        }
    }

    /**
     * 隐藏等待dialog
     */
    public static void dismissDialog() {
        try{
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }}catch (Exception e){
            LogUtils.e(TAG, "dismissDialog-----Error" + e);
        }
    }

    /**
     * 隐藏editDialog
     */
    public static void dismissEditDialog() {
        if (editDialog != null && editDialog.isShowing()) {
            editDialog.dismiss();
            editDialog = null;
        }
    }
}
