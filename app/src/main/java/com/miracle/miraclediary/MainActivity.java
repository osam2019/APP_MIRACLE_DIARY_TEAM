package com.miracle.miraclediary;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {



    private int  myHour, myMinute,myHour2, myMinute2;



    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button timePickerButton = (Button) findViewById(R.id.timepickerbutton);
        Button timePickerButton2 = (Button) findViewById(R.id.timepickerbutton2);

        timePickerButton.setOnClickListener(this);
        timePickerButton2.setOnClickListener(this);

    }



    public void onClick(View v) {



        final Calendar c = Calendar.getInstance();



        switch (v.getId()) {

            case R.id.timepickerbutton:

                myHour = c.get(Calendar.HOUR_OF_DAY);

                myMinute = c.get(Calendar.MINUTE);

                Toast.makeText(MainActivity.this,

                        "- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG)

                        .show();

                Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener,

                        myHour, myMinute, false);

                dlgTime.show();

                break;

            case R.id.timepickerbutton2:
                myHour2 = c.get(Calendar.HOUR_OF_DAY);

                myMinute2 = c.get(Calendar.MINUTE);

                Toast.makeText(MainActivity.this,

                        "- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG)

                        .show();

                Dialog dlgTime2 = new TimePickerDialog(this, myTimeSetListener,

                        myHour2, myMinute2, false);

                dlgTime2.show();
                break;


            default:

                break;

        }

    }

    private TimePickerDialog.OnTimeSetListener myTimeSetListener

            = new TimePickerDialog.OnTimeSetListener() {



        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String time = "Hour: " + String.valueOf(hourOfDay) + "\n"

                    + "Minute: " + String.valueOf(minute);

            Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();

        }

    };



}