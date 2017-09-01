package digiwin.smartdepott100.core.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

import static digiwin.smartdepott100.core.base.BaseFirstModuldeHActivity.ExitMode.EXITD;


/**
 * @author xiemeng
 * @des 是否需要调用清空未保存数据时调用
 * @date 2017/3/9
 */
public abstract class BaseFirstModuldeHActivity extends BaseTitleHActivity {
    private static CustomDialog mDialog;
    @Override
    public void onBackPressed() {
        showQuitDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                isShowExitDialogAndDelete(exitOrDel());
            }

            @Override
            public void onCallback2() {

            }
        });
    }

    /**
     * 子类执行弹出选择框是否删除ExitMode.EXITISD
     * 或者退出ExitMode.EXITD
     */
    public abstract ExitMode exitOrDel();

    /**
     * 是否退出
     */
    private void isShowExitDialogAndDelete(ExitMode flag) {
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.FLAG, flag.name);
        CommonLogic commonLogic = CommonLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
        showLoadingDialog();
        commonLogic.exit(map, new CommonLogic.ExitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                if (AddressContants.ISDELETE.equals(msg)) {
                    delete();
                } else {
                    activity.finish();
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 删除且退出或者直接退出
     */
    private void delete() {
        showExitActivityDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                isShowExitDialogAndDelete(EXITD);
            }

            @Override
            public void onCallback2() {
                activity.finish();
            }
        });
    }

    /**
     * 退出调用接口
     */
    public enum ExitMode {
        /**
         * 直接删除
         */
        EXITD("1"),
        /**
         * 判断是否删除
         */
        EXITISD("0");
        // 成员变量
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // 构造方法
        private ExitMode(String name) {
            this.name = name;
        }

    }


    /**
     * 弹出确定取消对话框
     */
    public void showQuitDialog(final OnDialogTwoListener listener) {
        try {
            if (context != null) {
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_moulde_exit)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.btn_ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    mDialog.dismiss();
                                listener.onCallback1();
                            }
                        }).addViewOnclick(R.id.btn_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    mDialog.cancel();
                                listener.onCallback2();
                            }
                        }).cancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                mDialog= builder.build();
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showSureOrQuitDialogAndCall-----Error");
        }
    }
}
