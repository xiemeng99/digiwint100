package digiwin.smartdepott100.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by ChangquanSun
 * 2017/1/17
 */

public class Test extends DataSupport {

    private String destId;//目的地ID
    private String cnName;//中文名
    private String enName;//英文名

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
