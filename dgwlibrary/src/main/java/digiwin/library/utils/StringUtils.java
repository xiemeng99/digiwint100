package digiwin.library.utils;

import android.widget.EditText;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 常用方法
 */
public class StringUtils {
    /**
     * 计算字符串的中文长度
     *
     * @param s
     * @return
     */
    public static int getChineseNum(String s) {
        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    public static String objToString(Object obj) {
        if (obj != null && !String.valueOf(obj).equals("") && !String.valueOf(obj).equals("null")) {
            return String.valueOf(obj);
        } else {
            return "";
        }
    }

    /**
     * 转换字符串，如果字符串是NULL,或者“null”，或者"",统一返回""
     *
     * @param str 需要转换的字符串
     * @return String 统一返回空字符串
     */
    public static String toString(String str) {
        if (null != str && !str.equals("") && !str.equals("null")) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0) || str.equals("null")) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 设置字符串
     * @return boolean 返回是否为空
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 格式化数字返回整数型
     *
     * @param number
     * @return int
     */
    public static int parseInt(String number) {
        int intNumber = 0;
        try {
            intNumber = Integer.parseInt(number.trim());
        } catch (NumberFormatException err) {
            intNumber = 0;
            err.printStackTrace();
        }
        catch (Exception err) {
             intNumber = 0;
        }
        return intNumber;
    }

    /**
     * 设置editext只读属性
     */
    public static void setEditTextReadOnly(EditText view) {
        view.setCursorVisible(false); // 设置输入框中的光标不可见
        view.setFocusable(false); // 无焦点
        view.setFocusableInTouchMode(false); // 触摸时也得不到焦点
        view.setFocusableInTouchMode(false);// 虚拟键盘隐藏
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        // 当然在此之前，你要判断分母是否为0，
        // 为0你可以根据实际需求做相应的处理

        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个整数相除
     *
     * @param a
     * @param b
     * @return
     */
    public static int div(int a, int b) {
        int num = 0;
        try {
            if (a % b == 0) {
                num = a / b;
            } else {
                num = a / b + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 转换STRING成FLOAT
     *
     * @return
     */
    public static float string2Float(String string) {
        float a = 0f;
        try {
            if (!StringUtils.isBlank(string)) {
                a = Float.valueOf(string);
            }
        } catch (Exception e) {
            a = 0;
        }
        return a;
    }

    /**
     * string 相加
     *
     * @param str1
     * @param str2
     * @return
     */
    public static float sum(String str1, String str2) {
        if (isBlank(str1)){
            str1="0";
        }
        if (isBlank(str2)){
            str2="0";
        }
        BigDecimal bd1 = new BigDecimal(str1);
        BigDecimal bd2 = new BigDecimal(str2);
        return bd1.add(bd2).floatValue();
    }

    /**
     * string 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float sub(String d1, String d2) {
        if (isBlank(d1)){
            d1="0";
        }
        if (isBlank(d2)){
            d2="0";
        }
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.subtract(bd2).floatValue();
    }


    /**
     * 根据，拆分
     */
    public static List<String> split(String str) {
        List<String> list = new ArrayList<String>();
        if (null != str) {
            String[] split = str.split(",");
            for (String o : split) {
                list.add(o);
            }
        }
        return list;
    }

    /**
     * 把后台传过来的\\n替换为\n
     */
    public static String lineChange(String str) {
        if (null != str) {
            String result = str.replace("\\n", "\n");
            return result;
        }
        return str;
    }

    /**
     * 整数去0
     */
    public static String deleteZero(String str) {
        if (null != str) {
            if (str.indexOf(".") > 0) {
                str = str.replaceAll("0+?$", "");//去掉多余的0
                str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
                return str;
            }
        }
        return str;
    }

    /**
     * 取出其中小的数量
     *
     * @return
     */
    public static String getMinQty(String numb1, String numb2) {
        return StringUtils.string2Float(numb1) < StringUtils.string2Float(numb2) ? numb1 : numb2;
    }


    // 将汉字转换为全拼
    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += java.lang.Character.toString(t1[i]);
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

}
