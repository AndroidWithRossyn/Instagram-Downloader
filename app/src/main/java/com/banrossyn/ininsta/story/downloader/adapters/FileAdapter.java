package com.banrossyn.ininsta.story.downloader.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.VideoPlayerActivity;
import com.banrossyn.ininsta.story.downloader.listener.FileClickInterface;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private String TAG = "FileAdapter";
    public Context context;
    public List<File> fileArrayList;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public FileClickInterface fileListClickInterface;

    public FileAdapter(Context context, List<File> fileArrayList, FileClickInterface fileListClickInterface) {
        this.context = context;
        this.fileArrayList = fileArrayList;
        this.fileListClickInterface = fileListClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_downloaded, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        File fileItem = fileArrayList.get(i);
        @SuppressLint("SimpleDateFormat") String format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a").format(new Date(fileItem.lastModified()));
        viewHolder.textViewName.setText(fileArrayList.get(i).getName());
        viewHolder.textViewTime.setText(format);

        try {
            String extension = fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
            if (extension.equals(".mp4")) {
                viewHolder.imageViewPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageViewPlay.setVisibility(View.GONE);
            }
            Glide.with(context).load(fileItem.getPath()).into(viewHolder.imageViewCover);
        } catch (Exception ex) {
            Log.d(TAG, "onBindViewHolder: "+ex.getLocalizedMessage());
        }

        viewHolder.relativeLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileListClickInterface.getPosition(i, fileItem);
            }
        });

        viewHolder.imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo", fileItem.getPath().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return fileArrayList == null ? 0 : fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout relativeLayoutContent;
        public ImageView imageViewCover;
        public ImageView imageViewPlay;
        public ImageView imageViewMenu;
        public TextView textViewName;
        public TextView textViewTime;

        public ViewHolder(View view) {
            super(view);
            this.relativeLayoutContent = view.findViewById(R.id.relativeLayoutContent);
            this.imageViewCover = view.findViewById(R.id.imageViewCover);
            this.imageViewPlay = view.findViewById(R.id.imageViewPlay);
            this.textViewName = view.findViewById(R.id.textViewName);
            this.textViewTime = view.findViewById(R.id.textViewTime);
        }
    }

}