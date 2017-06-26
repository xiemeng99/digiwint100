package digiwin.smartdepott100.module.bean.common;


/**
 * @author xiemeng
 * @des 扫描仓库共同使用
 * @date 2017/2/23
 */
public class ScanLocatorBackBean {
    /**
     * 储位编号
     */
    private String storage_spaces_no;
    /**
     * 储位名称
     */
    private  String storage_spaces_name;
    /**
     *  仓库编号
     */
    private String warehouse_no;
    /**
     *  仓库名称
     */
    private String warehouse_name;
    /**
     * 展示
     */
    private String showing;

    /**
     * 是否允许仓负库存
     */
    private String allow_negative_stock;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getStorage_spaces_name() {
        return storage_spaces_name;
    }

    public void setStorage_spaces_name(String storage_spaces_name) {
        this.storage_spaces_name = storage_spaces_name;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }

    public String getAllow_negative_stock() {
        return allow_negative_stock;
    }

    public void setAllow_negative_stock(String allow_negative_stock) {
        this.allow_negative_stock = allow_negative_stock;
    }
}
