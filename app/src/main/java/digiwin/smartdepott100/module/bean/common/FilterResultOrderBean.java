package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * 筛选结果 待办事项 数据
 */

public class FilterResultOrderBean implements Serializable {

    /**
     * 仓库名称
     */
    private String warehouse_name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 单号
     */
    private String doc_no;
    /**
     * 日期 出货日期
     */
    private String create_date;

    /**
     * 部门
     */

    private String department_name ;
    /**
     * 人员
     */
    private String employee_name ;
    /**
     * 人员编号
     */
    private String employee_no ;
    /**
     * 部门编号
     */
    private String department_no ;
    /**
     * 供应商名称
     */
    private String supplier_name ;
    /**
     * 送货地址
     */
    private String delivery_address;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 锁定原因
     */
    private String lock_reason ;

    /**
     * 退料单号
     */
    private String return_no;

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }


    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }
    public String getItem_no() {
        return item_no;
    }
    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }
    public void setCustomer_name(String customer_no) {
        this.customer_name = customer_name;
    }
    /**
     * 客户
     */
    private String customer_name;
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getLock_reason() {
        return lock_reason;
    }

    public void setLock_reason(String lock_reason) {
        this.lock_reason = lock_reason;
    }


    public String getReturn_no() {
        return return_no;
    }

    public void setReturn_no(String return_no) {
        this.return_no = return_no;
    }
}
