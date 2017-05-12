package digiwin.smartdepott100.core.json;

/**
 * @author xiemeng
 * @des java服务返回json格式解析
 * @date 2017/3/29
 */
public class JsonParseForJava {
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
}
