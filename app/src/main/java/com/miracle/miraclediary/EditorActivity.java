package com.miracle.miraclediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.miracle.miraclediary.dialog.EditorTutorialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditorActivity extends BaseCustomBarActivity {

    private final String INIT_STR = "할 일을 적어볼까요? :)";
    private boolean __DEBUG__ = true;
    private ImageButton SubmitButton;
    private ImageButton BackButton;
    private EditText ContextEditText;
    private String m_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        SetActionBarLayout(R.layout.actionbar_editor);



    }

    @Override
    protected void Init() {
        SetEvents();
        CreatePopUp();
    }

    //이벤트 초기설정 함수입니다.
    private void SetEvents() {
        //툴바 버튼
        SubmitButton = findViewById(R.id.actionbar_submit);
        BackButton = findViewById(R.id.actionbar_prev);
        //내용
        ContextEditText = findViewById(R.id.editText_context);
        ContextEditText.setText(INIT_STR);

        //등록버튼의 이벤트 리스너
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                SubmitContext();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                finish();
            }
        });

        ContextEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UpdateContext(null);
                    if (m_context.equals(INIT_STR)) {
                        UpdateContext("");
                    }
                }
            }
        });


    }

    //팝업이 생성되는 함수입니다.
    private void CreatePopUp() {

        ArrayList dates = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current_date = sdf.format(new Date());

        boolean isNotificaton = true;

        for(int i = 0; i < dates.size(); i++) {
            if(current_date.equals(dates.get(i))) {
                isNotificaton = false;
                break;
            }
        }

        isNotificaton = isNotificaton || DBManager.getInstance().isNotification();

        if (isNotificaton) {
            DBManager.getInstance().setNotification(false);
            Intent intent_popup = new Intent(EditorActivity.this, EditorTutorialDialog.class);
            startActivityForResult(intent_popup, 1);
        }

    }

    //작성 버튼을 눌렀을 시 발생하는 함수입니다.
    public void SubmitContext() {
        UpdateContext(null);

        //if (__DEBUG__) {
        //    Log.d("일기 작성 내용", m_context);
        //}
        sqlAdd();
        finish();
    }

    public void sqlAdd() {
        // Temp DbHelper
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into TestTable2 (textDate, textBody) values (?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String data = sdf.format(new Date());

        String[] arg1 = {data, m_context};

        db.execSQL(sql, arg1);
    }

    //작성 중인 일기 내용을 여러 방면으로 업데이트하는 함수입니다.
    public void UpdateContext(String str) {
        m_context = str == null ? ContextEditText.getText().toString() : str;
        if (str != null) {
            ContextEditText.setText(str);
        }
    }


}
