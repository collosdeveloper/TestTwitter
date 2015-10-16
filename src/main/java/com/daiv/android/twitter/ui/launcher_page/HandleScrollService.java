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

package com.daiv.android.twitter.ui.launcher_page;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.daiv.android.twitter.settings.AppSettings;


public class HandleScrollService extends IntentService {

    public static AppSettings settings;
    public static long id;

    public HandleScrollService() {
        super("HandleScrollService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.v("Test_launcher", "running scroll service");

        sendBroadcast(new Intent("com.daiv.android.twitter.CLEAR_PULL_UNREAD"));

        SharedPreferences sharedPrefs = getSharedPreferences("com.daiv.android.twitter_world_preferences",
                Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

        sharedPrefs.edit().putBoolean("refresh_me", true).commit();
    }
}
