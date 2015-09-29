package me.zujko.globalpics.events;

import java.util.List;

import me.zujko.globalpics.models.Photo;

public class PhotoLoadEvent {
    public final List<Photo> photoList;

    public PhotoLoadEvent(List<Photo> photoList) {
        this.photoList = photoList;
    }
}

