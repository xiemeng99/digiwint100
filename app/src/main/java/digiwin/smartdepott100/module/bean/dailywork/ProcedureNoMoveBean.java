package digiwin.smartdepott100.module.bean.dailywork;

/**
 * @author xiemeng
 * @des
 * @date 2017/5/19 18:58
 */

public class ProcedureNoMoveBean {
    /**
     * 作业编号
     */
    private String subop_no;
    /**
     * 作业序
     */
    private String op_seq;
    /**
     * 作业名称
     */
    private String subop_name;
    /**
     * 上站良品数量
     */
    private String before_set_qty;
    /**
     * app显示
     */
    private String showing;

    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    public String getOp_seq() {
        return op_seq;
    }

    public void setOp_seq(String op_seq) {
        this.op_seq = op_seq;
    }

    public String getSubop_name() {
        return subop_name;
    }

    public void setSubop_name(String subop_name) {
        this.subop_name = subop_name;
    }

    public String getBefore_set_qty() {
        return before_set_qty;
    }

    public void setBefore_set_qty(String before_set_qty) {
        this.before_set_qty = before_set_qty;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
