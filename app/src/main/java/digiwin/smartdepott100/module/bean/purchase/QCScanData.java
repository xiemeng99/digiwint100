package digiwin.smartdepott100.module.bean.purchase;

import java.io.Serializable;

/**
 * Created by maoheng on 2017/8/11.
 */

public class QCScanData implements Serializable {
    /**
     * 条目
     */
    private String item;
    /**
     * 交付号
     */
    private String delivery_bill_no;
    /**
     * 收货号
     */
    private String receipt_no;
    /**
     * 供应商编码
     */
    private String supplier_no;
    /**
     * 供应商名称
     */
    private String supplier_name;
    /**
     * 创建日期
     */
    private String create_date;
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
     * 规格
     */
    private String item_spec;
    /**
     * 数量
     */
    private String qty;
    /**
     * 最小数量
     */
    private String wait_min;
    /**
     * 检验单号
     */
    private String qc_no;
    /**
     * 行序
     */
    private String seq;
    /**
     * 检验项目
     */
    private String inspection_item;
    /**
     * 检验项目说明
     */
    private String inspection_item_name;
    /**
     * 检验位置
     */
    private String inspection_position;
    /**
     * 缺点等级
     */
    private String defect_grade;
    /**
     * 缺点等级说明
     */
    private String defect_grade_name;
    /**
     * AQL
     */
    private String aql;
    /**
     * 允收数
     */
    private String ac_qty;
    /**
     * 拒绝数
     */
    private String re_qty;
    /**
     * K法标准值
     */
    private String k_standard;
    /**
     * F法标准值
     */
    private String f_standard;
    /**
     * 抽验量
     */
    private String sample_qty;
    /**
     * 缺点数（可以维护）
     */
    private String defect_qty;
    /**
     * 项目判定结果（1,2）
     */
    private String qc_result;
    /**
     * 规范上限
     */
    private String usl;
    /**
     * 检验上限
     */
    private String upper_range;
    /**
     * 检验标准值
     */
    private String standard_value;
    /**
     * 检验下限
     */
    private String lower_range;
    /**
     * 规范下限
     */
    private String slb;
    /**
     * 计量单位
     */
    private String unit_no;
    /**
     * 检验规格说明
     */
    private String inspection_spec;
    /**
     * 备注
     */
    private String remark;
    /**
     * 抽样计划
     */
    private String sampling_plan;
    /**
     * 不良数（可维护）
     */
    private String defect_reason_qty;
    /**
     * fqc使用
     * 工单号
     */
    private String wo_no;
    /**
     * fqc使用
     * 完工单号
     */
    private String stock_in_no;
    /**
     * 部门编码
     * fqc使用
     */
    private String department_no;
    /**
     * 部门名称
     * fqc使用
     */
    private String department_name;

    /**
     * 来源单号
     */
    private String source_no;
    /**
     * 参考单号
     */
    private String refer_no;

    /**
     * #客户编码
     */
    private String customer_no;
    /**
     * #客户名称
     */
    private String customer_name;


    public String getInspection_item() {
        return inspection_item;
    }

    public void setInspection_item(String inspection_item) {
        this.inspection_item = inspection_item;
    }

    public String getInspection_item_name() {
        return inspection_item_name;
    }

    public void setInspection_item_name(String inspection_item_name) {
        this.inspection_item_name = inspection_item_name;
    }

    public String getInspection_position() {
        return inspection_position;
    }

    public void setInspection_position(String inspection_position) {
        this.inspection_position = inspection_position;
    }

    public String getDefect_grade() {
        return defect_grade;
    }

    public void setDefect_grade(String defect_grade) {
        this.defect_grade = defect_grade;
    }

    public String getDefect_grade_name() {
        return defect_grade_name;
    }

    public void setDefect_grade_name(String defect_grade_name) {
        this.defect_grade_name = defect_grade_name;
    }

    public String getAql() {
        return aql;
    }

    public void setAql(String aql) {
        this.aql = aql;
    }

    public String getAc_qty() {
        return ac_qty;
    }

    public void setAc_qty(String ac_qty) {
        this.ac_qty = ac_qty;
    }

    public String getRe_qty() {
        return re_qty;
    }

    public void setRe_qty(String re_qty) {
        this.re_qty = re_qty;
    }

    public String getK_standard() {
        return k_standard;
    }

    public void setK_standard(String k_standard) {
        this.k_standard = k_standard;
    }

    public String getF_standard() {
        return f_standard;
    }

    public void setF_standard(String f_standard) {
        this.f_standard = f_standard;
    }

    public String getSample_qty() {
        return sample_qty;
    }

    public void setSample_qty(String sample_qty) {
        this.sample_qty = sample_qty;
    }

    public String getDefect_qty() {
        return defect_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_qty = defect_qty;
    }

    public String getQc_result() {
        return qc_result;
    }

    public void setQc_result(String qc_result) {
        this.qc_result = qc_result;
    }

    public String getUsl() {
        return usl;
    }

    public void setUsl(String usl) {
        this.usl = usl;
    }

    public String getUpper_range() {
        return upper_range;
    }

    public void setUpper_range(String upper_range) {
        this.upper_range = upper_range;
    }

    public String getStandard_value() {
        return standard_value;
    }

    public void setStandard_value(String standard_value) {
        this.standard_value = standard_value;
    }

    public String getLower_range() {
        return lower_range;
    }

    public void setLower_range(String lower_range) {
        this.lower_range = lower_range;
    }

    public String getSlb() {
        return slb;
    }

    public void setSlb(String slb) {
        this.slb = slb;
    }

    public String getInspection_spec() {
        return inspection_spec;
    }

    public void setInspection_spec(String inspection_spec) {
        this.inspection_spec = inspection_spec;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampling_plan() {
        return sampling_plan;
    }

    public void setSampling_plan(String sampling_plan) {
        this.sampling_plan = sampling_plan;
    }

    public String getDefect_reason_qty() {
        return defect_reason_qty;
    }

    public void setDefect_reason_qty(String defect_reason_qty) {
        this.defect_reason_qty = defect_reason_qty;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDelivery_bill_no() {
        return delivery_bill_no;
    }

    public void setDelivery_bill_no(String delivery_bill_no) {
        this.delivery_bill_no = delivery_bill_no;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getQc_no() {
        return qc_no;
    }

    public void setQc_no(String qc_no) {
        this.qc_no = qc_no;
    }

    public String getSupplier_no() {
        return supplier_no;
    }

    public void setSupplier_no(String supplier_no) {
        this.supplier_no = supplier_no;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWait_min() {
        return wait_min;
    }

    public void setWait_min(String wait_min) {
        this.wait_min = wait_min;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getStock_in_no() {
        return stock_in_no;
    }

    public void setStock_in_no(String stock_in_no) {
        this.stock_in_no = stock_in_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getSource_no() {
        return source_no;
    }

    public void setSource_no(String source_no) {
        this.source_no = source_no;
    }

    public String getRefer_no() {
        return refer_no;
    }

    public void setRefer_no(String refer_no) {
        this.refer_no = refer_no;
    }


    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
