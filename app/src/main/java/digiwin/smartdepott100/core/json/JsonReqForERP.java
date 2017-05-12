package digiwin.smartdepott100.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;

/**
 * 发送Json
 *
 * @author xiemeng
 * @version [V1.00, 2016-8-8]
 * @see [相关类/方法]
 * @since V1.00
 */
public class JsonReqForERP {
    /**
     * parameter数据
     */
    public static Object parameter = null;

    /**
     * data数据
     */
    public static Object data = null;

    public static final String TAG = "JsonReqForERP";

    public static class Request {
        @JSONField(name = "key", ordinal = 1)
        public String key = "d41d8cd98f00b204e9800998ecf8427e";

        @JSONField(name = "type", ordinal = 2)
        public String type = "sync";

        @JSONField(name = "host", ordinal = 3)
        public Host host = new Host();

        @JSONField(name = "datakey", ordinal = 4)
        public Datakey datakey = new Datakey();

        @JSONField(name = "service", ordinal = 5)
        public Service service = new Service();

        @JSONField(name = "payload", ordinal = 6)
        public Payload payload = new Payload();
    }

    public static class Host {
        @JSONField(name = "prod", ordinal = 1)
        public String prod = "APP";

        @JSONField(name = "ip", ordinal = 2)
        public String ip = "192.168.10.1";

        @JSONField(name = "lang", ordinal = 3)
        public String id = "zh_CN";

        @JSONField(name = "acct", ordinal = 4)
        public String acct = AddressContants.ACCTFIRSTLOGIN;

        @JSONField(name = "timestamp", ordinal = 5)
        public String timestamp = "";

        @JSONField(name = "appid", ordinal = 5)
        public String appid = TelephonyUtils.getDeviceId(BaseApplication.getContext());

        @JSONField(name = "module", ordinal = 5)
        public String module = "";

        @JSONField(name = "version", ordinal = 5)
        public String version = String.valueOf(TelephonyUtils.getMAppVersion(BaseApplication.getContext()));

    }

    public static class Service {
        @JSONField(name = "prod", ordinal = 1)
        public String prod = "T100";

        @JSONField(name = "name", ordinal = 2)
        public String name = "recipe_upload";

        @JSONField(name = "ip", ordinal = 1)
        public String ip = "10.40.40.18";

        @JSONField(name = "id", ordinal = 2)
        public String id = "topprd";

    }

    public static class Datakey {

        @JSONField(name = "EntId", ordinal = 1)
        public String EntId = AddressContants.ENTERPRISEFIRSTLOGIN;

        @JSONField(name = "CompanyId", ordinal = 2)
        public String CompanyId = AddressContants.SITEFIRSTLOGIN;

    }

    public static class Payload {
        @JSONField(name = "std_data", ordinal = 1)
        public Std_Data std_data = new Std_Data();
    }

    public static class Std_Data {
        @JSONField(name = "parameter", ordinal = 1)
        public Object parameter = JsonReqForERP.parameter;

        @JSONField(name = "data", ordinal = 2)
        public Object data = JsonReqForERP.data;
    }

    /**
     * 没有查询条件获取数据,没有payload节点
     * @param timestamp 时间戳
     * @param serviceName 接口名
     * @return
     */
    public static String noWhereJson(String module,String serviceName,String timestamp) {
        String req = "";
        try {
            Request main = new Request();
            inithead(main,module,serviceName, timestamp);
            JSONObject object = (JSONObject) JSON.toJSON(main);
            object.remove("payload");
            req = object.toJSONString();
            LogUtils.i(TAG, "noWhereJson----->" + req);
        } catch (Exception e) {
            LogUtils.e(TAG, "noWhereJson错误");
        }
        return req;
    }

    /**
     * 没有查询条件获取数据,有payload节点
     * @param timestamp 时间戳
     * @param type 接口名
     * @return
     */
    public static String noWhereJson1(String module,String serviceName,String timestamp) {
        String req = "";
        try {
            Request main = new Request();
            inithead(main,module,serviceName, timestamp);
            JSONObject object = (JSONObject) JSON.toJSON(main);
            req = object.toJSONString();
            LogUtils.i(TAG, "noWhereJson----->" + req);
        } catch (Exception e) {
            LogUtils.e(TAG, "noWhereJson1错误"+e);
        }
        return req;
    }

    /**
     * 将parameter中数据组成hashmap方式传入
     * @param map parameter中数据
     * @param timestamp 时间戳
     * @param serviceName 接口名
     * @return
     */
    public static <T> String mapCreateJson(String module,String serviceName, String timestamp,Map<String, T> map) {
        String req = "";
        try {
            Std_Data param = new Std_Data();
            JSONObject object = (JSONObject) JSON.toJSON(param);
            object.putAll(map);
            object.remove("data");
            Request main = new Request();
            inithead(main,module, serviceName,timestamp);
            main.payload.std_data.parameter = object;
            req = JSON.toJSONString(main);
            LogUtils.i(TAG, "mapjson------>" + req);
        } catch (Exception e) {
            LogUtils.e(TAG, "mapCreateJson异常");
        }
        return req;
    }

    /**
     * parameter内数据传递
     * 当中数据需根据具体模块封装
     * @param objs parameter中对象
     * @param timestamp 时间戳
     * @param serviceName 接口名
     * @return
     */
    public static String objCreateJson(String module,String serviceName, String timestamp,Object objs) {
        String req = "";
        try {
            if (!(objs instanceof HashMap)) {
                parameter = objs;
                Std_Data param = new Std_Data();
                JSONObject object = (JSONObject) JSON.toJSON(param);
                object.remove("data");
                object.remove("parameter");
                object.put("parameter", parameter);
                Request main = new Request();
                inithead(main,module,serviceName,timestamp);
                req = JSON.toJSONString(main);
                LogUtils.i(TAG, "objs------>" + req);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "objCreateJson异常");
        }
        return req;
    }

    /**
     * 初始化用户名等信息
     */
    private static void inithead(Request main,String module, String serviceName,String timestamp) {
        main.host.module=module;
        main.service.name = serviceName;
        main.host.timestamp=timestamp;
        AccoutBean userInfo = LoginLogic.getUserInfo();
//        if(ReqTypeName.GETAC.equals(serviceName)){
//            main.datakey.EntId =AddressContants.ENTERPRISEFIRSTLOGIN;
//            main.datakey.CompanyId = AddressContants.SITEFIRSTLOGIN;
//            main.host.acct = AddressContants.ACCTFIRSTLOGIN;
//        }
        if (null != userInfo && null != userInfo.getEnterprise_no() && null != userInfo.getSite_no()) {
            main.datakey.EntId = userInfo.getEnterprise_no();
            main.datakey.CompanyId = userInfo.getSite_no();
            main.host.acct = userInfo.getAccount();
        }
    }

    ;

}
