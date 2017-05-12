package digiwin.smartdepott100.core.jpush;


import android.content.Context;
import android.content.Intent;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.login.activity.LoginActivity;

/**
 * @des      极光推送消息接收
 * @author  xiemeng
 * @date    2017/1/13
 */

public class NotificationClickEventReceiver
{
    private static final String TAG = NotificationClickEventReceiver.class.getSimpleName();
    
    private Context mContext;
    
    public NotificationClickEventReceiver(Context context)
    {
        mContext = context;
        // 注册接收消息事件
        JMessageClient.registerEventReceiver(this);
    }
    
    /**
     * 收到消息处理
     * 
     * @param notificationClickEvent 通知点击事件
     */
    public void onEvent(NotificationClickEvent notificationClickEvent)
    {
      LogUtils.d(TAG, "[onEvent] NotificationClickEvent !!!!");
        if (null == notificationClickEvent)
        {
          LogUtils.w(TAG, "[onNotificationClick] message is null");
            return;
        }
        Message msg = notificationClickEvent.getMessage();
        if (msg != null)
        {
            String targetId = msg.getTargetID();
            String appKey = msg.getFromAppKey();
            ConversationType type = msg.getTargetType();
            Conversation conv;
            //TODO:配置需要跳转的页面
           Intent notificationIntent = new Intent(mContext, LoginActivity.class);
            if (type == ConversationType.single)
            {
                conv = JMessageClient.getSingleConversation(targetId, appKey);
                notificationIntent.putExtra(BaseApplication.TARGET_ID, targetId);
                notificationIntent.putExtra(BaseApplication.TARGET_APP_KEY, appKey);
                LogUtils.i(TAG, "msg.fromAppKey() " + appKey);
            }
            //群聊使用
            else
            {
                conv = JMessageClient.getGroupConversation(Long.parseLong(targetId));
             //   notificationIntent.putExtra(ErpApplication.GROUP_ID, Long.parseLong(targetId));
            }
            conv.resetUnreadCount();
            LogUtils.d("Notification", "Conversation unread msg reset");
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.putExtra("fromGroup", false);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(notificationIntent);
        }
    }
    
}
