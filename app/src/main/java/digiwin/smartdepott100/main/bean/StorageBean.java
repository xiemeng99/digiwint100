package digiwin.smartdepott100.main.bean;

import org.litepal.crud.DataSupport;

/**
 * @author xiemeng
 * @des 仓库
 * @date 2017/2/10
 */
public class StorageBean extends DataSupport {
    /**
     * 仓库
     */
    private String warehouse_no;

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }
}
