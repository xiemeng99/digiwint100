package digiwin.smartdepott100.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;

/**
 * @author xiemeng
 * @des java服务返回json格式解析
 * @date 2017/3/29
 */
public class JsonParseForJava {

    private static final String TAG = "JsonParseForJava";
    /**
     * 系统响应消息
     */
    private String appmsg;
    /**
     * 描述
     */
    private String description;
    /**
     * 系统响应码
     */
    private String appcode;

    /**
     * 语音提示朗读内容
     */
    private String read;
    /**
     * 数据
     */
    private String data;

    public String getAppmsg() {
        return appmsg;
    }

    public void setAppmsg(String appmsg) {
        this.appmsg = appmsg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * JSONString--TO--Object
     */
    public static <T> T getObject(String jsonString, Class<T> cls)
    {
        T t = null;
        try
        {
            t = JSON.parseObject(jsonString, cls);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "getObject发生异常");
        }
        return t;
    }

    /**
     * JSONString--TO--List<Object>
     */
    public static <T> List<T> getObjects(String jsonString, Class<T> cls)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            list = JSON.parseArray(jsonString, cls);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "getObjects发生异常");
        }
        return list;
    }

    /**
     * JSONString--TO--List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getlistKeyMaps(String jsonString)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try
        {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>()
            {
            });
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "getlistKeyMaps发生异常");
        }
        return list;
    }
}
