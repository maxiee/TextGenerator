package com.maxiee.textgenerator.markov;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maxiee on 15-5-14.
 */
public class Markov {

    private static String mBlackList1 = "[^\u4e00-\u9fa5]";
    private static String mBlackList2 = "[一]";

    public static String generateText(JSONObject md) throws JSONException {
        JSONObject model = md;
        Random rand = new Random();
        int randomIndex = rand.nextInt(model.length());
        String currentFragment = model.names().getString(randomIndex);
        String ret = "";
        for (int i = 0; i < 30; i++) {
            String newCahr = getNextChar(model, currentFragment);
            if (newCahr == null) {
                return "";
            }
            ret += newCahr;
            currentFragment = newCahr;
        }
        return ret;
    }

    private static String getNextChar(JSONObject model, String fragment) throws JSONException {
        ArrayList<String> chars = new ArrayList<>();
        if (!model.has(fragment)) {
            Log.d("maxiee", "模型无此fragment");
            return null;
        }
        JSONObject freqModel = model.getJSONObject(fragment);
        Iterator<String> keys = freqModel.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            int freq = 0;
            try {
                freq = freqModel.getInt(key);
                Log.d("maxiee", "连接词:"+key+"频数"+String.valueOf(freq));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i=0; i<freq; i++) {
                chars.add(key);
            }
        }
        Random rand = new Random();
        return chars.get(rand.nextInt(chars.size()));
    }

    public static JSONObject generateModel(ArrayList<String> textList, int order) {
        JSONObject model = new JSONObject();
        for (String text:textList) {
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
                        count += 1;
                        fragmentModel.put(String.valueOf(nextChar), count);
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
