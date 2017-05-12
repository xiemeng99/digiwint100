package digiwin.smartdepott100.core.net;

import android.content.Context;

import digiwin.library.net.IDownLoadCallBack;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.smartdepott100.R;

/**
 * Created by ChangquanSun
 * 2017/1/5
 */

public  abstract class IDownLoadCallBackImp implements IDownLoadCallBack{
    @Override
    public void onFailure(Context context,Throwable throwable) {
        throwable.printStackTrace();
//        ToastUtils.showToastByString(context,throwable.getMessage());
        AlertDialogUtils.dismissDialog();
        AlertDialogUtils.showFailedDialog(context,R.string.check_network_error);
    }
}
