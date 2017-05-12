package digiwin.smartdepott100.module.bean.common;

/**
 * @author xiemeng
 * @des 明细删除和修改
 * @date 2017/2/28
 */
public class DeleteUpdateBean {
    /**
     * 更新删除标记
     * D:删除
     * U:更新
     */
    private String scandetail_upd_type;
    /**
     * APP申请序列号
     */
    private String app_reqseq;
    /**
     * 计划数量（总）
     */
    private String available_in_qty;
    /**
     * 已扫量
     */
    private String barcode_qty;

    public String getScandetail_upd_type() {
        return scandetail_upd_type;
    }

    public void setScandetail_upd_type(String scandetail_upd_type) {
        this.scandetail_upd_type = scandetail_upd_type;
    }

    public String getApp_reqseq() {
        return app_reqseq;
    }

    public void setApp_reqseq(String app_reqseq) {
        this.app_reqseq = app_reqseq;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }
}
