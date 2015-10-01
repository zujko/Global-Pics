package me.zujko.globalpics.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.zujko.globalpics.R;
import me.zujko.globalpics.adapters.PhotoAdapter;
import me.zujko.globalpics.events.PhotoLoadEvent;
import me.zujko.globalpics.models.Photo;

public class MainActivity extends AppCompatActivity {

    private LocationManager mLocationManager;
    private RecyclerView recyclerView;
    private static List<Photo> photoList;
    private PhotoAdapter adapter;
    private Toolbar mToolbar;

    double testLat = 40.754929;
    double testLong = -73.981934;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Global Pics");
        //GlobalPicsApplication.getJobManager().addJobInBackground(new PhotoSearchJob("flower",testLat,testLong,5,40,1));
        photoList = new ArrayList<Photo>();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createNoGpsAlert();
        }

        recyclerView = (RecyclerView) findViewById(R.id.photo_recyclerview);

        adapter = new PhotoAdapter(photoList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

    /**
     * Creates an alert dialog if Location Services are not enabled on the device.
     * Location Services are needed to show images near the user.
     */
    private void createNoGpsAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.error_title_gps_disabled))
                .setMessage(getString(R.string.error_message_gps_disabled))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);

        AlertDialog mNoGpsAlertDialog = builder.create();
        mNoGpsAlertDialog.show();
    }

    public void onEventMainThread(PhotoLoadEvent event) {
        photoList.addAll(event.photoList);
        adapter.notifyDataSetChanged();
    }
}
