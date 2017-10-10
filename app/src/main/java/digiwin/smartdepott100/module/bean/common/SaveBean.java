package digiwin.smartdepott100.module.bean.common;

/**
 * @author xiemeng
 * @des 保存通用
 * @date 2017/2/23
 */
public class SaveBean {
    /**
     * 物料条码
     */
    private String barcode_no;
    /**
     * 料号
     */
    private String item_no;

    /**
     * 出仓库代码
     */
    private String warehouse_out_no;
    /**
     * 出储位代码
     */
    private String storage_spaces_out_no;
    /**
     * 入仓库代码
     */
    private String warehouse_in_no;

    /**
     * 入储位代码
     */
    private String storage_spaces_in_no;

    private String storage_spaces_no;
    /**
     * 批次号
     */
    private String lot_no;
    /**
     * 料件单位
     */
    private String unit_no;
    /**
     * 库存/欠料量
     */
    private String qty;
    /**
     * 可入库量
     */
    private String available_in_qty;
    /**
     * 箱条码
     */
    private String package_no;

    /**
     * 扫描汇总量
     */
    private String scan_sumqty;
    /**
     * 班组
     */
    private String workgroup_no;
    /**
     * 部门
     */
    private String department_no;
    /**
     * 人员
     */
    private String employee_no;
    /**
     * 工作站
     */
    private String workstation_no;
    /**
     * 客户
     */
    private String customer_no;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 来源单号
     */
    private String doc_no;
    /**
     * 理由码
     */
    private String reason_code_no;
    /**
     * 是否检查fifo
     */
    private  String fifo_check;

    /**
     * 批次日期
     */
    private String lot_date;
    /**
     * 仓库
     */
    private  String warehouse_no;
    /**
     * 炉号
     */
    private  String furnace_no;
    /**
     * 是否允许仓负库存
     */
    private String allow_negative_stock;

    /**
     * 条码类型，3，4的时候自动保存
     */
    private String item_barcode_type;

    private String barcode_qty;

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    /**
     * 产品特征码

     */
    private String product_no;

    /**
     * 数量
     */
    public String req_qty;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getPackage_no() {
        return package_no;
    }

    public void setPackage_no(String package_no) {
        this.package_no = package_no;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }

    public String getLot_date() {
        return lot_date;
    }

    public void setLot_date(String lot_date) {
        this.lot_date = lot_date;
    }

    public String getWorkgroup_no() {
        return workgroup_no;
    }

    public void setWorkgroup_no(String workgroup_no) {
        this.workgroup_no = workgroup_no;
    }

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

    public String getWorkstation_no() {
        return workstation_no;
    }

    public void setWorkstation_no(String workstation_no) {
        this.workstation_no = workstation_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

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

    public String getWarehouse_in_no() {
        return warehouse_in_no;
    }

    public void setWarehouse_in_no(String warehouse_in_no) {
        this.warehouse_in_no = warehouse_in_no;
    }

    public String getStorage_spaces_in_no() {
        return storage_spaces_in_no;
    }

    public void setStorage_spaces_in_no(String storage_spaces_in_no) {
        this.storage_spaces_in_no = storage_spaces_in_no;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getWarehouse_out_no() {
        return warehouse_out_no;
    }

    public void setWarehouse_out_no(String warehouse_out_no) {
        this.warehouse_out_no = warehouse_out_no;
    }

    public String getStorage_spaces_out_no() {
        return storage_spaces_out_no;
    }

    public void setStorage_spaces_out_no(String storage_spaces_out_no) {
        this.storage_spaces_out_no = storage_spaces_out_no;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getReason_code_no() {
        return reason_code_no;
    }

    public void setReason_code_no(String reason_code_no) {
        this.reason_code_no = reason_code_no;
    }

    public String getFifo_check() {
        return fifo_check;
    }

    public void setFifo_check(String fifo_check) {
        this.fifo_check = fifo_check;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getAllow_negative_stock() {
        return allow_negative_stock;
    }

    public void setAllow_negative_stock(String allow_negative_stock) {
        this.allow_negative_stock = allow_negative_stock;
    }

    public String getFurnace_no() {
        return furnace_no;
    }

    public void setFurnace_no(String furnace_no) {
        this.furnace_no = furnace_no;
    }

    /**
     * 储位
     */
    private String warehouse_storage;

    public String getWarehouse_storage() {
        return warehouse_storage;
    }

    public void setWarehouse_storage(String warehouse_storage) {
        this.warehouse_storage = warehouse_storage;
    }


    public String getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }
}
