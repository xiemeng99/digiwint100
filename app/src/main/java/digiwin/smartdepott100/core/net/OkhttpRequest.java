package digiwin.smartdepott100.core.net;

import android.content.Context;

import digiwin.library.constant.SharePreKey;
import digiwin.library.net.IRequestManager;
import digiwin.library.net.RequestFactory;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.core.appcontants.AddressContants;

/**
 * Created by ChangquanSun
 * 2017/1/5
 * 网络请求工具类(二次封装，请求直接使用该类)
 */

public class OkhttpRequest {

    private IRequestManager RequestManager;
    private static OkhttpRequest instance;
    /**
     * webService地址
     */
    private String url;
    public static OkhttpRequest getInstance(Context context){
        return instance=new OkhttpRequest(context);
    }

    private OkhttpRequest(Context context) {
         RequestManager= RequestFactory.getRequestManager(context);
         String urlFlag=(String)SharedPreferencesUtils.get(context, SharePreKey.CURRENT_ADDRESS, AddressContants.TEST_FLAG);
         if(AddressContants.FORMAL_FLAG.equals(urlFlag)){
             url = (String)SharedPreferencesUtils.get(context, SharePreKey.FORMAL_ADDRESS, AddressContants.FORMAL_ADDRESS);
         }else {
             url = (String)SharedPreferencesUtils.get(context, SharePreKey.TEST_ADDRESS, AddressContants.TEST_ADDRESS);
         }
    }

    public void get(IRequestCallbackImp requestCallBack){
        RequestManager.get(url,requestCallBack);
    }

    public void post(String requestBodyXml, IRequestCallbackImp requestCallBack){
        RequestManager.post(url,requestBodyXml,requestCallBack);
    }

    public void downLoad(String downLoadUrl,String filePath,String apkName,IDownLoadCallBackImp downLoadCallBack){
        RequestManager.downLoadFile(downLoadUrl,filePath,apkName,downLoadCallBack);
    }

}