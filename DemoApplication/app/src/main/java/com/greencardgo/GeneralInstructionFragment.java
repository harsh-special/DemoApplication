package com.greencardgo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by harsh.parikh on 09-Nov-17.
 */

public class GeneralInstructionFragment extends Fragment {

    private String text;
    private WebView webview;

    @Nullable
    public static GeneralInstructionFragment newInstance(Context context) {

        Bundle args = new Bundle();
        args.putString("mTitleText", context.getString(R.string.headre_general_instruction));
        GeneralInstructionFragment fragment = new GeneralInstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disclaimer, null, false);
        webview = (WebView) view.findViewById(R.id.webview);

        text = getActivity().getResources().getString(R.string.general_instruction_text);
        setData();

        return view;
    }


    private void setData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
    }
}
