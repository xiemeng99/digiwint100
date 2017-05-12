package digiwin.smartdepott100.module.bean.sale.traceproduct;

/**
 * @author 毛衡
 * @des 产品质量追溯 当前库存
 * @date 2017/4/7
 */

public class CurrentInventoryBean {
    /**
     * 仓库编号
     */
    private String warehouse_no;
    /**
     * 储位编号
     */
    private String storage_spaces_no;
    /**
     * 批次
     */
    private String lot_no;
    /**
     * 数量
     */
    private String qty;

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

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
