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
    public String package_no;
    /**
     *  箱内件数
     */
    public String qty;
    /**
     * 项次
     */
    public String seq;
    /**
     * 条码编号
     */
    public String barcode_no;
    /**
     * 料号
     */
    public String item_no;
    /**
     * 品名
     */
    public String item_name;
    /**
     * 规格
     */
    public String item_spec;
    /**
     * 数量
     */
    public String item_qty;
    /**
     * 标志（删除或插入）
     */
    public String flag;

    /**
     * 仓库编号
     */
    public String warehouse_no;
    /**
     * 库位编号
     */
    public String storage_spaces_no;

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
}
