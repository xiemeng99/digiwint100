package digiwin.smartdepott100.main.bean;

import java.io.Serializable;

/**
 * Created by ChangquanSun
 * 2017/2/6
 */

public class ModuleBean implements Serializable {
    //module名称
    private int nameRes;
    //图标
    private int iconRes;
    // 对应后台的id
    private String id;
    // 隐式跳转intent
    private String intent;

    public ModuleBean(int nameRes, int iconRes, String id, String intent) {
        this.nameRes = nameRes;
        this.iconRes = iconRes;
        this.id = id;
        this.intent = intent;
    }

    public int getNameRes() {
        return nameRes;
    }

    public void setNameRes(int nameRes) {
        this.nameRes = nameRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

}
