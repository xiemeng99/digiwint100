package digiwin.smartdepott100.module.bean.common;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;

/**
 * @author 唐孟宇
 * @des 筛选条件Bean
 */
public class FilterBean {

    public FilterBean(){
       BaseApplication instance = BaseApplication.getInstance();
        this.pagesize = (String) SharedPreferencesUtils.get(instance, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM);
        this.warehouse_no= LoginLogic.getWare();
    }
    /**
     * 每页笔数
     */
    private String pagesize;
    /**
     * 工单号  16接口
     */
    private String wo_no;

    /**
     * 发出仓   16接口
     */

    private String warehouse_no;
    /**
     * 单号
     */
    private String doc_no;
    /**
     * 发出仓
     * 拨出仓
     */
    private String warehouse_out_no;

    /**
     * 接收仓
     * 拨入仓
     */
    private String warehouse_in_no;
    /**
     * 物料条码
     */
    private String barcode_no;
    /**
     * 料号
     */

    private String item_no;
    /**
     * 部门编码
     */
    private String department_no;
    /**
     * 班组编码
     */
    private String workgroup_no;
    /**
     * 人员
     */
    private String employee_no;
    /**
     * 计划日起
     */
    private String date_begin;
    /**
     * 计划日止
     */
    private String date_end;
    /**
     * 供应商编号
     */
    private String supplier_no;

    /**
     * 品名
     */
    private String item_name;
    /**
     * 客户编号
     */
    private String customer_no;
    /**
     * 下阶料号
     */
    private String low_order_item_no;
    /**
     * 批号
     */
    private String lot_no;
    /**
     * 库位
     */
    private String warehouse_storage;
    /**
     * 人员
     */
    private String issuing_no;
    /**
     * 调拨拨入拨出状态
     */
    private String doc_stus;
    /**
     * 收货单号
     */
    private String receipt_no;

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
     this.pagesize=pagesize;
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

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getWorkgroup_no() {
        return workgroup_no;
    }

    public void setWorkgroup_no(String workgroup_no) {
        this.workgroup_no = workgroup_no;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getSupplier_no() {
        return supplier_no;
    }

    public void setSupplier_no(String supplier_no) {
        this.supplier_no = supplier_no;
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


    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getLow_order_item_no() {
        return low_order_item_no;
    }

    public void setLow_order_item_no(String low_order_item_no) {
        this.low_order_item_no = low_order_item_no;
    }


    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }


    public String getWarehouse_storage() {
        return warehouse_storage;
    }

    public void setWarehouse_storage(String warehouse_storage) {
        this.warehouse_storage = warehouse_storage;
    }

    public String getIssuing_no() {
        return issuing_no;
    }

    public void setIssuing_no(String issuing_no) {
        this.issuing_no = issuing_no;
    }

    public String getDoc_stus() {
        return doc_stus;
    }

    public void setDoc_stus(String doc_stus) {
        this.doc_stus = doc_stus;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }
}
