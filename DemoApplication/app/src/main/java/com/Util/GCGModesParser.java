package com.Util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bhavin on 2/1/2017.
 */
public class GCGModesParser {
    public static String fromPoint = "";
    public static String toPoint = "toPoint";
    public static String options = "options";
    public static String title = "title";
    public static String type = "type";
    public static String optionA = "optionA";
    public static String optionB = "optionB";
    public static String end = "end";
    public static String MCQues1 = "";
    public static String MCQues2;
    public static String MCQues3;
    public static String MCQues4;
    public static String MCQues5;
    public static String id = "id";
    public static JSONObject jsonObject_mode2;
    public static String currentRootNode = "";
    public static String currentStepText = "";
    public static String currentStepType = "";
    public static ArrayList<String> arrQuestionaireSelected = new ArrayList<>();
    public static ArrayList<String> arrOctagonSelected = new ArrayList<>();
    public static String exitType = "exitType";

    static HashMap<Object, JSONObject> dicMain = new HashMap<Object, JSONObject>();
    public static HashMap<Object, String> dicStateMode = new HashMap<Object, String>();
    public static JSONObject dicCurrentState;
//    public static JSONObject dicStateMode;

    public static void setData(HashMap<Object, JSONObject> data) {
        dicMain = data;
        jsonObject_mode2 = data.get("Mode2");
        try {
            dicCurrentState = jsonObject_mode2.getJSONObject("D1");
            currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
            currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            Log.e("TAG", "dicCurrentState==>" + dicCurrentState.getJSONObject(String.valueOf(GCGModesParser.optionA)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static boolean isStepWithOption() {
        boolean is_step = false;
        try {
            if (!dicCurrentState.getString(String.valueOf(GCGModesParser.options)).isEmpty()) {
                is_step = true;
            } else {
                is_step = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is_step;
    }

    public static boolean getNodeState(String key) {
        if (dicStateMode.containsKey(key)) {
            if (dicStateMode.get(key).equals("yes")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static void saveState(String key, String value) {
        dicStateMode.put(key, value);
    }

    public static JSONArray choices() {
        if (isStepWithOption() == true) {
            try {
                JSONArray jsonArray = dicCurrentState.getJSONArray(String.valueOf(GCGModesParser.options));
                int choice = jsonArray.length();
                return jsonArray;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void moveToStepNo() {
        try {
//            JSONObject jsonObject = null;
//            JSONArray jsonArray = dicCurrentState.getJSONArray(String.valueOf(GCGModesParser.options));
//            for (int i = 0; i < jsonArray.length(); i++) {
//                jsonObject = jsonArray.getJSONObject(i);
//            }
//            if (jsonObject.has(GCGModesParser.optionB)){
//
//                JSONObject safeDicCurrentState = dicCurrentState.getJSONObject(String.valueOf(GCGModesParser.optionB));
//                String nextStep = safeDicCurrentState.getString(String.valueOf(GCGModesParser.toPoint));
//
//                currentRootNode = nextStep;
//                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
//                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
//                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
//            }
//            else {
//
//            }

            Object safeDicCurrentState = dicCurrentState.get(String.valueOf(GCGModesParser.optionB));
            Log.e("TAG", "Object==>" + safeDicCurrentState);
            if (safeDicCurrentState instanceof JSONObject) {
                JSONObject jsonObject1 = (JSONObject) safeDicCurrentState;
                String nextStep = jsonObject1.getString(String.valueOf(GCGModesParser.toPoint));
                currentRootNode = nextStep;
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "no");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            } else if (dicCurrentState.get(String.valueOf(GCGModesParser.options)) instanceof JSONArray) {
                JSONArray jsonArray1 = (JSONArray) dicCurrentState.get(String.valueOf(GCGModesParser.options));
                String toPoint = getToPointFromOptions((JSONArray) safeDicCurrentState);
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "no");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(toPoint));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            }




          /*  JSONObject safeDicCurrentState = dicCurrentState.getJSONObject(String.valueOf(GCGModesParser.optionB));
            String nextStep = safeDicCurrentState.getString(String.valueOf(GCGModesParser.toPoint));

            currentRootNode = nextStep;
            saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "no");
            dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
            currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
            currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));*/
            Log.e("TAG", "dicCurrentState==>" + dicCurrentState);
            Log.e("TAG", "currentRootNode==>" + currentRootNode);
            Log.e("TAG", "currentStepText==>" + currentStepText);
            Log.e("TAG", "currentStepType==>" + currentStepType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void moveToStepYes() {
        try {
//            JSONObject jsonObject = null;
//            JSONArray jsonArray = dicCurrentState.getJSONArray(String.valueOf(GCGModesParser.options));
//            Log.e("TAG", "jsonArray==>" + jsonArray);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                jsonObject = jsonArray.getJSONObject(i);
//            }
//            if (jsonObject.has("optionB")) {
            Log.e("TAG", "if Option a==>");

            Object safeDicCurrentState = dicCurrentState.get(String.valueOf(GCGModesParser.optionA));
            Log.e("TAG", "Object==>" + safeDicCurrentState);
            if (safeDicCurrentState instanceof JSONObject) {
                JSONObject jsonObject1 = (JSONObject) safeDicCurrentState;
                String nextStep = jsonObject1.getString(String.valueOf(GCGModesParser.toPoint));
                currentRootNode = nextStep;
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            } else if (dicCurrentState.get(String.valueOf(GCGModesParser.options)) instanceof JSONArray) {
                JSONArray jsonArray1 = (JSONArray) dicCurrentState.get(String.valueOf(GCGModesParser.options));
                String toPoint = getToPointFromOptions((JSONArray) safeDicCurrentState);
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(toPoint));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            }
//            } else {
//                Log.e("TAG", "else Option a==>");
//
//
//            }

//            JSONObject safeDicCurrentState = dicCurrentState.getJSONObject(String.valueOf(GCGModesParser.optionA));
//            String nextStep = safeDicCurrentState.getString(String.valueOf(GCGModesParser.toPoint));
//            currentRootNode = nextStep;
//            dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
//            currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
//            currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            Log.e("TAG", "dicCurrentState==>" + dicCurrentState);
            Log.e("TAG", "currentRootNode==>" + currentRootNode);
            Log.e("TAG", "currentStepText==>" + currentStepText);
            Log.e("TAG", "currentStepType==>" + currentStepType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getToPointFromOptions(JSONArray jsonArray1) {
        String toPoint = "";

        Log.e("TAG", "jsonArray1==>" + jsonArray1);
        for (int i = 0; i < jsonArray1.length(); i++) {
            try {
                ArrayList<String> arrPositive = new ArrayList<>();
                ArrayList<String> arrNegative = new ArrayList<>();
                ArrayList<Boolean> arrPositiveValues = new ArrayList<>();
                ArrayList<Boolean> arrNegativeValues = new ArrayList<>();
                for (int j = 0; j < jsonArray1.getJSONObject(i).getJSONArray("positive").length(); j++) {
                    Log.e("TAG", "for===>1");
                    arrPositive.add(jsonArray1.getJSONObject(i).getJSONArray("positive").get(j).toString());
                    Log.e("TAG", "arrPositive.get(i))===>" + arrPositive);
                    arrPositiveValues.add(getNodeState(arrPositive.get(j)));
                }
                for (int j = 0; j < jsonArray1.getJSONObject(i).getJSONArray("negative").length(); j++) {
                    Log.e("TAG", "for===>2");
                    arrNegative.add(jsonArray1.getJSONObject(i).getJSONArray("negative").get(j).toString());
                    Log.e("TAG", "arrNegative.get(i))===>" + arrNegative);
                    arrNegativeValues.add(getNodeState(arrNegative.get(j)));
                }
//                if (arrPositive.size() > 0) {
//                    Log.e("TAG","arrPositive.get(i))==>"+arrPositive.get(i));
//                    arrPositiveValues.add(getNodeState(arrPositive.get(i)));
//                }
//                if (arrNegative.size() > 0) {
//                    Log.e("TAG","arrNegative.get(i))==>"+arrNegative.get(i));
//                    arrNegativeValues.add(getNodeState(arrNegative.get(i)));
//                }

                boolean isAllPositive = arrPositiveValues.size() > 0 ? !arrPositiveValues.contains(false) : false;
                boolean isAllNegative = arrNegativeValues.size() > 0 ? !arrNegativeValues.contains(true) : false;

                if (arrPositiveValues.size() > 0 && arrNegativeValues.size() > 0) {
                    Log.e("TAG", "arrPositive");
                    Log.e("TAG", "negative ");

                    if (isAllNegative && isAllPositive) {
                        toPoint = jsonArray1.getJSONObject(i).getString("toPoint");
                    }
                } else if (arrNegative.size() == 0) {
                    if (isAllPositive) {
                        toPoint = jsonArray1.getJSONObject(i).getString("toPoint");
                    }
                } else if (arrPositive.size() == 0) {
                    if (isAllNegative) {
                        toPoint = jsonArray1.getJSONObject(i).getString("toPoint");
                    }
                } else {
                    toPoint = jsonArray1.getJSONObject(i).getString("toPoint");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toPoint;
    }

    public static HashMap<Object, Object> validateQustionnaire() {
        HashMap<Object, Object> map = new HashMap<>();
        if (arrQuestionaireSelected.contains("Option1") && arrQuestionaireSelected.contains("Option3")) {
            map.put("success", "false");
            map.put("title", "It is not possible for options 1 and 3 to both be true. Please try again");
            map.put("optionID", "");
            return map;
        } else if (arrQuestionaireSelected.contains("Option5") && arrQuestionaireSelected.size() > 1) {
            map.put("success", "false");
            map.put("title", "It is not possible for 'None of the above' and another option to both be true. Please try again");
            map.put("optionID", "");
            return map;
        } else if (arrQuestionaireSelected.contains("Option1") && arrQuestionaireSelected.size() == 1) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option1");
            return map;
        } else if (arrQuestionaireSelected.contains("Option2") && arrQuestionaireSelected.size() == 1) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option2");
            return map;
        } else if (arrQuestionaireSelected.contains("Option3") && arrQuestionaireSelected.size() == 1) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option3");
            return map;
        } else if (arrQuestionaireSelected.contains("Option4") && arrQuestionaireSelected.size() == 1) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option4");
            return map;
        } else if (arrQuestionaireSelected.contains("Option5") && arrQuestionaireSelected.size() == 1) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option5");
            return map;
        } else if (arrQuestionaireSelected.contains("Option1") && arrQuestionaireSelected.contains("Option2") &&
                arrQuestionaireSelected.size() == 2) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option1");
            return map;
        } else if (arrQuestionaireSelected.contains("Option2") && arrQuestionaireSelected.contains("Option3") &&
                arrQuestionaireSelected.size() == 2) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option2");
            return map;
        } else if (arrQuestionaireSelected.contains("Option1") && arrQuestionaireSelected.contains("Option4") &&
                arrQuestionaireSelected.size() == 2) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option1");
            return map;
        } else if (arrQuestionaireSelected.contains("Option2") && arrQuestionaireSelected.contains("Option4") &&
                arrQuestionaireSelected.size() == 2) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option2");
            return map;
        } else if (arrQuestionaireSelected.contains("Option3") && arrQuestionaireSelected.contains("Option4") &&
                arrQuestionaireSelected.size() == 2) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option3");
            return map;
        } else if (arrQuestionaireSelected.contains("Option1") && arrQuestionaireSelected.contains("Option2") &&
                arrQuestionaireSelected.contains("Option4") && arrQuestionaireSelected.size() == 3) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option1");
            return map;
        } else if (arrQuestionaireSelected.contains("Option3") && arrQuestionaireSelected.contains("Option2") &&
                arrQuestionaireSelected.contains("Option4") && arrQuestionaireSelected.size() == 3) {
            map.put("success", "true");
            map.put("title", "");
            map.put("optionID", "Option2");
            return map;
        } else {
            map.put("success", "false");
            map.put("title", "Something went wrong");
            map.put("optionID", "");
            return map;
        }
    }

    public static void moveToStepOK() {
        try {
            JSONArray jsonArray = dicCurrentState.getJSONArray(String.valueOf(GCGModesParser.options));
            Log.e("TAG", "jsonArray==>" + jsonArray);
            if (jsonArray.length() == 1) {
                JSONObject safeDicCurrentState = jsonArray.getJSONObject(0);
                String nextStep = safeDicCurrentState.getString(String.valueOf(GCGModesParser.toPoint));
                if (safeDicCurrentState.opt(GCGModesParser.exitType) != null) {
                    removeValueForPPKey();
                    saveState(safeDicCurrentState.getString(GCGModesParser.exitType), "yes");
                }
                currentRootNode = nextStep;
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            } else if (jsonArray.length() > 1) {
                String toPoint = getToPointFromOptionsCheckingFromPoint(jsonArray);
                if(toPoint.equalsIgnoreCase("")){
                    toPoint=getToPointFromOptions(jsonArray);
                }
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(toPoint));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            } else {
                String toPoint = getToPointFromOptionsCheckingFromPoint(jsonArray);
                if(toPoint.equalsIgnoreCase("")){
                    toPoint=getToPointFromOptions(jsonArray);
                }
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(toPoint));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void moveToStepChoice(String optionID) {

        try {
            Object safeDicCurrentState = dicCurrentState.get(String.valueOf(optionID));
            if (safeDicCurrentState instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) safeDicCurrentState;
                String nextStep = jsonObject.getString(String.valueOf(GCGModesParser.toPoint));
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                currentRootNode = nextStep;
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(currentRootNode));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            } else if (safeDicCurrentState instanceof JSONArray) {
                JSONArray jsonArray1 = (JSONArray) safeDicCurrentState;
                String toPoint = getToPointFromOptions(jsonArray1);
                saveState(dicCurrentState.getString(String.valueOf(GCGModesParser.id)), "yes");
                dicCurrentState = jsonObject_mode2.getJSONObject(String.valueOf(toPoint));
                currentStepText = dicCurrentState.getString(String.valueOf(GCGModesParser.title));
                currentStepType = dicCurrentState.getString(String.valueOf(GCGModesParser.type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getToPointFromOptionsCheckingFromPoint(JSONArray array) {
        String toPoint = "";

        for (int i = 0; i < array.length(); i++) {
            try {
                if (array.getJSONObject(i).get("fromPoint") instanceof String) {
                    String fromPoint = array.getJSONObject(i).getString("fromPoint");
                    boolean nodeState = isStateSavedFor(fromPoint);

                    if (nodeState) {
                        toPoint = array.getJSONObject(i).getString(GCGModesParser.toPoint);

                        if (array.getJSONObject(i).opt(GCGModesParser.exitType) != null) {
                            removeValueForPPKey();
                            saveState(array.getJSONObject(i).getString(GCGModesParser.exitType), "yes");
                        }
                    }
                } else {
                    JSONArray fromPoint = array.getJSONObject(i).getJSONArray("fromPoint");
                    boolean nodeState = isArrStateSavedFor(fromPoint);

                    if (nodeState) {
                        toPoint = array.getJSONObject(i).getString(GCGModesParser.toPoint);

                        if (array.getJSONObject(i).opt(GCGModesParser.exitType) != null) {
                            removeValueForPPKey();
                            saveState(array.getJSONObject(i).getString(GCGModesParser.exitType), "yes");
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toPoint;
    }

    private static boolean isStateSavedFor(String key) {
        if (dicStateMode.containsKey(key)) {
            return true;
        }

        return false;
    }

    private static boolean isArrStateSavedFor(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (!dicStateMode.containsKey(jsonArray.getString(i))) {
                    break;
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static void removeValueForPPKey() {
        dicStateMode.remove("PP1");
        dicStateMode.remove("PP2");
        dicStateMode.remove("PP3");
        dicStateMode.remove("PP4");

        dicStateMode.remove("M1_PP1");
        dicStateMode.remove("M1_PP2");
        dicStateMode.remove("M1_PP3");
        dicStateMode.remove("M1_PP4");


        dicStateMode.remove("M3_PP1");
        dicStateMode.remove("M3_PP1");
        dicStateMode.remove("M3_PP1");
        dicStateMode.remove("M3_PP1");

    }
}
