package digiwin.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 网络状态及版本信息工具类
 */
public class TelephonyUtils {
    private static final String TAG = "TelephonyUtil";

    /**
     * 获取软件信息
     *
     * @return
     */
    public static String getSoftwareInfo() {
        String softwareVersion = android.os.Build.VERSION.RELEASE;
        LogUtils.i(softwareVersion, "softwareVersion :" + softwareVersion);
        return softwareVersion;
    }

    /**
     * 获取硬件信息
     *
     * @return
     */
    public static String getHardwareInfo() {
        String hardwareInfo = android.os.Build.MODEL;
        LogUtils.i(TAG, "hardwareInfo : " + hardwareInfo);
        return hardwareInfo;
    }

    /**
     * 获取应用版本
     */
    public static float getMAppVersion(Context context) {
        float versionName = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionName
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = Float.valueOf(packageInfo.versionName);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        return netType;
    }

    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    @SuppressLint("SdCardPath")
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    @SuppressLint("SdCardPath")
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }

    }

    /**
     * 获取唯一设备号
     *
     * @param
     */
    public static String getDeviceId(Context aty) {
        StringBuffer sb = new StringBuffer();
        sb.append(getIME(aty));

        String wifi = getWifi(aty);
        if (wifi.length() > 6) {
            sb.append(wifi.substring(0, 5));
        } else {
            sb.append(wifi);
        }

        String androidId = getAndroidId(aty);
        if (androidId.length() > 6) {
            sb.append(androidId.substring(0, 5));
        } else {
            sb.append(androidId);
        }
        return sb.toString();
    }

    /**
     * 获取设备号
     *
     * @param context
     */
    public static String getIME(Context context) {
        String szImei = "0000";
        try {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (ContextCompat.checkSelfPermission(aty, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    {
//                        ActivityCompat.requestPermissions(aty, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
//                    }
//                }
//            }
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (null != TelephonyMgr.getDeviceId()) {
                szImei = TelephonyMgr.getDeviceId();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getDeviceId异常");
        }
        return szImei;
    }

    public static String getWifi(Context context) {
        String mac = "";
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        if (!StringUtils.isBlank(wifiMac)) {
            mac = wifiMac;
            return mac;
        }
        LogUtils.e(TAG, "getDeviceId : " + mac);
        return mac;
    }

    /**
     * 获取无线网ip地址
     * @param context
     */
    public static String getWifiIP(Context context) {
        String ip = "";
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //调用方法将int转换为地址字符串
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return ip;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String getAndroidId(Context context) {
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

}
