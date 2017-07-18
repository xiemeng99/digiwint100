package digiwin.smartdepott100.login.loginlogic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonParseForJava;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.bean.EntSiteBean;


/**
 * @author xiemeng
 * @des 登录页面逻辑
 * @date 2017/1/18
 */
public class LoginLogic {

    private static final String TAG = "LoginLogic";

    private Context mContext;
    /**
     * 模块编码
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static LoginLogic logic;

    private LoginLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }

    /**
     * 获取单例
     */
    public static LoginLogic getInstance(Context context, String module, String timestamp) {
        return logic = new LoginLogic(context, module, timestamp);
    }

    /**
     * 获取集团据点
     */
    public interface GetEntIdComListener {
        public void onSuccess(List<EntSiteBean> plants);

        public void onFailed(String msg);
    }

    /**
     * 获取集团据点
     */
    public void getEntIdCom(final Map<String, String> map, final GetEntIdComListener listener) {
        try {
            String createJson = JsonReqForERP.noWhereJsonforloginPage(mModule, ReqTypeName.GETOC, mTimestamp);
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.unknow_error);
                    //string = JsonText.readAssertResource();
                    if (null != string) {
                        if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                            List<EntSiteBean> entBeans = JsonResp.getParaDatas(string, "enterprise_site", EntSiteBean.class);
                            Connector.getDatabase();
                            DataSupport.deleteAll(EntSiteBean.class);
                            DataSupport.saveAll(entBeans);
                            if (entBeans.size() > 0) {
                                List<EntSiteBean> entComBeen = merge(entBeans);
                                listener.onSuccess(entComBeen);
                                return;
                            } else {
                                error = mContext.getString(R.string.data_null);
                            }
                        } else {
                            error = JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getEntIdCom" + e);
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }

    /**
     * 获取集团据点
     */
    public interface GetSiteListener {
        public void onSuccess(List<EntSiteBean> plants);

        public void onFailed(String msg);
    }

    /**
     * 获取集团据点
     */
    public void getSite(final String entId, final GetSiteListener listener) {
        try {
            SQLiteDatabase db = Connector.getDatabase();
            List<EntSiteBean> companyBeen = DataSupport.where("enterprise_no=?", entId).find(EntSiteBean.class);
            listener.onSuccess(companyBeen);
        } catch (Exception e) {
            LogUtils.e(TAG, "getSite" + e);
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }


    /**
     * 验证是否授权可以登录
     */
    public interface CheckBindInfoListener {
        public void onSuccess();

        public void onFailed(String msg);
    }

    /**
     * 验证是否授权可以登录
     */
    public void checkBindInfo(final Map<String, String> map, final CheckBindInfoListener listener) {
        try {
            String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.DEVICHECK, mTimestamp,map);
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.unknow_error);
                    //string = JsonText.readAssertResource();
                    JsonParseForJava resp = JsonParseForJava.getObject(string, JsonParseForJava.class);
                    if (null != resp) {
                        if (ReqTypeName.SUCCCESSCODEJAVA.equals(resp.getAppcode())) {
                            listener.onSuccess();
                        } else {
                            error = JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getEntIdCom" + e);
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }

    /**
     * 登录
     */
    public interface LoginListener {
        public void onSuccess(AccoutBean accoutBean);

        public void onFailed(String msg);
    }

    /**
     * 登录
     */
    public void login(final Map<String, String> map, String plant, final LoginListener listener) {
        try {
            String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.GETAC, mTimestamp, map);
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.login_error);
                    if (null != string) {
                        if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                            AccoutBean accoutBeen = JsonResp.getParaData(string, AccoutBean.class);
                            if (null!=accoutBeen) {
                                listener.onSuccess(accoutBeen);
                                return;
                            }
                        } else {
                            error = JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            listener.onFailed(mContext.getString(R.string.unknow_error));
            LogUtils.e(TAG, "login异常");
        }

    }

    /**
     * 需要判断是否为null
     *
     * @return
     */
    @Nullable
    public static AccoutBean getUserInfo() {
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size() > 0) {
            AccoutBean accoutBean = accoutBeen.get(0);
            return accoutBean;
        }
        return null;
    }

    /**
     * 获取仓库
     */
    public static String getWare() {
        String ware = "";
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size() > 0) {
            ware = accoutBeen.get(0).getWare();
        }
        return ware;
    }


    /**
     * 合并相同的集团
     */
    public List<EntSiteBean> merge(List<EntSiteBean> datas) {
        Map<String, EntSiteBean> map = new HashMap<String, EntSiteBean>();
        if (datas != null) {
            for (EntSiteBean data : datas) {
                if (map.get(data.getEnterprise_no()) == null) {
                    map.put(data.getEnterprise_no(), data);
                } else {
                    EntSiteBean allData = map.get(data.getEnterprise_no());
                }
            }
        }
        // 迭代出展示的数据
        List<EntSiteBean> dataLists = new ArrayList<EntSiteBean>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            EntSiteBean alldata = map.get(it.next());
            dataLists.add(alldata);
        }
        return dataLists;
    }
}
