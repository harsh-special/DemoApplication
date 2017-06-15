package com.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.greencardgo.R;


public class AboutUsFragment extends Fragment {


    private String text;
    private WebView webview;


    public static AboutUsFragment newInstance(Context context) {

        Bundle args = new Bundle();
        args.putString("mTitleText", "About Us");
        AboutUsFragment fragment = new AboutUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, null, false);
        webview = (WebView) view.findViewById(R.id.webview);
        text=getActivity().getResources().getString(R.string.about_us_text);
        setData();

        return view;
    }


    private void setData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
    }
}

