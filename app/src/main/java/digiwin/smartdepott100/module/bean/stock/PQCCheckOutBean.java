package digiwin.smartdepott100.module.bean.stock;

import java.io.Serializable;

/**
 * Created by qGod on 2017/5/29
 * Thank you for watching my code
 * PQC检验bean
 */

public class PQCCheckOutBean implements Serializable {
//    report_no      #报工单号
//    report_date    #报工日期
//    report_time    #报工时间
//    wo_no          #工单号
//    item_name      #品名
//    item_no        #料号
//    plot_no        #物料批号
//    subop_no       #工序
//    op_seq         #作业序
//    undefect_qty  #数量
//    qc_type  #检验类型

    /**
     * 检验类型
     */
    public String qc_type;
    /**
     * 报工单号
     */
    public String report_no;
    /**
     * 报工日期
     */
    public String report_date;
    /**
     * 报工时间
     */
    public String report_time;
    /**
     * 工单号
     */
    public String wo_no;
    /**
     * 品名
     */
    public String item_name;
    /**
     * 料号
     */
    public String item_no;
    /**
     * 物料批号
     */
    public String plot_no;
    /**
     * 工序
     */
    public String subop_no;
    /**
     * 作业序
     */
    public String op_seq;
    /**
     * 数量
     */
    public String undefect_qty;

    public String getQc_type() {
        return qc_type;
    }

    public void setQc_type(String qc_type) {
        this.qc_type = qc_type;
    }

    public String getReport_no() {
        return report_no;
    }

    public void setReport_no(String report_no) {
        this.report_no = report_no;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getPlot_no() {
        return plot_no;
    }

    public void setPlot_no(String plot_no) {
        this.plot_no = plot_no;
    }

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

    public String getUndefect_qty() {
        return undefect_qty;
    }

    public void setUndefect_qty(String undefect_qty) {
        this.undefect_qty = undefect_qty;
    }
}
