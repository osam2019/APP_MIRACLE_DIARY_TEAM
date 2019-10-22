package com.miracle.miraclediary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditorActivity extends AppCompatActivity {

    private boolean __DEBUG__ = true;

    private Button SubmitButton;
    private EditText ContextEditText;

    private String m_context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        SetEvents();
    }

    private void SetEvents() {
        //등록 버튼
        SubmitButton = findViewById(R.id.bt_submit);
        //내용
        ContextEditText = findViewById(R.id.editText_context);

        //등록버튼의 이벤트 리스너
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                SubmitContext();
            }
        });

        ContextEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    UpdateConText(null);
                    if(m_context.equals("일기를 적어볼까요 :)")) {
                        UpdateConText("");
                    }
                }
            }
        });


    }

    public void SubmitContext() {
        UpdateConText(null);

        if (__DEBUG__) {
            Log.d("Test", m_context);
        }

        //여기서부터 db에 저장하시면 됩니다
    }

    public void UpdateConText(String str) {
        m_context = str == null ? ContextEditText.getText().toString() : str;
        if(str != null) {
            ContextEditText.setText(str);
        }
    }


}
