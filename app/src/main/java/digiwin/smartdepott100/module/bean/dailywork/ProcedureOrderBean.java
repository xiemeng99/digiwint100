package digiwin.smartdepott100.module.bean.dailywork;

/**
 * @author xiemeng
 * @des 富钛工序报工  扫描工单号
 * @date 2017/5/16 10:10
 */

public class ProcedureOrderBean {
    /**
     * 料号
     */
    private String item_no;
    /**
     * 单位
     */
    private String unit_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 规格
     */
    private String item_spec;

    private String showing;

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
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

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
