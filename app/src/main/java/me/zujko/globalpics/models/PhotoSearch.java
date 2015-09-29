package me.zujko.globalpics.models;

import java.util.List;

public class PhotoSearch {
    PhotoResult photos;

    public PhotoResult getPhotoResult() {
        return photos;
    }

    public class PhotoResult {
        Integer page;

        Integer pages;

        Integer perpage;

        String total;

        List<Photo> photo;

        public Integer getPage() {
            return page;
        }

        public Integer getPages() {
            return pages;
        }

        public Integer getPerpage() {
            return perpage;
        }

        public String getTotal() {
            return total;
        }

        public List<Photo> getListOfPhotos() {
            return photo;
        }

    }
}
