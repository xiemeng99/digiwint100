package digiwin.library.net;

import android.content.Context;

import java.io.File;

/**
 * @author xiemeng
 * @des 文件上传
 * @date 2017/4/20
 */
public interface IUpdateCallBack {
    /**
     * 上传进度回调
     */
    void onProgressCallBack(long progress, long total);

    /**
     * 上传成功
     */
    void onResponse(String msg);

    /**
     * 上传失败
     */
    void onFailure(Context context, String throwable);
}
