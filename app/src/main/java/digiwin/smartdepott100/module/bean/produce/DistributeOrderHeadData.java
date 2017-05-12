package digiwin.smartdepott100.module.bean.produce;

import java.io.Serializable;

/**
 * @author 唐孟宇
 * @des 生产配货汇总页面单头数据
 * @date 2017/3/04
 */
public class DistributeOrderHeadData implements Serializable{

//    <Field name="wareout" value="103"/>
//    <Field name="warein" value=""/>
//    <Field name="department_no" value="030200"/>
//    <Field name="departname" value=""/>
//    <Field name="workgroup_no" value="3001"/>


    public String getWorkgroup_no() {
        return workgroup_no;
    }

    public void setWorkgroup_no(String workgroup_no) {
        this.workgroup_no = workgroup_no;
    }

    public String getWareout() {
        return wareout;
    }

    public void setWareout(String wareout) {
        this.wareout = wareout;
    }

    public String getWarein() {
        return warein;
    }

    public void setWarein(String warein) {
        this.warein = warein;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getDepartname() {
        return department_no;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    /**
     * 拨出仓库
     */
    private String wareout;
    /**
     * 拨入仓库
     */
    private String warein;
    /**
     * 部门编号
     */
    private String department_no;
    /**
     * 部门名称
     */
    private String departname;
    /**
     * 班组
     */
    private String workgroup_no;

}
