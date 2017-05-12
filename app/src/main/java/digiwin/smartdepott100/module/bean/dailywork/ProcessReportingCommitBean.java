package digiwin.smartdepott100.module.bean.dailywork;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 工序报工提交实体类
 * @date 2017/3/15
 */

public class ProcessReportingCommitBean implements Serializable{

    /**
     * 工单号
     */
    private String wo_no;

    /**
     * 作业编码
     */
    private String process_no;

    /**
     * 员工编码
     */
    private String emplyee_no;

    /**
     * 良品数
     */
    private String undefect_qty;

    /**
     * 不良品数
     */
    private String defect_qty;

    /**
     * 报工时数
     */
    private String report_hours;

    /**
     * 料件
     */
    private String item_no;

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getProcess_no() {
        return process_no;
    }

    public void setProcess_no(String process_no) {
        this.process_no = process_no;
    }

    public String getEmplyee_no() {
        return emplyee_no;
    }

    public void setEmplyee_no(String emplyee_no) {
        this.emplyee_no = emplyee_no;
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

    public String getReport_hours() {
        return report_hours;
    }

    public void setReport_hours(String report_hours) {
        this.report_hours = report_hours;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }
}
