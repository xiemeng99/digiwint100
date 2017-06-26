package digiwin.smartdepott100.module.bean.dailywork;

/**
 * @author xiemeng
 * @des 富态movein 提交
 * @date 2017/5/16 10:11
 */

public class ProcedureMoveCommitBean {

    /**
     * 工序号/作业编号
     */
    private String subop_no;
    /**
     * 作业序
     */
    private String op_seq;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 对接人员
     */
    private String employee_no;
    /**
     * 上站数量
     */
    private  String before_set_qty;
    /**
     * 实收数量
     */
    private String undefect_qty;
    /**
     * 提交返回的单号
     */
    private String report_no;


    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getUndefect_qty() {
        return undefect_qty;
    }

    public void setUndefect_qty(String undefect_qty) {
        this.undefect_qty = undefect_qty;
    }

    public String getBefore_set_qty() {
        return before_set_qty;
    }

    public void setBefore_set_qty(String before_set_qty) {

        this.before_set_qty = before_set_qty;
    }

    public String getReport_no() {
        return report_no;
    }

    public void setReport_no(String report_no) {
        this.report_no = report_no;
    }

    public String getOp_seq() {
        return op_seq;
    }

    public void setOp_seq(String op_seq) {
        this.op_seq = op_seq;
    }
}
