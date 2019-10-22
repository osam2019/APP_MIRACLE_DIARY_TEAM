package com.miracle.miraclediary.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.miracle.miraclediary.R;

public class EditorTutorialDialog extends AppCompatActivity {

    Button OKButton;

    TextView TitleTextView;
    TextView ContextTextView;

    int m_contextType = -1;
    String m_contextStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_editor_tutorial);

        TitleTextView = findViewById(R.id.det_title);
        ContextTextView = findViewById(R.id.det_context);

        //액티비티 간의 데이터를 받아옵니다.
        Intent intent = getIntent();
        //임시
        m_contextType = 1; // 0 : 아침, 1 : 저녁


        SetEvents();
        SetTitle();
    }

    private void SetEvents() {
        //버튼
        OKButton = findViewById(R.id.det_OK);

        //등록버튼의 이벤트 리스너
        OKButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void SetTitle() {
        String title_str;

        if (m_contextType == 0) {
            title_str = "어제 생각해놨던 일들이에요.";
        } else {
            title_str = "오늘 적었었던 내용들이에요.";
        }

        if (m_contextStr == null || m_contextStr.isEmpty()) {
            m_contextStr = "작성된게 없어요! 좀 더 노력해보아요!";
        }

        TitleTextView.setText(title_str);
        ContextTextView.setText(m_contextStr);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        return event.getAction() != MotionEvent.ACTION_OUTSIDE;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
