package me.zujko.globalpics.network;

import me.zujko.globalpics.models.PhotoSearch;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrApiService {
    String BASE_URL  = "https://api.flickr.com/services/rest";

    @GET("/?method=flickr.photos.search")
    PhotoSearch getPhotoResults(@Query("text") String searchText,
                                @Query("lat") double latitude,
                                @Query("lon") double longitude,
                                @Query("radius") Integer radius,
                                @Query("per_page") Integer perPage,
                                @Query("page") Integer page);
}
