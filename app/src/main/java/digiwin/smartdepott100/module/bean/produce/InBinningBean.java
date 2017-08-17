package digiwin.smartdepott100.module.bean.produce;

/**
 * Created by qGod on 2017/4/5
 * Thank you for watching my code
 */

public class InBinningBean {
    /**
     * 单号
     */
    private String wo_no ;
    /**
     * 部门编号
     */
    private String department_no;
    /**
     * 物料条码
     */
    private String item_no;
    /**
     * 每页笔数
     */
    private String pagesize;
    /**
     * 单号
     */
    private String doc_no;
    /**
     * 人员
     */
    private String employee_no;
    /**
     * 开始日期
     */
    private String date_begin;
    /**
     * 结束日期
     */
    private String date_end;
    /**
     * 仓库
     */
    private String warehouse_no;

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }
}
