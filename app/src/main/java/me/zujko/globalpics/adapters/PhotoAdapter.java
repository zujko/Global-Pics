package me.zujko.globalpics.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zujko.globalpics.R;
import me.zujko.globalpics.models.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> mPhotosList;
    private Context mContext;

    public PhotoAdapter(List<Photo> photosList, Context context) {
        mPhotosList = photosList;
        mContext = context;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.photo_item_layout, parent, false);

        return new ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder viewHolder, int position) {
        Photo photo = mPhotosList.get(position);

        String Url = "https://farm"+photo.getFarm()+".staticflickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+"_m.jpg";

        Picasso.with(mContext)
                .load(Url)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPhotosList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo_imageview);
        }
    }

}
