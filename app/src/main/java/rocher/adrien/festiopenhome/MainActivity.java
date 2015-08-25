package rocher.adrien.festiopenhome;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import android.util.Log;

import org.openhome.net.core.IMessageListener;
import org.openhome.net.core.InitParams;
import org.openhome.net.core.Library;
import org.openhome.net.core.SubnetList;
import org.openhome.net.core.NetworkAdapter;

import org.openhome.net.controlpoint.*;
import org.openhome.net.controlpoint.CpDeviceListUpnpAll;
import org.openhome.net.controlpoint.ICpDeviceListListener;


import java.net.Inet4Address;
import java.util.logging.Logger;
import java.util.logging.Level;



public class MainActivity extends AppCompatActivity {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Library lib;
    CpDeviceList DvList;
    CpDeviceListUpnpAll allDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InitParams initParams = new InitParams();
// set values
        initParams.setMsearchTimeSecs(1);// MsearchTimeSecs = 1,
        //initParams.setUseLoopbackNetworkAdapter(); //UseLoopbackNetworkAdapter = true,
        initParams.setDvServerPort(0);//DvUpnpWebServerPort = 0

        initParams.getMsearchTimeSecs();
        initParams.getDvServerPort();

        lib = new Library();
        lib.initialise(initParams);
        SubnetList subnetList = new SubnetList();
        NetworkAdapter nif = subnetList.getSubnet(0);
        Inet4Address subnet = nif.getSubnet();
        subnetList.destroy();
        lib.startCp(subnet);


/*
        initParams.setLogOutput(new IMessageListener() {
            @Override
            public void message(String msg) {
                logger.log(Level.INFO, msg);
            }
        });
*/
//        DvList = new CpDeviceList();

        allDevice = new CpDeviceListUpnpAll(new ICpDeviceListListener() {
            @Override
            public void deviceAdded(CpDevice cpDevice) {
                cpDevice.addRef();

                String presDv = cpDevice.getUdn();
                String friendDv = cpDevice.getAttribute("Upnp.FriendlyName").getValue();

                /*if (presDv.length() > 0) {
                    Log.d("New Device", presDv);
                }*/
                if (friendDv.length() > 0) {
                    Log.d("New Device", friendDv);
                }

//                DeviceFragment DvFrag = (DeviceFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_device);
//                ListView clientList = (ListView) DvFrag.getView().findViewById(R.id.ClientView);

            }

            @Override
            public void deviceRemoved(CpDevice cpDevice) {
                cpDevice.removeRef();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lib.close();
    }


}
