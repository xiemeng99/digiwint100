package digiwin.smartdepott100.module.bean.produce;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 完工入库实体类
 * @date 2017/3/13
 */

public class CompletingStoreBean implements Serializable{
    /**
     * 工单单号
     */
    private String wo_no;

    /**
     * 料号
     */
    private String item_no;

    /**
     * 可入库量
     */
    private String available_in_qty;

    /**
     * 匹配量
     */
    private String qty;

    /**
     * 仓库
     */
    private String warehouse_no;

    /**
     * 储位
     */
    private String storage_spaces_no;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    /**
     * 品名
     */
    private String item_name;

    /**
     * 品名
     */
    private String show;


    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }
}
