package digiwin.smartdepott100.module.bean.dailywork;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author xiemeng
 * @des 富态扫描  1：设备  2：人员  3：模具   4：刀具
 * @date 2017/5/16 10:11
 */

public class ProcedureDevMouKniBean extends DataSupport implements Serializable{
    /**
     * 1：设备  2：人员  3：模具   4：刀具
     */
    private String resource_type;
    /**
     *设备 模具 刀具编号
     */
    private String resource_no;

    /**
     *设备 模具 刀具名称
     */
    private String resource_name;

    private String showing;

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_no() {
        return resource_no;
    }

    public void setResource_no(String resource_no) {
        this.resource_no = resource_no;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {

        this.resource_name = resource_name;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
