package com.miracle.miraclediary;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity implements OnClickListener {
    TextView t1;
    TextView t2;
    boolean a;
    private int myHour, myMinute, myHour2, myMinute2;
    private TimePickerDialog.OnTimeSetListener myTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            if (hourOfDay != 0 && minute != 0) {
                TextView temp = null;
                String temp_str;
                if (!a) {
                    temp = t1;
                    temp_str = "아침 : ";
                } else {
                    temp = t2;
                    temp_str = "저녁 : ";
                }
                temp.setText(temp_str + hourOfDay + ":" + minute);
            }


        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        a = true;
    }

    public void timeSet(View v) {
        final Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.timepickerbutton:
                a = false;
                break;
            case R.id.timepickerbutton2:
                a = true;
                break;
        }

        Toast.makeText(MainActivity.this,
                "- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG).show();
        Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener,
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        dlgTime.show();
    }

    public void onClick(View v) {


    }
}