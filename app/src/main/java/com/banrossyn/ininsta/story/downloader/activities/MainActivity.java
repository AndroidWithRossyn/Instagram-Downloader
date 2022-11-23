package com.banrossyn.ininsta.story.downloader.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.adapters.StoryAdapter;
import com.banrossyn.ininsta.story.downloader.adapters.ProfileAdapter;
import com.banrossyn.ininsta.story.downloader.api.CommonAPI;
import com.banrossyn.ininsta.story.downloader.api.data.EdgeModel;
import com.banrossyn.ininsta.story.downloader.api.data.EdgeSidecarModel;
import com.banrossyn.ininsta.story.downloader.api.data.ResponseModel;
import com.banrossyn.ininsta.story.downloader.api.model.FullDetailModel;
import com.banrossyn.ininsta.story.downloader.api.model.NodeModel;
import com.banrossyn.ininsta.story.downloader.api.model.StoryModel;
import com.banrossyn.ininsta.story.downloader.api.model.TrayModel;
import com.banrossyn.ininsta.story.downloader.constants.Constants;
import com.banrossyn.ininsta.story.downloader.dialogs.RateDialog;
import com.banrossyn.ininsta.story.downloader.fragments.DownloadFragment;
import com.banrossyn.ininsta.story.downloader.listener.UserListInterface;
import com.banrossyn.ininsta.story.downloader.preference.SharePrefs;
import com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils;
import com.banrossyn.ininsta.story.downloader.utils.Utils;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

import static com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils.createFile;


public class MainActivity extends AppCompatActivity implements UserListInterface {
    MainActivity mainActivity;
    CommonAPI commonAPI;
    private ClipboardManager clipboardManager;
    public TextView textViewPaste, textViewDownload;

    public LottieAnimationView imageViewOpenFacebook, crown;
    public EditText editTextPasteUrl;

    FragmentTransaction fragmentTransaction;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    LinearLayout linearLayoutAccount;
    ConstraintLayout contraintLayoutLink;

    LinearLayout linearLayoutGallery, linearLayoutRate,
            linearLayoutShare, linearLayoutFeedBack, linearLayoutPrivacy,
            linearLayoutApps, linearLayoutStories, linearLayoutContainer, linearLayoutPlaceHolder;

    RelativeLayout scrollViewHelp;
    BubbleNavigationConstraintView bubbleNavigationLinearView;
    StoryAdapter storyAdapter;
    ProfileAdapter profileAdapter;
    private String photoUrl;
    private String videoUrl;

    RecyclerView recyclerViewUser, recyclerViewStories;
    private TextView textViewLogin;
    ProgressBar progressLoadingBar;

