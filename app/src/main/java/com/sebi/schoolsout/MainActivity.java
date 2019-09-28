package com.sebi.schoolsout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public Button button;
    public Timer timer;
    public boolean isToggled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isToggled){
                    isToggled = true;
                    timer = new Timer();
                    final TimerTask timerTask = new TimerTask() {

                        @Override
                        public void run() {
                            updateText();
                        }

                    };
                    timer.scheduleAtFixedRate(timerTask, 0, 100);
                } else {
                    isToggled = false;
                    timer.cancel();
                    button.setText("");
                }
            }
        });
    }

    void updateText() {
        button.setText(getDiff());
    }

    Date getCurrentTime() {
        String now = new Date().toString();
        SimpleDateFormat nowFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss");
        Date TimeNow = new Date();
        try {
            Date DateNow = nowFormat.parse(now);
            TimeNow = myFormat.parse(myFormat.format(DateNow));

        } catch (Exception e) {
            System.out.println("fucked up");
        }
        return TimeNow;
    }

    Date getEndTime() {
        Date today = new Date();
        Date then = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss");
        calendar.setTime(today);
        try {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                then = myFormat.parse("16:20:00");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                then = myFormat.parse("14:00:00");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                then = myFormat.parse("14:00:00");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                then = myFormat.parse("14:00:00");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                then = myFormat.parse("12:10:00");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                then = myFormat.parse("23:59:59");
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                then = myFormat.parse("23:59:59");
            }
        } catch (Exception e) {
            System.out.println("fucked up EndTime");
        }
        return then;
    }

    String getDiff(){
        long diffInMillies = Math.abs(getEndTime().getTime() - getCurrentTime().getTime());
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diffInMillies),
                TimeUnit.MILLISECONDS.toMinutes(diffInMillies) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMillies)),
                TimeUnit.MILLISECONDS.toSeconds(diffInMillies) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffInMillies)));
    }


}
