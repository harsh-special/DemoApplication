package com.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greencardgo.R;


public class AboutUsFragment extends Fragment {

    public static AboutUsFragment newInstance(Context context) {

        Bundle args = new Bundle();
        args.putString("mTitleText", "About Us");
        AboutUsFragment fragment = new AboutUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, null, false);
        return view;
    }


}
