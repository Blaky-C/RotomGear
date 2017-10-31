package com.example.rotomugear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rotomugear.PlayerActivity;
import com.example.rotomugear.R;
import com.example.rotomugear.bean.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2017/10/31.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<Music> musicList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        LinearLayout content;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.pic);
            name = (TextView) view.findViewById(R.id.name);
            content = (LinearLayout)view.findViewById(R.id.item);
        }
    }

    public MusicAdapter(List<Music> list){
        this.musicList = list;
    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        final MusicAdapter.ViewHolder holder = new ViewHolder(view);
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                PlayerActivity.clickItem(musicList.get(position));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.name.setText(music.getFileName());
        holder.image.setImageResource(R.drawable.default_music);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
