package digiwin.smartdepott100.module.bean.sale.traceproduct;

/**
 * @author 毛衡
 * @des 产品质量追溯 出货流向
 * @date 2017/4/7
 */

public class ShipmentToBean {
    /**
     * 出货日期
     */
    private String receipt_date;
    /**
     * 出货单号
     */
    private String receipt_no;
    /**
     * 客户名称
     */
    private String customer_name;
    /**
     * 客户订单号
     */
    private String customer_po_no;
    /**
     * 仓库编号
     */
    private String warehouse_no;
    /**
     * 数量
     */
    private String qty;

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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_po_no() {
        return customer_po_no;
    }

    public void setCustomer_po_no(String customer_po_no) {
        this.customer_po_no = customer_po_no;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
