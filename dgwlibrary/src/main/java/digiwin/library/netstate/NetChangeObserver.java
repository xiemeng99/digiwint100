package digiwin.library.netstate;


/**
 * 描述:
 * Created by mjd on 2017/1/16.
 */

public interface NetChangeObserver {

    void onConnect(NetworkUtils.NetworkType type);

    void onDisConnect();

}
