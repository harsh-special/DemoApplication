package com.greencardgo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class SplashActivity extends Activity implements View.OnClickListener {
    private Button btn_agree;
    private WebView webview;
    private String text;
    private TextView txtToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String languageToLoad = getIntent().getStringExtra("Language"); // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        ;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        initViews();
        initListner();

        btn_agree.setText(getResources().getString(R.string.agree));
        txtToolbarText.setText(getResources().getString(R.string.headre_splash_disclaimer_name));
    }


    private void initListner() {
        btn_agree.setOnClickListener(this);
    }

    private void initViews() {
        webview = (WebView) this.findViewById(R.id.webview);
        txtToolbarText= (TextView) findViewById(R.id.txt_toolbar_text);
        btn_agree = (Button) findViewById(R.id.btn_agree);

        WebSettings ws = webview.getSettings();
        ws.setDefaultFontSize(16);
        text=getResources().getString(R.string.disclaimer_text);
        setData();


    }

    private void setData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
    }

    @Override
    public void onClick(View view) {
        if (view == btn_agree) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("FileName",getIntent().getStringExtra("FileName"));
            finish();
            startActivity(intent);
        }
    }
}
