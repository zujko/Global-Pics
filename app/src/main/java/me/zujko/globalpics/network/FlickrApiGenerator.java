package me.zujko.globalpics.network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class FlickrApiGenerator {

    public static <S> S createFlickrApi(Class<S> flickrApiService) {
        OkHttpClient okHttpClient = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(FlickrApiService.BASE_URL)
                .setClient(new OkClient(okHttpClient));

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addQueryParam("api_key","API_KEY");
                request.addQueryParam("format","json");
                request.addQueryParam("nojsoncallback","1");
            }
        });

        return builder.build().create(flickrApiService);
    }
}
