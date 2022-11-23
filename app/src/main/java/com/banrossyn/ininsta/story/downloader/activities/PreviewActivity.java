package com.banrossyn.ininsta.story.downloader.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.adapters.PreviewAdapter;
import com.banrossyn.ininsta.story.downloader.utils.Utils;

import java.io.File;
import java.util.ArrayList;

import static com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils.shareImage;
import static com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils.shareVideo;

public class PreviewActivity extends AppCompatActivity {
    private PreviewActivity previewActivity;
    private ArrayList<File> fileArrayList;
    private int Position = 0;
    PreviewAdapter showImagesAdapter;
    public ViewPager viewPager;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.preview));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        previewActivity = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileArrayList= (ArrayList<File>) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position",0);
        }
        initViews();
        InterstitialAd();

    }

    public void initViews(){
        this.viewPager = findViewById(R.id.viewPager);
        showImagesAdapter=new PreviewAdapter(this, fileArrayList, PreviewActivity.this);

        this.viewPager.setAdapter(showImagesAdapter);
        this.viewPager.setCurrentItem(Position);

        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Position=arg0;
                System.out.println("Current position=="+Position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });

    }

    public void initDeleteDialog() {
        final BottomSheetDialog dialogSortBy = new BottomSheetDialog(PreviewActivity.this, R.style.SheetDialog);
        dialogSortBy.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSortBy.setContentView(R.layout.dialog_delete);
        dialogSortBy.show();
        TextView textViewDelete = dialogSortBy.findViewById(R.id.textViewDelete);
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                boolean delete = fileArrayList.get(Position).delete();
                if (delete){
                    fileArrayList.remove(Position);
                    showImagesAdapter.notifyDataSetChanged();
                    Utils.setToast(previewActivity,getResources().getString(R.string.file_deleted));
                    if (fileArrayList.size()==0){
                        onBackPressed();
                    }
                }

                dialogSortBy.dismiss();

            }
        });

        TextView textViewCancel = dialogSortBy.findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (interstitialAd != null) {
                    interstitialAd.show(PreviewActivity.this);
                    interstitialAd.setFullScreenContentCallback(
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    PreviewActivity.this.interstitialAd = null;
                                    dialogSortBy.dismiss();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    PreviewActivity.this.interstitialAd = null;
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                }
                            });
                } else {
                    dialogSortBy.dismiss();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        previewActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_delete:
                initDeleteDialog();
                return true;
            case R.id.action_share:
                shareView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareView() {
        if (fileArrayList.get(Position).getName().contains(".mp4")){
            Log.d("SSSSS", "onClick: "+fileArrayList.get(Position)+"");
            shareVideo(previewActivity,fileArrayList.get(Position).getPath());
        }else {
            shareImage(previewActivity,fileArrayList.get(Position).getPath());
        }
    }

    public final void InterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                getString(R.string.admob_inter_id),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        PreviewActivity.this.interstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
            interstitialAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                           PreviewActivity.this.interstitialAd = null;
                            onBackPressed();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            PreviewActivity.this.interstitialAd = null;
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
        } else {
            super.onBackPressed();
        }
    }
}
