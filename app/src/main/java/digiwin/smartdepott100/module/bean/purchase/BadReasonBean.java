package digiwin.smartdepott100.module.bean.purchase;

import java.io.Serializable;

/**
 * Created by MacheNike on 2017/3/22.
 * 不良原因
 */

public class BadReasonBean implements Serializable{

//    defect_reason             string     不良原因编号
//    defect_reason_name       string      不良原因名称

    /**
     * 不良原因编号
     */
    private String defect_reason = null;

    public String getDefect_reason() {
        return defect_reason;
    }

    public void setDefect_reason(String defect_reason) {
        this.defect_reason = defect_reason;
    }

    public String getDefect_reason_name() {
        return defect_reason_name;
    }

    public void setDefect_reason_name(String defect_reason_name) {
        this.defect_reason_name = defect_reason_name;
    }

    /**
     * 不良原因名称
     */

    private String defect_reason_name = null;
    /**
     * 不良数量
     */
    private String defect_reason_qty = null;

    public String getDefect_qty() {
        return defect_reason_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_reason_qty = defect_qty;
    }
}
