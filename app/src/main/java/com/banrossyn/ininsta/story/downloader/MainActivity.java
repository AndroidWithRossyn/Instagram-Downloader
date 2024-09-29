package com.banrossyn.ininsta.story.downloader;

import static com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils.createFile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.banrossyn.ininsta.story.downloader.adapters.ViewPagerAdapter;
import com.banrossyn.ininsta.story.downloader.api.CommonAPI;
import com.banrossyn.ininsta.story.downloader.databinding.ActivityMainBinding;
import com.banrossyn.ininsta.story.downloader.fragments.AboutFragment;
import com.banrossyn.ininsta.story.downloader.fragments.DownloadFragment;
import com.banrossyn.ininsta.story.downloader.fragments.HomeFragment;
import com.banrossyn.ininsta.story.downloader.fragments.StoryFragment;
import com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    MainActivity mainActivity;
    ActivityMainBinding binding;

    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static CommonAPI commonAPI;

    ViewPagerAdapter viewPagerAdapter;


    HomeFragment homeFragment = new HomeFragment();
    StoryFragment storyFragment = new StoryFragment();
    DownloadFragment downloadFragment = new DownloadFragment();
    AboutFragment aboutFragment = new AboutFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainActivity = this;
        commonAPI = CommonAPI.getInstance(mainActivity);


        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }

        DirectoryUtils.createFile();


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(storyFragment);
        viewPagerAdapter.addFragment(downloadFragment);
        viewPagerAdapter.addFragment(aboutFragment);

        binding.mainViewPager.setAdapter(viewPagerAdapter);
        binding.mainViewPager.setOffscreenPageLimit(3);


        binding.mainViewPager.setCurrentItem(0, true);

        binding.bottomBubbleNavigation.setCurrentActiveItem(0);


        binding.bottomBubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int i) {
                switch (i) {
                    case 0:
                        binding.mainViewPager.setCurrentItem(0, true);

                        break;
                    case 1:
                        binding.mainViewPager.setCurrentItem(1, true);
                        break;
                    case 2:
                        binding.mainViewPager.setCurrentItem(2, true);
                        break;
                    case 3:
                        binding.mainViewPager.setCurrentItem(3, true);
                        break;

                }
            }
        });


        binding.mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.bottomBubbleNavigation.setCurrentActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryActivity();
            }
        }

    }

    public void startGalleryActivity() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {

        super.onActivityResult(i, i2, intent);
    }

}