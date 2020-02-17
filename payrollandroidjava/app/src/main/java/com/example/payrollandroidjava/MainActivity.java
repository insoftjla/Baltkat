package com.example.payrollandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText etHourRate;
    EditText etHourFact;
    EditText etHourHoliday;
    EditText etHourNight;
    TextView tvPayRoll;
    TextView tvHourRate;
    TextView tvHourFact;
    TextView tvHourHoliday;
    TextView tvHourNight;

    TextView tvSalary;
    TextView tvSalaryOvertime;
    TextView tvSalaryHoliday;
    TextView tvSalaryNight;
    TextView tvWage;
    TextView tvNDFL;
    TextView tvLoose;

    Button btnCalculate;
    CheckBox chbChildren;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPayRoll = findViewById(R.id.tvPayRoll);
        tvPayRoll.setText(Language.TVPAYROLL);
        tvHourRate = findViewById(R.id.tvHourRate);
        tvHourRate.setText(Language.TVHOURRATE);
        tvHourFact = findViewById(R.id.tvHourFact);
        tvHourFact.setText(Language.TVHOURFACT);
        tvHourHoliday = findViewById(R.id.tvHourHoliday);
        tvHourHoliday.setText(Language.TVHOURHOLIDAY);
        tvHourNight = findViewById(R.id.tvHourNight);
        tvHourNight.setText(Language.TVHOURNIGHT);

        etHourRate = findViewById(R.id.etHourRate);
        //etHourRate.setHint("175");
        etHourFact = findViewById(R.id.etHourFact);
        //etHourFact.setHint("183");
        etHourHoliday = findViewById(R.id.etHourHoliday);
        //etHourHoliday.setHint("0");
        etHourNight = findViewById(R.id.etHourNight);
        //etHourNight.setHint("66");

        tvSalary = findViewById(R.id.tvSalary);
        tvSalaryOvertime = findViewById(R.id.tvSalaryOvertime);
        tvSalaryHoliday = findViewById(R.id.tvSalaryHoliday);
        tvSalaryNight = findViewById(R.id.tvSalaryNight);
        tvWage = findViewById(R.id.tvWage);
        tvNDFL = findViewById(R.id.tvNDFL);
        tvLoose = findViewById(R.id.tvLoose);

        chbChildren = findViewById(R.id.chbChildren);
        chbChildren.setText(Language.CHBCHILDREN);
        chbChildren.setChecked(true);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setText(Language.BTNCALCULATE);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    public void calculate() {

        float
                salaryNorm = 28750.0F,
                children = 0.0F,
                salaryNight = 0.0F,
                salaryHoliday = 0.0F,
                salaryOvertime = 0.0F,
                hourRate,
                hourFact,
                hourHoliday,
                hourNight,
                hourOvertime,
                salary,
                wage,
                NDFL;

        Toast toastHourRate = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ НОРМУ ЧАСОВ" , Toast.LENGTH_SHORT);
        Toast toastHourFact = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ КОЛИЧЕСТВО ОТРАБОТАННЫХ ЧАСОВ" , Toast.LENGTH_SHORT);

        try {
            hourRate = Float.parseFloat(String.valueOf(etHourRate.getText())); //Норма часов
        } catch (NumberFormatException e) {
            toastHourRate.show();
            return;
        }
        try {
            hourFact = Float.parseFloat(String.valueOf(etHourFact.getText())); //Отработанно часов
        }catch (NumberFormatException e){
            toastHourFact.show();
            return;
        }
        try {
            hourHoliday = Float.parseFloat(String.valueOf(etHourHoliday.getText())); //Праздничные час.
        }catch (NumberFormatException e){
            hourHoliday = 0.0F;
        }
        try {
            hourNight = Float.parseFloat(String.valueOf(etHourNight.getText())); //Ночные часы
        }catch (NumberFormatException e){
            hourNight = 0.0F;
        }
        hourOvertime = hourFact - hourRate;

        salary = salaryNorm / hourRate * hourFact;
        tvSalary.setText(Language.TVSALARY + salary);

        if (hourOvertime > 0.0F) {
            salaryOvertime = (2.0F * salaryNorm / hourRate) * 0.75F + (salaryNorm / hourRate * (hourOvertime - 2.0F));
            tvSalaryOvertime.setText(Language.SALARYOVERTIME + salaryOvertime);
        } else tvSalaryOvertime.setText(Language.SALARYOVERTIME + "NULL");

        if (hourHoliday > 0.0F) {
            salaryHoliday = salaryNorm / hourRate * hourHoliday;
            tvSalaryHoliday.setText(Language.SALARYHOLIDAY + salaryHoliday);
        } else tvSalaryHoliday.setText(Language.TVHOURHOLIDAY + "NULL");

        if (hourNight > 0.0F) {
            salaryNight = (salaryNorm / hourRate * hourNight) * 0.2F;
            tvSalaryNight.setText(Language.SALARYNIGHT + salaryNight);
        } else tvSalaryNight.setText(Language.SALARYNIGHT + "NULL");

        wage = salary + salaryNight + salaryHoliday + salaryOvertime;
        if (chbChildren.isChecked())
            children = 1400.0F;

        NDFL = (wage - children) * 0.13F;
        tvWage.setTextColor(Color.MAGENTA);
        tvWage.setText("ВСЕГО: " + wage);
        tvNDFL.setTextColor(Color.RED);
        tvNDFL.setText("НДФЛ: " + NDFL);
        tvLoose.setTextColor(Color.BLUE);
        tvLoose.setText("ИТОГО: " + (wage - NDFL));

    }
}
