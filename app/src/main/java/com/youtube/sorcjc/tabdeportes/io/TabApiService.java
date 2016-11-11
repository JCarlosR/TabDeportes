package com.youtube.sorcjc.tabdeportes.io;

import com.youtube.sorcjc.tabdeportes.io.response.CategoryPostsResponse;
import com.youtube.sorcjc.tabdeportes.io.response.RecentPostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TabApiService {

    @GET("get_recent_posts")
    Call<RecentPostsResponse> getRecentPosts();

    @GET("get_category_posts")
    Call<CategoryPostsResponse> getCategoryPosts(@Query("id") int categoryId);

}
