package digiwin.smartdepott100.main.bean;

/**
 * @author xiemeng
 * @des 解绑设备传入0时返回
 * @date 2017/4/10 11:40
 */

public class DeviceInfoBean {
    /**
     * 总数
     */
    private  String  total;
    /**
     * 已用
     */
    private String use;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
