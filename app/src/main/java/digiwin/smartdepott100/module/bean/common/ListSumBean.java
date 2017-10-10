package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * 点击待办事项其中一笔返回的 汇总数据 list.detail 接口
 */

public class ListSumBean implements Serializable {
    /**
     * 工单最大发料套数
     */
    private String wo_set_qty;
    /**
     * 线边最大发料套数
     */
    private String set_qty;
    /**
     * 到货日期(工单日期)
     */
    private String create_date;
    /**
     * 日期
     */
    private String date_begin;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 部门名称
     */
    private String department_name;
    /**
     * 部门编号
     */
    private String department_no;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 工单数量
     */
    private String wo_qty;
    /**
     * 备货数量（发料数量）
     */
    private String distribute_qty;
    /**
     * 入货数量
     */
    private String stock_in_qty;
    /**
     * 可入库量
     */
    private String available_in_qty;
    /**
     * 实发量、匹配量
     */
    private String scan_sumqty;

    /**
     * 规格
     */
    private String item_spec;
    /**
     * 单位
     */
    private String unit_no;
    /**
     * 需求量
     */
    private String shortage_qty;
    /**
     * 库存量
     */
    private String stock_qty;
    /**
     * 供应商
     */
    private String supplier_name;

    /**
     * 收货日期
     * 调拨日期
     */
    private String receipt_date;
    /**
     * 收货单号
     * 调拨单号
     */
    private String receipt_no;

    /**
     * 收货量
     */
    private String receipt_qty;
    /**
     * 物料条码类型
     */
    private String item_barcode_type="";
    /**
     * 先进先出管控否
     */
    private String fifo_check;

    /**
     * 发料仓
     */
    private String warehouse_out_no;
    /**
     * 接收仓
     */
    private String warehouse_in_no;

    /**
     * 人员名称
     */
    private String employee_name;
    /**
     * 班组名称
     */
    private String workgroup_name;
    /**
     * 调拨项次
     */
    private String receipt_seq;
    /**
     * 申请数量
     */
    private String req_qty;


    /**
     * 接收数量
     */
    private String accept_qty;
    /**
     * 库存单位
     */
    private String stock_unit_no;
    /**
     * 仓库库位
     */
    private String warehouse_storage;
    /**
     * 物料条码
     */
    private String barcode_no;

    /**
     * 下阶料
     */
    private String low_order_item_no;

    /**
     * 下阶料品名
     */

    private String low_order_item_name;

    /**
     * 下阶料规格
     */
    private String low_order_item_spec;

    /**
     * 匹配量
     */
    private String qty;

    /**
     * 仓库
     */
    private String warehouse_no;
    /**
     * 仓库
     */
    private String warehouse_name;
    /**
     * 到货单号
     */
    private String doc_no;

    /**
     * 是否确认数量 1为未确认 2为确认
     */
    private String check;

    /**
     * 储位
     */
    private String storage_spaces_no;
    /**
     * 通知单号
     */
    private String notice_no;

    /**
     * 客户编码
     */
    private String customer_no;
    /**
     * 客户名称
     */
    private String customer_name;
    /**
     *
     */
    private String match_qty;

    /**
     * #退料量
     */
    private String return_qty;

    /**
     * #（最大）可发量
     */
    private String issue_qty;

    /**
     * 批号
     */

    private String item_lot_no;
    /**
     *展示
     */
    private String show;
    /**
     *人员
     */
    private String employee_no;
    /**
     *备注
     */
    private String remark;

    /**
     * 需求量
     */
    private String apply_qty;
    /**
     * 领料单号
     */
    private String issuing_no;
    /**
     * 线边仓库存
     */
    private String  w_stock_qty;
    /**
     * iqc是否ok
     * 1,ok
     * 2,ng
     */
    private String result_type;
    /**
     * 物料批号
     */
    private String plot_no;
    /**
     * iqc使用项次
     */
    private String seq;
    /**
     * 供应商编码
     */
    private String supplier_no;
    /**
     * 允收数量
     */
    private String ac_qty;
    /**
     * 条码量
     */
    private String barcode_qty;
    /**
     * 质检数
     */
    private String qc_qty;

