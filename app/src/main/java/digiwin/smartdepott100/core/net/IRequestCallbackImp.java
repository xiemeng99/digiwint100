package digiwin.smartdepott100.core.net;

import android.content.Context;

import digiwin.library.net.IRequestCallBack;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.smartdepott100.R;

/**
 * Created by ChangquanSun
 * 2017/1/5
 * 请求回调接口（二次封装）
 */

public abstract class IRequestCallbackImp implements IRequestCallBack {
    @Override
    public void onFailure(Context context,Throwable throwable) {
        throwable.printStackTrace();
        AlertDialogUtils.dismissDialog();
        AlertDialogUtils.showFailedDialog(context, R.string.check_network_error);
//        ToastUtils.showToastByString(context,throwable.getMessage());
    }
}