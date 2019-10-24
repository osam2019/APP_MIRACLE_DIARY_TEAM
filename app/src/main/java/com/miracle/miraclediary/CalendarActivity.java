package com.miracle.miraclediary;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarActivity extends BaseCustomBarActivity {
    private static final String TAG = "CalendarActivity";
    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setDateTextAppearance(R.style.CalendarWidgetHeader);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        String[] result = {"2017,03,18", "2017,04,18", "2017,05,18", "2017,06,18"};
        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ArrayList dates = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);
                ArrayList contexts = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.CONTEXT);
                ArrayList titles = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.TEXTSUB);
                final Calendar cal = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String targetDate = dateFormat.format(date.getDate().getTime());
                String contextStr = "";
                ArrayList<String> list = new ArrayList<>();

                for(int i = 0, count = 0; i < dates.size(); i++) {
                    if(targetDate.equals(dates.get(i))) {
                        count++;
                        contextStr = titles.get(i) + "\n";
                        //contextStr += contexts.get(i);

                        list.add(contextStr);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarActivity.this,android.R.layout.simple_list_item_1,list);
                ListView conte = findViewById(R.id.list);
                conte.setAdapter(adapter);
//                TextView context = findViewById(R.id.calendar_context);
//                context.setText(Html.fromHtml(contextStr));
            }
        });
    }

    @Override
    protected void Init() {
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            List<CalendarDay> dates = new ArrayList<>();
            DBManager.getInstance().updateDB("TestTable");
            DBManager.getInstance().updateDB("TestTable2");
            ArrayList date = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);
            for (int i = 0; i < date.size(); i++) {
                String[] split = date.get(i).toString().split("-");
                int year = Integer.parseInt(split[0]);
                int month = Integer.parseInt(split[1]);
                int dayy = Integer.parseInt(split[2]);
                calendar.set(year, month - 1, dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.parseColor("#4CAF50"), calendarDays, CalendarActivity.this));
        }
    }
}