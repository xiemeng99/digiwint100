package digiwin.smartdepott100.login.activity.setting_dialog;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.DialogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.login.activity.LoginActivity;


/**
 * 设置界面弹出框
 * @Author 毛衡
 * Created by Administrator on 2017/1/10 0010.
 */

public class SettingDialog {
    static RadioButton preRb;
    public static void showSettingDialog(final Activity context){
        //绑定布局
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_login_setting_layout,null);
        //Dialog
        final DialogUtils mDialog = new DialogUtils(context,dialogView);
        //第一行正式区
        final LinearLayout setup_formal_linear;
        final RadioButton setup_formal_rb;
        final TextView setup_formal_tv;
        final EditText setup_formal_et;
        final View setup_formal_view;

        //第二行测试区
        final LinearLayout setup_test_linear;
        final RadioButton setup_test_rb;
        final TextView setup_test_tv;
        final EditText setup_test_et;
        final View setup_test_view;

        //第三行语言别
        final RadioGroup setup_rg;
        final RadioButton setup_easyChinese_rb;
        final RadioButton setup_traditionalChinese_rb;
        final RadioButton setup_english_rb;

        //确定按钮
        TextView setup_yes_tv;


        setup_formal_linear = (LinearLayout) dialogView.findViewById(R.id.setup_formal_linear);
        setup_formal_rb = (RadioButton) dialogView.findViewById(R.id.setup_formal_rb);
        setup_formal_tv = (TextView) dialogView.findViewById(R.id.setup_formal_tv);
        setup_formal_et = (EditText) dialogView.findViewById(R.id.setup_formal_et);
        setup_formal_view = dialogView.findViewById(R.id.setup_formal_view);

        setup_test_linear = (LinearLayout) dialogView.findViewById(R.id.setup_test_linear);
        setup_test_rb = (RadioButton) dialogView.findViewById(R.id.setup_test_rb);
        setup_test_tv = (TextView) dialogView.findViewById(R.id.setup_test_tv);
        setup_test_et = (EditText) dialogView.findViewById(R.id.setup_test_et);
        setup_test_view = dialogView.findViewById(R.id.setup_test_view);

        setup_rg = (RadioGroup) dialogView.findViewById(R.id.setup_rg);
        setup_easyChinese_rb = (RadioButton) dialogView.findViewById(R.id.setup_easyChinese_rb);
        setup_traditionalChinese_rb = (RadioButton) dialogView.findViewById(R.id.setup_traditionalChinese_rb);
        setup_english_rb = (RadioButton) dialogView.findViewById(R.id.setup_english_rb);

        setup_yes_tv = (TextView) dialogView.findViewById(R.id.setup_yes_tv);

        String currentFlag = (String) SharedPreferencesUtils.get(context,SharePreKey.CURRENT_ADDRESS, AddressContants.TEST_FLAG);
        String strTest = (String) SharedPreferencesUtils.get(context, SharePreKey.TEST_ADDRESS, AddressContants.TEST_ADDRESS);
        String strFormal = (String) SharedPreferencesUtils.get(context,SharePreKey.FORMAL_ADDRESS, AddressContants.FORMAL_ADDRESS);

        setup_formal_et.setText(strFormal);
        setup_test_et.setText(strTest);

        final TypedArray a = context.obtainStyledAttributes(new int[] {
                R.attr.Base_color
        });

        if(AddressContants.TEST_FLAG.equals(currentFlag)){
            setup_formal_rb.setChecked(false);
            setup_test_rb.setChecked(true);
            setup_formal_et.setTextColor(context.getResources().getColor(R.color.black_32));
            setup_test_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_formal_tv.setTextColor(context.getResources().getColor(R.color.black_32));
            setup_test_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_formal_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
            setup_test_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_test_et.requestFocus();
        }else {
            setup_formal_rb.setChecked(true);
            setup_test_rb.setChecked(false);
            setup_formal_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_test_et.setTextColor(context.getResources().getColor(R.color.black_32));
            setup_formal_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_test_tv.setTextColor(context.getResources().getColor(R.color.black_32));
            setup_formal_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
            setup_test_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
            setup_formal_et.requestFocus();
        }

