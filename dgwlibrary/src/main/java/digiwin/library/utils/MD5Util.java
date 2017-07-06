package digiwin.library.utils;

import java.security.MessageDigest;

/**
 * @author xiemeng
 * @des Md5加密
 * @date 2017/1/17
 */

public class MD5Util {

    private static final String TAG = "MD5Util";

    /***
     * MD5加密 生成32位md5码
     *
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5EncodeDeviceId(String inStr) {
        inStr = "0" + inStr;
        try {
            MessageDigest md5 = null;
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            LogUtils.e(TAG, "md5EncodeDeviceId异常");
        }
        return inStr;
    }

}
