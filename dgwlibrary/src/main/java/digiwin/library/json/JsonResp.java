package digiwin.library.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import digiwin.library.utils.LogUtils;

/**
 * @author xiemeng
 * @des json返回
 * @date 2017/1/14
 */

public class JsonResp {
    private static final String TAG = "JsonResp";

    public static class Resp {
        @JSONField(name = "srvver", ordinal = 1)
        public String srvver;

        @JSONField(name = "srvcode", ordinal = 2)
        public String srvcode;

        @JSONField(name = "payload", ordinal = 3)
        public String payload;
    }

    public static class Payload {
        @JSONField(name = "std_data", ordinal = 1)
        public String std_data;

    }

    public static class Std_data {
        @JSONField(name = "execution", ordinal = 1)
        public String execution;

        @JSONField(name = "parameter", ordinal = 2)
        public String parameter;

    }

    public static class Execution {
        @JSONField(name = "code", ordinal = 1)
        public String code;

        @JSONField(name = "sql_code", ordinal = 2)
        public String sql_code = "";

        @JSONField(name = "description", ordinal = 3)
        public String description = "";
    }

    public static class Parameter {

    }

    /**
     * 获取返回码
     *
     * @param resp 返回数据
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCode(String resp) {
        String code = "-1";
        LogUtils.i(TAG, "返回报文：" + resp);
        try {
            Resp resp2 = getObject(resp, Resp.class);
            Payload payload = getObject(resp2.payload, Payload.class);
            Std_data std_data = getObject(payload.std_data, Std_data.class);
            Execution execution = getObject(std_data.execution, Execution.class);
            code = execution.code;
            LogUtils.i(TAG, "数据返回码code：" + code);
        } catch (Exception e) {
            LogUtils.e(TAG, "获取返回码失败" + e);
        }
        return code;
    }

    /**
     * 获取description
     *
     * @param resp 返回数据
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDescription(String resp) {
        String description =resp;
        try {
            Resp resp2 = getObject(resp, Resp.class);
            Payload payload = getObject(resp2.payload, Payload.class);
            Std_data std_data = getObject(payload.std_data, Std_data.class);
            Execution execution = getObject(std_data.execution, Execution.class);
            description = execution.description;
            if (null!=description&&!description.trim().endsWith("!")&&!description.trim().endsWith("！")){
                description=description+"!";
            }
            LogUtils.i(TAG, "数据描述description：" + description);
        } catch (Exception e) {
            LogUtils.e(TAG, "获取返回码失败" + e);
        }
        return description;
    }

    /**
     * 获取数据集合
     *
     * @param resp 返回报文
     * @param type parameter 下一个节点名
     * @param cls  数据类型
     */
    public static <T> List<T> getParaDatas(String resp, String type, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Resp resp2 = getObject(resp, Resp.class);
            Payload payload = getObject(resp2.payload, Payload.class);
            String std_data = payload.std_data;
            JSONObject std_dataObj = JSON.parseObject(std_data);
            JSONObject paraObj = std_dataObj.getJSONObject("parameter");
            JSONArray array = paraObj.getJSONArray(type);
            list = getObjects(array.toJSONString(), cls);
        } catch (Exception e) {
            LogUtils.e(TAG, "getParaDatas失败" + e);
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param resp 返回报文
     * @param cls  数据类型
     */
    public static <T> T getParaData(String resp, Class<T> cls) {
        T t = null;
        try {
            Resp resp2 = getObject(resp, Resp.class);
            Payload payload = getObject(resp2.payload, Payload.class);
            String std_data = payload.std_data;
            JSONObject std_dataObj = JSON.parseObject(std_data);
            JSONObject paraObj = std_dataObj.getJSONObject("parameter");

            t = getObject(paraObj.toJSONString(), cls);
        } catch (Exception e) {
            LogUtils.e(TAG, "getParaData失败" + e);
        }
        return t;
    }

    /**
     * parameter中只有一个参数时调用
     * @param resp
     * @param type
     * @return
     */
    public static String getParaString(String resp, String type) {
        String t = "";
        try {
            Resp resp2 = getObject(resp, Resp.class);
            Payload payload = getObject(resp2.payload, Payload.class);
            String std_data = payload.std_data;
            JSONObject std_dataObj = JSON.parseObject(std_data);
            t = std_dataObj.getJSONObject("parameter").getString(type);
            if (null==t){
                t="";
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getParaString" + e);
        }
        return t;
    }

    /**
     * JSONString--TO--Object
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            LogUtils.e(TAG, "getObject发生异常" + e);
        }
        return t;
    }

    /**
     * JSONString--TO--List<Object>
     */
    public static <T> List<T> getObjects(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            LogUtils.e(TAG, "getObjects发生异常" + e);
        }
        return list;
    }

    /**
     * JSONString--TO--List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getlistKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getlistKeyMaps发生异常" + e);
        }
        return list;
    }

}
