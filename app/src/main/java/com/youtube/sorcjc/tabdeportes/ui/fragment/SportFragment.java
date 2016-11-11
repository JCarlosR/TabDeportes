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
import com.youtube.sorcjc.tabdeportes.io.response.CategoryPostsResponse;
import com.youtube.sorcjc.tabdeportes.io.response.RecentPostsResponse;
import com.youtube.sorcjc.tabdeportes.model.Post;
import com.youtube.sorcjc.tabdeportes.ui.adapter.PostAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportFragment extends Fragment implements Callback<CategoryPostsResponse> {

    private PostAdapter postAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private static final String ARG_TITLE = "sectionTitle";
    private static final String ARG_CATEGORY_ID = "categoryId";
    private String sectionTitle;
    private int categoryId;


    public SportFragment() {
        // Required empty public constructor
    }

    public static SportFragment newInstance(String sectionTitle, int categoryId) {
        SportFragment fragment = new SportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, sectionTitle);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionTitle = getArguments().getString(ARG_TITLE);
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }

        loadRecentPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView tvSectionTitle = (TextView) v.findViewById(R.id.tvSectionTitle);
        tvSectionTitle.setText(sectionTitle);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        setupRecyclerView(v);
        return v;
    }

    private void setupRecyclerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);
    }

    private void loadRecentPosts() {
        Call<CategoryPostsResponse> call = TabApiAdapter.getApiService().getCategoryPosts(categoryId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CategoryPostsResponse> call, Response<CategoryPostsResponse> response) {
        if (response.isSuccessful()) {
            CategoryPostsResponse categoryPostsResponse = response.body();
            // Log.d("SportFragment", "Mostrando " + categoryPostsResponse.getCount() + " posts !");

            ArrayList<Post> posts = categoryPostsResponse.getPosts();
            postAdapter.setDataSet(posts);

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Call<CategoryPostsResponse> call, Throwable t) {
        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }


}