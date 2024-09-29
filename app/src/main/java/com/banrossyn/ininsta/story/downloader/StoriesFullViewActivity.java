package com.banrossyn.ininsta.story.downloader;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class StoriesFullViewActivity extends AppCompatActivity {
    private StoriesFullViewActivity activity;
    private String filePathNew;
    ImageView imageViewPreview,imageViewBack;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_stories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_stories), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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