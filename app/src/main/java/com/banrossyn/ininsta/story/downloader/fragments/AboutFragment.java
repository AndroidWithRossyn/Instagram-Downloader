package com.banrossyn.ininsta.story.downloader.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.banrossyn.ininsta.story.downloader.MainActivity;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.databinding.FragmentAboutBinding;
import com.banrossyn.ininsta.story.downloader.dialogs.RateDialog;

public class AboutFragment extends Fragment {


    FragmentAboutBinding binding;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAboutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.linearLayoutShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "In Insta \n\n Hello Let me Recommend you this App Source Code @Github\n Download easy Instagram Story's, Post's, Reels IGTV and More. ";
            shareMessage = shareMessage + "https://github.com/AndroidWithRossyn/Instagram-Downloader";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        });

        binding.linearLayoutRate.setOnClickListener(v -> new RateDialog(requireActivity(), false).show());

        binding.linearLayoutFeedBack.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + requireActivity().getString(R.string.email_feedback)));
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_SUBJECT, requireActivity().getString(R.string.app_name));
            startActivity(intent);


        });

        binding.linearLayoutPrivacy.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url)))));

        binding.linearLayoutApps.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.developer_account_link)))));

        binding.linearLayoutCode.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.source_code)))));

        binding.linearLayoutPaid.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.paid_apk)))));

    }
}