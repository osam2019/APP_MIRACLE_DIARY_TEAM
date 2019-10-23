package com.miracle.miraclediary;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditorTextWatcher implements TextWatcher {

    //이렇게 코딩하지 마세요!
    //여기는 이미 망한 클래스입니다.

    private EditText context;
    private TextHighlightChanger lights;
    private ImageButton button;

    public boolean isInit() {
        return isInit;
    }

    private boolean isInit = false;

    public EditorTextWatcher(EditText context, TextHighlightChanger lights, ImageButton button) {
        this.context = context;
        this.lights = lights;
        this.button = button;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!isInit) {
            isInit = true;
            button.setAlpha(1.0f);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
