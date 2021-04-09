package com.example.covidtracker.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.covidtracker.R;
import com.example.covidtracker.activity.MainActivity;
import com.example.covidtracker.preference.UserPreference;
import com.example.covidtracker.reminder.DailyReminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.covidtracker.utils.TimeUtils.TIME_FORMAT;
import static com.example.covidtracker.utils.TimeUtils.getArrayTime;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private final String TIME_PICKER_TAG = "time_picker";
    private String reminderTime;
    private TimePicker timePicker;;
    private UserPreference userPreference;
    private MainActivity context;

    public NotificationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreference = new UserPreference(getContext());

        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
                reminderTime = simpleDateFormat.format(calendar.getTime());
            }
        });

        loadReminderTimeFromPreference();

        Button btnSimpan = view.findViewById(R.id.btn_simpan);
        Button btnBatal = view.findViewById(R.id.btn_batal);
        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_simpan){
            userPreference.setReminderTime(reminderTime); // Simpan waktu reminder
            new DailyReminder().setReminder(getContext(), reminderTime); // Hidupkan reminder
            Toast.makeText(getContext(), "Pengaturan reminder berhasil disimpan", LENGTH_LONG).show();
        } else if (id == R.id.btn_batal){
            loadReminderTimeFromPreference();
        }
    }

    private void loadReminderTimeFromPreference(){
        reminderTime = userPreference.getReminderTime();
        int[] arrayTime = getArrayTime(reminderTime);
        timePicker.setCurrentHour(arrayTime[0]);
        timePicker.setCurrentMinute(arrayTime[1]);
        new DailyReminder().setReminder(getContext(), reminderTime);
    }
}