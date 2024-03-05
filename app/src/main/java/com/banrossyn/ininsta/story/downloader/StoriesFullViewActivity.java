package com.banrossyn.ininsta.story.downloader;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class StoriesFullViewActivity extends AppCompatActivity {
    private StoriesFullViewActivity activity;
    private String filePathNew;
    ImageView imageViewPreview,imageViewBack;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_stories);
        this.activity = this;
        imageViewPreview = findViewById(R.id.imageViewPreview);
        imageViewBack = findViewById(R.id.imageViewClose);
        if (getIntent().getExtras() != null) {
            this.filePathNew = getIntent().getStringExtra("ImageDataFile");
            Glide.with(this.activity).load(this.filePathNew).into(this.imageViewPreview);
        }
        initViews();
    }

    public void initViews() {
        this.imageViewBack.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.activity = this;
    }
}