package digiwin.library.net;

import android.content.Context;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 */

public class RequestFactory {
    public static IRequestManager getRequestManager(Context context) {
        return OkHttpRequestManager.getInstance(context);
    }

    public static IRequestManager getRequestT100Manager(Context context) {
        return OkHttpRequestT100Manager.getInstance(context);
    }

    public static IRequestManager getRequestJsonManager(Context context) {
        return OkHttpRequestJsonManager.getInstance(context);
    }
}
