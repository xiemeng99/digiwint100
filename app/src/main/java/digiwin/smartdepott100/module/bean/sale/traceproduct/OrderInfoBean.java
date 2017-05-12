package digiwin.smartdepott100.module.bean.sale.traceproduct;

/**
 * @author maoheng
 * @des 产品质量追溯 工单信息
 * @date 2017/4/6
 */

public class OrderInfoBean {
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 部门名称
     */
    private String department_name;
    /**
     * 工单版本
     */
    private String wo_ver;
    /**
     * 图号
     */
    private String drawing_no;
    /**
     * 客户订单号
     */
    private String customer_po_no;
    /**
     * 工单数量
     */
    private String wo_qty;
    /**
     * 工艺路线
     */
    private String wo_process;
    /**
     * 物料编号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**;
     * 规格
     */
    private String item_spec;
    /**
     * 单位
     */
    private String unit_no;
    /**
     * 标准用量
     */
    private String standard_qpa;
    /**
     * 实际用量
     */
    private String actual_qpa;
    /**
     * 应发量
     */
    private String recommended_qty;
    /**
     * 实发量
     */
    private String qty;

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getWo_ver() {
        return wo_ver;
    }

    public void setWo_ver(String wo_ver) {
        this.wo_ver = wo_ver;
    }

    public String getDrawing_no() {
        return drawing_no;
    }

    public void setDrawing_no(String drawing_no) {
        this.drawing_no = drawing_no;
    }

    public String getCustomer_po_no() {
        return customer_po_no;
    }

    public void setCustomer_po_no(String customer_po_no) {
        this.customer_po_no = customer_po_no;
    }

    public String getWo_qty() {
        return wo_qty;
    }

    public void setWo_qty(String wo_qty) {
        this.wo_qty = wo_qty;
    }

    public String getWo_process() {
        return wo_process;
    }

    public void setWo_process(String wo_process) {
        this.wo_process = wo_process;
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

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getStandard_qpa() {
        return standard_qpa;
    }

    public void setStandard_qpa(String standard_qpa) {
        this.standard_qpa = standard_qpa;
    }

    public String getActual_qpa() {
        return actual_qpa;
    }

    public void setActual_qpa(String actual_qpa) {
        this.actual_qpa = actual_qpa;
    }

    public String getRecommended_qty() {
        return recommended_qty;
    }

    public void setRecommended_qty(String recommended_qty) {
        this.recommended_qty = recommended_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
