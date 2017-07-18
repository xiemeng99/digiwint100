package digiwin.smartdepott100.core.json;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;

/**
 * @author xiemeng
 * @des java平台json请求格式
 * @date 2017/3/29
 */
public class JsonReqForJava {

    public static final String TAG = "JsonReqForJava";

    private Context mContext;

    public JsonReqForJava(Context context) {
        mContext = context.getApplicationContext();

        this.appid = TelephonyUtils.getDeviceId(mContext);
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
            this.user = accoutBean.getEmployee_no();
            this.plant = accoutBean.getEnterprise_no();
        }
        this.pagesize = (String) SharedPreferencesUtils.get(mContext, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM);
    }

    /**
     * 调用服务名
     */
    private String service;
    /**
     * 调用设备序号
     */
    private String appid;
    /**
     * 调用模块
     */
    private String appmodule;
    /**
     * 时间戳
     */
    private String timestamp;
    ;
    /**
     * 当前营运中心
     */
    private String plant;
    /**
     * 当前用户
     */
    private String user;
    /**
     * 当前页数
     */
    private String pagenow;
    /**
     * 每页显示笔数
     */
    private String pagesize;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppmodule() {
        return appmodule;
    }

    public void setAppmodule(String appmodule) {
        this.appmodule = appmodule;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPagenow() {
        return pagenow;
    }

    public void setPagenow(String pagenow) {
        this.pagenow = pagenow;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String toJson(Object obj) {
        String s = JSON.toJSONString(obj);
        LogUtils.i(TAG, "toJson-->" + s);
        return s;
    }
}