    /**
     * 物料特征
     */
    private String signcode;
    /**
     * 物料特征
     */
    private String product_no;

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

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

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getRemark() {
        return remark;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getWo_qty() {
        return wo_qty;
    }

    public void setWo_qty(String wo_qty) {
        this.wo_qty = wo_qty;
    }

    public String getStock_in_qty() {
        return stock_in_qty;
    }

    public void setStock_in_qty(String stock_in_qty) {
        this.stock_in_qty = stock_in_qty;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
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

    public String getReceipt_qty() {
        return receipt_qty;
    }

    public void setReceipt_qty(String receipt_qty) {
        this.receipt_qty = receipt_qty;
    }

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    public String getFifo_check() {
        return fifo_check;
    }

    public void setFifo_check(String fifo_check) {
        this.fifo_check = fifo_check;
    }

    public String getStock_unit_no() {
        return stock_unit_no;
    }

    public void setStock_unit_no(String stock_unit_no) {
        this.stock_unit_no = stock_unit_no;
    }

    public String getWarehouse_storage() {
        return warehouse_storage;
    }

    public void setWarehouse_storage(String warehouse_storage) {
        this.warehouse_storage = warehouse_storage;
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

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }


    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
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

    public String getWorkgroup_name() {
        return workgroup_name;
    }

    public void setWorkgroup_name(String workgroup_name) {
        this.workgroup_name = workgroup_name;
    }

    public String getReceipt_seq() {
        return receipt_seq;
    }

    public void setReceipt_seq(String receipt_seq) {
        this.receipt_seq = receipt_seq;
    }

    public String getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }

    public String getDistribute_qty() {
        return distribute_qty;
    }

    public void setDistribute_qty(String distribute_qty) {
        this.distribute_qty = distribute_qty;
    }

    public String getAccept_qty() {
        return accept_qty;
    }

    public void setAccept_qty(String accept_qty) {
        this.accept_qty = accept_qty;
    }

    public String getLow_order_item_no() {
        return low_order_item_no;
    }

    public void setLow_order_item_no(String low_order_item_no) {
        this.low_order_item_no = low_order_item_no;
    }

    public String getLow_order_item_name() {
        return low_order_item_name;
    }

    public void setLow_order_item_name(String low_order_item_name) {
        this.low_order_item_name = low_order_item_name;
    }

    public String getLow_order_item_spec() {
        return low_order_item_spec;
    }

    public void setLow_order_item_spec(String low_order_item_spec) {
        this.low_order_item_spec = low_order_item_spec;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
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

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getNotice_no() {
        return notice_no;
    }

    public void setNotice_no(String notice_no) {
        this.notice_no = notice_no;
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

    public String getMatch_qty() {
        return match_qty;
    }

    public void setMatch_qty(String match_qty) {
        this.match_qty = match_qty;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getReturn_qty() {
        return return_qty;
    }

    public void setReturn_qty(String return_qty) {
        this.return_qty = return_qty;
    }

    public String getIssue_qty() {
        return issue_qty;
    }

    public void setIssue_qty(String issue_qty) {
        this.issue_qty = issue_qty;
    }

    public String getItem_lot_no() {
        return item_lot_no;
    }

    public void setItem_lot_no(String item_lot_no) {
        this.item_lot_no = item_lot_no;
    }

    public String getW_stock_qty() {
        return w_stock_qty;
    }

    public void setW_stock_qty(String w_stock_qty) {
        this.w_stock_qty = w_stock_qty;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getWo_set_qty() {
        return wo_set_qty;
    }

    public void setWo_set_qty(String wo_set_qty) {
        this.wo_set_qty = wo_set_qty;
    }

    public String getSet_qty() {
        return set_qty;
    }

    public void setSet_qty(String set_qty) {
        this.set_qty = set_qty;
    }

    public String getApply_qty() {
        return apply_qty;
    }

    public void setApply_qty(String apply_qty) {
        this.apply_qty = apply_qty;
    }

    public String getIssuing_no() {
        return issuing_no;
    }

    public void setIssuing_no(String issuing_no) {

        this.issuing_no = issuing_no;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getPlot_no() {
        return plot_no;
    }

    public void setPlot_no(String plot_no) {
        this.plot_no = plot_no;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    private String showing;

    public String getSupplier_no() {
        return supplier_no;
    }

    public void setSupplier_no(String supplier_no) {
        this.supplier_no = supplier_no;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }

    public String getAc_qty() {
        return ac_qty;
    }

    public void setAc_qty(String ac_qty) {
        this.ac_qty = ac_qty;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }

    public String getQc_qty() {
        return qc_qty;
    }

    public void setQc_qty(String qc_qty) {
        this.qc_qty = qc_qty;
    }

    @Override
    public String toString() {
        return "ListSumBean{" +
                "wo_set_qty='" + wo_set_qty + '\'' +
                ", set_qty='" + set_qty + '\'' +
                ", create_date='" + create_date + '\'' +
                ", wo_no='" + wo_no + '\'' +
                ", department_name='" + department_name + '\'' +
                ", department_no='" + department_no + '\'' +
                ", item_no='" + item_no + '\'' +
                ", item_name='" + item_name + '\'' +
                ", wo_qty='" + wo_qty + '\'' +
                ", distribute_qty='" + distribute_qty + '\'' +
                ", stock_in_qty='" + stock_in_qty + '\'' +
                ", available_in_qty='" + available_in_qty + '\'' +
                ", scan_sumqty='" + scan_sumqty + '\'' +
                ", item_spec='" + item_spec + '\'' +
                ", unit_no='" + unit_no + '\'' +
                ", shortage_qty='" + shortage_qty + '\'' +
                ", stock_qty='" + stock_qty + '\'' +
                ", supplier_name='" + supplier_name + '\'' +
                ", receipt_date='" + receipt_date + '\'' +
                ", receipt_no='" + receipt_no + '\'' +
                ", receipt_qty='" + receipt_qty + '\'' +
                ", item_barcode_type='" + item_barcode_type + '\'' +
                ", fifo_check='" + fifo_check + '\'' +
                ", warehouse_out_no='" + warehouse_out_no + '\'' +
                ", warehouse_in_no='" + warehouse_in_no + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", workgroup_name='" + workgroup_name + '\'' +
                ", receipt_seq='" + receipt_seq + '\'' +
                ", req_qty='" + req_qty + '\'' +
                ", accept_qty='" + accept_qty + '\'' +
                ", stock_unit_no='" + stock_unit_no + '\'' +
                ", warehouse_storage='" + warehouse_storage + '\'' +
                ", barcode_no='" + barcode_no + '\'' +
                ", low_order_item_no='" + low_order_item_no + '\'' +
                ", low_order_item_name='" + low_order_item_name + '\'' +
                ", low_order_item_spec='" + low_order_item_spec + '\'' +
                ", qty='" + qty + '\'' +
                ", warehouse_no='" + warehouse_no + '\'' +
                ", warehouse_name='" + warehouse_name + '\'' +
                ", doc_no='" + doc_no + '\'' +
                ", check='" + check + '\'' +
                ", storage_spaces_no='" + storage_spaces_no + '\'' +
                ", notice_no='" + notice_no + '\'' +
                ", customer_no='" + customer_no + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", match_qty='" + match_qty + '\'' +
                ", return_qty='" + return_qty + '\'' +
                ", issue_qty='" + issue_qty + '\'' +
                ", item_lot_no='" + item_lot_no + '\'' +
                ", show='" + show + '\'' +
                ", employee_no='" + employee_no + '\'' +
                ", remark='" + remark + '\'' +
                ", apply_qty='" + apply_qty + '\'' +
                ", issuing_no='" + issuing_no + '\'' +
                ", w_stock_qty='" + w_stock_qty + '\'' +
                ", result_type='" + result_type + '\'' +
                ", plot_no='" + plot_no + '\'' +
                ", seq='" + seq + '\'' +
                ", supplier_no='" + supplier_no + '\'' +
                ", showing='" + showing + '\'' +
                '}';
    }
}
