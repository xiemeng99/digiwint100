package digiwin.smartdepott100.module.bean.purchase;

/**
 * @author 赵浩然
 * @module 采购收货 单身实体类
 * @date 2017/3/8
 */

public class MaterialReceiptBean {

    /**
     * 到货单号
     */
    private String doc_no;

    /**
     * 到货日期
     */
    private String create_date;

    /**
     * 供应商名称
     */
    private String supplier_name;

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

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * 料号
     */
    private String item_no;

    /**
     * 單位
     */
    private String unit_no;

    /**
     * 品名
     */
    private String item_name;

    /**
     * 規格
     */
    private String item_spec;

    /**
     * 需求量
     */
    private String shortage_qty;

    /**
     * 实收量
     */
    private String qty;

    /**
     * 库位
     */
    private String storage_spaces_no;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }


    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
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

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * 是否确认数量 1为未确认 2为确认
     */
    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
