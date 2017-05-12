package digiwin.library.net;

import android.content.Context;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 * 请求返回接口
 */

public interface IRequestCallBack {

    void onResponse(String string);

    void onFailure(Context context, Throwable throwable);
}
