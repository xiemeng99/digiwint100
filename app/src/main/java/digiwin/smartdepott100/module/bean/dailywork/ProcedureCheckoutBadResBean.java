package digiwin.smartdepott100.module.bean.dailywork;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author xiemeng
 * @des 生产调拨不良原因
 * @date 2017/5/21 15:16
 */

public class ProcedureCheckoutBadResBean extends DataSupport implements Serializable{
    /**
     * 不良原因编号
     */
    private String defect_reason;

    /**
     * 不良原因名称
     */

    private String defect_reason_name;
    /**
     * 不良数量
     */
    private String defect_reason_qty ;

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

    public String getDefect_reason_qty() {
        return defect_reason_qty;
    }

    public void setDefect_reason_qty(String defect_reason_qty) {
        this.defect_reason_qty = defect_reason_qty;
    }

    @Override
    public String toString() {
        return "ProcedureCheckoutBadResBean{" +
                "defect_reason='" + defect_reason + '\'' +
                ", defect_reason_name='" + defect_reason_name + '\'' +
                ", defect_reason_qty='" + defect_reason_qty + '\'' +
                '}';
    }
}
