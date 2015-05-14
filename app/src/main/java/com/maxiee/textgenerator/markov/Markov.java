package com.maxiee.textgenerator.markov;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maxiee on 15-5-14.
 */
public class Markov {

    private static String mBlackList1 = "[^\u4e00-\u9fa5]";
    private static String mBlackList2 = "[一]";

    public static String generateText() {
        return "";
    }

    public static JSONObject generateModel(ArrayList<String> textList, int order) {
        JSONObject model = new JSONObject();
        for (String text:textList) {
//            text = pureText(text);
            for (int i = 0; i < text.length() - order; i++) {
                String fragment = text.substring(i, i+order);
                JSONObject fragmentModel;
                char  nextChar = text.charAt(i+order);
                if (!model.has(fragment)) {
                    JSONObject subJson = new JSONObject();
                    try {
                        model.put(fragment, subJson);
                    } catch (Exception e) {continue;}
                    fragmentModel = subJson;
                } else {
                    try {
                        fragmentModel = model.getJSONObject(fragment);
                    } catch (Exception e) {continue;}
                }
                if (!fragmentModel.has(String.valueOf(nextChar))) {
                    try {
                        fragmentModel.put(String.valueOf(nextChar), 1);
                    } catch (Exception e) {continue;}
                } else {
                    try {
                        int count = fragmentModel.getInt(String.valueOf(nextChar));
                    } catch (Exception e) {continue;}
                }
            }
        }
        return model;
    }

    public static String pureText(String text) {
        Pattern p = Pattern.compile(mBlackList1);
        Matcher m = p.matcher(text);
        p = Pattern.compile(mBlackList2);
        m = p.matcher(m.replaceAll(""));
        return m.replaceAll("");
    }
}
