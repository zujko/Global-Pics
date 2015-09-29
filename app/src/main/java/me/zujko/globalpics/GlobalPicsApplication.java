package me.zujko.globalpics;

import android.app.Application;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import me.zujko.globalpics.network.FlickrApiGenerator;
import me.zujko.globalpics.network.FlickrApiService;

public class GlobalPicsApplication extends Application {
    public static JobManager jobManager;

    public static FlickrApiService FLICKR_API;

    @Override
    public void onCreate() {
        super.onCreate();
        createJobManager();
        createFlickrApi();
    }

    private void createFlickrApi() {
        FLICKR_API = FlickrApiGenerator.createFlickrApi(FlickrApiService.class);
    }

    private void createJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOB MANAGER";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG + " ERROR", String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG + " ERROR", String.format(text, args));
                    }
                })
                .minConsumerCount(1)
                .maxConsumerCount(5)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();

        jobManager = new JobManager(this, configuration);

    }

    public static JobManager getJobManager() {
        return jobManager;
    }
}
