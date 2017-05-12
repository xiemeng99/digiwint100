package digiwin.library.net;

import java.util.Map;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 * 请求接口父类接口,目前只添加get，post方法，后续有需要可以添加
 */

public interface IRequestManager {

    void get(String url, IRequestCallBack requestCallBack);

    /**
     * xml格式
     */
    void post(String url, String requestBody, IRequestCallBack requestCallBack);

    /**
     * 需要token
     */
    void post(String url, Map<String,String> paramsMap, IRequestCallBack requestCallBack);
    /**
     * 文件下载
     */
    void downLoadFile(String url, String filepath, String apkName, IDownLoadCallBack callBack);

    /**
     *上传文件
     */
    void updateFile(String url,Map<String,Object> maps, IUpdateCallBack callBack);


}
