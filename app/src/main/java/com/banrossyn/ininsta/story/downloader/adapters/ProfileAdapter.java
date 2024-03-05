package com.banrossyn.ininsta.story.downloader.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.banrossyn.ininsta.story.downloader.R;
import com.banrossyn.ininsta.story.downloader.api.model.TrayModel;
import com.banrossyn.ininsta.story.downloader.listener.UserListInterface;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TrayModel> trayModelArrayList;
    private UserListInterface userListInterface;

    public ProfileAdapter(Context context2, ArrayList<TrayModel> arrayList, UserListInterface userListInterface2) {
        this.context = context2;
        this.trayModelArrayList = arrayList;
        this.userListInterface = userListInterface2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        viewHolder.textViewName.setText(this.trayModelArrayList.get(i).getUser().getFullname());
        Glide.with(this.context).load(this.trayModelArrayList.get(i).getUser().getProfilepicurl()).thumbnail(0.2f).into(viewHolder.imageViewCover);
        viewHolder.relativeLayoutContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userListInterface.FacebookUserListClick(i, trayModelArrayList.get(i));
            }
        });

    }
    @Override
    public int getItemCount() {
        ArrayList<TrayModel> arrayList = this.trayModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayoutContent;
        public ImageView imageViewCover;
        public TextView textViewName;
        public TextView textViewUser;

        public ViewHolder(View view) {
            super(view);
            this.relativeLayoutContent = view.findViewById(R.id.relativeLayoutContent);
            this.imageViewCover = view.findViewById(R.id.imageViewStory);
            this.textViewName = view.findViewById(R.id.textViewName);
            this.textViewUser = view.findViewById(R.id.textViewUser);
        }
    }
}