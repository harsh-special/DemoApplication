package com.greencardgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class SplashActivity extends Activity implements View.OnClickListener {
    private Button btn_agree;
    private WebView webview;
    private String text = "<html><body>"
            + "<H2 align=\"center\"> DISCLAIMER: </H2>"
            + "<p align=\"justify\">"
            + "Information provided by this GreenCard Go! app on possible methods, if any, for you to obtain a \"green card\" in order to be a legal permanent resident of the United States is" +
            " provided as legal information only.  It is absolutely NOT intended to be legal advice.  " +
            "Every care and effort has been taken to ensure this app contains the latest and most accurate immigration information; however, G" +
            "reenCard Go! makes no warranty or representation whatsoever, whether express or implied, as to the accuracy of such information. " +
            " Any action you take or rely upon after using this app is your own responsibility and GreenCard Go! bears no responsibility or connection to such action. " +
            " Consultation with a qualified immigration attorney is highly recommended for definitive advice and guidance for your immigration solutions and planning, " +
            "such as with the Law Office of Larry L. Doan, GuruImmigration.com, in Los Angeles, California, the creator of this app."
            + "</p> "
            + "<p align=\"justify\">"
            + "Also, if you have ever been arrested, charged, convicted of a crime anywhere," +
            " deported or removed from the US, have multiple unlawful entries into the US, or have had an immigration history in the US but are now living outside," +
            " a consultation with an immigration attorney is absolutely a must, as this app only provides information as " +
            "to what is possible for a person without these grounds of inadmissibility.  With these grounds on your records," +
            " it still may be possible for you to obtain a green card but only after being approved for certain difficult waivers or pardons or upon satisfying other conditions.  " +
            "Only a qualified attorney can advise you on these steps."
            + "<H2 align=\"center\"> GENERAL INSTRUCTIONS: </H2>"
            + "</p> "
            + "<p align=\"justify\">"
            + "This app is for those who are primarily interested in ways to immigrate permanently to the United States, " +
            "that is, to obtain what is known as a \"green card.\"  Whether you are already in the US or currently outside, " +
            "obtaining a green card means that you are entitled to live and work permanently in this country.  Although this app does cover some, " +
            "it is less focused on non-permanent ways to visit or stay in the US as there are numerous such ways, which require a tremendous amount of information-gathering and analysis. " +
            " Instead, we believe that users of this app are more interested in permanently immigrating to this country."
            + "</p> "
            + "<p align=\"justify\">"
            + "Note:  This app is not intended for those who already have a green card, " +
            "strangely enough that is a situation that we sometimes encounter when a person wants to \"check\" on how they obtained their card."
            + "</p> "
            + "</body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        initListner();
        setData();
    }


    private void initListner() {
        btn_agree.setOnClickListener(this);
    }

    private void initViews() {
        webview = (WebView) this.findViewById(R.id.webview);
        btn_agree = (Button) findViewById(R.id.btn_agree);
    }

    private void setData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
    }

    @Override
    public void onClick(View view) {
        if (view == btn_agree) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
