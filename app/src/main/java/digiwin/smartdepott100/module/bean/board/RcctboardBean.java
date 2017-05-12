package digiwin.smartdepott100.module.bean.board;

/**
 * @author xiemeng
 * @des 收货检验看板
 * @date 2017/3/8
 */
public class RcctboardBean {
    /**
     * 收货日期
     */
    private String receipt_date;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 实收数量
     */
    private String receipt_qty;
    /**
     * 合格状态
     */
    private String statu;
    /**
     * 供应商简称
     */
    private String supplier_name;
    /**
     * 等待小时
     */
    private String hours;
    /**
     * 审核日期
     */
    private String confirm_date;
    /**
     * 审核时间
     */
    private String confirm__time;
    /**
     * 标记--急料否。Y红色，其他绿色
     */
    private String rush;

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

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

    public String getReceipt_qty() {
        return receipt_qty;
    }

    public void setReceipt_qty(String receipt_qty) {
        this.receipt_qty = receipt_qty;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(String confirm_date) {
        this.confirm_date = confirm_date;
    }

    public String getConfirm__time() {
        return confirm__time;
    }

    public void setConfirm__time(String confirm__time) {
        this.confirm__time = confirm__time;
    }

    public String getRush() {
        return rush;
    }

    public void setRush(String rush) {
        this.rush = rush;
    }
}
