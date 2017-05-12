package digiwin.smartdepott100.module.bean.sale.traceproduct;

import java.io.Serializable;

/**
 * @author 毛衡
 * @des 产品质量追溯 质量追溯右上角明细
 * @date 2017/4/7
 */

public class TraceProductDetailBean implements Serializable {
    /**
     * 条码
     */
    private String barcode_no;
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
     * 检验员/员工 编号
     */
    private String employee_no;
    /**
     * 检验员/员工 姓名
     */
    private String employee_name;
    /**
     * 部门
     */
    private String department_name;
    /**
     * 不良原因代码
     */
    private String defect_reason;
    /**
     * 不良原因说明
     */
    private String defect_reason_name;
    /**
     * 不良数量
     */
    private String defect_reason_qty;

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
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

    public String getItem_spec() {
        return item_spec;
    }

    public void setItem_spec(String item_spec) {
        this.item_spec = item_spec;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDefect_reason() {
        return defect_reason;
    }

    public void setDefect_reason(String defect_reason) {
        this.defect_reason = defect_reason;
    }

    public String getDefect_reason_name() {
        return defect_reason_name;
    }

    public void setDefect_reason_name(String defect_reason_name) {
        this.defect_reason_name = defect_reason_name;
    }

    public String getDefect_reason_qty() {
        return defect_reason_qty;
    }

    public void setDefect_reason_qty(String defect_reason_qty) {
        this.defect_reason_qty = defect_reason_qty;
    }
}
