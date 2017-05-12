package digiwin.library.net;

import android.content.Context;

import java.io.File;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 * 下载回调接口
 */

public interface IDownLoadCallBack {
    /**
     * 下载进度回调
     */
    void onProgressCallBack(long progress,long total);
    /**
     * 下载成功
     */
    void onResponse(File file);
    /**
     * 下载失败
     */
    void onFailure(Context context,Throwable throwable);
}
