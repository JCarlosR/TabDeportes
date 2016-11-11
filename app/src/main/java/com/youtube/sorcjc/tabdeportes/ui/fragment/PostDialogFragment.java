package com.youtube.sorcjc.tabdeportes.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youtube.sorcjc.tabdeportes.R;

public class PostDialogFragment extends DialogFragment {

    private String post_title, post_date, post_content, post_image;

    private TextView tvTitle, tvDate, tvContent;
    private ImageView ivThumbnail;

    public static PostDialogFragment newInstance(String post_title, String post_date, String post_content, String post_image) {
        PostDialogFragment f = new PostDialogFragment();

        Bundle args = new Bundle();
        args.putString("post_title", post_title);
        args.putString("post_date", post_date);
        args.putString("post_content", post_content);
        args.putString("post_image", post_image);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        post_title = getArguments().getString("post_title");
        post_date = getArguments().getString("post_date");
        post_content = getArguments().getString("post_content");
        post_image = getArguments().getString("post_image");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_post, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(post_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        setHasOptionsMenu(true);

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(post_title);

        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setText(post_date);

        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(Html.fromHtml(post_content));

        ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);

        Picasso.with(getContext()).load(post_image).fit().centerCrop().into(ivThumbnail);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}