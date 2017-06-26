package digiwin.smartdepott100.core.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import android.content.res.AssetManager;

import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.core.base.BaseApplication;

/**
 * 
 * 测试JSON
 * 将测试文本粘贴到asset目录下jsontext里即可进行测试
 * 
 * @author xiemeng
 * @version [V1.00, 2016-8-10]
 * @see [相关类/方法]
 * @since V1.00
 */
public class JsonText
{
    private static final String TAG = "JsonText";
    
    /**
     * json文本方式测试
     * 将测试文本粘贴到asset目录下jsontext里即可进行测试
     * 
     * @param context
     * @param strAssertFileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String readAssertResource()
    {
        AssetManager assetManager = BaseApplication.getInstance().getAssets();
        String strResponse = "";
        try
        {
            InputStream ims = assetManager.open("jsonfile");
            strResponse = getStringFromInputStream(ims);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return strResponse;
    }
    
    private static String getStringFromInputStream(InputStream a_is)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try
        {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        LogUtils.i(TAG, "json测试数据：" + sb.toString());
        return sb.toString();
    }
    
}
