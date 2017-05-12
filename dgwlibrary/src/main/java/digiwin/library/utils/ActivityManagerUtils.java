package digiwin.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChangquanSun
 * 2017/1/9
 * Activity管理类utils
 */

public class ActivityManagerUtils {

    private static List<Activity> activityLists=new ArrayList<>();
    public static List<Activity> getActivityLists() {
        return activityLists;
    }

    public static void addActivity(Activity activity){
        if (activity!=null)
        activityLists.add(activity);
    }

    public static void removeActivity(Activity activity){
        if (activity!=null)
            activityLists.remove(activity);
    }

    public static void finishAllActivity(){
        for (Activity activity:activityLists){
            if(activity!=null &&!activity.isFinishing())
            activity.finish();
        }
        System.exit(0);
    }

    public static void finishOtherActivity(String activityName){
        for (Activity activity:activityLists){
            if(activity!=null &&!activity.isFinishing()){
                if(!activity.getClass().getSimpleName().equals(activityName)){
                    activity.finish();
                }
            }
        }
        System.exit(0);
    }

    public static Activity getActivity(String className){
        for (Activity activity:activityLists){
            if(activity!=null && activity.getClass().getSimpleName().equals(className))
               return activity;
        }
        return null;
    }
    /**
     * 启动一个Activity并关闭当前Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity
     */
    public static void startActivityAndFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 启动Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }
    /**
     * 隐式启动Activity
     */
    public static void startActivity(Activity activity,String intentAction) {
        Intent intent = new Intent(intentAction);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity并回调数据
     *
     * @param activity   当前Activity
     * @param cls        要启动的Activity Class
     * @param resultCode
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int resultCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 启动Activity并传输和回调数据
     * @param activity   当前Activity
     * @param cls        要启动的Activity Class
     */
    public static void startActivityBundleForResult(Activity activity, Class<?> cls,  Bundle bundle,int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动Activity并传递Bundle数据
     *
     * @param act    当前Activity
     * @param cls    要启动的Activity Class
     * @param bundle 数据
     */
    public static void startActivityForBundleData(Activity act, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(act, cls);
        intent.putExtras(bundle);
        act.startActivity(intent);
    }
    /**
     * 启动Activity并传递Bundle数据
     *结束当前Activity
     * @param act    当前Activity
     * @param cls    要启动的Activity Class
     * @param bundle 数据
     */
    public static void startActivityForBundleDataFinish(Activity act, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(act, cls);
        intent.putExtras(bundle);
        act.startActivity(intent);
        act.finish();
    }


    /**
     * 打开Activity并删除中间页面
     *
     * @param act
     */
    public static void startActivityAndClear(Activity act, Class<?> cls) {
        Intent intent = new Intent(act, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(intent);
    }

    /**
     * 启动网络设置
     *
     * @param activity 当前Activity
     */
    public static void startSetNetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 启动系统设置
     *
     * @param activity 当前Activity
     */
    public static void startSetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }



}
