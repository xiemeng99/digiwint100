package digiwin.smartdepott100.core.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import digiwin.library.R;
import digiwin.library.constant.SharePreKey;
import digiwin.library.constant.SystemConstant;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ToastUtils;
import digiwin.library.voiceutils.VibratorUtil;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;

import static digiwin.library.constant.SharePreKey.VIBRATE_SETTING;
import static digiwin.library.constant.SystemConstant.VIBRATEMETION;

public abstract class BaseAppFragment extends Fragment {
    protected Context context;
    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
        activity = getActivity();
    }

    /**
     * 绑定布局id
     *
     * @return
     */
    protected abstract int bindLayoutId();

    /**
     * 业务逻辑
     */
    protected abstract void doBusiness();

    /**
     * 等待对话框
     */
    protected void showLoadingDialog() {
        AlertDialogUtils.showLoadingDialog(activity);
    }

    /**
     * 取消对话框显示
     */
    protected void dismissLoadingDialog() {
        AlertDialogUtils.dismissDialog();
    }



    /**
     * 显示退出界面dialog（无回调）
     */
    protected void showExitActivityDialog() {
        AlertDialogUtils.showExitDialog(context, getResources().getString(R.string.label_exit_activity_sure));
    }

    /**
     * 显示退出界面dialog（有回调）
     */
    protected void showExitActivityDialog(OnDialogTwoListener listener) {
        AlertDialogUtils.showSureOrQuitDialogAndCall(context, R.string.label_exit_activity_sure, listener);
    }
    /**
     * 是否确认提交
     */
    protected void showCommitSureDialog(OnDialogTwoListener listener) {
        AlertDialogUtils.showSureOrQuitDialogAndCall(context, R.string.is_sure_commit, listener);
    }

    /**m
     * 显示提交成功dialog（无回调）
     */
    protected void showCommitSuccessDialog(Object content) {
        AlertDialogUtils.showCommitSuccessDialog(context, content);
    }

    /**
     * 显示提交成功dialog（有回调）
     */
    protected void showCommitSuccessDialog(Object content, OnDialogClickListener listener) {
        AlertDialogUtils.showCommitSuccessDialogAndCall(context, content, listener);
    }

    /**
     * 显示提交失败dialog（无回调）
     */
    protected void showCommitFailDialog(Object content) {
        AlertDialogUtils.showCommitFailDialog(context, content);
        voice(content);
        vibrate();
    }

    /**
     * 显示提交事变dialog（有回调）
     */
    protected void showCommitFailDialog(Object content, OnDialogClickListener listener) {
        AlertDialogUtils.showCommitFailDialogAndCall(context, content, listener);
        voice(content);
        vibrate();
    }

    /**
     * 显示失败dialog
     */
    protected void showFailedDialog(Object content) {
        AlertDialogUtils.showFailedDialog(context, content);
        voice(content);
        vibrate();
    }

    /**
     * 显示失败dialog
     */
    protected void showFailedDialog(Object content, OnDialogClickListener listener) {
        AlertDialogUtils.showFailedDialog(context, content, listener);
        voice(content);
        vibrate();
    }

    /**
     * Toast显示,string
     */
    public void showToast(String stringContent) {
        ToastUtils.showToastByString(context, stringContent);
    }

    /**
     * Toast显示,int
     */
    public void showToast(int stringId) {
        ToastUtils.showToastByInt(context, stringId);
    }


    /**
     * 震动
     */
    public void vibrate() {
        String VIBRATE = (String) SharedPreferencesUtils.get(activity, VIBRATE_SETTING, VIBRATEMETION);
        if (VIBRATE.equals(VIBRATEMETION)) {//是否震动提醒
            VibratorUtil.Vibrate(activity, VibratorUtil.VIBRATETIME);
        }
    }

    /**
     * 语音
     */
    public void voice(Object text){
        String voicer = "";
        String chooseVoiceType = (String) SharedPreferencesUtils.get(activity, SharePreKey.VOICER_SELECTED, activity.getString(R.string.voicer_not));
        if (chooseVoiceType.equals(activity.getString(R.string.voicer_male))) {
            voicer = SystemConstant.VIXF;
            if (text instanceof String) {
                VoiceUtils.getInstance(context, voicer).speakText((String) text);
            }else  if(text instanceof  Integer){
                VoiceUtils.getInstance(context, voicer).speakText(activity.getResources().getString((int)text));
            }
        } else if (chooseVoiceType.equals(activity.getString(R.string.voicer_female))) {
            voicer = SystemConstant.VIXY;
            if (text instanceof String) {
                VoiceUtils.getInstance(context, voicer).speakText((String) text);
            }else  if(text instanceof  Integer){
                VoiceUtils.getInstance(context, voicer).speakText(activity.getResources().getString((int)text));
            }
        } else {
            voicer = SystemConstant.NOMETION;
        }
    }

}
