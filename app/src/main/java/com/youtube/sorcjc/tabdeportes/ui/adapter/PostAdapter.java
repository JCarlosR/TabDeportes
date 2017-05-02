package com.youtube.sorcjc.tabdeportes.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.youtube.sorcjc.tabdeportes.Global;
import com.youtube.sorcjc.tabdeportes.R;
import com.youtube.sorcjc.tabdeportes.model.Post;
import com.youtube.sorcjc.tabdeportes.ui.activity.PanelActivity;
import com.youtube.sorcjc.tabdeportes.ui.fragment.PostDialogFragment;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> dataSet;

    // Provide a reference to the views for each data item
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // context
        Context context;
        // text views
        TextView tvDate;
        TextView tvTitle;
        TextView tvCategories;
        // images
        ImageView ivThumbnail;
        ProgressBar progressBar;
        // main data
        String post_categories, post_title, post_date, post_content, post_image;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();

            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvCategories = (TextView) v.findViewById(R.id.tvCategories);

            ivThumbnail = (ImageView) v.findViewById(R.id.ivThumbnail);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }

        void loadImage(final String imageUrl) {
            Picasso.with(context)
                    .load(imageUrl)
                    .fit().centerCrop()
                    .into(ivThumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            // Infinite load
                        }
                    });
        }

        void setOnClickListeners() {
            ivThumbnail.setOnClickListener(this);
            tvTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvTitle:
                    showFullPost();
                    break;
                case R.id.ivThumbnail:
                    showFullPost();
                    break;
            }
        }

        private void showFullPost() {
            PanelActivity panelActivity = ((PanelActivity) context);

            // Add count
            Global global = (Global) panelActivity.getApplicationContext();
            global.increasePostsViewed();
            if (global.getPostsViewed() >= 3) {
                panelActivity.showInterstitial();
            }

            FragmentManager fragmentManager = panelActivity.getSupportFragmentManager();
            PostDialogFragment newFragment = PostDialogFragment.newInstance(
                    post_categories, post_title, post_date, post_content, post_image
            );

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }

    }

    public PostAdapter() {
        dataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<Post> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from data-set at this position
        Post currentPost = dataSet.get(position);

        // and replace the contents
        holder.tvDate.setText(currentPost.getDate());
        holder.tvTitle.setText(currentPost.getTitle());
        holder.tvCategories.setText(currentPost.getCategoryList());
        final String imageUrl = currentPost.getAttachments().get(0).getUrl();
        holder.loadImage(imageUrl);

        // set events
        holder.setOnClickListeners();

        // take the title, date and content
        holder.post_categories = currentPost.getCategoryList();
        holder.post_title = currentPost.getTitle();
        holder.post_date = currentPost.getDate();
        holder.post_content = currentPost.getContent(); // Html.fromHtml(currentPost.getContent()).toString();
        holder.post_image = imageUrl;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
