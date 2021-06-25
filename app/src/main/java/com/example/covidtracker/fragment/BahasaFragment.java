package com.example.covidtracker.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.covidtracker.R;
import com.example.covidtracker.preference.UserPreference;
import com.example.covidtracker.reminder.DailyReminder;

import java.util.Locale;

import static com.example.covidtracker.utils.TimeUtils.getArrayTime;

public class BahasaFragment extends Fragment implements View.OnClickListener{

    private UserPreference userPreference;
    private String selectedLanguage;
    private RadioButton rbId, rbEn;

    public BahasaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bahasa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreference = new UserPreference(getContext());

        RadioGroup rgLang = view.findViewById(R.id.rg_lang);
        rgLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_id){
                    selectedLanguage = "in";
                } else if(checkedId == R.id.rb_en){
                    selectedLanguage = "en";
                }
            }
        });

        rbId = view.findViewById(R.id.rb_id);
        rbEn = view.findViewById(R.id.rb_en);

        loadLanguageFromPreference();

        Button btnSimpan = view.findViewById(R.id.btn_simpan);
        Button btnBatal = view.findViewById(R.id.btn_batal);
        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_simpan){
            userPreference.setLanguage(selectedLanguage);
            Log.d("tagg", selectedLanguage);
            loadLanguageFromPreference();
            if (getActivity() != null) getActivity().recreate();
        } else if (id == R.id.btn_batal){
            loadLanguageFromPreference();
        }
    }

    private void loadLanguageFromPreference(){
        selectedLanguage = userPreference.getLanguage();
        setLocale(getActivity(), selectedLanguage);
        if (selectedLanguage.equals("in")) rbId.setChecked(true);
        else if (selectedLanguage.equals("en")) rbEn.setChecked(true);
    }

    private void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}