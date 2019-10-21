package com.miracle.miraclediary;

import android.os.Bundle;
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

        SetClickEvents();
    }

    private void SetClickEvents() {
        //등록 버튼
        SubmitButton = findViewById(R.id.bt_submit);
        //내용
        ContextEditText = findViewById(R.id.editText_context);

        //등록버튼의 이벤트 리스너
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                SetContext(ContextEditText.getText().toString());
            }
        });


    }

    public void SetContext(String context) {
        m_context = context;
        if (__DEBUG__) {
            Log.d("Test", m_context);
        }
    }


}
