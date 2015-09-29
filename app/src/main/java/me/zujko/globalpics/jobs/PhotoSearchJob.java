package me.zujko.globalpics.jobs;

import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import me.zujko.globalpics.GlobalPicsApplication;
import me.zujko.globalpics.models.PhotoSearch;
import retrofit.RetrofitError;

public class PhotoSearchJob extends Job {
    static final String TAG = "PHOTO SEARCH JOB";

    final String searchText;
    final double latitude;
    final double longitude;
    final Integer radius;
    final Integer perPage;
    final Integer page;

    public PhotoSearchJob(String searchText, double latitude, double longitude, Integer radius, Integer perPage, Integer page) {
        super(new Params(1).requireNetwork());
        this.searchText = searchText;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.perPage = perPage;
        this.page = page;
    }

    @Override
    public void onAdded() {}

    @Override
    public void onRun() throws Throwable {
        try {
            PhotoSearch photoSearch = GlobalPicsApplication.FLICKR_API.getPhotoResults(searchText, latitude, longitude, radius, perPage, page);
        } catch (RetrofitError error) {
            Log.e(TAG,error.toString());
        }
    }

    @Override
    protected void onCancel() {
        Log.d(TAG,"Job was canceled");
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
