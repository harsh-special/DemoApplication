package com.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greencardgo.R;

/**
 * Created by admin on 1/23/2017.
 */
public class DisclaimerFragment extends Fragment{
    @Nullable
    public static DisclaimerFragment newInstance(Context context) {

        Bundle args = new Bundle();
        args.putString("mTitleText", context.getString(R.string.headre_disclaimer_name));
        DisclaimerFragment fragment = new DisclaimerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disclaimer, null, false);
        return view;
    }
}
