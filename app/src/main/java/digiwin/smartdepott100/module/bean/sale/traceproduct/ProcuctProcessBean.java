package digiwin.smartdepott100.module.bean.sale.traceproduct;

/**
 * @author 毛衡
 * @des 产品质量追溯 生产过程
 * @date 2017/4/7
 */

public class ProcuctProcessBean {
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 流转卡
     */
    private String process_card;
    /**
     * 时间
     */
    private String report_datetime;
    /**
     * 线别
     */
    private String line_no;
    /**
     * 工序
     */
    private String subop_no;
    /**
     * 设备编号
     */
    private String machine_no;
    /**
     * 是否有投料
     */
    private String item_in_status;
    /**
     * 是否有工作组
     */
    private String workgroup_in_status;
    /**
     * 是否有QC检验
     */
    private String qc_check_status;

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getProcess_card() {
        return process_card;
    }

    public void setProcess_card(String process_card) {
        this.process_card = process_card;
    }

    public String getReport_datetime() {
        return report_datetime;
    }

    public void setReport_datetime(String report_datetime) {
        this.report_datetime = report_datetime;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public String getItem_in_status() {
        return item_in_status;
    }

    public void setItem_in_status(String item_in_status) {
        this.item_in_status = item_in_status;
    }

    public String getWorkgroup_in_status() {
        return workgroup_in_status;
    }

    public void setWorkgroup_in_status(String workgroup_in_status) {
        this.workgroup_in_status = workgroup_in_status;
    }

    public String getQc_check_status() {
        return qc_check_status;
    }

    public void setQc_check_status(String qc_check_status) {
        this.qc_check_status = qc_check_status;
    }
}
