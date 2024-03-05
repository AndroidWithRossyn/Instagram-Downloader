package com.banrossyn.ininsta.story.downloader.fragments;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils.createFile;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.banrossyn.ininsta.story.downloader.MainActivity;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.api.data.EdgeModel;
import com.banrossyn.ininsta.story.downloader.api.data.EdgeSidecarModel;
import com.banrossyn.ininsta.story.downloader.api.data.ResponseModel;
import com.banrossyn.ininsta.story.downloader.constants.Constants;
import com.banrossyn.ininsta.story.downloader.databinding.FragmentHomeBinding;
import com.banrossyn.ininsta.story.downloader.preference.SharePrefs;
import com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils;
import com.banrossyn.ininsta.story.downloader.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import io.reactivex.observers.DisposableObserver;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private ClipboardManager clipboardManager;
    private String photoUrl;
    private String videoUrl;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clipboardManager = (ClipboardManager) requireActivity().getSystemService(CLIPBOARD_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewPaste.setOnClickListener(v -> {
            pasteText();
        });

        binding.textViewDownload.setOnClickListener(v -> {
            String obj = binding.editTextPasteUrl.getText().toString();
            if (obj.equals("")) {
                Utils.setToast(requireActivity(), getResources().getString(R.string.enter_url));
            } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            } else {
                getInstagramData();
            }
        });

        binding.imageViewOpenFacebook.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://instagram.com/");
            Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
            instagram.setPackage("com.instagram.android");
            try {
                startActivity(instagram);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/")));
            }

        });
        binding.crown.setOnClickListener(v -> {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/androidwithrossyn")));

        });
    }

    private void getInstagramData() {
        try {
            createFile();

            URL url = new URL(binding.editTextPasteUrl.getText().toString());
            String host = url.getHost();
            Log.e("initViews: ", host);
            if (host.equals("www.instagram.com")) {
                downloadUrl(binding.editTextPasteUrl.getText().toString());
            } else {
                Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pasteText() {
        try {
            binding.editTextPasteUrl.setText("");
            String stringExtra = requireActivity().getIntent().getStringExtra("CopyIntent");
            if (stringExtra != null) {
                if (!stringExtra.equals("")) {
                    if (stringExtra.contains(Constants.INSTAGRAM_SITE)) {
                        binding.editTextPasteUrl.setText(stringExtra);
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
                    binding.editTextPasteUrl.setText(itemAt.getText().toString());
                }
            } else if (this.clipboardManager.getPrimaryClip().getItemAt(0).getText().toString().contains(Constants.INSTAGRAM_SITE)) {
                binding.editTextPasteUrl.setText(this.clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUrlWithoutParameters(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            return "";
        }
    }

    private void downloadUrl(String Url) {
        String UrlWithoutQP = getUrlWithoutParameters(Url);
        UrlWithoutQP = UrlWithoutQP + "?__a=1&__d=dis";
        try {
            Utils utils = new Utils(requireActivity());
            if (utils.isNetworkAvailable()) {
                if (MainActivity.commonAPI != null) {
                    Utils.showProgress(requireActivity());
                    MainActivity.commonAPI.Result(disposableObserver, UrlWithoutQP, OrgCookies());
                }
            } else {
                Utils.setToast(requireActivity(), getResources().getString(R.string.no_net_conn));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private DisposableObserver<JsonObject> disposableObserver = new DisposableObserver<JsonObject>() {
        public void onNext(JsonObject jsonObject) {
            Utils.hideProgress(requireActivity());
            try {
                Log.e("onNext: ", jsonObject.toString());
                ResponseModel responseModel = (ResponseModel) new Gson().fromJson(jsonObject.toString(), new TypeToken<ResponseModel>() {
                }.getType());
                EdgeSidecarModel edgeSidecarToChildren = responseModel.getGraphql().getShortcodeMedia().getEdgeSidecarToChildren();
                if (edgeSidecarToChildren != null) {
                    List<EdgeModel> edges = edgeSidecarToChildren.getEdges();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getNode().isVideo()) {
                            videoUrl = edges.get(i).getNode().getVideoUrl();
                            String str = videoUrl;

                            Utils.startDownload(str, DirectoryUtils.FOLDER, requireActivity(), "Instagram_" + System.currentTimeMillis() + ".mp4");
                            binding.editTextPasteUrl.setText("");
                            videoUrl = "";
                        } else {
                            photoUrl = edges.get(i).getNode().getDisplayResources().get(edges.get(i).getNode().getDisplayResources().size() - 1).getSrc();
                            String str2 = photoUrl;

                            Utils.startDownload(str2, DirectoryUtils.FOLDER, requireActivity(), "Instagram_" + System.currentTimeMillis() + ".png");
                            photoUrl = "";
                            binding.editTextPasteUrl.setText("");
                        }
                    }
                } else if (responseModel.getGraphql().getShortcodeMedia().isVideo()) {
                    videoUrl = responseModel.getGraphql().getShortcodeMedia().getVideoUrl();
                    String str3 = videoUrl;

                    Utils.startDownload(str3, DirectoryUtils.FOLDER, requireActivity(), "Instagram_" + System.currentTimeMillis() + ".mp4");
                    videoUrl = "";
                    binding.editTextPasteUrl.setText("");
                } else {
                    photoUrl = responseModel.getGraphql().getShortcodeMedia().getDisplayResources().get(responseModel.getGraphql().getShortcodeMedia().getDisplayResources().size() - 1).getSrc();
                    String str4 = photoUrl;

                    Utils.startDownload(str4, DirectoryUtils.FOLDER, requireActivity(), "Instagram_" + System.currentTimeMillis() + ".png");
                    photoUrl = "";
                    binding.editTextPasteUrl.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            Utils.hideProgress(requireActivity());
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgress(requireActivity());
        }
    };


    public final String[] tempCookies = new String[]{
            "mid=ZN2CxAABAAHzMIv07waly4FXKqK5; ig_did=ACB4224A-B140-41E7-A997-D70B39DEF4D7; ig_nrcb=1; csrftoken=2DrgWxAo8cElhRvzAfStl6vtVFLrFLNi",
            "mid=ZN2LfwABAAHuZKYpRlMpeosaCh8F; ig_did=D5A933FC-34A1-4279-8A2A-DE4A44A0C5E9; ig_nrcb=1; csrftoken=4QSWZ2AIRXo0C033A1ba82Pw47SXZmWX",
            "mid=ZN2T_QABAAGw6zGlN-S7TkDdWs6w; ig_did=7224E0E4-B549-4F0F-91E4-D91ADFBE0FA7; ig_nrcb=1; csrftoken=zTfR4I2PsePGaRcm4CelEkT8NDvbmYCE",
            "mid= ZaOLiwALAAGPBkMCZ6Rq06Wbzdsx; datr=evifZdUhQn6sGdNuvuOtNOvU; ig_did=F3C79A5C-4A71-46FF-9E13-26FA2815FEBA; dpr=1.25; csrftoken=x35xVMDI5uATq7iW4dTn-o"
            // add your own cookies
    };

    public String getTempCookiesOld() {
        int random = new Random().nextInt(4);
        return tempCookies[random];
    }

    public String OrgCookies() {
        if (!Objects.equals(SharePrefs.getInstance(requireActivity()).getString(SharePrefs.COOKIES), "")) {
            return SharePrefs.getInstance(requireActivity()).getString(SharePrefs.USERID);
        } else {
            return getTempCookiesOld();
        }
    }
}