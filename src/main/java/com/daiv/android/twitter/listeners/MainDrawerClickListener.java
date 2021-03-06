/*
 * Copyright 2014 "" daiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.daiv.android.twitter.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.daiv.android.twitter.R;
import com.daiv.android.twitter.adapters.MainDrawerArrayAdapter;
import com.daiv.android.twitter.adapters.TimelinePagerAdapter;
import com.daiv.android.twitter.settings.AppSettings;
import com.daiv.android.twitter.ui.MainActivity;
import com.daiv.android.twitter.manipulations.widgets.NotificationDrawerLayout;

import java.util.HashSet;
import java.util.Set;

public class MainDrawerClickListener implements AdapterView.OnItemClickListener {

    private Context context;
    private NotificationDrawerLayout drawer;
    private ViewPager viewPager;
    private boolean noWait;
    private int swipablePages = 0;

    private String[] shownElements;
    private Set<String> set;

    private SharedPreferences sharedPreferences;

    public MainDrawerClickListener(Context context, NotificationDrawerLayout drawer, ViewPager viewPager) {
        this.context = context;
        this.drawer = drawer;
        this.viewPager = viewPager;
        this.noWait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                context.getResources().getBoolean(R.bool.isTablet);
        sharedPreferences = context.getSharedPreferences("com.daiv.android.twitter_world_preferences",
                Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

        int currentAccount = sharedPreferences.getInt("current_account", 1);

        for (int i = 0; i < TimelinePagerAdapter.MAX_EXTRA_PAGES; i++) {
            String pageIdentifier = "account_" + currentAccount + "_page_" + (i + 1);
            int type = sharedPreferences.getInt(pageIdentifier, AppSettings.PAGE_TYPE_NONE);

            if (type != AppSettings.PAGE_TYPE_NONE) {
                swipablePages++;
            }
        }

        set = sharedPreferences.getStringSet("drawer_elements_shown_" + currentAccount, new HashSet<String>());
        shownElements = new String[set.size()];
        int i = 0;
        for (Object o : set.toArray()) {
            shownElements[i] = (String) o;
            i++;
        }
    }

    int realPages = 0;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        context.sendBroadcast(new Intent("com.daiv.android.twitter.MARK_POSITION"));

        // we will increment until we find one that is in the set of shown elements
        for (int index = 0; index <= i; index++) {
            if (!set.contains(index + "")) {
                i++;
            }
        }

        if (i < swipablePages) {
            if (MainDrawerArrayAdapter.current < swipablePages) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            drawer.closeDrawer(Gravity.START);
                        } catch (Exception e) {
                            // landscape mode
                        }
                    }
                }, noWait ? 0 : 300);

                viewPager.setCurrentItem(i, true);
            } else {
                final int pos = i;
                try {
                    drawer.closeDrawer(Gravity.START);
                } catch (Exception e) {
                    // landscape mode
                }

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("page_to_open", pos);
                intent.putExtra("from_drawer", true);

                sharedPreferences.edit().putBoolean("should_refresh", false).commit();

                final Intent fIntent = intent;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            context.startActivity(fIntent);
                            ((Activity) context).overridePendingTransition(0, 0);
                            ((Activity) context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, noWait ? 0 : 400);

            }
        } else {
            final int pos = i;
            try {
                drawer.closeDrawer(Gravity.START);
            } catch (Exception e) {
                // landscape mode
            }

            Intent intent = null;

            final Intent fIntent = intent;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        context.startActivity(fIntent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity)context).finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, noWait ? 0 : 400);

        }

    }
}
