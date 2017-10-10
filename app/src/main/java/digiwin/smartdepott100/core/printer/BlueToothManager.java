package digiwin.smartdepott100.core.printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.UUID;

import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.stock.PrintBarcodeBean;


/**
 * @author xiemeng
 * @des 蓝牙设置
 * @date 2017/1/17
 */

public class BlueToothManager {
    private static final String TAG = "BlueToothManager";
    public static BlueToothManager manager;
    public static Context mContext;
    /**
     * 蓝牙系统适配
     */
    private BluetoothAdapter mBTAdapter;
    /**
     * 蓝牙连接服务
     */
    private static BluetoothDevice mBTDevice;
    public static BluetoothSocket mBTSocket;

    public PrintSend mPrintSend;
    private static final String NAME = "BTPrinter";

    private UUID dvcUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String mDeviceName = "";

    private static Handler handler;

    private BlueToothManager(Context context) {
        mContext = context;
    }

    public static BlueToothManager getManager(Context context) {
        if (null == manager) {
            manager = new BlueToothManager(context);
            handler = new Handler(Looper.getMainLooper());
        }
        return manager;
    }

    /**
     * 连接设备
     */
    public interface ConnectListener {
        public void onSuccess();

        public void onFailed(int msg);
    }

    /**
     * 连接设备
     *
     * @param address    蓝牙地址
     * @param deviceName 蓝牙名称
     */
    public void connect(String address, final String deviceName, final ConnectListener listener) {
        try {
            mBTAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null == mBTAdapter) {
                listener.onFailed(R.string.connect_device_failed);
                return;
            }
            mBTDevice = mBTAdapter.getRemoteDevice(address);
            //android 2.3以下使用的
//            mBTSocket = mBTDevice.createRfcommSocketToServiceRecord(dvcUUID);
            //安卓新版本使用
            mBTSocket = mBTDevice.createInsecureRfcommSocketToServiceRecord(dvcUUID);

            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBTSocket.connect();
                        mPrintSend = new PrintSend(mBTSocket);
                        if (null != mPrintSend) {
                            mDeviceName = deviceName;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccess();
                                }
                            });
                            return;
                        }
                    } catch (IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFailed(R.string.connect_device_failed);
                            }
                        });
                        LogUtils.e(TAG, "connect异常---IOException---");
                    }
                }
            }, null);
        } catch (Exception e) {
            listener.onFailed(R.string.connect_device_failed);
            LogUtils.e(TAG, "connect异常");
        }
    }

    /**
     * 确认蓝牙是否开启并连接成功
     */
    public boolean isOpen() {
        boolean flag = true;
        //没有开启
        if (null == mBTDevice) {
            flag = false;
            return flag;
        }
        //没有连接
        if (null == mPrintSend) {
            flag = false;
            return flag;
        }
        return flag;
    }

    /**
     * 获取连接设备名称
     *
     * @return
     */
    public String getDeviceName() {
        return mDeviceName;
    }


    /**
     * 关闭蓝牙
     */
    public void close() {
        try {
            mBTAdapter.disable();
            mBTSocket.close();
        } catch (Exception e) {
            LogUtils.i(TAG, " close()异常");
        }
    }


    /**
     * 打印出通单
     *
     * @see [类、类#方法、类#成员]
     */
    public boolean printBarocde(String box) {
        boolean flag = false;
        if (null == mBTDevice) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.connect_bluedevice));
            flag = false;
            return flag;
        }

        if (null == mPrintSend) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.blue_connected_failed));
            flag = false;
            return flag;
        }
        String encoding2 = "A\n" + "PS\n"
                + "%0H0040V0040L0202P02C9B箱号:" + box + "\n"
               // + "%0H0040V0080L0101P02C9B" + num + "\n"
               // + "%0H0080V01402D30,M,05,1,0DN" + num.length() + "," + num
                + "Q1\n" + "Z\n";
        mPrintSend.sendBtMessage(encoding2);
        return flag;
    }
    /**
     * 打印条码
     *
     * @see [类、类#方法、类#成员]
     */
    public boolean printMaterialCode(PrintBarcodeBean printBarcodeBean,int sumnum) {
        boolean flag = false;
        if (null == mBTDevice) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.connect_bluedevice));
            flag = false;
            return flag;
        }

        if (null == mPrintSend) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.blue_connected_failed));
            flag = false;
            return flag;
        }
        int num = Integer.valueOf(printBarcodeBean.getQty());
        String encoding2 = "A\n" + "PS\n"
                + "%0H0040V0070L0101P02C9B" +mContext.getResources().getString(R.string.item_name)+": "+ printBarcodeBean.getItem_name() + "\n"
                + "%0H0040V0140L0101P02C9B" +mContext.getResources().getString(R.string.model)+": "+ printBarcodeBean.getItem_spec() + "\n"
                + "%0H0150V02102D30,M,05,1,0DN"+printBarcodeBean.getBarcode().length()+","+printBarcodeBean.getBarcode()
                + "%0H0040V0380L0101P02C9B" + printBarcodeBean.getBarcode() + "\n"
                + "%0H0040V0450L0101P02C9B" +mContext.getResources().getString(R.string.num)+": "+num
