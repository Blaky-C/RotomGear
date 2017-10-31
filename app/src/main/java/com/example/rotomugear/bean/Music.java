package com.example.rotomugear.bean;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.example.rotomugear.R;

import java.io.File;

/**
 * Created by Jack on 2017/10/31.
 */

public class Music {

    private MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    private String name;
    private String fileName;
    private String artist;
    private Bitmap pic = null;
    private String absolutePath;

    public Music(){
    }

    public Music(File f) {
        absolutePath = f.getAbsolutePath();
        Log.d("Music", absolutePath);
        mmr.setDataSource(absolutePath);
        name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        //使用默认图片
        //byte[] p = mmr.getEmbeddedPicture();
        //Log.d("Music", p+"");
        /*if (p!=null){
            pic = BitmapFactory.decodeByteArray(p, 0, p.length);
        }else {*/
        pic = null;

        fileName = f.getName();
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getArtist() {
        return artist;
    }

    public Bitmap getPic() {
        return pic;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
