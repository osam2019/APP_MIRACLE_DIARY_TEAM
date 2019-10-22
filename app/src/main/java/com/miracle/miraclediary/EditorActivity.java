package com.miracle.miraclediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.miracle.miraclediary.dialog.EditorTutorialDialog;
import com.miracle.miraclediary.dialog.HabitEditorDialog;

public class EditorActivity extends AppCompatActivity {

    private final String INIT_STR = "할 일을 적어볼까요? :)";
    private boolean __DEBUG__ = true;
    private Button SubmitButton;
    private EditText ContextEditText;
    private String m_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        SetEvents();
        CreatePopUp();
    }

    //이벤트 초기설정 함수입니다.
    private void SetEvents() {
        //등록 버튼
        SubmitButton = findViewById(R.id.bt_submit);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.actionbar_default, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        return true;
    }

    //팝업이 생성되는 함수입니다.
    private void CreatePopUp() {
        //차 후 인텐트를 이용한 데이터 공유 시 수정
        Intent intent = getIntent();


        if (true) {
            Intent intent_popup = new Intent(EditorActivity.this, EditorTutorialDialog.class);
            startActivityForResult(intent_popup, 1);
        }

    }

    //작성 버튼을 눌렀을 시 발생하는 함수입니다.
    public void SubmitContext() {
        UpdateContext(null);

        if (__DEBUG__) {
            Log.d("일기 작성 내용", m_context);
        }

        //여기서부터 db에 저장하시면 됩니다

    }

    //작성 중인 일기 내용을 여러 방면으로 업데이트하는 함수입니다.
    public void UpdateContext(String str) {
        m_context = str == null ? ContextEditText.getText().toString() : str;
        if (str != null) {
            ContextEditText.setText(str);
        }
    }


}
