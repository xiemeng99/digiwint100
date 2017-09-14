package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * @author xiemeng
 * @des 扫描明细展示
 * @date 2017/2/27
 */
public class DetailShowBean implements Serializable{
    /**
     * APP申请序列号
     */
    private String app_reqseq;
    /**
     * 物料条码
     */
    private String barcode_no;
    /**
     * 库位编号
     */
    private String storage_spaces_no;
    /**
     * 扫描数量
     */
    private String barcode_qty;
    /**
     * 扫描单位
     */
    private String unit_no;
    /**
     * 批号
     */
    private  String lot_no;
    /**
     * 拨入库位，调拨使用
     */
    private String storage_spaces_in_no;
    /**
     * 箱号
     */
    private String package_no;

    /**
     * 物料特征
     */
    private String signcode;
    /**
     * 物料特征说明
     */
    private String signcode_instruction;

    public String getSigncode() {
        return signcode;
    }

    public void setSigncode(String signcode) {
        this.signcode = signcode;
    }

    public String getSigncode_instruction() {
        return signcode_instruction;
    }

    public void setSigncode_instruction(String signcode_instruction) {
        this.signcode_instruction = signcode_instruction;
    }

    private String scan_sumqty;

    public String getApp_reqseq() {
        return app_reqseq;
    }

    public void setApp_reqseq(String app_reqseq) {
        this.app_reqseq = app_reqseq;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getStorage_spaces_in_no() {
        return storage_spaces_in_no;
    }

    public void setStorage_spaces_in_no(String storage_spaces_in_no) {
        this.storage_spaces_in_no = storage_spaces_in_no;
    }

    public String getPackage_no() {
        return package_no;
    }

    public void setPackage_no(String package_no) {
        this.package_no = package_no;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }
}
