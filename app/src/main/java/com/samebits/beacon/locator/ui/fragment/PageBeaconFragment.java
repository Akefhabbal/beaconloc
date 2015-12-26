/*
 *
 *  Copyright (c) 2015 SameBits UG. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.samebits.beacon.locator.ui.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samebits.beacon.locator.BeaconLocatorApp;
import com.samebits.beacon.locator.data.DataManager;
import com.samebits.beacon.locator.model.IManagedBeacon;
import com.samebits.beacon.locator.util.Constants;

/**
 * Created by vitas on 20/12/15.
 */
public abstract class PageBeaconFragment extends PreferenceFragmentCompat {

    protected DataManager mDataManager;
    protected IManagedBeacon mBeacon;
    protected int mPage;


    public void setBeacon(IManagedBeacon beacon) {
        this.mBeacon = beacon;
    }

    abstract public void onCreatePreferences(Bundle savedInstanceState, String rootKey);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = BeaconLocatorApp.from(getActivity()).getComponent().dataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        readArguments();
        setData();

        return fragmentView;
    }

    protected abstract void setData();

    protected void readArguments() {
        if (getArguments() != null) {
            mPage = getArguments().getInt(Constants.ARG_PAGE);
            mBeacon = getArguments().getParcelable(Constants.ARG_BEACON);
        }
    }

    protected void updateBeacon() {
        mDataManager.updateBeacon(mBeacon);
    }


    protected void storeBeacon() {
        if (mDataManager.storeBeacon(mBeacon)) {
            Log.d(Constants.TAG, String.format("Beacon %s is stored in db", mBeacon.getId()));
        }
    }
}