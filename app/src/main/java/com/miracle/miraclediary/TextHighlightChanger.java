package com.miracle.miraclediary;

import android.graphics.Color;

import java.util.HashMap;

public class TextHighlightChanger {

    private HashMap<Character, String> m_highlights = null;

    public TextHighlightChanger() {
        m_highlights = new HashMap<>();
    }

    public void AddHighlight(char keyword, String color) {
        m_highlights.put(keyword, color);
    }

    public String SetHighlight(String src) {
        String result = "";

        char result_c[] = src.toCharArray();
        boolean isHighlight = false;
        for(int i = 0; i < result_c.length; i++) {

            if(m_highlights.containsKey(result_c[i])){
               String color = m_highlights.get(result_c[i]);

               result += "<font color=\'" + color + "\'>";
               isHighlight = true;
            }else if(isHighlight && result_c[i] == ' ') {
                isHighlight = false;
                result += "</font>";
            }

            result += result_c[i];
        }



        return result;
    }
}
