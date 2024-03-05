package com.banrossyn.ininsta.story.downloader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banrossyn.ininsta.story.downloader.LoginActivity;
import com.banrossyn.ininsta.story.downloader.MainActivity;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.adapters.ProfileAdapter;
import com.banrossyn.ininsta.story.downloader.adapters.StoryAdapter;
import com.banrossyn.ininsta.story.downloader.api.CommonAPI;
import com.banrossyn.ininsta.story.downloader.api.model.FullDetailModel;
import com.banrossyn.ininsta.story.downloader.api.model.StoryModel;
import com.banrossyn.ininsta.story.downloader.api.model.TrayModel;
import com.banrossyn.ininsta.story.downloader.databinding.FragmentStoryBinding;
import com.banrossyn.ininsta.story.downloader.listener.UserListInterface;
import com.banrossyn.ininsta.story.downloader.preference.SharePrefs;
import com.banrossyn.ininsta.story.downloader.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;


public class StoryFragment extends Fragment implements UserListInterface {


    FragmentStoryBinding binding;
    ProfileAdapter profileAdapter;
    StoryAdapter storyAdapter;

    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity().getApplicationContext(), 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerViewUser.setLayoutManager(gridLayoutManager);
        binding.recyclerViewUser.setNestedScrollingEnabled(false);

        if (SharePrefs.getInstance(requireActivity()).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
            getStoriesApi();
            binding.textViewLogin.setText(getResources().getString(R.string.logout));
            binding.linearLayoutStories.setVisibility(View.VISIBLE);
            binding.linearLayoutPlaceHold.setVisibility(View.GONE);
        } else {
            binding.textViewLogin.setText(getResources().getString(R.string.login));
            binding.linearLayoutStories.setVisibility(View.GONE);
            binding.linearLayoutPlaceHold.setVisibility(View.VISIBLE);
        }
        binding.textViewLogin.setOnClickListener(view1 -> {
            if (!SharePrefs.getInstance(requireActivity()).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                startActivityForResult(new Intent(requireActivity(), LoginActivity.class), 100);
                return;
            }
            initLogoutDialog();
        });


    }

    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i != 100 || i2 != -1) {
                return;
            }
            if (SharePrefs.getInstance(requireActivity()).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                binding.textViewLogin.setText(getResources().getString(R.string.logout));
                binding.linearLayoutStories.setVisibility(View.VISIBLE);
                binding.linearLayoutPlaceHold.setVisibility(View.GONE);
                getStoriesApi();
                return;
            }
            binding.textViewLogin.setText(getResources().getString(R.string.login));
            binding.linearLayoutStories.setVisibility(View.GONE);
            binding.linearLayoutPlaceHold.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStoriesApi() {
        try {
            if (!new Utils(requireActivity()).isNetworkAvailable()) {
                Utils.setToast(requireActivity(), requireActivity().getResources().getString(R.string.no_net_conn));
            } else if (MainActivity.commonAPI != null) {
                binding.progressLoadingBar.setVisibility(View.VISIBLE);
                CommonAPI commonAPI1 = MainActivity.commonAPI;
                DisposableObserver<StoryModel> disposableObserver = storyObserver;
                Log.d("storyFragment", "getStoriesApi");
                commonAPI1.getStories(disposableObserver, SharePrefs.getInstance(requireActivity()).getString(SharePrefs.COOKIES));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DisposableObserver<StoryModel> storyObserver = new DisposableObserver<StoryModel>() {

        public void onNext(StoryModel storyModel) {
            binding.recyclerViewUser.setVisibility(View.VISIBLE);
            binding.progressLoadingBar.setVisibility(View.GONE);
            try {
                Log.d("storyFragment", "storyObserver");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < storyModel.getTray().size(); i++) {
                    try {
                        if (storyModel.getTray().get(i).getUser().getFullname() != null) {
                            arrayList.add(storyModel.getTray().get(i));
                        }
                    } catch (Exception unused) {
                    }
                }
                profileAdapter = new ProfileAdapter(requireActivity(), arrayList, StoryFragment.this);
                binding.recyclerViewUser.setAdapter(profileAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            binding.progressLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            binding.progressLoadingBar.setVisibility(View.GONE);
        }
    };


    @Override
    public void FacebookUserListClick(int i, TrayModel trayModel) {
        getStories(String.valueOf(trayModel.getUser().getPk()));
    }

    private void getStories(String userid) {
        try {
            if (!new Utils(requireActivity()).isNetworkAvailable()) {
                Utils.setToast(requireActivity(), requireActivity().getResources().getString(R.string.no_net_conn));
            } else if (MainActivity.commonAPI != null) {
                Log.d("storyFragment", "getStoriesList");
                binding.progressLoadingBar.setVisibility(View.VISIBLE);
                CommonAPI commonAPI1 = MainActivity.commonAPI;
                DisposableObserver<FullDetailModel> disposableObserver = storyDetailObserver;
                commonAPI1.getFullFeed(disposableObserver, userid, SharePrefs.getInstance(requireActivity()).getString(SharePrefs.COOKIES));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DisposableObserver<FullDetailModel> storyDetailObserver = new DisposableObserver<FullDetailModel>() {
        public void onNext(FullDetailModel fullDetailModel) {
            Log.d("storyFragment", "fullDetailModel: " + new Gson().toJson(fullDetailModel));
            binding.recyclerViewStories.setVisibility(View.VISIBLE);
            binding.progressLoadingBar.setVisibility(View.GONE);
            try {
                Log.d("storyFragment", "onNext: STORIES  " + fullDetailModel.getReels_media().get(0).getItems().size());
                binding.recyclerViewStories.setLayoutManager(new GridLayoutManager(requireActivity().getApplicationContext(), 3));
                binding.recyclerViewStories.setHasFixedSize(true);
                storyAdapter = new StoryAdapter(requireActivity(), fullDetailModel.getReels_media().get(0).getItems());
                binding.recyclerViewStories.setAdapter(storyAdapter);
                storyAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
            Log.d("storyFragment", "onError: Throwable  " + th.getMessage());
            binding.progressLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
            binding.progressLoadingBar.setVisibility(View.GONE);
        }
    };

    public void initLogoutDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.SheetDialog);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.dialog_login);
        bottomSheetDialog.show();
        TextView textViewYes = bottomSheetDialog.findViewById(R.id.textViewYes);
        textViewYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharePrefs.getInstance(requireActivity()).putBoolean(SharePrefs.IS_INSTAGRAM_LOGIN, false);
                SharePrefs.getInstance(requireActivity()).putString(SharePrefs.COOKIES, "");
                SharePrefs.getInstance(requireActivity()).putString(SharePrefs.CSRF, "");
                SharePrefs.getInstance(requireActivity()).putString(SharePrefs.SESSIONID, "");
                SharePrefs.getInstance(requireActivity()).putString(SharePrefs.USERID, "");
                if (SharePrefs.getInstance(requireActivity()).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN).booleanValue()) {
                    binding.textViewLogin.setText(getResources().getString(R.string.logout));
                    binding.linearLayoutStories.setVisibility(View.VISIBLE);
                    binding.linearLayoutPlaceHold.setVisibility(View.GONE);
                } else {
                    binding.textViewLogin.setText(getResources().getString(R.string.login));
                    binding.linearLayoutStories.setVisibility(View.GONE);
                    binding.linearLayoutPlaceHold.setVisibility(View.VISIBLE);
                }
                bottomSheetDialog.dismiss();
            }
        });

        TextView textViewCancel = bottomSheetDialog.findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }
}