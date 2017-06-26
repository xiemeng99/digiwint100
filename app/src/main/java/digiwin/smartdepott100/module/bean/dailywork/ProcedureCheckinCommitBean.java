package digiwin.smartdepott100.module.bean.dailywork;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiemeng
 * @des 富钛生产报工提交
 * @date 2017/5/16 10:10
 */

public class ProcedureCheckinCommitBean implements Serializable {
    /**
     * 工序号/作业编号
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
    /**
     * 生产批次号
     */
    private String plot_no;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 生产料号
     */
    private String item_no;

    /**
     * 人员
     */
    private List<ProcedureEmployeeBean> employee_list;
    /**
     * 模具 设备 刀具
     */
    private List<ProcedureDevMouKniBean> resource_list;

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

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getSubop_name() {
        return subop_name;
    }

    public void setSubop_name(String subop_name) {
        this.subop_name = subop_name;
    }

    public List<ProcedureEmployeeBean> getEmployee_list() {
        return employee_list;
    }

    public void setEmployee_list(List<ProcedureEmployeeBean> employee_list) {
        this.employee_list = employee_list;
    }

    public List<ProcedureDevMouKniBean> getResource_list() {
        return resource_list;
    }

    public void setResource_list(List<ProcedureDevMouKniBean> resource_list) {
        this.resource_list = resource_list;
    }

    public String getOp_seq() {
        return op_seq;
    }

    public void setOp_seq(String op_seq) {
        this.op_seq = op_seq;
    }
}
