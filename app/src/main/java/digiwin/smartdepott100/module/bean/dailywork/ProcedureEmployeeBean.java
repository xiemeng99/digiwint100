package digiwin.smartdepott100.module.bean.dailywork;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author xiemeng
 * @des 富态扫描  1：设备  2：人员  3：模具   4：刀具
 * @date 2017/5/16 10:11
 */

public class ProcedureEmployeeBean extends DataSupport implements Serializable{
    /**
     *  1：设备  2：人员  3：模具   4：刀具
     */
    private String resource_type;
    /**
     *人员编号
     */
    private String employee_no;

    /**
     *人员名称
     */
    private String employee_name;

    private String showing;

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {

        this.employee_name = employee_name;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
