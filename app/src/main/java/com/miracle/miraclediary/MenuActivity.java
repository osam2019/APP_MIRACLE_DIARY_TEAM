package com.miracle.miraclediary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends BaseCustomBarActivity {

    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        mar();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        DBManager.getInstance().updateDB("TestTable");
        DBManager.getInstance().updateDB("TestTable2");
        ArrayList dates = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);
        bar(dates);
        level(dates);
    }
    @Override
    protected void Init() {

    }

    public void goToMain(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToEdit(View v){

        Intent intent = new Intent(this, GoalActivity.class);
        startActivity(intent);
    }
    public void goToDiary(View v){

        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }
    void bar(ArrayList date){
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        String starttime;
        Date tempdate =new Date();
//        int compare;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date currentdate = new Date();
        try {
            tempdate =simpleDateFormat.parse("2200-12-31");
            for(int i = 0; i<date.size();i++)
            {
                if(tempdate.before((Date)date.get(i))){
                    tempdate = (Date)date.get(i);
                }
            }

        }
        catch (Exception e){
        }

        final int level = date.size() / 10;
        final int exp = (date.size() % 10) * 50;
        progressBar.setMax(500);

        final Timer exp_ani_timer = new Timer();
        TimerTask exp_ani_task = new TimerTask() {

            float t = -1.f;

            @Override
            public void run() {
                progressBar.setProgress(0);
                float max = (float) (Math.PI);
                while(t < max) {
                    t += 0.02;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(t < 0.f) continue;
                    progressBar.setProgress((int)(exp * (1 - (Math.cos(t) + 1) / 2)));
                }
                progressBar.setProgress(exp);
                exp_ani_timer.cancel();

            }
        };

        exp_ani_timer.schedule(exp_ani_task, 0);

//        if(compare<30){
//            progressBar.setMax(20);
//            progressBar.setProgress(date.size());
//        }else if(compare<60) {
//            progressBar.setMax(40);
//            progressBar.setProgress(date.size());
//        }
//        else {
//            progressBar.setMax(60);
//            progressBar.setProgress(date.size());
//        }

    }
    void mar(){
        String [] goodWords = {"습관이란 인간으로 하여금 그 어떤 일도 할 수 있게 만들어준다. -도스토옙스키",
                "가난과 부,실패와 성공은 모두 습관 때문이다. -중국 속담",
                "타고난 본성은 비슷하지만, 습관에 의해서 달라진다. -공자",
                "습관을 바꾸는 것만으로도 자신의 인생을 바꿀 수 있다. -윌리엄 제임스",
                "노력을 중단하는 것보다 더 위험한 것은 없다.그것은 습관을 잃는다.습관은 버리기는 쉽지만,다시 들이기는 어렵다. -빅토르 마리 위고",
                "생활은 습관이 짜낸 천에 불과하다. -아미엘",
                "우유부단한 것만이 습관화되어 있는 사람보다 더 비참한 사람은 없다. -제임스",
                "습관은 나무 껍질에 새겨 놓은 문자 같아서 그 나무가 자라남에 따라 확대된다. -새뮤얼 스마일스",
                "습관이 인간생활의 위대한 안내자이다. -데이비드 흄",
                "처음에는 우리가 습관을 만들지만, 그 다음에는 습관이 우리를 만든다. -존 드라이든",
                "성공한 삶의 가장 큰 비밀은 목표를 정하고 성취해내는 것이다. -핸리 포드",
                "습관은 인간의 삶에 있어 가장 높은 판사와도 같다.그러니 반드시 좋은 습관을 기르도록 노력하라. -프란시스 베이컨",
                "노력을 중단하는 것보다 더 위험한 것은 없다. 습관은 버리기는 쉽지만, 다시 들이기는 어렵다. -빅토르 마리 위고",
                "습관이란 인간으로 하여금 어떤 일이든지 하게 만든다. -도스토예프스키",
                "인간의 습관과 생활방식은 큰 가지의 잎사귀처럼 변하게 마련이다.어떤 잎은 떨어지고 새 잎이 난다. -단테"};
        // int random = new Random().nextInt(goodWords.length)-1;
        TextView text = (TextView)findViewById(R.id.textView);
        text.setText(goodWords[1] + "/" + goodWords[1 + 1] + "/" + goodWords[1 + 2]);
        text.setSelected(true);
    }
    void level(ArrayList date) {
        textView = (TextView) findViewById(R.id.levelView);
        textView.setText("Level " + date.size());
    }
}
