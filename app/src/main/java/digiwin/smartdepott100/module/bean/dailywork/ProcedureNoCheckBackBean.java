package digiwin.smartdepott100.module.bean.dailywork;

/**
 * @author xiemeng
 * @des 富态扫描工序序号返回
 * @date 2017/5/16 10:11
 */

public class ProcedureNoCheckBackBean {
    /**
     * 工序号
     */
    private String subop_no;
    /**
     * 作业序
     */
    private String op_seq;

    /**
     * 工序名称
     */
    private String subop_name;

    private String showing;
    /**
     * Y时只能扫工单，N只能扫生产批次
     */
    private String subop_status;

    /**
     * 工序使用刀具否check out 使用
     * Y必须扫描N不扫
     */
    private String subop_knife_status;

    /**
     * 工序使用模具否check out 使用
     * Y必须扫描N不扫
     */
    private String subop_mould_status;


    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    public String getSubop_name() {
        return subop_name;
    }

    public void setSubop_name(String subop_name) {
        this.subop_name = subop_name;
    }

    public String getSubop_status() {
        return subop_status;
    }

    public void setSubop_status(String subop_status) {
        this.subop_status = subop_status;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }

    public String getSubop_knife_status() {
        return subop_knife_status;
    }

    public void setSubop_knife_status(String subop_knife_status) {
        this.subop_knife_status = subop_knife_status;
    }

    public String getSubop_mould_status() {
        return subop_mould_status;
    }

    public void setSubop_mould_status(String subop_mould_status) {
        this.subop_mould_status = subop_mould_status;
    }

    public String getOp_seq() {
        return op_seq;
    }

    public void setOp_seq(String op_seq) {
        this.op_seq = op_seq;
    }
}