        //设置默认语言
        String language = (String) SharedPreferencesUtils.get(context, SharePreKey.LANGUAGE, "");
        Locale curLocale = context.getResources().getConfiguration().locale;
        if(AddressContants.EASY_CHINESE.equals(language)){
            setup_easyChinese_rb.setChecked(true);
            preRb = setup_easyChinese_rb;
        }else if(AddressContants.TRADITIONAL_CHINESE.equals(language)){
            setup_traditionalChinese_rb.setChecked(true);
            preRb = setup_traditionalChinese_rb;
        }else if(AddressContants.ENGLISH.equals(language)){
            setup_english_rb.setChecked(true);
            preRb = setup_english_rb;
        }else {
            if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)){
                setup_easyChinese_rb.setChecked(true);
                preRb = setup_easyChinese_rb;
            }else if (curLocale.equals(Locale.TRADITIONAL_CHINESE)){
                setup_traditionalChinese_rb.setChecked(true);
                preRb = setup_traditionalChinese_rb;
            }else if (curLocale.equals(Locale.ENGLISH)){
                setup_english_rb.setChecked(true);
                preRb = setup_english_rb;
            }else {
                preRb = setup_easyChinese_rb;
                setup_easyChinese_rb.setChecked(true);
            }
        }
        setup_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.setup_easyChinese_rb:
                        preRb = setup_easyChinese_rb;
                        break;
                    case R.id.setup_traditionalChinese_rb:
                        preRb = setup_traditionalChinese_rb;
                        break;
                    case R.id.setup_english_rb:
                        preRb = setup_english_rb;
                        break;
                    default:
                        break;
                }
            }
        });

        //第一行点击
        setup_formal_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setup_formal_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_test_et.setTextColor(context.getResources().getColor(R.color.black_32));
                setup_formal_rb.setChecked(true);
                setup_test_rb.setChecked(false);
                setup_formal_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_test_tv.setTextColor(context.getResources().getColor(R.color.black_32));
                setup_formal_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_test_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
                setup_formal_et.requestFocus();
            }
        });

        //第二行点击
        setup_test_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setup_formal_et.setTextColor(context.getResources().getColor(R.color.black_32));
                setup_test_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_formal_rb.setChecked(false);
                setup_test_rb.setChecked(true);
                setup_formal_tv.setTextColor(context.getResources().getColor(R.color.black_32));
                setup_test_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_formal_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
                setup_test_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                setup_test_et.requestFocus();
            }
        });

        //监听焦点
        setup_formal_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    setup_formal_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                    setup_test_et.setTextColor(context.getResources().getColor(R.color.black_32));
                    setup_formal_rb.setChecked(true);
                    setup_test_rb.setChecked(false);
                    setup_formal_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                    setup_test_tv.setTextColor(context.getResources().getColor(R.color.black_32));
                    setup_formal_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                    setup_test_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
                }else {
                    setup_formal_et.setTextColor(context.getResources().getColor(R.color.black_32));
                    setup_test_et.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                    setup_formal_rb.setChecked(false);
                    setup_test_rb.setChecked(true);
                    setup_formal_tv.setTextColor(context.getResources().getColor(R.color.black_32));
                    setup_test_tv.setTextColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                    setup_formal_view.setBackgroundColor(context.getResources().getColor(R.color.gray_da));
                    setup_test_view.setBackgroundColor(a.getColor(0,context.getResources().getColor(R.color.Base_color)));
                }
            }
        });

        //确定按钮
        setup_yes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //存储当前的
                SharedPreferencesUtils.put(context,SharePreKey.LANGUAGE,preRb.getText().toString().trim());
                SharedPreferencesUtils.put(context,SharePreKey.FORMAL_ADDRESS,setup_formal_et.getText().toString().trim());
                SharedPreferencesUtils.put(context,SharePreKey.TEST_ADDRESS,setup_test_et.getText().toString().trim());
                if(setup_formal_rb.isChecked()){
                    SharedPreferencesUtils.put(context,SharePreKey.CURRENT_ADDRESS, AddressContants.FORMAL_FLAG);
                }else {
                    SharedPreferencesUtils.put(context,SharePreKey.CURRENT_ADDRESS, AddressContants.TEST_FLAG);
                }
                mDialog.dismissDialog();
                String language = (String) SharedPreferencesUtils.get(context,SharePreKey.LANGUAGE, "");
                if(AddressContants.EASY_CHINESE.equals(language)){
                    setLang(context,Locale.SIMPLIFIED_CHINESE);
                }else if(AddressContants.TRADITIONAL_CHINESE.equals(language)){
                    setLang(context,Locale.TRADITIONAL_CHINESE);
                }else if(AddressContants.ENGLISH.equals(language)){
                    setLang(context,Locale.ENGLISH);
                }else {

                }
                ActivityManagerUtils.startActivity(context,LoginActivity.class);
                List<Activity> activityLists = ActivityManagerUtils.getActivityLists();
                for (Activity mActivity:activityLists){
                    if(!mActivity.getClass().getSimpleName().equals("LoginActivity")){
                        if(mActivity!=null &&!mActivity.isFinishing()){
                            mActivity.finish();
                        }
                    }
                }
//                Activity mainActivity = ActivityManagerUtils.getActivity("MainActivity");
//                if(mainActivity!=null){
//                    mainActivity.finish();
//                }
//                Activity settingActivity = ActivityManagerUtils.getActivity("SettingActivity");
//                if(settingActivity!=null){
//                    settingActivity.finish();
//                }
//                Activity userInfoActivity = ActivityManagerUtils.getActivity("UserInfoActivity");
//                if(userInfoActivity!=null){
//                    userInfoActivity.finish();
//                }
                context.finish();
            }
        });


        //弹出Dialog
        mDialog.showDialog();
    }

    /**
     * 语言切换方法
     * @Author 毛衡
     */
    public static void setLang(Activity context,Locale l) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = l;
        resources.updateConfiguration(config, dm);
    }
}
