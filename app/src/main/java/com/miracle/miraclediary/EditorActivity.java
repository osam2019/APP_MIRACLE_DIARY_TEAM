package com.miracle.miraclediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miracle.miraclediary.dialog.EditorTutorialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EditorActivity extends BaseCustomBarActivity {

    private final String INIT_STR = "할 일을 적어볼까요? :)";
    private final String INIT_TITLE_STR = "제목을 입력하세요.";
    private ImageButton SubmitButton;
    private ImageButton BackButton;
    private EditText TitleEditText;
    private EditText ContextEditText;
    private EditorTextWatcher textWatcher;

    private TextHighlightChanger lights;
    private String m_context;
    private String m_title; // 제목 넣어주시면 됩니다.
    private String idx; // 수정시 필요한 게시글 고유 번호입니다.
    private boolean mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        SetActionBarLayout(R.layout.actionbar_editor);
    }

    @Override
    protected void Init() {

        lights = new TextHighlightChanger();
        lights.AddHighlight('#', "#d86d1b");

        SetEvents();


        // 작성,수정을 구분하는 영역 (0: 작성, 1: 수정)
        Intent intent = getIntent();
        mode = intent.getBooleanExtra("mode",false);
        if ( mode == true)
        {
            idx = intent.getStringExtra("idx");
            ((EditText)findViewById(R.id.editText_title)).setText (intent.getStringExtra("subject"));
            ((EditText)findViewById(R.id.editText_context)).setText (intent.getStringExtra("body"));
            intent.getStringExtra("body");

            // 버튼을 수정으로 만드는 명령어 넣을 곳
        }
        else
        {
            // 버튼을 작성으로 만드는 명령어 넣을 곳
        }

        CreatePopUp();
    }

    //이벤트 초기설정 함수입니다.
    private void SetEvents() {
        //툴바 버튼
        SubmitButton = findViewById(R.id.actionbar_submit);
        BackButton = findViewById(R.id.actionbar_prev);
        //제목
        TitleEditText = findViewById(R.id.editText_title);
        TitleEditText.setText(INIT_TITLE_STR);
        //내용
        ContextEditText = findViewById(R.id.editText_context);
        ContextEditText.setText(INIT_STR);
        textWatcher = new EditorTextWatcher(ContextEditText, lights, SubmitButton);
        ContextEditText.addTextChangedListener(textWatcher);

        //등록버튼의 이벤트 리스너
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                SubmitContext(textWatcher.isInit());
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        ContextEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UpdateContext(null);
                    if (m_context.equals(INIT_STR)) {
                        //UpdateContext("");
                    }
                } else {
                    UpdateContext(null);
                    ContextEditText.setText(Html.fromHtml(lights.SetHighlight(m_context)));
                }
            }
        });

        TitleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String title = TitleEditText.getText().toString();
                    if (title.equals(INIT_TITLE_STR)) {
                        TitleEditText.setText("");
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

        boolean isFirst = isNotificaton;
        isNotificaton = isNotificaton || DBManager.getInstance().isNotification();

        if (isNotificaton) {
            DBManager.getInstance().setNotification(false);
            Intent intent_popup = new Intent(EditorActivity.this, EditorTutorialDialog.class);
            intent_popup.putExtra("isFirst", isFirst);
            startActivityForResult(intent_popup, 1);
        }

    }

    //작성 버튼을 눌렀을 시 발생하는 함수입니다.
    public void SubmitContext(boolean isCanSave) {
        if(!isCanSave) return;
        UpdateContext(null);
        m_title = TitleEditText.getText().toString();
        if (mode == false)
            sqlAdd();
        else
            sqlEdit();
        finish();
    }

    public void sqlAdd() {
        // Temp DbHelper
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into TestTable2 (textDate, textBody, textSub) values (?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String data = sdf.format(new Date());

        String[] arg1 = {data, lights.SetHighlight(m_context), m_title};

        db.execSQL(sql, arg1);
    }
    public void sqlEdit(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update TestTable2 set textSub = ?, textBody = ? where idx=" + idx;

        String[] arg1 = {m_title, m_context};

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