    private AdView adView;
    private InterstitialAd interstitialAd;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        BannerAd();
        InterstitialAd();
        refreshAd();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutGallery, new DownloadFragment());
        fragmentTransaction.commit();

        mainActivity = this;
        commonAPI = CommonAPI.getInstance(mainActivity);

        createFile();
        initViews();

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }

        bubbleNavigationLinearView = findViewById(R.id.top_navigation_constraint);
        linearLayoutAccount = findViewById(R.id.helpview);
        contraintLayoutLink = findViewById(R.id.homeview);
        linearLayoutGallery = findViewById(R.id.downloads_files_view);
        scrollViewHelp = findViewById(R.id.settingview);


        this.linearLayoutShare = findViewById(R.id.linearLayoutShare);
        this.linearLayoutContainer = findViewById(R.id.storiesview);
        this.linearLayoutRate = findViewById(R.id.linearLayoutRate);
        this.linearLayoutFeedBack = findViewById(R.id.linearLayoutFeedBack);
        this.linearLayoutPrivacy = findViewById(R.id.linearLayoutPrivacy);
        this.linearLayoutApps = findViewById(R.id.linearLayoutApps);

        this.linearLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "InSaver \n\n Hello Let me Recommend you this App\n Download easy Instagram Story's, Post's, Reels IGTV and More. ";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.banrossyn.post.story.downloader";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));

            }
        });

        this.linearLayoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RateDialog(MainActivity.this, false).show();
            }
        });

        this.linearLayoutFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd != null) {
                    interstitialAd.show(MainActivity.this);
                    interstitialAd.setFullScreenContentCallback(
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    MainActivity.this.interstitialAd = null;

                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:" + MainActivity.this.getString(R.string.email_feedback)));
                                    intent.setPackage("com.google.android.gm");
                                    intent.putExtra(Intent.EXTRA_SUBJECT, MainActivity.this.getString(R.string.app_name));
                                    startActivity(intent);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    MainActivity.this.interstitialAd = null;
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                }
                            });
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + MainActivity.this.getString(R.string.email_feedback)));
                    intent.setPackage("com.google.android.gm");
                    intent.putExtra(Intent.EXTRA_SUBJECT, MainActivity.this.getString(R.string.app_name));
                    startActivity(intent);
                }

            }
        });

        this.linearLayoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd != null) {
                    interstitialAd.show(MainActivity.this);
                    interstitialAd.setFullScreenContentCallback(
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    MainActivity.this.interstitialAd = null;

                                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url))));

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    MainActivity.this.interstitialAd = null;
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                }
                            });
                } else {

                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url))));

                }

            }
        });

        this.linearLayoutApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.developer_account_link))));
            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int i) {
                switch (i) {
                    case 0:
                        contraintLayoutLink.setVisibility(View.VISIBLE);
                        linearLayoutAccount.setVisibility(View.GONE);
                        linearLayoutGallery.setVisibility(View.GONE);
                        linearLayoutContainer.setVisibility(View.GONE);
                        scrollViewHelp.setVisibility(View.GONE);
                        break;
                    case 1:
                        contraintLayoutLink.setVisibility(View.GONE);
                        linearLayoutAccount.setVisibility(View.GONE);
                        linearLayoutGallery.setVisibility(View.GONE);
                        linearLayoutContainer.setVisibility(View.VISIBLE);
                        scrollViewHelp.setVisibility(View.GONE);
                        break;
                    case 2:
                        contraintLayoutLink.setVisibility(View.GONE);
                        linearLayoutAccount.setVisibility(View.VISIBLE);
                        linearLayoutGallery.setVisibility(View.GONE);
                        linearLayoutContainer.setVisibility(View.GONE);
                        scrollViewHelp.setVisibility(View.GONE);
                        break;
                    case 3:
                        contraintLayoutLink.setVisibility(View.GONE);
                        linearLayoutAccount.setVisibility(View.GONE);
                        linearLayoutGallery.setVisibility(View.VISIBLE);


                        scrollViewHelp.setVisibility(View.GONE);
                        linearLayoutContainer.setVisibility(View.GONE);
                        break;
                    case 4:
                        linearLayoutContainer.setVisibility(View.GONE);
                        contraintLayoutLink.setVisibility(View.GONE);
                        linearLayoutAccount.setVisibility(View.GONE);
                        linearLayoutGallery.setVisibility(View.GONE);
                        scrollViewHelp.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private boolean checkPermissions(int type) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) (MainActivity.this),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), type);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryActivity();
            } else {
                if (interstitialAd != null) {
                    interstitialAd.show(this);
                }

            }
            return;
        }

    }

    public void startGalleryActivity() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        mainActivity = this;
        assert mainActivity != null;
        clipboardManager = (ClipboardManager) mainActivity.getSystemService(CLIPBOARD_SERVICE);
        pasteText();
    }

    private void initViews() {
        this.imageViewOpenFacebook = findViewById(R.id.imageViewOpenFacebook);
        this.crown = findViewById(R.id.crown);
        this.textViewPaste = findViewById(R.id.textViewPaste);
        this.textViewDownload = findViewById(R.id.textViewDownload);
        this.editTextPasteUrl = findViewById(R.id.editTextPasteUrl);
        this.linearLayoutStories = findViewById(R.id.linearLayoutStories);
        this.linearLayoutPlaceHolder = findViewById(R.id.linearLayoutPlaceHold);
        this.textViewLogin = findViewById(R.id.textViewLogin);
        this.recyclerViewUser = findViewById(R.id.recyclerViewUser);
        this.recyclerViewStories = findViewById(R.id.recyclerViewStories);
        this.progressLoadingBar = findViewById(R.id.progressLoadingBar);

        clipboardManager = (ClipboardManager) mainActivity.getSystemService(CLIPBOARD_SERVICE);

        this.textViewDownload.setOnClickListener(v -> {
            String obj = this.editTextPasteUrl.getText().toString();
            if (obj.equals("")) {
                Utils.setToast(this.mainActivity, getResources().getString(R.string.enter_url));
            } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                Utils.setToast(this.mainActivity, getResources().getString(R.string.enter_valid_url));
            } else {
                getInstagramData();
            }
        });

        this.textViewPaste.setOnClickListener(v -> {
            pasteText();
        });

        this.imageViewOpenFacebook.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://instagram.com/");
            Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
            instagram.setPackage("com.instagram.android");
            try {
                startActivity(instagram);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/")));
            }

        });
        this.crown.setOnClickListener(v -> {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/banrossyn")));

        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        this.recyclerViewUser.setLayoutManager(gridLayoutManager);
        this.recyclerViewUser.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.linearLayoutStories.setVisibility(View.VISIBLE);
        if (SharePrefs.getInstance(this.mainActivity).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
            getStoriesApi();
            this.textViewLogin.setText(getResources().getString(R.string.logout));
            this.linearLayoutStories.setVisibility(View.VISIBLE);
            this.linearLayoutPlaceHolder.setVisibility(View.GONE);
        } else {
            this.textViewLogin.setText(getResources().getString(R.string.login));
            this.linearLayoutStories.setVisibility(View.GONE);
            this.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
        }
        this.textViewLogin.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (!SharePrefs.getInstance(mainActivity).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                    startActivityForResult(new Intent(mainActivity, LoginActivity.class), 100);
                    return;
                }
                initLogoutDialog();
            }
        });

        this.recyclerViewStories.setLayoutManager(new GridLayoutManager(MainActivity.this.getApplicationContext(), 3));
        this.recyclerViewStories.setAdapter(this.storyAdapter);
        this.recyclerViewStories.setHasFixedSize(true);
    }

    public void initLogoutDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.SheetDialog);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.dialog_login);
        bottomSheetDialog.show();
        TextView textViewYes = bottomSheetDialog.findViewById(R.id.textViewYes);
        textViewYes.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SharePrefs.getInstance(mainActivity).putBoolean(SharePrefs.IS_INSTAGRAM_LOGIN, false);
                SharePrefs.getInstance(mainActivity).putString(SharePrefs.COOKIES, "");
                SharePrefs.getInstance(mainActivity).putString(SharePrefs.CSRF, "");
                SharePrefs.getInstance(mainActivity).putString(SharePrefs.SESSIONID, "");
                SharePrefs.getInstance(mainActivity).putString(SharePrefs.USERID, "");
                if (SharePrefs.getInstance(mainActivity).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                    textViewLogin.setText(getResources().getString(R.string.logout));
                    linearLayoutStories.setVisibility(View.VISIBLE);
                    linearLayoutPlaceHolder.setVisibility(View.GONE);
                } else {
                    textViewLogin.setText(getResources().getString(R.string.login));
                    linearLayoutStories.setVisibility(View.GONE);
                    linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
                }
                bottomSheetDialog.dismiss();
            }
        });

        TextView textViewCancel = bottomSheetDialog.findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                bottomSheetDialog.dismiss();
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
                            MainActivity.this.interstitialAd = null;
                           finish();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            MainActivity.this.interstitialAd = null;
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {

        super.onPause();
    }


    private void getStoriesApi() {
        try {
            if (!new Utils(this.mainActivity).isNetworkAvailable()) {
                Utils.setToast(this.mainActivity, this.mainActivity.getResources().getString(R.string.no_net_conn));
            } else if (this.commonAPI != null) {
                this.progressLoadingBar.setVisibility(View.VISIBLE);
                CommonAPI commonAPI1 = this.commonAPI;
                DisposableObserver<StoryModel> disposableObserver = this.storyObserver;
                commonAPI1.getStories(disposableObserver, "ds_user_id=" + SharePrefs.getInstance(this.mainActivity).getString(SharePrefs.USERID) + "; sessionid=" + SharePrefs.getInstance(this.mainActivity).getString(SharePrefs.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void FacebookUserListClick(int i, TrayModel trayModel) {
        getStories(String.valueOf(trayModel.getUser().getPk()));
    }

    private void getStories(String str) {
        try {
            if (!new Utils(this.mainActivity).isNetworkAvailable()) {
                Utils.setToast(this.mainActivity, this.mainActivity.getResources().getString(R.string.no_net_conn));
            } else if (this.commonAPI != null) {
                this.progressLoadingBar.setVisibility(View.VISIBLE);
                CommonAPI commonAPI1 = this.commonAPI;
                DisposableObserver<FullDetailModel> disposableObserver = this.storyDetailObserver;
                commonAPI1.getFullFeed(disposableObserver, str, "ds_user_id=" + SharePrefs.getInstance(this.mainActivity).getString(SharePrefs.USERID) + "; sessionid=" + SharePrefs.getInstance(this.mainActivity).getString(SharePrefs.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInstagramData() {
        try {
            createFile();

            URL url = new URL(editTextPasteUrl.getText().toString());
            String host = url.getHost();
            Log.e("initViews: ", host);
            if (host.equals("www.instagram.com")) {
                setDownloadStory(editTextPasteUrl.getText().toString());
            } else {
                Utils.setToast(mainActivity, getResources().getString(R.string.enter_valid_url));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pasteText() {
        try {
            this.editTextPasteUrl.setText("");
            String stringExtra = getIntent().getStringExtra("CopyIntent");
            if (stringExtra != null) {
                if (!stringExtra.equals("")) {
                    if (stringExtra.contains(Constants.INSTAGRAM_SITE)) {
                        this.editTextPasteUrl.setText(stringExtra);
                        return;
                    }
                    return;
                }
            }
            if (!this.clipboardManager.hasPrimaryClip()) {
                Log.d("ContentValues", "PasteText");
            } else if (this.clipboardManager.getPrimaryClipDescription().hasMimeType("text/plain")) {
                ClipData.Item itemAt = this.clipboardManager.getPrimaryClip().getItemAt(0);
                if (itemAt.getText().toString().contains(Constants.INSTAGRAM_SITE)) {
                    this.editTextPasteUrl.setText(itemAt.getText().toString());
                }
            } else if (this.clipboardManager.getPrimaryClip().getItemAt(0).getText().toString().contains(Constants.INSTAGRAM_SITE)) {
                this.editTextPasteUrl.setText(this.clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUrlWithoutParameters(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(),
                    uri.getAuthority(),
                    uri.getPath(),
                    null,
                    uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.setToast(mainActivity, getResources().getString(R.string.enter_valid_url));
            return "";
        }
    }

    private void setDownloadStory(String Url) {
        String UrlWithoutQP = getUrlWithoutParameters(Url);
        UrlWithoutQP = UrlWithoutQP + "?__a=1&__d=dis";
        try {
            Utils utils = new Utils(mainActivity);
            if (utils.isNetworkAvailable()) {
                if (commonAPI != null) {
                    Utils.showProgress(mainActivity);
                    commonAPI.Result(disposableObserver, UrlWithoutQP,
                            "ds_user_id=" + SharePrefs.getInstance(mainActivity).getString(SharePrefs.USERID)
                                    + "; sessionid=" + SharePrefs.getInstance(mainActivity).getString(SharePrefs.SESSIONID));
                }
            } else {
                Utils.setToast(mainActivity, getResources().getString(R.string.no_net_conn));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private DisposableObserver<JsonObject> disposableObserver = new DisposableObserver<JsonObject>() {
        public void onNext(JsonObject jsonObject) {
            Utils.hideProgress(mainActivity);
            try {
                Log.e("onNext: ", jsonObject.toString());
                ResponseModel responseModel = (ResponseModel) new Gson().fromJson(jsonObject.toString(), new TypeToken<ResponseModel>() {
                }.getType());
                EdgeSidecarModel edgeSidecarToChildren = responseModel.getGraphql().getShortcodeMedia().getEdgeSidecarToChildren();
                if (edgeSidecarToChildren != null) {
                    List<EdgeModel> edges = edgeSidecarToChildren.getEdges();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getNode().isVideo()) {
                            MainActivity.this.videoUrl = edges.get(i).getNode().getVideoUrl();
                            String str = MainActivity.this.videoUrl;
                            MainActivity instagramActivity = MainActivity.this.mainActivity;
                            Utils.startDownload(str, DirectoryUtils.FOLDER, instagramActivity, "Instagram_" + System.currentTimeMillis() + ".mp4");
                            MainActivity.this.editTextPasteUrl.setText("");
                            MainActivity.this.videoUrl = "";
                        } else {
                            MainActivity.this.photoUrl = edges.get(i).getNode().getDisplayResources().get(edges.get(i).getNode().getDisplayResources().size() - 1).getSrc();
                            String str2 = MainActivity.this.photoUrl;
                            MainActivity instagramActivity2 = MainActivity.this.mainActivity;
                            Utils.startDownload(str2, DirectoryUtils.FOLDER, instagramActivity2, "Instagram_" + System.currentTimeMillis() + ".png");
                            MainActivity.this.photoUrl = "";
                            MainActivity.this.editTextPasteUrl.setText("");
                        }
                    }
                } else if (responseModel.getGraphql().getShortcodeMedia().isVideo()) {
                    MainActivity.this.videoUrl = responseModel.getGraphql().getShortcodeMedia().getVideoUrl();
                    String str3 = MainActivity.this.videoUrl;
                    MainActivity instagramActivity3 = MainActivity.this.mainActivity;
                    Utils.startDownload(str3, DirectoryUtils.FOLDER, instagramActivity3, "Instagram_" + System.currentTimeMillis() + ".mp4");
                    MainActivity.this.videoUrl = "";
                    MainActivity.this.editTextPasteUrl.setText("");
                } else {
                    MainActivity.this.photoUrl = responseModel.getGraphql().getShortcodeMedia().getDisplayResources().get(responseModel.getGraphql().getShortcodeMedia().getDisplayResources().size() - 1).getSrc();
                    String str4 = MainActivity.this.photoUrl;
                    MainActivity instagramActivity4 = MainActivity.this.mainActivity;
                    Utils.startDownload(str4, DirectoryUtils.FOLDER, instagramActivity4, "Instagram_" + System.currentTimeMillis() + ".png");
                    MainActivity.this.photoUrl = "";
                    MainActivity.this.editTextPasteUrl.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            Utils.hideProgress(mainActivity);
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgress(mainActivity);
        }
    };

    private DisposableObserver<FullDetailModel> storyDetailObserver = new DisposableObserver<FullDetailModel>() {
        public void onNext(FullDetailModel fullDetailModel) {
            MainActivity.this.recyclerViewUser.setVisibility(View.VISIBLE);
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
            try {
                MainActivity.this.storyAdapter = new StoryAdapter(MainActivity.this.mainActivity, fullDetailModel.getReelFeed().getItems());
                MainActivity.this.recyclerViewStories.setAdapter(MainActivity.this.storyAdapter);
                MainActivity.this.storyAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
        }
    };

    private DisposableObserver<StoryModel> storyObserver = new DisposableObserver<StoryModel>() {

        public void onNext(StoryModel storyModel) {
            MainActivity.this.recyclerViewUser.setVisibility(View.VISIBLE);
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < storyModel.getTray().size(); i++) {
                    try {
                        if (storyModel.getTray().get(i).getUser().getFullname() != null) {
                            arrayList.add(storyModel.getTray().get(i));
                        }
                    } catch (Exception unused) {
                    }
                }
                MainActivity.this.profileAdapter = new ProfileAdapter(MainActivity.this.mainActivity, arrayList, MainActivity.this.mainActivity);
                MainActivity.this.recyclerViewUser.setAdapter(MainActivity.this.profileAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            MainActivity.this.progressLoadingBar.setVisibility(View.GONE);
        }
    };

    @Override
    public void FacebookUserListClick(int i, NodeModel nodeModel) {
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i != 100 || i2 != -1) {
                return;
            }
            if (SharePrefs.getInstance(this.mainActivity).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                this.textViewLogin.setText(getResources().getString(R.string.logout));
                this.linearLayoutStories.setVisibility(View.VISIBLE);
                this.linearLayoutPlaceHolder.setVisibility(View.GONE);
                getStoriesApi();
                return;
            }
            this.textViewLogin.setText(getResources().getString(R.string.login));
            this.linearLayoutStories.setVisibility(View.GONE);
            this.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BannerAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                adView.loadAd(adRequest);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });

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
                        MainActivity.this.interstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }


    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.

                    super.onVideoEnd();
                }
            });
        }
    }


    private void refreshAd() {


        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_Native_id));

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;

                        if (Build.VERSION.SDK_INT >= 21) {
                            isDestroyed = isDestroyed();
                        }
                        if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (MainActivity.this.nativeAd != null) {
                            MainActivity.this.nativeAd.destroy();
                        }
                        MainActivity.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

//        VideoOptions videoOptions =
//            new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();
//
//        NativeAdOptions adOptions =
//            new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                            refresh.setEnabled(true);
//                                        String error =
//                                                String.format(
//                                                        "domain: %s, code: %d, message: %s",
//                                                        loadAdError.getDomain(),
//                                                        loadAdError.getCode(),
//                                                        loadAdError.getMessage());

                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());

//        videoStatus.setText("");
    }

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }
}