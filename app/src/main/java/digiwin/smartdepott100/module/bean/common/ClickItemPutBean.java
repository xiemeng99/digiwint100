package digiwin.smartdepott100.module.bean.common;

/**
 * Created by 唐孟宇 on 2017/3/13.
 */

/**
 * 点击待办事项其中一笔传入的数据
 */
public class ClickItemPutBean {
    /**
     * 单号
     */
    private String doc_no ;

    /**
     * 发出仓
     */
    private String warehouse_out_no ;
    /**
     * 拨入仓
     */
    private String warehouse_in_no ;
    /**
     * 日期
     */
    private String receipt_date ;
    /**
     * 调拨单号
     */
    private String receipt_no;
    /**
     * 仓库
     */
    private String warehouse_no ;
    /**
     * 料号
     */
    private String item_no ;

    /**
     * 工单编号
     */
    private String wo_no;
    /**
     * 部门编码
     */
    private String department_no;

    /**
     * 数量
     */
    private String qty;
    /**
     * 出通单号
     */
    private String notice_no;
    /**
     * 物料条码
     */
    private String barcode_no;

    /**
     * 品名
     */
    private String item_name;

    /**
     * 库位
     */
    private String storage_space;
    /**
     * 储位编码
     */
    private String storage_space_no;
    /**
     * 员工编号
     */
    private String employee_no;
    /**
     * 日期
     */
    private String create_date;
    /**
     * 标记（1：物料库存；2：物料条码库存）
     */
    private  String flag;
    /**
     * 领料单号
     */
    private String issuing_no;
    /**
     * 领料单号
     */
    private String stock_in_no;

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getStorage_space() {
        return storage_space;
    }

    public void setStorage_space(String storage_space) {
        this.storage_space = storage_space;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getWarehouse_out_no() {
        return warehouse_out_no;
    }

    public void setWarehouse_out_no(String warehouse_out_no) {
        this.warehouse_out_no = warehouse_out_no;
    }

    public String getWarehouse_in_no() {
        return warehouse_in_no;
    }

    public void setWarehouse_in_no(String warehouse_in_no) {
        this.warehouse_in_no = warehouse_in_no;
    }

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

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }


    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getNotice_no() {
        return notice_no;
    }

    public void setNotice_no(String notice_no) {
        this.notice_no = notice_no;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIssuing_no() {
        return issuing_no;
    }

    public void setIssuing_no(String issuing_no) {
        this.issuing_no = issuing_no;
    }

    public String getStock_in_no() {
        return stock_in_no;
    }

    public void setStock_in_no(String stock_in_no) {
        this.stock_in_no = stock_in_no;
    }

    public String getStorage_space_no() {
        return storage_space_no;
    }

    public void setStorage_space_no(String storage_space_no) {
        this.storage_space_no = storage_space_no;
    }
}
