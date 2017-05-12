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
    private String ware;

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware;
    }
}
