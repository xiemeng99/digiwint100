package digiwin.smartdepott100.module.bean.dailywork;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module 工序报工实体类
 * @date 2017/3/15
 */

public class ProcessReportingBean implements Serializable{

    /**
     * app展示
     */
    private String show;

    /**
     * 料号
     */
    private String item_no;

    /**
     * 品名
     */
    private String item_name;

    /**
     * 作业名
     */
    private String process_name;

    /**
     * 本日可转
     */
    private String working_num;

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public String getWorking_num() {
        return working_num;
    }

    public void setWorking_num(String working_num) {
        this.working_num = working_num;
    }
}
