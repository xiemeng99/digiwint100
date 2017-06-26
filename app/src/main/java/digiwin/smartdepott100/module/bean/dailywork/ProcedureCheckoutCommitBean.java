package digiwin.smartdepott100.module.bean.dailywork;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiemeng
 * @des 生产报工checkout 提交
 * @date 2017/5/22 08:54
 */

public class ProcedureCheckoutCommitBean implements Serializable{

    /**
     * 工序号/作业编号
     */
    private String subop_no;
    /**
     * 作业序
     */
    private String op_seq;
    /**
     * 生产批次号
     */
    private String plot_no;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 1：设备
     */
    private String machine_no;
    /**
     * 3：模具
     */
    private String mould_no;
    /**
     * 4：刀具
     */
    private String knife_no;
    /**
     * 编号
     */
    private String resource_no;
    /**
     * 人员编号
     */
    private String employee_no;
    /**
     * 良品数量
     */
    private String undefect_qty;
    /**
     * 待处理量
     */
    private String defect_qty;
    /**
     * 单身
     */
    private List<ProcedureCheckoutBadResBean> defect_reason_list;

    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    public String getPlot_no() {
        return plot_no;
    }

    public void setPlot_no(String plot_no) {
        this.plot_no = plot_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }


    public String getResource_no() {
        return resource_no;
    }

    public void setResource_no(String resource_no) {
        this.resource_no = resource_no;
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

    public String getDefect_qty() {
        return defect_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_qty = defect_qty;
    }

    public List<ProcedureCheckoutBadResBean> getDefect_reason_list() {
        return defect_reason_list;
    }

    public void setDefect_reason_list(List<ProcedureCheckoutBadResBean> defect_reason_list) {
        this.defect_reason_list = defect_reason_list;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public String getMould_no() {
        return mould_no;
    }

    public void setMould_no(String mould_no) {
        this.mould_no = mould_no;
    }

    public String getKnife_no() {
        return knife_no;
    }

    public void setKnife_no(String knife_no) {

        this.knife_no = knife_no;
    }

    public String getOp_seq() {
        return op_seq;
    }

    public void setOp_seq(String op_seq) {
        this.op_seq = op_seq;
    }
}