//                + "  "+ unit+"\n"
                + "Q1\n" + "Z\n";
        if(num != 0){
            if(sumnum%num == 0){
                for (int i = 0; i < StringUtils.string2Float(printBarcodeBean.getPrint_num()); i++) {
                    mPrintSend.sendBtMessage(encoding2);
                }
            }else{
                for (int i = 0; i < StringUtils.string2Float(printBarcodeBean.getPrint_num())-1; i++) {
                    mPrintSend.sendBtMessage(encoding2);
                }
                num = sumnum%num;
                encoding2 = "A\n" + "PS\n"
                        + "%0H0040V0070L0101P02C9B" +mContext.getResources().getString(R.string.item_name)+": "+ printBarcodeBean.getItem_name() + "\n"
                        + "%0H0040V0140L0101P02C9B" +mContext.getResources().getString(R.string.model)+": "+ printBarcodeBean.getItem_spec() + "\n"
                        + "%0H0150V02102D30,M,05,1,0DN"+printBarcodeBean.getBarcode().length()+","+printBarcodeBean.getBarcode()
                        + "%0H0040V0380L0101P02C9B" + printBarcodeBean.getBarcode() + "\n"
                        + "%0H0040V0450L0101P02C9B" +mContext.getResources().getString(R.string.num)+": "+num
//                + "  "+ unit+"\n"
                        + "Q1\n" + "Z\n";
                mPrintSend.sendBtMessage(encoding2);
            }
        }
        return flag;
    }

    /**
     * 打印标准条码
     *
     * @see [类、类#方法、类#成员]
     */
    public boolean printSmartLable(PrintBarcodeBean printBarcodeBean,int sumnum) {
        boolean flag = false;
        if (null == mBTDevice) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.connect_bluedevice));
            flag = false;
            return flag;
        }

        if (null == mPrintSend) {
            AlertDialogUtils.showFailedDialog(mContext, mContext.getString(R.string.blue_connected_failed));
            flag = false;
            return flag;
        }
//        int num = Integer.valueOf(printBarcodeBean.getQty());
        int num = 10;
        String barcode = "A01A01A01A01";
        String encoding2 = "A\n" + "PS\n"
                + "%0H0040V0050L0101P02C9B" +mContext.getResources().getString(R.string.vendor)+": "+ "厂商" + "\n"
                + "%0H0040V0120L0101P02C9B" +mContext.getResources().getString(R.string.data)+": "+ "日期" + "\n"
                + "%0H0040V0190L0101P02C9B" +mContext.getResources().getString(R.string.item_no)+": "+ "料号" + "\n"
                + "%0H0280V00402D30,M,06,1,0DN"+barcode.length()+","+barcode
                + "%0H0040V0260L0101P02C9B" +mContext.getResources().getString(R.string.item_name)+": "+ "品名" + "\n"
                + "%0H0040V0330L0101P02C9B" +mContext.getResources().getString(R.string.model)+": "+ "规格" + "\n"
                + "%0H0040V0400L0101P02C9B" +mContext.getResources().getString(R.string.batch_no)+": "+ "批号" + "\n"
                + "%0H0040V0470L0101P02C9B" +mContext.getResources().getString(R.string.num)+": "+num
//                + "  "+ unit+"\n"
                + "Q1\n" + "Z\n";
        mPrintSend.sendBtMessage(encoding2);
//        if(num != 0){
//            if(sumnum%num == 0){
//                for (int i = 0; i < StringUtils.string2Float(printBarcodeBean.getPrint_num()); i++) {
//                    mPrintSend.sendBtMessage(encoding2);
//                }
//            }else{
//                for (int i = 0; i < StringUtils.string2Float(printBarcodeBean.getPrint_num())-1; i++) {
//                    mPrintSend.sendBtMessage(encoding2);
//                }
//                num = sumnum%num;
//                encoding2 = "A\n" + "PS\n"
//                        + "%0H0040V0070L0101P02C9B" +mContext.getResources().getString(R.string.item_name)+": "+ printBarcodeBean.getItem_name() + "\n"
//                        + "%0H0040V0140L0101P02C9B" +mContext.getResources().getString(R.string.model)+": "+ printBarcodeBean.getItem_spec() + "\n"
//                        + "%0H0150V02102D30,M,05,1,0DN"+printBarcodeBean.getBarcode().length()+","+printBarcodeBean.getBarcode()
//                        + "%0H0040V0380L0101P02C9B" + printBarcodeBean.getBarcode() + "\n"
//                        + "%0H0040V0450L0101P02C9B" +mContext.getResources().getString(R.string.num)+": "+num
////                + "  "+ unit+"\n"
//                        + "Q1\n" + "Z\n";
//                mPrintSend.sendBtMessage(encoding2);
//            }
//        }
        return flag;
    }
}
