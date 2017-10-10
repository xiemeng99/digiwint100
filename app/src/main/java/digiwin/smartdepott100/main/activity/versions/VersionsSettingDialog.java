package digiwin.smartdepott100.main.activity.versions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.DialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.customview.RoundedProgressBar;
import digiwin.smartdepott100.core.net.IDownLoadCallBackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.login.bean.AppVersionBean;

/**
 * 版本信息弹出框
 * @author 毛衡
 */

public class VersionsSettingDialog {

    public static void showVersionDialog(final Activity context, final AppVersionBean versionBean){

        /**
         * 自定义进度条
         */
        final RoundedProgressBar myProgressBar;
        final RelativeLayout rl_download;
        final TextView tv_download;
        /**
         * 版本更新内容
         */
        final TextView versions_dialog_tv;
        /**
         * 版本号
         */
        final TextView versions_num_tv;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_versions_setting, null);
        final DialogUtils dialogUtils = new DialogUtils(context,view);
        //点击外部和任何地方都不可消失
//        dialogUtils.setCanceledOnTouchOutside();
        myProgressBar = (RoundedProgressBar) view.findViewById(R.id.myProgressBar);
        rl_download = (RelativeLayout) view.findViewById(R.id.rl_download);
        tv_download = (TextView) view.findViewById(R.id.tv_download);
        versions_dialog_tv = (TextView) view.findViewById(R.id.versions_dialog_tv);
        versions_dialog_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        versions_num_tv = (TextView) view.findViewById(R.id.versions_num_tv);

        if(versionBean!=null){
            String verWhat="";
            if(null != versionBean.getVerwhat()) {
                 verWhat = versionBean.getVerwhat().replace("。", "\n");
            }
            versions_num_tv.setText(versionBean.getVernum());
            versions_dialog_tv.setText(verWhat);
            dialogUtils.showDialog((int) (ViewUtils.getScreenWidth(context)*0.8), WindowManager.LayoutParams.WRAP_CONTENT);
        }else{
            return;
        }
        //设置进度条最大值
        myProgressBar.setMaxCount(100);
        rl_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_download.setClickable(false);
                tv_download.setTextColor(context.getResources().getColor(R.color.Assist_color));
                String url = versionBean.getVerurl();
                final String apkName = AddressContants.APK_NAME;
                final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                OkhttpRequest.getInstance(context).downLoad(url, absolutePath, apkName, new IDownLoadCallBackImp() {
                    @Override
                    public void onProgressCallBack(long progress, long total) {
                        dialogUtils.setCanceledOnTouchOutside();
                        int pro = (int) (progress*100/ total);
                        if(pro == 100){
                            tv_download.setText(R.string.download_ok);
                            dialogUtils.dismissDialog();
                            //下载完成自动安装apk
                            File file = new File(absolutePath+"/"+apkName);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                            context.startActivity(intent);
                        }
                        if(pro<=3&&pro>=1){
                            tv_download.setText(3+"%");
                            myProgressBar.setCurrentCount(3);
                        }else {
                            tv_download.setText(pro+"%");
                            myProgressBar.setCurrentCount(pro);
                        }
                    }

                    @Override
                    public void onResponse(File file) {
                        LogUtils.e("VersionsSettingDialog","file---ok");
                    }
                });
            }
        });
    }
}
