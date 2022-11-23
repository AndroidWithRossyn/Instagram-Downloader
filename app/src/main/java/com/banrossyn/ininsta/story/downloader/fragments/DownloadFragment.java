package com.banrossyn.ininsta.story.downloader.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.activities.MainActivity;
import com.banrossyn.ininsta.story.downloader.activities.PreviewActivity;
import com.banrossyn.ininsta.story.downloader.adapters.FileAdapter;
import com.banrossyn.ininsta.story.downloader.listener.FileClickInterface;
import com.banrossyn.ininsta.story.downloader.utils.DirectoryUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class DownloadFragment extends Fragment implements FileClickInterface {
    private FileAdapter fileAdapter;
    private ArrayList<File> fileArrayList;
    private MainActivity activity;
    private LinearLayout linearLayoutPlaceHolder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewFile;

    public static DownloadFragment newInstance(String param1) {
        DownloadFragment downloadedFragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putString("m", param1);
        downloadedFragment.setArguments(args);
        return downloadedFragment;
    }

    @Override
    public void onAttach(@NotNull Context _context) {
        super.onAttach(_context);
        activity = (MainActivity) _context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("m");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        activity = (MainActivity) getActivity();
        getAllFiles();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = layoutInflater.inflate(R.layout.fragment_download, container, false);
        this.linearLayoutPlaceHolder = inflate.findViewById(R.id.linearLayoutPlaceHolder);
        this.swipeRefreshLayout = inflate.findViewById(R.id.swipeRefreshLayout);
        this.recyclerViewFile = inflate.findViewById(R.id.recyclerViewFile);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getAllFiles();
            swipeRefreshLayout.setRefreshing(false);
        });

        return inflate;
    }

    private void getAllFiles() {
        this.fileArrayList = new ArrayList<>();
        File[] listFiles = DirectoryUtils.DIRECTORY_FOLDER.listFiles();
        if (listFiles != null) {
            fileAdapter = new FileAdapter(activity, fileArrayList, DownloadFragment.this);
            recyclerViewFile.setAdapter(fileAdapter);
            this.fileArrayList.addAll(Arrays.asList(listFiles));
            if (listFiles.length > 0) {
                this.linearLayoutPlaceHolder.setVisibility(View.GONE);
                this.swipeRefreshLayout.setVisibility(View.VISIBLE);
                return;
            }
            this.swipeRefreshLayout.setVisibility(View.GONE);
            this.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
            return;
        }
        this.swipeRefreshLayout.setVisibility(View.GONE);
        this.linearLayoutPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void getPosition(int position, File file) {
        Intent inNext = new Intent(activity, PreviewActivity.class);
        inNext.putExtra("ImageDataFile", fileArrayList);
        inNext.putExtra("Position", position);
        activity.startActivity(inNext);
    }
}
