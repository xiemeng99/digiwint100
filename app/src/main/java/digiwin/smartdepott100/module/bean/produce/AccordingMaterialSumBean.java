package digiwin.smartdepott100.module.bean.produce;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 依成品发料 汇总实体类
 * @date 2017/3/2
 */

public class AccordingMaterialSumBean implements Serializable {
    /**
     * 工单号
     */
    private  String doc_no;

    /**
     * 成品品名
     */
    private String item_name;

    /**
     * 成品规格
     */
    private String item_spec;

    /**
     * 下阶料
     */
    private String low_order_iitem_no;

    /**
     * 下阶料品名
     */
    private String low_order_item_name;

    /**
     * 下阶料规格
     */
    private String low_order_item_spec;

    /**
     * 下阶料单位
     */
    private String unit_no;



    private String shortage_qty;
    /**
     * 申请量
     */
    private String apply_qty ;

    /**
     * 库存量
     */
    private String stock_qty;

    /**
     * 匹配量
     */
    private String scan_sumqty;

    /**
     * 先进先出管控否 Y/N
     */
    private String fifo_check;

    /**
     *物料条码类型
     */
    private String item_barcode_type;

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

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

    public String getLow_order_iitem_no() {
        return low_order_iitem_no;
    }

    public void setLow_order_iitem_no(String low_order_iitem_no) {
        this.low_order_iitem_no = low_order_iitem_no;
    }

    public String getLow_order_item_name() {
        return low_order_item_name;
    }

    public void setLow_order_item_name(String low_order_item_name) {
        this.low_order_item_name = low_order_item_name;
    }

    public String getLow_order_item_spec() {
        return low_order_item_spec;
    }

    public void setLow_order_item_spec(String low_order_item_spec) {
        this.low_order_item_spec = low_order_item_spec;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getApply_qty() {
        return apply_qty;
    }

    public void setApply_qty(String apply_qty) {
        this.apply_qty = apply_qty;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }

    public String getFifo_check() {
        return fifo_check;
    }

    public void setFifo_check(String fifo_check) {
        this.fifo_check = fifo_check;
    }
    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }
}
