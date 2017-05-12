package digiwin.smartdepott100.module.bean.produce;

import java.io.Serializable;

/**
 * @author 唐孟宇
 * @des 生产配货汇总页面数据展示
 * @date 2017/3/02
 */
public class DistributeSumShowBean implements Serializable{
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 规格
     */
    private String item_spec;
    /**
     * 单位
     */
    private String unit_no;

    /**
     * 欠料量
     */
    private String shortage_qty;
    /**
     * 库存量
     */
    private String stock_qty;
    /**
     * 待复核
     */
    private String uncheck_qty;
    /**
     * 实发量
     */
    private String scan_sumqty;
    /**
     * 物料条码类型
     */
    private String item_barcode_type;
    /**
     * 先进先出管控否
     */
    private String fifo_check;

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
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

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

    public String getFifo_check() {
        return fifo_check;
    }

    public void setFifo_check(String fifo_check) {
        this.fifo_check = fifo_check;
    }

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    public String getUncheck_qty() {
        return uncheck_qty;
    }

    public void setUncheck_qty(String uncheck_qty) {
        this.uncheck_qty = uncheck_qty;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }
}
