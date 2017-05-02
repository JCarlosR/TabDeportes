package com.youtube.sorcjc.tabdeportes.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youtube.sorcjc.tabdeportes.R;
import com.youtube.sorcjc.tabdeportes.io.TabApiAdapter;
import com.youtube.sorcjc.tabdeportes.io.response.RecentPostsResponse;
import com.youtube.sorcjc.tabdeportes.model.Post;
import com.youtube.sorcjc.tabdeportes.ui.adapter.PostAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements  Callback<RecentPostsResponse> {

    private PostAdapter postAdapter;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadRecentPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView tvSectionTitle = (TextView) v.findViewById(R.id.tvSectionTitle);
        tvSectionTitle.setText("Portada");

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        setupRecyclerView(v);
        return v;
    }

    private void setupRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);
    }

    private void loadRecentPosts() {
        Call<RecentPostsResponse> call = TabApiAdapter.getApiService().getRecentPosts();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RecentPostsResponse> call, Response<RecentPostsResponse> response) {
        if (response.isSuccessful()) {
            RecentPostsResponse recentPostsResponse = response.body();
            // Log.d("HomeFragment", "Mostrando " + recentPostsResponse.getCount() + " posts !");

            ArrayList<Post> posts = recentPostsResponse.getPosts();
            postAdapter.setDataSet(posts);

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            /*String content = posts.get(0).getContent();
            content = Html.fromHtml(content).toString();*/
        }
    }

    @Override
    public void onFailure(Call<RecentPostsResponse> call, Throwable t) {
        Log.d("HomeFragment", t.getLocalizedMessage());
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


}
