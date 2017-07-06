package digiwin.library.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qGod
 * 2016/12/16
 * 线程池工具类
 */

public class ThreadPoolManager {

    public static ThreadPoolManager sInstance;

    private ExecutorService executor;

    private ThreadPoolManager() {
        executor = Executors.newCachedThreadPool();
    }

    public static synchronized ThreadPoolManager getInstance() {
        if (sInstance == null) {
            sInstance = new ThreadPoolManager();
        }
        return sInstance;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            TaskCompletedListener listener = (TaskCompletedListener) msg.obj;
            if (listener != null) {
                //更新UI，运行在UI线程当中
                listener.doSome();
            }
        }
    };

    /**
     * 方法描述：关闭线程池不再接受新的任务
     */
    public void stopReceiveTask() {

        if (!executor.isShutdown()) {
            executor.shutdown();
        }

    }

    /**
     * 方法描述：停止所有线程,包括等待
     */
    public void stopAllTask() {
        if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    /**
     * 添加一个新任务
     *
     * @param runnable 任务的Runnable对象
     */
    public void executeTask(Runnable runnable, TaskCompletedListener listener) {
        if (executor.isShutdown()) {
            ThreadPoolManager.getInstance();
        }
        //异步操作
        if (runnable != null) {
            executor.execute(runnable);
        }
        //回调主线程，用于更新相关ui
        if (listener != null) {
            Message msg = new Message();
            msg.obj = listener;
            handler.sendMessage(msg);
        }
    }

    public interface TaskCompletedListener {
        void doSome();
    }
}
