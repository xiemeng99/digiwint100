package digiwin.smartdepott100.module.bean.stock;

/**
 * Created by qGod on 2017/5/28
 * Thank you for watching my code
 * 流转标签打印bean
 */

public class PrintLabelFlowBean {
//    wo_no      #工单
//    item_no     #料号
//    item_name   #品名
//    so_no       #订单
//    so_due_date #订单预交日
//    plot_no     物料批号

//    item_no        #料号
//    item_name      #品名
//    so_no          #订单
//    set_qty        #数量
//    shipment_no    #出货单号
//    shipment_date  #出货日期
//    plot_no        #物料批号

    /**
     * 工单
     */
    private String wo_no;
    /**
     * 数量
     */
    private String set_qty;
    /**
     * 出货单号
     */
    private String shipment_no;
    /**
     * 出货日期
     */
    private String shipment_date;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name ;
    /**
     * 订单
     */
    private String so_no ;
    /**
     * 订单预交日
     */
    private String so_due_date ;
    /**
     * 物料批号
     */
    private String plot_no ;

    public String getSet_qty() {
        return set_qty;
    }

    public void setSet_qty(String set_qty) {
        this.set_qty = set_qty;
    }

    public String getShipment_no() {
        return shipment_no;
    }

    public void setShipment_no(String shipment_no) {
        this.shipment_no = shipment_no;
    }

    public String getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
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

    public String getSo_no() {
        return so_no;
    }

    public void setSo_no(String so_no) {
        this.so_no = so_no;
    }

    public String getSo_due_date() {
        return so_due_date;
    }

    public void setSo_due_date(String so_due_date) {
        this.so_due_date = so_due_date;
    }

    public String getPlot_no() {
        return plot_no;
    }

    public void setPlot_no(String plot_no) {
        this.plot_no = plot_no;
    }
}
