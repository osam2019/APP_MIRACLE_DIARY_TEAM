package com.miracle.miraclediary.dialog;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.miracle.miraclediary.DBHelper;
import com.miracle.miraclediary.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HabitEditorDialog extends AppCompatActivity {

    private final String INIT_STR = "오늘 들일 습관은 무엇인가요? :)";
    private boolean __DEBUG__ = true;

    Button OKButton;
    EditText ContextEditText;

    private String m_context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_habit_editor);

        ContextEditText = findViewById(R.id.habit_editor_context);
        OKButton = findViewById(R.id.habit_editor_submit);
        ContextEditText.setText(INIT_STR);



        SetEvents();
    }

    private void SetEvents() {

        //등록버튼의 이벤트 리스너
        OKButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SubmitContext();
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

        //취소버튼의 이벤트 리스너
        findViewById(R.id.habit_editor_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    //작성 버튼을 눌렀을 시 발생하는 함수입니다.
    public void SubmitContext() {
        UpdateContext(null);

        //if (__DEBUG__) {
            sqlAdd();
            //Log.d("습관 작성 내용", m_context);
       // }


        //여기서부터 db에 저장하시면 됩니다
    }
    // sqlAdd code
    public void sqlAdd() {
        // Temp DbHelper
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into TestTable (textDate, textBody, mode) values (?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String data = sdf.format(new Date());

        String [] arg1 = { data, m_context, "1"};

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
