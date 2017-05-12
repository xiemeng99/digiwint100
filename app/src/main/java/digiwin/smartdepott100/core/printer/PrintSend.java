package digiwin.smartdepott100.core.printer;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import digiwin.library.utils.LogUtils;


/**
 * @author xiemeng
 * @des 打印机发送文本
 * @date 2017/2/20
 */
public class PrintSend {
    private static final String TAG = "PrintSend";
    /**
     * 蓝牙通信
     */
    private BluetoothSocket mBlueSocket;
    /**
     * 无线通信
     */
    private Socket mSocket;

    public PrintSend(BluetoothSocket socket) {
        mBlueSocket = socket;
    }

    public PrintSend(Socket socket) {
        mSocket = socket;
    }

    /**
     * 蓝牙发送字符串
     */
    public void sendBtMessage(String message) {
        OutputStream tmpOut = null;
        try {
            tmpOut = mBlueSocket.getOutputStream();
            byte[] send = getBytes(message);
            tmpOut.write(send, 0, send.length);
            tmpOut.flush();
        } catch (IOException e) {
            LogUtils.e(TAG, "Exception during write" + e);
        } catch (Exception e) {
            LogUtils.e(TAG, "write异常" + e);
        }

    }

    /**
     * 无线发送字符串
     */
    public void sendMessage(String message) {
        OutputStream tmpOut = null;
        try {
            tmpOut = mSocket.getOutputStream();
            byte[] send = getBytes(message);
            tmpOut.write(send, 0, send.length);
            tmpOut.flush();
        } catch (IOException e) {
            LogUtils.e(TAG, "Exception during write" + e);
        } catch (Exception e) {
            LogUtils.e(TAG, "write异常" + e);
        }
    }

    /**
     * 转换成 byte[]
     */
    private byte[] getBytes(String message) {
        byte[] send = null;
        if (message.length() > 0) {
            try {
                send = new String(message).getBytes("GB2312");
            } catch (UnsupportedEncodingException e) {
                send = message.getBytes();
            }
        }
        return send;
    }

}
