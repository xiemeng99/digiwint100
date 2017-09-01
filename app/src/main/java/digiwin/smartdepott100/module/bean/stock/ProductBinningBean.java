package digiwin.smartdepott100.module.bean.stock;

/**
 * Created by qGod on 2017/4/2
 * Thank you for watching my code
 * 产品装箱bean
 */

public class ProductBinningBean {
    /**
     * 装箱号
     */
    private String package_no;
    /**
     * 箱内件数
     */
    private String qty;
    /**
     * 项次
     */
    private String seq;
    /**
     * 条码编号
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
     * 数量
     */
    private String item_qty;
    /**
     * 标志（删除或插入）
     */
    private String flag;

    /**
     * 仓库编号
     */
    private String warehouse_no;
    /**
     * 库位编号
     */
    private String storage_spaces_no;
    /**
     * 最大包装件数
     */
    private String max_package_qty;
    /**
     * 箱内件数
     */
    private String packages;
    /**
     * 包装毛重
     */
    private String package_gross_weight;
    /**
     * 包装净重
     */
    private String package_net_weight;

    /**
     * 条码类型
     */
    private String item_barcode_type;
    /**
     * 单位
     */
    private String unit_no;

    private String showing;

    private String available_in_qty;

    private String barcode_qty;

    private String scan_sumqty;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPackage_no() {
        return package_no;
    }

    public void setPackage_no(String package_no) {
        this.package_no = package_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public String getMax_package_qty() {
        return max_package_qty;
    }

    public void setMax_package_qty(String max_package_qty) {
        this.max_package_qty = max_package_qty;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getPackage_gross_weight() {
        return package_gross_weight;
    }

    public void setPackage_gross_weight(String package_gross_weight) {
        this.package_gross_weight = package_gross_weight;
    }

    public String getPackage_net_weight() {
        return package_net_weight;
    }

    public void setPackage_net_weight(String package_net_weight) {
        this.package_net_weight = package_net_weight;
    }

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }
}
