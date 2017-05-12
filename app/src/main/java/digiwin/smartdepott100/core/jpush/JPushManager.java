package digiwin.smartdepott100.core.jpush;

import android.content.Context;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.core.base.BaseApplication;

/**
 * @des      极光IM
 * @author  xiemeng
 * @date    2017/1/13
 */

public class JPushManager
{
    private static final String TAG = "JPushManager";
    
    private static Context context = BaseApplication.getInstance().getApplicationContext();
    /**
     * 登陆，自动注册
     */
    public static void login(final String username, final String password)
    {
        JMessageClient.getUserInfo(username, new GetUserInfoCallback()
        {
            @Override
            public void gotResult(int arg0, String arg1, UserInfo user)
            {
                
                LogUtils.i(TAG, "用户信息"+username + user);
                if (null == user)
                {
                    LogUtils.i(TAG, "进行注册");
                    register(username, password);
                }
                else
                {
                    LogUtils.i(TAG,"登录");
                    loginIn(username, password);
                }
            }
        });
        
    }
    
    /**
     * 注册极光成功后登陆
     */
    private static void register(final String username, final String password)
    {
        JMessageClient.register(username, password, new BasicCallback()
        {
            @Override
            public void gotResult(int arg0, String arg1)
            {
                LogUtils.i(TAG, "--------------JPushManager register>" + arg0);
                // 898001表示用户已存在
                if (arg0 == 0||arg0 == 898001)
                {
                    loginIn(username, password);
                }
            }
        });
        
    }
    
    /**
     * 登陆
     */
    private static void loginIn(String username, String password)
    {
        if (!"".equals(username.trim()) && !"".equals(password))
        {
            if (null != getCurrentUserInfo())
            {
                loginOut();
            }
            JMessageClient.login(username, password, new BasicCallback()
            {
                @Override
                public void gotResult(int arg0, String arg1)
                {
                    LogUtils.i(TAG, "--------------JPushManager return>" + arg0);
                    
                }
            });
        }
        
    }
    
    /**
     * 获取当前登录用户
     */
    private static UserInfo getCurrentUserInfo()
    {
        UserInfo myInfo = JMessageClient.getMyInfo();
        return myInfo;
    }
    
    /**
     * 登出
     */
    public static void loginOut()
    {
        LogUtils.i(TAG, "-----------logout>");
        JMessageClient.logout();
        
    }
    
    /**
     * 发送消息
     */
    public static void sendMessage(String targetName, String text)
    {
        if (!"".equals(targetName.trim()) && !"".equals(text.trim()))
        {
            LogUtils.i(TAG,"sendMessage---"+targetName+text);
            Message message = JMessageClient.createSingleTextMessage(targetName, text);
            JMessageClient.sendMessage(message);
        }
    }
}
