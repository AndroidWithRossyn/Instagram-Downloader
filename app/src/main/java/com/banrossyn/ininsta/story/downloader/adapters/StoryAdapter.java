package com.banrossyn.ininsta.story.downloader.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.StoriesFullViewActivity;
import com.banrossyn.ininsta.story.downloader.VideoPlayerActivity;
import com.banrossyn.ininsta.story.downloader.api.model.ItemModel;
import com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils;
import com.banrossyn.ininsta.story.downloader.utils.Utils;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ItemModel> storyItemModelList;

    public StoryAdapter(Context context, ArrayList<ItemModel> arrayList) {
        this.context = context;
        this.storyItemModelList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stories, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ItemModel itemModel = this.storyItemModelList.get(i);
        try {
            if (itemModel.getMediatype() == 2) {
                viewHolder.imageViewPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageViewPlay.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.getImageversions2().getCandidates().get(0).getUrl()).into(viewHolder.imageViewCover);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoryAdapter.this.context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo", itemModel.getVideoversions().get(0).getUrl());
                StoryAdapter.this.context.startActivity(intent);

            }
        });

        viewHolder.imageViewCover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (itemModel.getMediatype() == 2) {
                    Intent intent = new Intent(StoryAdapter.this.context, VideoPlayerActivity.class);
                    intent.putExtra("PathVideo", itemModel.getVideoversions().get(0).getUrl());
                    StoryAdapter.this.context.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(StoryAdapter.this.context, StoriesFullViewActivity.class);
                intent2.putExtra("ImageDataFile", itemModel.getImageversions2().getCandidates().get(0).getUrl());
                StoryAdapter.this.context.startActivity(intent2);
            }
        });


        viewHolder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemModel.getMediatype() == 2) {
                    String url = itemModel.getVideoversions().get(0).getUrl();
                    Context context = StoryAdapter.this.context;
                    Utils.startDownload(url, DirectoryUtils.FOLDER, context, "Instagram_story_" + System.currentTimeMillis() + ".mp4");
                    return;
                }
                String url2 = itemModel.getImageversions2().getCandidates().get(0).getUrl();
                Context context2 = StoryAdapter.this.context;
                Utils.startDownload(url2, DirectoryUtils.FOLDER, context2, "Instagram_story_" + System.currentTimeMillis() + ".png");
            }
        });
    }
    @Override
    public int getItemCount() {
        ArrayList<ItemModel> arrayList = this.storyItemModelList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayoutContent;
        public ImageView imageViewCover;
        public ImageView imageViewPlay;
        public ImageView imageViewDownload;

        public ViewHolder(View view) {
            super(view);
            this.relativeLayoutContent = view.findViewById(R.id.relativeLayoutContent);
            this.imageViewCover = view.findViewById(R.id.imageViewCover);
            this.imageViewPlay = view.findViewById(R.id.imageViewPlay);
            this.imageViewDownload = view.findViewById(R.id.imageViewDownload);
        }
    }
}