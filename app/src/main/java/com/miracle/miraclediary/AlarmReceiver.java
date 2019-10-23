package com.miracle.miraclediary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("aaa",""+ intent.getFlags());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, SplashActivity.class);
        int flag = -1;
        {
            try {
                flag = intent.getIntExtra("flag",-1);
            } catch (Exception e) {
            }
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        DBManager.getInstance().setNotification(true);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

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
        int random = new Random().nextInt(goodWords.length)+1;
        if(random>goodWords.length){
            random = goodWords.length-1;
        }
        String temp_str = flag == 0 ? "아침" : "저녁";
        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker(temp_str + " 일기를 작성할 시간이에요!")
                .setContentTitle(temp_str + " 일기를 작성할 시간이에요.\n산뜻한 " + temp_str + "을 시작해봐요!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(goodWords[random]))
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());

            Calendar nextNotifyTime = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            //nextNotifyTime.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            Date currentDateTime = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context.getApplicationContext(),"다음 추가 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}
