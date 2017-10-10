package digiwin.smartdepott100.main.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.DialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ToastUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.printer.BlueToothManager;

/**
 * @author xiemeng
 * @des 蓝牙设备选择对话框
 * @date 2017/1/16
 */

public class BlueToothDialog {

    private static final String TAG = "BlueToothDialog";
    /**
     * 蓝牙设备列表
     */
    RecyclerView rv_list;

    List<String> datas;
    /**
     * 蓝牙系统适配
     */
    BluetoothAdapter mBtAdapter;
    /**
     * 设备列表适配
     */
    BlueToothChooseAdapter adapter;

    Activity mActivity;
    /**
     * 连接管理
     */
    BlueToothManager blueToothManager;

    public static final int REQUEST_ENABLE_BT = 8807;

    DialogUtils dialogUtils;

    public BlueToothDialog(Activity activity, IsConnectedDeviceListener deviceListener) {
        deviceListener.isConnected(false);
        mActivity = activity;
        blueToothManager = BlueToothManager.getManager(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_bluetooth, null);
        dialogUtils = new DialogUtils(activity, dialogView);
        datas = new ArrayList<>();
        rv_list = (RecyclerView) dialogView.findViewById(R.id.rv_list);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null != mBtAdapter) {
            //如果有蓝牙设备则打开蓝牙
//            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            activity.startActivityForResult(enabler, REQUEST_ENABLE_BT);
            LinearLayoutManager manager = new LinearLayoutManager(activity);
            adapter = new BlueToothChooseAdapter(datas, activity);
            rv_list.setLayoutManager(manager);
            rv_list.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
            rv_list.setAdapter(adapter);
            dialogUtils.showDialog((int) (ViewUtils.getScreenWidth(activity) * 0.8), (int) (ViewUtils.getScreenHeight(activity) * 0.5));
            getDevices();
            clickItem(deviceListener);
        }
    }

    /**
     * 点击一个设备进行连接
     */
    private void clickItem(final IsConnectedDeviceListener deviceListener) {
        adapter.setClick(new BlueToothChooseAdapter.BlueToothOnItemClickListener() {
            @Override
            public void onClick(View view, int position, final BlueToothChooseAdapter.mViewHolder holder) {
                try {
                    AlertDialogUtils.showLoadingDialog(mActivity);
                    String item = datas.get(position);
                    String address = item.substring(item.length() - 17);
                    BluetoothDevice device = mBtAdapter.getRemoteDevice(address);
                    String deviceName = device.getName();
                    blueToothManager.connect(address, deviceName, new BlueToothManager.ConnectListener() {
                        @Override
                        public void onSuccess() {
                            //TODO:更新设置的UI
                            try {
                                AlertDialogUtils.dismissDialog();
                                ToastUtils.showToastByInt(mActivity, R.string.connect_device_ok);
                                holder.tv_bluename.setTextColor(mActivity.getResources().getColor(R.color.Base_color));
                                holder.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.bluetoothchose));
                                deviceListener.isConnected(true);
                                dialogUtils.dismissDialog();
                            } catch (Exception e) {
                                dialogUtils.dismissDialog();
                                connectFailed(holder, R.string.connect_device_failed);
                                deviceListener.isConnected(false);
                                LogUtils.e(TAG, "clickItem===onSuccess====异常");
                            }
                        }

                        @Override
                        public void onFailed(int msg) {
                            connectFailed(holder, R.string.connect_device_failed);
                            deviceListener.isConnected(false);
                        }
                    });
                } catch (Exception e) {
                    LogUtils.e(TAG, "clickItem异常");
                }
            }
        });
    }

    /**
     * 获取已经绑定过的设备列表
     * int deviceType = device.getBluetoothClass().getMajorDeviceClass();
     * 不同设备类型该值不同，比如computer蓝牙为256、phone 蓝牙为512、打印机蓝牙为1536等等。
     */
    private void getDevices() {
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                int deviceType = device.getBluetoothClass().getMajorDeviceClass();
                if (1536==deviceType&&!datas.contains(device)) {
                    datas.add(device.getName() + "\n" + device.getAddress());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 用于判断蓝牙是否连接成功
     */
    public interface IsConnectedDeviceListener {
        public void isConnected(boolean isConnected);
    }

    private void connectFailed(final BlueToothChooseAdapter.mViewHolder holder, final int msg) {
        AlertDialogUtils.dismissDialog();
        AlertDialogUtils.showFailedDialog(mActivity, mActivity.getResources().getString(msg));
        holder.tv_bluename.setTextColor(mActivity.getResources().getColor(R.color.connect_failed));
        holder.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.connect_error));

    }

    public void hide(){
        dialogUtils.dismissDialog();
    }


}
