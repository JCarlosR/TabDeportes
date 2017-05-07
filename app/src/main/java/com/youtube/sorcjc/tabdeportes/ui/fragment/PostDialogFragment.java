package com.youtube.sorcjc.tabdeportes.ui.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.youtube.sorcjc.tabdeportes.R;

public class PostDialogFragment extends DialogFragment {

    private String post_categories, post_title, post_date, post_content, post_image;

    private TextView tvTitle, tvDate, tvCategories;
    private ImageView ivThumbnail;

    private WebView webView;

    public static PostDialogFragment newInstance(String post_categories, String post_title, String post_date, String post_content, String post_image) {
        PostDialogFragment f = new PostDialogFragment();

        Bundle args = new Bundle();
        args.putString("post_categories", post_categories);
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

        post_categories = getArguments().getString("post_categories");
        post_title = getArguments().getString("post_title");
        post_date = getArguments().getString("post_date");
        post_content = getArguments().getString("post_content");
        post_image = getArguments().getString("post_image");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

        tvCategories = (TextView) view.findViewById(R.id.tvCategories);
        tvCategories.setText(post_categories);

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(post_title);

        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setText(post_date);
        tvDate.setVisibility(View.GONE); // !!

        webView = (WebView) view.findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setPadding(0, 0, 0, 0);
        // webView.setInitialScale(getScale());
        webView.setInitialScale(1);

        // Prevent scroll
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);

        // this causes the scrollbar to not take up space, and allows the web to fill the viewport
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        ViewTreeObserver viewTreeObserver  = webView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = webView.getMeasuredHeight();
                // Log.d("onGlobalLayout", "measuredHeight => " + height);
                if (height > 100) {
                    // set new height
                    ViewGroup.LayoutParams params = webView.getLayoutParams();
                    params.height = height;
                    webView.requestLayout();

                    // min sdk 16
                    webView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        /*viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int height = webView.getMeasuredHeight();
                Log.d("PreDrawPost", "measuredHeight => " + height);
                if (height > 0) {
                    webView.getViewTreeObserver().removeOnPreDrawListener(this);
                    // Set new height
                    ViewGroup.LayoutParams params = webView.getLayoutParams();
                    params.height = height;
                    webView.requestLayout();
                }

                return false;
            }
        });*/

        post_content = "<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /><body>" + post_content + "</body></html>";
        // Log.d("PostDialog", "After: " + post_content);
        webView.loadData(post_content, "text/html; charset=utf-8", "utf-8");

        ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
        Picasso.with(getContext()).load(post_image).fit().centerCrop().into(ivThumbnail);

        // Load Ad banner
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    private int getScale() {
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Toast.makeText(getContext(), "width => " + width, Toast.LENGTH_SHORT).show();
        Double val = new Double(width) / 560;
        val = val * 100d;
        return val.intValue();
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
            // close button click
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}