package com.banrossyn.ininsta.story.downloader.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.PreviewActivity;
import com.banrossyn.ininsta.story.downloader.VideoPlayerActivity;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;


public class PreviewAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<File> imageList;
    private LayoutInflater layoutInflater;
    PreviewActivity previewActivity;

    public PreviewAdapter(Context context, ArrayList<File> imageList, PreviewActivity previewActivity) {
        this.context = context;
        this.imageList = imageList;
        this.previewActivity = previewActivity;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup view, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.item_preview, view, false);

        assert imageLayout != null;
        final ImageView imageViewCover = imageLayout.findViewById(R.id.imageViewCover);
        final ImageView imageViewPlay = imageLayout.findViewById(R.id.imageViewPlay);


        Glide.with(context).load(imageList.get(position).getPath()).into(imageViewCover);
        view.addView(imageLayout, 0);
        String extension = imageList.get(position).getName().substring(imageList.get(position).getName().lastIndexOf("."));
        if (extension.equals(".mp4")){
            imageViewPlay.setVisibility(View.VISIBLE);
        }else {
            imageViewPlay.setVisibility(View.GONE);
        }

        imageViewPlay.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(imageList.get(position).getPath()), "video/*");
            context.startActivity(intent);
        });

        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo", imageList.get(position).getPath());
                context.startActivity(intent);

            }
        });

        return imageLayout;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, @NotNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}