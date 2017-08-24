package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 保存后返回的数据
 * @date 2017/3/30
 */

public class SaveBackBean implements Serializable{

    /**
     * 已扫量
     */
    private String scan_sumqty;


    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }
}
