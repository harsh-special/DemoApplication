package com.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class HomeFragment extends Fragment implements View.OnClickListener {
    private WebView tv_question;
    private WebView tv_question_text;
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
        args.putString("mTitleText", context.getResources().getString(R.string.app_name));
        HomeFragment fragment = new HomeFragment();
        GCGModesParser.dicStateMode.clear();
        GCGModesParser.buffer.setLength(0);
        GCGModesParser.arrQuestionaireSelected.clear();
        GCGModesParser.arrOctagonSelected.clear();
        GCGModesParser.dicStateModeForPdf.clear();
        GCGModesParser parser = new GCGModesParser();
        GCGModesParser.jsonContantFile=null;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main, null, false);

        initView(view);
        setListner();
        getContentsFromConstantFile();
        getContentsFromJsonFile();
        return view;
    }

    private void initView(View v) {
        tv_question = (WebView) v.findViewById(R.id.tv_question_diamond);
        tv_question_text = (WebView) v.findViewById(R.id.tv_question_diamond_note);
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
        modes_json_string = loadJSONFromAsset("Modes.json");
        try {
            modes_json_object = new JSONObject(modes_json_string);
            Log.e("TAG", "modes_json_object===>" + modes_json_object);
            dicMode.put("json", modes_json_object);
            Log.e("TAG", "dicMode.get(\"json\")==>" + dicMode.get("json"));
            loadParser();
            setDataToView();
            setTitleValue(GCGModesParser.currentStepText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private String loadJSONFromAsset(String jsonFile) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(jsonFile);
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
            setTitleValue(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("redRectangle")) {
            Log.e("TAG", "view redRectangle");
            if (GCGModesParser.arrQuestionaireSelected.size() > 0) {
                if (GCGModesParser.dicStateMode.containsKey("AP_OV4")) {
                    GCGModesParser.dicStateMode.put("D3","yes");
                    ll_redRectangle.setVisibility(View.VISIBLE);
                    ll_diamond.setVisibility(View.GONE);
                    ll_oval.setVisibility(View.GONE);
                    setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
                    setTitleValue(GCGModesParser.currentStepText);
                } else {
                    GCGModesParser.moveToStepChoice(GCGModesParser.arrQuestionaireSelected.get(0));
                    setDataToView();
                }
            } else {
                ll_redRectangle.setVisibility(View.VISIBLE);
                ll_diamond.setVisibility(View.GONE);
                ll_oval.setVisibility(View.GONE);
                setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
                setTitleValue(GCGModesParser.currentStepText);
            }

        } else if (GCGModesParser.currentStepType.equals("octagon")) {
            Log.e("TAG", "view octagon");
            ll_redRectangle.setVisibility(View.VISIBLE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.GONE);
            setupOptionsInQuestionaireView(GCGModesParser.currentStepType);
            setTitleValue(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("oval")) {
            ll_redRectangle.setVisibility(View.GONE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.VISIBLE);
            setTitleValue(GCGModesParser.currentStepText);
        } else if (GCGModesParser.currentStepType.equals("yellowHexa")) {
            try {
                if (GCGModesParser.dicCurrentState.getString("hasChoice").equals("yes")) {
                    ll_redRectangle.setVisibility(View.VISIBLE);
                    ll_diamond.setVisibility(View.GONE);
                    ll_oval.setVisibility(View.GONE);
                    setupOptionsInQuestionaireView(GCGModesParser.currentStepType);

                    setTitleValue(GCGModesParser.currentStepText);

                } else {
                    ll_redRectangle.setVisibility(View.GONE);
                    ll_diamond.setVisibility(View.GONE);
                    ll_oval.setVisibility(View.VISIBLE);
                    setTitleValue(GCGModesParser.currentStepText);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (GCGModesParser.currentStepType.equals("-1")) {
            ll_redRectangle.setVisibility(View.GONE);
            ll_diamond.setVisibility(View.GONE);
            ll_oval.setVisibility(View.GONE);
            setTitleValue("Thank you for using GreenCard! Go.  Please check your email and download the pdf file to see the options you've just selected and the recommended methods for you to immigrate to or remain in the US.  If you would like to run the app again with different options, just hit the Refresh or Home button. ");
        }

    }

    private void setTitleValue(String currentStepText) {
        tv_question.scrollTo(0, 0);
        StringBuilder builder = new StringBuilder();
        if (hasKey(currentStepText)) {
            builder.append("<html><body><h3><span style=\"font-weight:normal\"><p align=\"justify\">");
            builder.append(
                    GCGModesParser.jsonContantFile.optString(currentStepText).equalsIgnoreCase("")
                            ?
                            currentStepText
                            : GCGModesParser.jsonContantFile.optString(currentStepText)
            );
            builder.append("</p></span></h3></body></html>");

        } else {
//        String text1 =
//                        "<html>
//            <title><h2>P52_H7_TITLE<h2></title>
// <body><p align=\"justify\">F1 category = unmarried sons/daughters (21 or over) of citizens</br>" +
//                        "F2B = unmarried sons/daughters (21 or over) of green-card holders</br>" +
//                        "F3 = married sons/daughters of citizens</br>" +
//                        "F4 = siblings of 21 or over citizens</br>" +
//                        "</br>" +
//                        "A petition's priority date is usually its filing date with USCIS.  Since there is a limited number of visa numbers available each year in each category (except for immediate relative petitions), the earlier is your petition's priority date, the sooner there will be a visa number available for you.  That is, the further ahead in line the petition will be.  When a petition's priority date is 'current', it means it's now the turn of that petition (along with other petitions with the same priority date) to have a visa number, i.e. they are at the head of the line and visa numbers are immediately available for those petitions.  Only with a visa number available can you continue to the next step of securing a green card.   </br>" +
//                        "</br>" +
//                        "But even with a visa number available, the petition has to be 'approved' first.  Being approved means that USCIS has found that the relationship claimed between you and petitioner (your parent or sibling here) exists and is genuine.  Usually, a petition will be approved long before its priority date becomes current.</p>" +
//                         "<p align=\"justify\">"
//                        +"<i>Tip</i>:  If the I-130 was filed for you by your parent under F2B category, and while waiting for your priority date to be current, have parent file to become citizen if priority dates move faster under F1 category.  If parent files for citizenship anyway, you will be automatically converted from F2B to F1, but you have the right to 'opt out' and stay in F2B if priority dates are moving faster with F2B.</p></body></html>";
//
//
//        String text2 =
//                "<html><body>"+
//                        "<p align=\"justify\">"
//                        +"<i>Note</i>: a \"child\" can also include your stepchild, as long as you were married to his or her parent before the stepchild turned 18.</p></body><html>";

//        setData(text2);
//        setData(text2);
//            tv_question.setText(Html.fromHtml(text2));
//            tv_question.setText(currentStepText);
            builder.append("<html><body><h3><span style=\"font-weight:normal\"><p align=\"justify\">");
            builder.append(currentStepText);
            builder.append("</p></span></h3></body></html>");
        }
//
        try {
            builder.append(GCGModesParser.dicCurrentState.has("body")
                    ?
                    getStateModeForPdfValue(GCGModesParser.dicCurrentState.getString("body"))
                    : "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (GCGModesParser.dicCurrentState.has("note")) {
                tv_question_text.setVisibility(
                        View.VISIBLE);
                tv_question_text.getSettings().setJavaScriptEnabled(true);
                tv_question_text.loadDataWithBaseURL("", getStateModeForPdfValue(GCGModesParser.dicCurrentState.getString("note")), "text/html", "UTF-8", "");
            } else
                tv_question_text.setVisibility(
                        View.GONE);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        setData(builder.toString());
//        tv_question.setText(builder);
    }

    private void setData(String text) {
        tv_question.getSettings().setJavaScriptEnabled(true);
        tv_question.loadDataWithBaseURL("", text, "text/html", "UTF-8", "");
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
                                GCGModesParser.dicStateMode.put(arr_options.getJSONObject(i).getString("id"), "yes");
                            } else {
                                GCGModesParser.dicStateMode.remove(arr_options.getJSONObject(i).getString("id"));
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
            HashMap hashMap = GCGModesParser.moveToStepYes();

            if (hashMap.get("success").equals("true")) {
                setDataToView();
            } else {
                Toast.makeText(getActivity(), hashMap.get("title").toString(), Toast.LENGTH_LONG).show();
            }
        } else if (v == btn_no) {
            GCGModesParser.moveToStepNo(getActivity());
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
                    HashMap hashMap = GCGModesParser.validateOptionsForOctagon(GCGModesParser.arrOctagonSelected.get(0));
                    if (hashMap.get("success").equals("true")) {
                        GCGModesParser.moveToStepChoice(GCGModesParser.arrOctagonSelected.get(0));
                    } else {
                        Toast.makeText(getActivity(), hashMap.get("title").toString(), Toast.LENGTH_LONG).show();
                    }
                    setDataToView();
                }
            }
        } else if (v == btn_ok) {
            GCGModesParser.moveToStepOK(getActivity());
            setDataToView();
        }
    }

    private void getContentsFromConstantFile() {

        try {
            GCGModesParser.jsonContantFile = new JSONObject(loadJSONFromAsset("ConstantsFile.json"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean hasKey(String key) {
        if (GCGModesParser.jsonContantFile != null) {
            return GCGModesParser.jsonContantFile.has(key);
        }
        return false;
    }

    private static String getStateModeForPdfValue(String key){
        String valueFromJson=   GCGModesParser.jsonContantFile.optString(key).equalsIgnoreCase("")
                ?
                key
                : GCGModesParser.jsonContantFile.optString(key);
        return valueFromJson;
    }


}
