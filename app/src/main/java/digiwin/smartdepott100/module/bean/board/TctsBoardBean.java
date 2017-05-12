package digiwin.smartdepott100.module.bean.board;

/**
 * @author xiemeng
 * @des 检验完成待入库看板
 * @date 2017/3/9
 */
public class TctsBoardBean {
    /**
     * 收货日期
     */
    private String receipt_date;
    /**
     * #收货单
     */
    private String receipt_no;
    /**
     * #收货单项次
     */
    private String receipt_no_seq;
    /**
     * #料件编号
     */
    private String item_no;
    /**
     * #料件名称
     */
    private String item_name;
    /**
     * #实收数量
     */
    private String receipt_qty;
    /**
     * #主要仓库
     */
    private String warehouse_no;
    /**
     * #合格/免检待入库
     */
    private String statu;
    /**
     * #供应商简称
     */
    private String supplier_name;
    /**
     * #等待小时
     */
    private String hours;
    /**
     * #审核日期
     */
    private String confirm_date;
    /**
     * #审核时间
     */
    private String receipt_time;
    /**
     * #急料否
     */
    private String rush;

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getReceipt_no_seq() {
        return receipt_no_seq;
    }

    public void setReceipt_no_seq(String receipt_no_seq) {
        this.receipt_no_seq = receipt_no_seq;
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

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
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

    public String getReceipt_time() {
        return receipt_time;
    }

    public void setReceipt_time(String receipt_time) {
        this.receipt_time = receipt_time;
    }

    public String getRush() {
        return rush;
    }

    public void setRush(String rush) {
        this.rush = rush;
    }
}
