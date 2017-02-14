package com.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Util.GCGModesParser;
import com.greencardgo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 1/23/2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private TextView tv_question;
    private Button btn_yes, btn_no, btn_ok, btn_next;
    private ArrayList<String> al_que = new ArrayList<>();
    private LinearLayout ll_diamond, ll_redRectangle, ll_oval, ll_checkbox;
    private String modes_json_string;
    private JSONObject modes_json_object;
    private HashMap<String, JSONObject> dicMode = new HashMap<>();
    RadioGroup rg_options;
    View view;
    private HashMap<CompoundButton, JSONArray> hQuestionarre;
    private HashMap<CompoundButton, JSONArray> hOctagon;

    public static HomeFragment newInstance(Context context) {

        Bundle args = new Bundle();
        args.putString("mTitleText", "Go Green Card");
        HomeFragment fragment = new HomeFragment();
        GCGModesParser.dicStateMode.clear();
        GCGModesParser parser = new GCGModesParser();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main, null, false);

        initView(view);
        setListner();
        getContentsFromJsonFile();
        return view;
    }

    private void initView(View v) {
        tv_question = (TextView) v.findViewById(R.id.tv_question_diamond);
        btn_no = (Button) v.findViewById(R.id.btn_no);
        btn_yes = (Button) v.findViewById(R.id.btn_yes);
        btn_ok = (Button) v.findViewById(R.id.btn_ok);
        ll_diamond = (LinearLayout) v.findViewById(R.id.ll_diamond);
        ll_redRectangle = (LinearLayout) v.findViewById(R.id.ll_redRectangle);
        ll_oval = (LinearLayout) v.findViewById(R.id.ll_oval);
        ll_checkbox = (LinearLayout) v.findViewById(R.id.ll_checkbox);
        btn_next = (Button) v.findViewById(R.id.btn_next);
        rg_options = new RadioGroup(getActivity());
    }

    private void setListner() {
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    private void getContentsFromJsonFile() {
        modes_json_string = loadJSONFromAsset();
        try {
            modes_json_object = new JSONObject(modes_json_string);
            Log.e("TAG", "modes_json_object===>" + modes_json_object);
            dicMode.put("json", modes_json_object);
            Log.e("TAG", "dicMode.get(\"json\")==>" + dicMode.get("json"));
            loadParser();
            setDataToView();
            tv_question.setText(GCGModesParser.currentStepText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Modes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadParser() {
        JSONObject jsonObject = dicMode.get("json");
        HashMap<Object, JSONObject> hashMap = new HashMap<>();
        try {
            Log.e("TAG", "mode2===>" + jsonObject.getJSONObject("Mode2"));
            hashMap.put("Mode2", jsonObject.getJSONObject("Mode2"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GCGModesParser.setData(hashMap);


    }

    public void setDataToView() {
        if (GCGModesParser.currentStepType.equals("diamond")) {
            Log.e("TAG", "view diamond");
            ll_diamond.setVisibility(View.VISIBLE);
            ll_oval.setVisibility(View.GONE);
            ll_redRectangle.setVisibility(View.GONE);
            tv_question.setText(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("redRectangle")) {
            Log.e("TAG", "view redRectangle");
            ll_redRectangle.setVisibility(View.VISIBLE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.GONE);
            setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
            tv_question.setText(GCGModesParser.currentStepText);

        } else if (GCGModesParser.currentStepType.equals("octagon")) {
            Log.e("TAG", "view octagon");
            ll_redRectangle.setVisibility(View.VISIBLE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.GONE);
            setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
            tv_question.setText(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("oval")) {
            ll_redRectangle.setVisibility(View.GONE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.VISIBLE);
            tv_question.setText(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("yellowHexa")) {
            try {
                if (GCGModesParser.dicCurrentState.getString("hasChoice").equals("yes")) {
                    ll_redRectangle.setVisibility(View.VISIBLE);
                    ll_diamond.setVisibility(View.GONE);
                    ll_oval.setVisibility(View.GONE);
                    setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
                    tv_question.setText(GCGModesParser.currentStepText);
                } else {
                    ll_redRectangle.setVisibility(View.GONE);
                    ll_diamond.setVisibility(View.GONE);
                    ll_oval.setVisibility(View.VISIBLE);
                    tv_question.setText(GCGModesParser.currentStepText);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void setupOptionsInQuestionaireView(String currentStepType) {
        ll_checkbox.removeAllViews();
        rg_options.removeAllViews();
        JSONArray arr_options = GCGModesParser.choices();
        for (int i = 0; i < arr_options.length(); i++) {
            View child;
            CheckBox cb = null;
            RadioButton radioButton = null;
            if (currentStepType.equals("redRectangle")) {
                cb = new CheckBox(getActivity());

            } else {
                radioButton = new RadioButton(getActivity());
            }
            createRadioButton(cb, radioButton, currentStepType, i, arr_options);

        }

    }

    private void createRadioButton(final CheckBox cb, final RadioButton radioButton, String currentStepType, final int i, final JSONArray arr_options) {
        try {
//            ll_checkbox.removeAllViews();
            hQuestionarre = new HashMap<CompoundButton, JSONArray>();
//            hOctagon = new HashMap<CompoundButton, JSONArray>();


            if (currentStepType.equals("redRectangle")) {
                cb.setText(arr_options.getJSONObject(i).getString("title"));
                cb.setTag(arr_options.getJSONObject(i).getString("id"));
                GCGModesParser.arrQuestionaireSelected.clear();
                GCGModesParser.dicStateMode.remove("Option1");
                GCGModesParser.dicStateMode.remove("Option2");
                GCGModesParser.dicStateMode.remove("Option3");
                GCGModesParser.dicStateMode.remove("Option4");
                GCGModesParser.dicStateMode.remove("Option5");

                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        try {
                            hQuestionarre.put(compoundButton, arr_options);
                            if (cb.isChecked()) {
                                GCGModesParser.arrQuestionaireSelected.add(arr_options.getJSONObject(i).getString("id"));
                                GCGModesParser.dicStateMode.put(arr_options.getJSONObject(i).getString("id"), "yes");
                            } else {
                                GCGModesParser.arrQuestionaireSelected.remove(hQuestionarre.get(compoundButton).getJSONObject(i).getString("id"));
                                GCGModesParser.dicStateMode.remove(hQuestionarre.get(compoundButton).getJSONObject(i).getString("id"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                ll_checkbox.addView(cb);
            } else {
                GCGModesParser.arrOctagonSelected.clear();
                rg_options.setOrientation(LinearLayout.VERTICAL);
                radioButton.setText(arr_options.getJSONObject(i).getString("title"));
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        try {

                            if (b) {
                                GCGModesParser.arrOctagonSelected.clear();
                                GCGModesParser.arrOctagonSelected.add(arr_options.getJSONObject(i).getString("id"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        hOctagon.put(compoundButton, arr_options);
//                        try {
//                            if (radioButton.isChecked())
//                                GCGModesParser.arrOctagonSelected.add(arr_options.getJSONObject(i).getString("id"));
//
//                            else
//                                GCGModesParser.arrOctagonSelected.remove(hOctagon.get(compoundButton).getJSONObject(i).get("id"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
                rg_options.addView(radioButton);
                ll_checkbox.addView(rg_options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == btn_yes) {
            GCGModesParser.moveToStepYes();
            setDataToView();
        } else if (v == btn_no) {
            GCGModesParser.moveToStepNo();
            setDataToView();
        } else if (v == btn_next) {
            if (GCGModesParser.currentStepType.equals("redRectangle")) {
                HashMap hashMap = GCGModesParser.validateQustionnaire();
                String OptionID = hashMap.get("optionID").toString();
                if (hashMap.get("success").equals("true")) {
                    GCGModesParser.moveToStepChoice(OptionID);
                    setDataToView();
                } else {
                    Toast.makeText(getActivity(), hashMap.get("title").toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                if (GCGModesParser.arrOctagonSelected.size() > 0) {
                    GCGModesParser.moveToStepChoice(GCGModesParser.arrOctagonSelected.get(0));
                    setDataToView();
                }
            }
        } else if (v == btn_ok) {
            GCGModesParser.moveToStepOK();
            setDataToView();
        }
    }


}
