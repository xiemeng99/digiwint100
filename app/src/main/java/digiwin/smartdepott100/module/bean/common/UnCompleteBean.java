package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * Created by ChangquanSun
 * 2017/3/1
 * 未完事项bean类
 */

public class UnCompleteBean implements Serializable{
    /**
     * 异动日期
     */
   private String transaction_date ;
    /**
     * 异动时间
     */
    private String  transaction_time;
    /**
     * 人员
     */
    private String  employee_no;
    /**
     *  申请串号
     */
    private String  app_reqno;
    /**
     *  ERP抛转状态
     */
    private String transfer_status;

    /**
     *来源单号
     */
    private String doc_no;

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getApp_reqno() {
        return app_reqno;
    }

    public void setApp_reqno(String app_reqno) {
        this.app_reqno = app_reqno;
    }

    public String getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(String transfer_status) {
        this.transfer_status = transfer_status;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }
}
