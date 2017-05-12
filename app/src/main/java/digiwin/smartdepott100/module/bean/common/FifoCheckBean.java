package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 依成品发料FIFO 实体类
 * @date 2017/3/15
 */

public class FifoCheckBean implements Serializable{
    /**
     * 品名
     */
    private String item_name;
    /**
     * 規格
     */
    private String item_spec;

    /**
     * 批号
     */
    private String lot_no;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_spec() {
        return item_spec;
    }

    public void setItem_spec(String item_spec) {
        this.item_spec = item_spec;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    /**
     * 需求量
     */

    private String shortage_qty;

    /**
     * 储位编号
     */
    private String storage_spaces_no;

    /**
     * 料号
     */
    private String item_no;

    /**
     * 批次日期
     */
    private String lot_date;

    /**
     * 物料条码
     */
    private String barcode_no;

    /**
     * 库存量
     */
    private String stock_qty;

    /**
     * 建议量
     */
    private String recommended_qty;

    /**
     * 实发量
     */
    private String scan_sumqty;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getLot_date() {
        return lot_date;
    }

    public void setLot_date(String lot_date) {
        this.lot_date = lot_date;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getRecommended_qty() {
        return recommended_qty;
    }

    public void setRecommended_qty(String recommended_qty) {
        this.recommended_qty = recommended_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }
}
