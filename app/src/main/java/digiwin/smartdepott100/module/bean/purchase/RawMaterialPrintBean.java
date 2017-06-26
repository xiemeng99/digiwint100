package digiwin.smartdepott100.module.bean.purchase;

/**
 * @author xiemeng
 * @des 原材料标签打印
 * @date 2017/6/8 10:39
 */

public class RawMaterialPrintBean {
    /**
     * 收货单号
     */
    private String doc_no;
    /**
     * 收货日期
     */
    private String create_date;
    /**
     * 供应商代码
     */
    private String supplier_no;
    /**
     * 供应商名称
     */
    private String supplier_name;
    /**
     * 项次
     */
    private String seq;
    /**
     * 材料料号
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
     * 炉号
     */
    private String  furnace_no;
    /**
     * 批次
     */
    private String lot_no;
    /**
     * 收货数量
     */
    private String receipt_qty;

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

    public String getFurnace_no() {
        return furnace_no;
    }

    public void setFurnace_no(String furnace_no) {
        this.furnace_no = furnace_no;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getReceipt_qty() {
        return receipt_qty;
    }

    public void setReceipt_qty(String receipt_qty) {
        this.receipt_qty = receipt_qty;
    }
}
