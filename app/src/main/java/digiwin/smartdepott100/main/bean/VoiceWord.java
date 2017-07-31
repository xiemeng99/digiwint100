package digiwin.smartdepott100.main.bean;

/**
 * Created by 唐孟宇 on 2017/7/27/027.
 * 语音 用户词表 中间类
 */

public class VoiceWord {
   private String name = null;
   private String word = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

//    { "name" : "default" , "words" : [ "收货检验", "快速收货" ] },
    @Override
    public String toString() {
        return "{" +"\"" + "name"+ "\"" +":" + "\""+ name +"\"" +
                "," +"\""  + "words" +"\"" +":" + "[" + "\"" + word + "\"" + "," + "\"" + word + "\"" + "]" +
                '}';
    }
}
