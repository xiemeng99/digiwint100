package digiwin.smartdepott100.module.bean.common;


/**
 * @author xiemeng
 * @des 扫描员工编号共同使用
 * @date 2017/2/23
 */
public class ScanEmployeeBackBean {

    /**
     *  部门名称
     */
    private String department_name;
    /**
     *  姓名
     */
    private String employee_name;
    /**
     *  人员编号
     */
    private String employee_no;
    /**
     * 展示
     */
    private String showing;
    /**
     * 部门编号
     */
    private String department_no;

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
