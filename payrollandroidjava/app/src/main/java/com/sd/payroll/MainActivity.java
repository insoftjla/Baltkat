package com.sd.payroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    CalculateSalary calculateSalary;

    static final String
            PAYROLL_PREFERENCES = "payrollsettings";
    static final String
            SALARY_NORM = "salarynorm",
            HOUR_RATE = "hourrate",
            HOUR_FACT = "hourfact",
            HOUR_HOLIDAY = "hourholiday",
            HOUR_NIGHT = "hournight",
            CHILDREN = "children";
    private SharedPreferences preferences;

    TextView tvCalendar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateSalary = new CalculateSalary();

        /**  Загружаем настройки */
        preferences = getSharedPreferences(PAYROLL_PREFERENCES, Context.MODE_PRIVATE);
        loadPref();

        Calendar calendar = new GregorianCalendar();
        Locale locale = new Locale("ru");
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd MMM yyyy", locale);
        tvCalendar = findViewById(R.id.tvCalendar);
        tvCalendar.setText(dataFormat.format(calendar.getTime()));

        /**  Устанавливаем фрагмент по умолчанию */
        setFragment(new PayrollFragment());

        /**  Действия по смене фрагментов в навигационном меню */
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.payroll:
                                setPref();
                                setFragment(new PayrollFragment());
                                break;
                            case R.id.settings:
                                setFragment(new SettingFragment());
                                break;
                        }
                        return false;
                    }
                });
    }

    void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_place, fragment);
        fragmentTransaction.commit();
    }

    public void setPref() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SALARY_NORM, String.valueOf(calculateSalary.getSalaryNorm()));
        editor.putString(HOUR_RATE, String.valueOf(calculateSalary.getHourRate()));
        editor.putString(HOUR_FACT, String.valueOf(calculateSalary.getHourFact()));
        editor.putString(HOUR_HOLIDAY, String.valueOf(calculateSalary.getHourHoliday()));
        editor.putString(HOUR_NIGHT, String.valueOf(calculateSalary.getHourNight()));
        editor.putString(CHILDREN, String.valueOf(calculateSalary.getChildren()));
        editor.apply();
    }

    void loadPref() {
            calculateSalary.setSalaryNorm(Double.parseDouble(preferences.getString(SALARY_NORM, "0")));
            calculateSalary.setHourRate(Double.parseDouble(preferences.getString(HOUR_RATE, "0")));
            calculateSalary.setHourFact(Double.parseDouble(preferences.getString(HOUR_FACT, "0")));
            calculateSalary.setHourHoliday(Double.parseDouble(preferences.getString(HOUR_HOLIDAY, "0")));
            calculateSalary.setHourNight(Double.parseDouble(preferences.getString(HOUR_NIGHT, "0")));
            calculateSalary.setChildren(Double.parseDouble(preferences.getString(CHILDREN, "0")));
    }

    @Override
    protected void onStop() {
        super.onStop();
        setPref();
    }
}
