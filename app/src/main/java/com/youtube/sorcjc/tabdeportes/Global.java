package com.youtube.sorcjc.tabdeportes;

import android.app.Application;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.youtube.sorcjc.tabdeportes.ui.activity.PanelActivity;
import com.youtube.sorcjc.tabdeportes.ui.fragment.PostDialogFragment;

import org.json.JSONObject;

public class Global extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize OneSignal
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();
    }

    private void openThePost(String post_categories, String post_title, String post_date, String post_content, String post_image) {
        Intent intent = new Intent(this, PanelActivity.class);
        intent.putExtra("from_notification", true);
        intent.putExtra("post_categories", post_categories);
        intent.putExtra("post_title", post_title);
        intent.putExtra("post_date", post_date);
        intent.putExtra("post_content", post_content);
        intent.putExtra("post_image", post_image);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            int post_id;
            String post_categories, post_title, post_content, post_image;

            // If the notification comes with data
            if (data != null) {
                // we look for the post_id
                post_id = data.optInt("post_id");
                post_categories = data.optString("post_categories");
                post_title = data.optString("post_title");
                post_content = data.optString("post_content");
                post_image = data.optString("post_image");
                // and if it has a valid value
                if (post_id != 0) {
                    // we show the post to the user.
                    openThePost(post_categories, post_title, "", post_content, post_image);
                }
            }
        }
    }
}