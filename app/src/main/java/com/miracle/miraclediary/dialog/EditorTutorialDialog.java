package com.miracle.miraclediary.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.miracle.miraclediary.DBManager;
import com.miracle.miraclediary.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditorTutorialDialog extends AppCompatActivity {

    Button OKButton;

    TextView TitleTextView;
    TextView ContextTextView;

    String m_contextStr = null;
    boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_editor_tutorial);

        TitleTextView = findViewById(R.id.det_title);
        ContextTextView = findViewById(R.id.det_context);
        ContextTextView.setMovementMethod(new ScrollingMovementMethod());

        //액티비티 간의 데이터를 받아옵니다.
        Intent intent = getIntent();
        isFirst = intent.getExtras().getBoolean("isFirst");



        SetEvents();
        SetContext();
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

        if (isFirst) {
            title_str = "어제 생각해놨던 일들이에요.";
        } else {
            title_str = "오늘 적었었던 내용들이에요.";
        }

        if (m_contextStr == null || m_contextStr.isEmpty()) {
            m_contextStr = "<b>작성된게 없어요! 좀 더 노력해보아요!</b>";
        }

        TitleTextView.setText(title_str);
        ContextTextView.setText(Html.fromHtml(m_contextStr));

    }

    private void SetContext() {
        ArrayList dates = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);
        ArrayList contexts = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.CONTEXT);
        final Calendar cal = Calendar.getInstance();
        if(isFirst)
            cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = dateFormat.format(cal.getTime());


        for(int i = 0, count = 0; i < dates.size(); i++) {
            if(targetDate.equals(dates.get(i))) {
                count++;
                if(m_contextStr == null) m_contextStr = "";
                m_contextStr += "<b>" + count + "번째 일기</b><br/>";
                m_contextStr += contexts.get(i) + "<br/><br/>";
            }
        }

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
