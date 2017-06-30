package digiwin.smartdepott100.core.printer;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.module.bean.purchase.RawMaterialPrintBean;
import digiwin.smartdepott100.module.bean.stock.PrintLabelFlowBean;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;


/**
 * @des      无线打印机管理
 * @author  xiemeng
 * @date    2017/2/21
 */
public class WiFiPrintManager {
    private static final String TAG = "WiFiPrintManager";
    public static WiFiPrintManager manager;
    /**
     * 超时时间
     */
    private int Net_ReceiveTimeout = 1000;
    /**
     * 是否开启
     */
    public boolean isOpen = false;
    private Socket socket;

    private PrintSend printSend;

    private static Handler handler;


    public static WiFiPrintManager getManager() {
       // if (null == manager) {
            manager = new WiFiPrintManager();
            handler=new Handler(Looper.getMainLooper());
      //  }
        return manager;
    }

    public  interface OpenWiFiPrintListener{
        public void  isOpen(boolean isOpen);
    }

    /**
     * @param IP           打印机IP地址
     * @param OPEN_NETPORT 端口
     * @return 是否开启
     */
    public void openWiFi(final String IP, final int OPEN_NETPORT, final OpenWiFiPrintListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG, "openWiFi--");
                try {
                    SocketAddress ipe = new InetSocketAddress("10.10.0.203", 9100);
                    socket = new Socket();
                    socket.connect(ipe);
                    socket.setSoTimeout(Net_ReceiveTimeout);
                    printSend = new PrintSend(socket);
                    isOpen = socket.isConnected();
                } catch (IOException e) {
                    isOpen = false;
                    LogUtils.e(TAG, "IOException--连接不成功");
                }
                catch (Exception e) {
                    isOpen = false;
                    LogUtils.e(TAG, "Exception--连接不成功"+e);
                }
                LogUtils.e(TAG, "isOpen--isOpen："+isOpen);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.isOpen(isOpen);
                    }
                });
            }
        },null);
    }
    /**
     * @param IP 打印机IP地址(含端口)
     * @return 是否开启
     */
    public void openWiFi(final String IP, final OpenWiFiPrintListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG, "openWiFi--");
                try {
                    int index = IP.indexOf(":");
                    //ip地址10.10.0.203:9100
                    String ip = IP.substring(0,index);
                    int duankou=Integer.parseInt(IP.substring(index+1, IP.length()));
                    LogUtils.e(TAG,"ip---"+ip+"duankou---"+duankou);
                    SocketAddress ipe = new InetSocketAddress(ip,duankou);
                    socket = new Socket();
                    socket.connect(ipe);
                    socket.setSoTimeout(Net_ReceiveTimeout);
                    printSend = new PrintSend(socket);
                    isOpen = socket.isConnected();
                } catch (IOException e) {
                    isOpen = false;
                    LogUtils.e(TAG, "IOException--连接不成功"+e);
                }
                catch (Exception e) {
                    isOpen = false;
                    LogUtils.e(TAG, "Exception--连接不成功"+e);
                }
                LogUtils.e(TAG, "isOpen--isOpen："+isOpen);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.isOpen(isOpen);
                    }
                });
            }
        },null);
    }

    /**
     * 关闭无线通信
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印测试内容
     */
    public void printText(String msg) {
        try {
            String encoding2="A"+
                    "H400V300P2L0101K9B中文ABCD1234"+
                    "%0H0040V0100BG02120>G" + "123"+ "\n"+
                    "Q1"+
                    "Z";
            String encoding3="1231";
            printSend.sendMessage(encoding3);
            close();
        } catch (Exception e) {

        }
    }
    /**
     * 打印测试内容
     */
    public void printText3(String msg) {
        try {
            BaseApplication context = BaseApplication.getInstance();
            String chinese = "^XA"+"\n"+
                    "^FO80,20^GB660,360,2^FS"+
                    "^SEE:GB18030.DAT^FS"+
                    "^CWZ,E:SIMSUN.FNT^FS"+
                    "^CI26"+"\n"+
                    "^FO100,40^AZN,24,32^FD"+context.getString(R.string.supplier)+"    "+"测试供应商"+"^FS"+
                    "^FO100,100^AZN,24,32^FD"+context.getString(R.string.item_no)+"    "+"测试料号"+"^FS"+
                    "^FO100,160^AZN,24,32^FD"+context.getString(R.string.item_name)+"    "+"测试品名"+"^FS"+
                    "^FO100,220^AZN,24,32^FD"+context.getString(R.string.model)+"    "+"测试规格"+"^FS"+
                    "^FO600,40^BQN,2,7^FDMM,A"+"barcode_123"+"^FS"+
                    "^FO100,280^ABN,17,17^FD"+context.getString(R.string.luhao)+"    "+"测试炉号"+"^FS"+
                    "^FO100,340^ABN,17,17^FD"+context.getString(R.string.weight)+"    "+"标签数"+"^FS"+
                    "^FO100,400^ABN,17,17^FD"+context.getString(R.string.data)+"    "+"2017-06-07"+"^FS"+
                    "^FO100,460^ABN,17,17^FD"+context.getString(R.string.write_name)+"^FS"+
                    "^XZ";
            printSend.sendMessage(chinese);
        } catch (Exception e) {

        }
    }
    /**
     * 原材料打印
     * @param bean
     * @param printlabel
     */
    public void printRawMaterial(RawMaterialPrintBean bean, String printlabel) {
        try {
            BaseApplication context = BaseApplication.getInstance();
            String chinese = "^XA"+"\n"+
                    "^FO80,20^GB660,360,2^FS"+
                    "^SEE:GB18030.DAT^FS"+
                    "^CWZ,E:SIMSUN.FNT^FS"+
                    "^CI26"+"\n"+
                    "^FO100,40^AZN,24,32^FD"+context.getString(R.string.supplier)+"    "+bean.getSupplier_name()+"^FS"+
                    "^FO100,100^AZN,24,32^FD"+context.getString(R.string.item_no)+"    "+bean.getItem_no()+"^FS"+
                    "^FO100,160^AZN,24,32^FD"+context.getString(R.string.item_name)+"    "+bean.getItem_name()+"^FS"+
                    "^FO100,220^AZN,24,32^FD"+context.getString(R.string.model)+"    "+bean.getItem_spec()+"^FS"+
                    "^FO600,40^BQN,2,7^FDMM,A"+bean.getLot_no()+"^FS"+
                    "^FO100,280^ABN,17,17^FD"+context.getString(R.string.luhao)+"    "+bean.getFurnace_no()+"^FS"+
                    "^FO100,340^ABN,17,17^FD"+context.getString(R.string.weight)+"    "+printlabel+"^FS"+
                    "^FO100,400^ABN,17,17^FD"+context.getString(R.string.data)+"    "+bean.getCreate_date()+"^FS"+
                    "^FO100,460^ABN,17,17^FD"+context.getString(R.string.write_name)+"^FS"+
                    "^XZ";
            printSend.sendMessage(chinese);
        } catch (Exception e) {

        }
    }
    /**
     * 流传打印
     * @param bean
     * @param printlabel
     */
    public void printFlowText(PrintLabelFlowBean bean,String printlabel) {
        try {
            BaseApplication context = BaseApplication.getInstance();
            String chinese = "^XA"+"\n"+
                    "^FO80,20^GB660,360,2^FS"+
                    "^SEE:GB18030.DAT^FS"+
                    "^CWZ,E:SIMSUN.FNT^FS"+
                    "^CI26"+"\n"+
                    "^FO100,40^AZN,24,32^FD"+context.getString(R.string.order_number)+"    "+bean.getWo_no()+"^FS"+
                    "^FO100,100^AZN,24,32^FD"+context.getString(R.string.item_no)+"    "+bean.getItem_no()+"^FS"+
                    "^FO100,160^AZN,24,32^FD"+context.getString(R.string.item_name)+"    "+bean.getItem_name()+"^FS"+
                    "^FO600,40^BQN,2,7^FDMM,A"+bean.getPlot_no()+"^FS"+
                    "^FO100,220^ABN,17,17^FD"+context.getString(R.string.num)+"    "+printlabel+"^FS"+
                    "^FO100,280^ABN,17,17^FD"+context.getString(R.string.shipping_date)+"    "+bean.getShipment_date()+"^FS"+
                    "^FO100,340^ABN,17,17^FD"+context.getString(R.string.batch_no)+"    "+bean.getPlot_no()+"^FS"+
                    "^XZ";
            printSend.sendMessage(chinese);
        } catch (Exception e) {

        }
    }
    /**
     * 成品标签打印
     */
    public void printFinishText(PrintLabelFlowBean bean,String printlabel) {
        try {
            BaseApplication context = BaseApplication.getInstance();
            String chinese = "^XA"+"\n"+
                    "^FO80,20^GB660,360,2^FS"+
                    "^SEE:GB18030.DAT^FS"+
                    "^CWZ,E:SIMSUN.FNT^FS"+
                    "^CI26"+"\n"+
                    "^FO100,40^AZN,24,32^FD"+context.getString(R.string.item_no)+"    "+bean.getItem_no()+"^FS"+
                    "^FO100,100^AZN,24,32^FD"+context.getString(R.string.item_name)+"    "+bean.getItem_name()+"^FS"+
                    "^FO600,40^BQN,2,7^FDMM,A"+bean.getPlot_no()+"^FS"+
                    "^FO100,160^ABN,17,17^FD"+context.getString(R.string.in_order)+"    "+bean.getSo_no()+"^FS"+
                    "^FO100,220^ABN,17,17^FD"+context.getString(R.string.num)+"    "+printlabel+"^FS"+
                    "^FO100,280^ABN,17,17^FD"+context.getString(R.string.shipping_order)+"    "+bean.getShipment_no()+"^FS"+
                    "^FO100,340^ABN,17,17^FD"+context.getString(R.string.shipping_date)+"    "+bean.getShipment_date()+"^FS"+
                    "^FO100,400^ABN,17,17^FD"+context.getString(R.string.batch_no)+"    "+bean.getPlot_no()+"^FS"+
                    "^XZ";
            printSend.sendMessage(chinese);
        } catch (Exception e) {

        }
    }

}
