/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.thebasicapp.EasyResumeBuilder;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * An ad listener that toasts all ad events.
 */
public class ToastAdListener extends AdListener {
    private Context mContext;
    private AdView _mAdView;
    public ToastAdListener(Context context,AdView mAdView) {
        this.mContext = context;
        this._mAdView=mAdView;
    }

    @Override
    public void onAdLoaded() {
        this._mAdView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAdOpened() {
     //   Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
       // Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
    }


}
