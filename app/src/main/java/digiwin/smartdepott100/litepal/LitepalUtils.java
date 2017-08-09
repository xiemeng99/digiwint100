package digiwin.smartdepott100.litepal;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by ChangquanSun
 * 2017/1/17
 *
 */

public class LitepalUtils {

    public LitepalUtils() {

    }

    /**
     *  刷新数据库，每次增删改时调用
     */
    public void refreshDatabase(){
        Connector.getDatabase();
    }

    //---------------------增
    public void addData(Class<? extends  Test> cls){
        Test test=new Test();
        //可以进行相关赋值
        //调用save方法保存，会有一个boolean返回值用于判断是否保存成功
        test.save();
    }
    public void addListData(List<Test> datas){
        DataSupport.saveAll(datas);
    }

    //--------------------改
    public void upData(){
        ContentValues values = new ContentValues();
        values.put("xxx", "今日iPhone6 Plus发布");
//        DataSupport.update(TEST.class, values, 2);//修改id为2的数据
//        DataSupport.updateAll(TEST.class, values);//修改所有
        DataSupport.updateAll(Test.class, values, "destId = ?", "xxx");
//      DataSupport.updateAll(News.class, values, "title_bg = ? and commentcount > ?", "今日iPhone6发布", "0");
    }

    //-------------------删
    public void deleteData(String...where){
        for (String str:where){

        }
        DataSupport.deleteAll(Test.class);
        DataSupport.deleteAll(Test.class, "title_bg = ? and commentcount = ?", "今日iPhone6发布", "0");
        //或者
        Test test = new Test();
        test.delete();
    }

    //-------------------查询
    public void findData(){
        Test news = DataSupport.find(Test.class, 1);//查询id为1的数据
        List<Test> allNews = DataSupport.findAll(Test.class);
        List<Test> newsList = DataSupport.where("commentcount > ?", "0").find(Test.class);
    }
}
