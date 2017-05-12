package digiwin.library.cockroach;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ChangquanSun
 * 2017/2/26
 * 全局异常捕捉工具类，防止APP crash
 */

public class Cockroach {
    private static ExceptionHandler sExceptionHandler;
    private static Thread.UncaughtExceptionHandler unCaughtExceptionHandler;
    /**
     * 用于判断是否重复安装
     */
    private static boolean isInstalled=false;

    public interface ExceptionHandler {
        void handlerException(Thread thread, Throwable throwable);
    }

    private Cockroach(){
    }

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     */
    public static synchronized void install(ExceptionHandler exceptionHandler){
        if(isInstalled) return;
        isInstalled=true;
        sExceptionHandler=exceptionHandler;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Looper.loop();
                    }catch(Throwable throwable){
                        if(throwable instanceof QuitCockroachException) return;
                        if(sExceptionHandler!=null)
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(),throwable);
                    }
                }
            }
        });
        unCaughtExceptionHandler= Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler!=null)
                    sExceptionHandler.handlerException(t,e);
            }
        });
    }

    /**
     * 取消全局异常捕捉
     */
    public static synchronized void unInstall(){
        if(!isInstalled) return;
         isInstalled=false;
        sExceptionHandler=null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new QuitCockroachException("取消全局异常处理");
            }
        });

    }

}
