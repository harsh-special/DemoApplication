package com.greencardgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Util.GCGModesParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pdfhelper.LanguageSelection;

import java.lang.reflect.Type;
import java.util.List;

public class LanguageActivity extends Activity {

    private RecyclerView recLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        recLanguage = (RecyclerView) findViewById(R.id.rec_language);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recLanguage.setLayoutManager(manager);
        recLanguage.setAdapter(new CustomAdapter(GCGModesParser.loadJSONFromAsset("Language.json", this).replaceAll("\r\n", "")));
    }

    private class CustomAdapter extends RecyclerView.Adapter {
        LanguageSelection languageSelection;
        List<LanguageSelection.Language> lstLanguage;

        public CustomAdapter(String fileData) {
            Type listType = new TypeToken<LanguageSelection>() {
            }.getType();
            languageSelection = new Gson().fromJson(fileData, listType);

            lstLanguage = languageSelection.getLanguages();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_language, parent, false);

            return new RecyclerView.ViewHolder(itemView) {

            };
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            TextView txtLanguage = (TextView) holder.itemView.findViewById(R.id.txt_language);
            txtLanguage.setText(lstLanguage.get(position).getLanguage());
            txtLanguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LanguageActivity.this, SplashActivity.class);
                    intent.putExtra("Language", lstLanguage.get(holder.getAdapterPosition()).getCode());
                    intent.putExtra("FileName", lstLanguage.get(holder.getAdapterPosition()).getFileName());
                    startActivity(intent);
                    finish();


                }
            });
        }

        @Override
        public int getItemCount() {
            return lstLanguage.size();
        }
    }
}
