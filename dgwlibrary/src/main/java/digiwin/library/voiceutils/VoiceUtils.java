package digiwin.library.voiceutils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.UserWords;

import java.util.HashMap;
import java.util.LinkedHashMap;

import digiwin.library.R;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ToastUtils;

/**
 * 科大讯飞语音工具类
 * Created by mengyuTang on 2017/2/3.
 */

public class VoiceUtils {
    /**
     * 检测声音完毕接口
     */
    public interface VoiceComListener{
        public void isCom(boolean flag);
    }

    private static VoiceUtils instances;
    private static String TAG = "VoiceUtils";
    private Context mContext;
    public ApkInstaller mInstaller;
    /**
     * 监听
     */
    private VoiceComListener mListnener;

    private static String mVoicer;

    // 用HashMap存储听写结果
    private static HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    /**
     * @param context
     * @param voicer  声音类型 男生vixf 女生vixy 不提醒""
     * @return
     */
    public static VoiceUtils getInstance(Context context, String voicer) {
        instances = new VoiceUtils(context);
        mVoicer = voicer;
        return instances;
    }

    private VoiceUtils(Context context) {
        this.mContext = context;
        //上传用户词表
        mInstaller = new ApkInstaller(mContext);
    }

    /**
     * 初始化监听
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            LogUtils.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                //showTip("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            LogUtils.d(TAG, "暂停播放");
        }

        @Override
        public void onSpeakPaused() {
            LogUtils.d(TAG, "暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            LogUtils.d(TAG, "继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (null!=mListnener){
            mListnener.isCom(true);}
            if (error == null) {
                LogUtils.d(TAG, "播放完成");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 获取识别的语音字符串
     */
    public interface GetVoiceTextListener{
        /**
         * 获取别的语音字符串
         * @return
         */
        String getVoiceText(String str);
    }

    private GetVoiceTextListener mTextListener;

    /**
     * 解析出的语音字符串
     */
    private static StringBuffer resultbuffer;

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
            System.out.println(" 没有解析的 :" + result);

            String text = JsonParser.parseIatResult(result);//解析过后的
            System.out.println(" 解析后的 :" + text);

            if(mTextListener != null){
                mTextListener.getVoiceText(text);
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    }

    static SpeechSynthesizer mTts;

    private void showTip(final String str) {
        ToastUtils.showToastByString(mContext, str);
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    private void setParam() {
        try {
            mTts = SpeechSynthesizer.createSynthesizer(mContext, null);
            // 清空参数
            mTts.setParameter(SpeechConstant.PARAMS, null);
            // 根据合成引擎设置相应参数
//		if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, mVoicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "100");
            //设置播放器音频流类型
            mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
            // 设置播放合成音频打断音乐播放，默认为true
            mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
            mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
            mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
        } catch (Exception e) {
            LogUtils.e(TAG, "setParam异常");
        }
    }

    /**
     * 读出文本内容
     *
     * @param text
     */
    public void speakText(String text) {
//       FlowerCollector.onResume(mContext);
//       FlowerCollector.onEvent(mContext, "tts_play");
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
                mInstaller.install();
            } else {
                //showTip("语音合成失败,错误码: " + code);
            }

        }
    }

    /**
     * 读出文本内容
     *
     * @param text
     */
    public void speakText(String text,VoiceComListener listener) {
        // 设置参数
        mListnener=listener;
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                mInstaller.install();
            } else {
            }

        }
    }


    /**
     * 语音识别，将识别的内容以字符串形式在接口中返回
     *
     * @return
     */
    public void voiceToText(GetVoiceTextListener listener) {
        try{
            mTextListener = listener;
            //1. 创建RecognizerDialog对象
            RecognizerDialog mDialog = new RecognizerDialog(mContext, mTtsInitListener);
            //2. 设置accent、 language等参数
            mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文
            mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置语言区域  mandarin为中文
            // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
            // 结果
            // mDialog.setParameter("asr_sch", "1");
            // mDialog.setParameter("nlp_version", "2.0");
            //3.设置回调接口
            mDialog.setListener(new MyRecognizerDialogListener());
            //4. 显示dialog，接收语音输入
            mDialog.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        // 听写结果回调接口 (返回Json 格式结果，用户可参见附录 13.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见 Demo中JsonParser 类；
        //isLast等于true 时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            System.out.println(results.getResultString()) ;
            sppechresult = results.getResultString();
            showTip(results.getResultString()) ;
        }

        // 会话发生错误回调接口
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true)) ;
            // 获取错误码描述
            Log. e(TAG, "error.getPlainDescription(true)==" + error.getPlainDescription(true ));
        }

        // 开始录音
        public void onBeginOfSpeech() {
            showTip(" 开始录音 ");
        }

        //volume 音量值0~30， data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(" 声音改变了 ");
        }

        // 结束录音
        public void onEndOfSpeech() {
            showTip(" 结束录音 ");
        }

        // 扩展用接口
        public void onEvent(int eventType, int arg1 , int arg2, Bundle obj) {
        }
    };

    String sppechresult = "";
    /**
     * 语音识别 不弹出录音dialog
     */
    public String startSpeech() {
        String result = "";
        //1. 创建SpeechRecognizer对象，第二个参数： 本地识别时传 InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer( mContext, null); //语音识别器
        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant. DOMAIN, "iat" );// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mIat.setParameter(SpeechConstant. ACCENT, "mandarin" );// 设置普通话
        //3. 开始听写
        mIat.startListening(mRecoListener);
        try{
            if(null !=  sppechresult && sppechresult.length()>0){
                result =  sppechresult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传用户词表
     */
    public void submitUserWords(){
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer( mContext, null);
    // 上传用户词表，userwords 为用户词表文件。
        UserWords userword = new UserWords(mContext.getResources().getString(R.string.user_words));
        String contents = userword.toString();
        Log.d(TAG,"contents:"+contents);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        // 指定引擎类型
         mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        int ret = mIat.updateLexicon("userword", contents, lexiconListener);
        if(ret != ErrorCode.SUCCESS)
        {
            Log.d(TAG,"上传用户词表失败：" + ret);
        }else{
            Log.d(TAG,"上传用户词表成功：" + ret);
        }

    }

    /**
     * 上传用户词表
     * @param userwords 用户词表，在app的string文件中
     */
    public void submitUserWords(String userwords){
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer( mContext, null);
        UserWords userword = new UserWords(userwords);
        String contents = userword.toString();
        Log.d(TAG,"contents:"+contents);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        // 指定引擎类型
         mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        int ret = mIat.updateLexicon("userword", contents, lexiconListener);
        if(ret != ErrorCode.SUCCESS)
        {
            Log.d(TAG,"上传用户词表失败：" + ret);
        }else{
            Log.d(TAG,"上传用户词表成功：" + ret);
        }

    }
    // 上传用户词表监听器。
    private LexiconListener lexiconListener = new LexiconListener()
    {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error)
        {
            if(error != null)
            {
                LogUtils.d(TAG,"error:"+error.toString());
            }else
            {
                LogUtils.d(TAG,"上传成功！");
            }
        }
    };
}
