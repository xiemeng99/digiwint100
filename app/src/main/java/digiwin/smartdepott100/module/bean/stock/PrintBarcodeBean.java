package digiwin.smartdepott100.module.bean.stock;

/**
 * 打印条码 Bean
 * Created by MacheNike on 2017/3/24.
 */

public class PrintBarcodeBean {

    /**
     * 条码
     */
    private String barcode = null;
    /**
     * 料号
     */
    private String item_no = null;
    /**
     * 品名
     */
    private String item_name = null;
    /**
     * 规格
     */
    private String item_spec = null;
    /**
     * 数量
     */
    private String qty = null;

    public String getSum_qty() {
        return sum_qty;
    }

    public void setSum_qty(String sum_qty) {
        this.sum_qty = sum_qty;
    }

    /**
     * 总数量
     */
    private String sum_qty = null;
    /**
     *打印张数
     */
    private String print_num = null;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBarcode_type() {
        return barcode_type;
    }

    public void setBarcode_type(String barcode_type) {
        this.barcode_type = barcode_type;
    }

    /**
     * 条码类型
     */
    private String barcode_type = null;

    public String getPrint_num() {
        return print_num;
    }

    public void setPrint_num(String print_num) {
        this.print_num = print_num;
    }
}
