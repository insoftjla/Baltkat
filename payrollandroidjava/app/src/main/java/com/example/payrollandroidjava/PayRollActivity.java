package com.example.payrollandroidjava;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class PayRollActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sPref;
    CalculateSalary calcS = new CalculateSalary();
    DecimalFormat decimalFormat;
    Calendar calendar;
    SimpleDateFormat dataFormat;

    final String
            PREF_HOUR_RATE =    "HOUR_RATE",
            PREF_HOUR_FACT =    "HOUR_FACT",
            PREF_HOUR_HOLIDAY = "HOUR_HOLIDAY",
            PREF_HOUR_NIGHT =   "HOUR_NIGHT",
            PREF_CHILDREN =     "CHILDREN";

    EditText
            etHourRate,
            etHourFact,
            etHourHoliday,
            etHourNight,
            etChildren;

    TextView
            tvCalendar,
            tvSalary,
            tvSalaryNorm,
            tvSalaryOvertime,
            tvSalaryHoliday,
            tvSalaryNight,
            tvAccrued,
            tvWithheld,
            tvNDFL,
            tvLoose,
            tvChildrenResult;

    Button btnCalculate;

    @SuppressLint({"SetTextI18n", "WrongViewCast", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payroll);
        calcS = new CalculateSalary();

        calendar = new GregorianCalendar();
        Locale locale = new Locale("ru");
        dataFormat = new SimpleDateFormat("dd MMM yyyy", locale);
        tvCalendar = findViewById(R.id.tvCalendar);
        tvCalendar.setText(dataFormat.format(calendar.getTime()));

        decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        tvSalaryNorm = findViewById(R.id.tvSalaryNorm);
        tvSalaryNorm.setText(Resurce.SALARY_NORM + " P.");

        etHourRate = findViewById(R.id.etHourRate);
        etHourFact = findViewById(R.id.etHourFact);
        etHourHoliday = findViewById(R.id.etHourHoliday);
        etHourNight = findViewById(R.id.etHourNight);
        etChildren = findViewById(R.id.etChildren);

        tvSalary = findViewById(R.id.tvSalary);
        tvSalaryOvertime = findViewById(R.id.tvSalaryOvertime);
        tvSalaryHoliday = findViewById(R.id.tvSalaryHoliday);
        tvSalaryNight = findViewById(R.id.tvSalaryNight);
        tvAccrued = findViewById(R.id.tvWage);
        tvWithheld = findViewById(R.id.tvWithheld);
        tvNDFL = findViewById(R.id.tvNDFL);
        tvChildrenResult = findViewById(R.id.tvChildrenResult);
        tvLoose = findViewById(R.id.tvLoose);

        loadPref();
        calculate();

        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        calculate();
        setsPref();
    }

    @SuppressLint("SetTextI18n")
    public void calculate() {

        Toast toastHourRate = Toast.makeText(getApplicationContext(), Resurce.TOESTHOURRATE, Toast.LENGTH_SHORT);
        Toast toastHourFact = Toast.makeText(getApplicationContext(), Resurce.TOESTHOURFACT, Toast.LENGTH_SHORT);

/**     Проверяем EditText и присваеваем не введеным значениям 0 */
        try {
            calcS.setHourRate(Double.parseDouble(String.valueOf(etHourRate.getText()))); //Норма часов
        } catch (NumberFormatException e) {
            toastHourRate.show();
            return;
        }
        try {
            calcS.setHourFact(Double.parseDouble(String.valueOf(etHourFact.getText()))); //Отработанно часов
        } catch (NumberFormatException e) {
            toastHourFact.show();
            return;
        }
        try {
            calcS.setHourHoliday(Double.parseDouble(String.valueOf(etHourHoliday.getText()))); //Праздничные час.
        } catch (NumberFormatException e) {
            calcS.setHourHoliday(0);
        }
        try {
            calcS.setHourNight(Double.parseDouble(String.valueOf(etHourNight.getText()))); //Ночные часы
        } catch (NumberFormatException e) {
            calcS.setHourNight(0);
        }

        try {
            calcS.setChildren(Double.parseDouble(String.valueOf(etChildren.getText()))); // Вычет
        } catch (NumberFormatException e) {
            calcS.setChildren(0);
        }

/**      Проверяем переработку, если есть считаем и выводим резудьтат */
        if (calcS.hourOvertime() > 0) {
            tvSalaryOvertime.setText(decimalFormat.format(calcS.salaryOvertime()) + Resurce.CURRENCY);
        } else {
            tvSalaryOvertime.setText("NULL");
        }

/**      Проверяем праздничные, если есть считаем и выводим резудьтат */
        if (calcS.hourHoliday() > 0) {
            tvSalaryHoliday.setText(decimalFormat.format(calcS.salaryHoliday()) + Resurce.CURRENCY);
        } else {
            tvSalaryHoliday.setText("NULL");
        }

/**      Проверяем ночные, если есть считаем и выводим резудьтат */
        if (calcS.hourNight() > 0) {
            tvSalaryNight.setText(decimalFormat.format(calcS.getSalaryNight()) + Resurce.CURRENCY);
        } else {
            tvSalaryNight.setText("NULL");
        }

        tvAccrued.setText(decimalFormat.format(calcS.getAccrued()) + Resurce.CURRENCY); // Начислено
        tvSalary.setText(decimalFormat.format(calcS.salary()) + Resurce.CURRENCY); // Оклад
        tvChildrenResult.setText(decimalFormat.format(calcS.children())); // Вычет на ребенка
        tvWithheld.setText(decimalFormat.format(calcS.withheld()) + Resurce.CURRENCY); // Удержано
        tvNDFL.setText(decimalFormat.format(calcS.getAccrued() * Resurce.NDFL_RITE) + Resurce.CURRENCY); // НДФЛ
        tvLoose.setText(decimalFormat.format(calcS.getAccrued() - calcS.getAccrued() * Resurce.NDFL_RITE) + Resurce.CURRENCY); // Итого
    }

    void setsPref() {
        if (String.valueOf(etHourRate.getText()).equals("") || String.valueOf(etHourFact.getText()).equals(""))
            return;

        float
                prefLong_HourRite = Float.parseFloat(String.valueOf(etHourRate.getText())),
                prefLong_HourFact = Float.parseFloat(String.valueOf(etHourFact.getText())),
                prefLong_HourHoliday = 0,
                prefLong_HourNight = 0,
                prefLong_Children = 0;
        if (!String.valueOf(etHourHoliday.getText()).equals(""))
            prefLong_HourHoliday = Float.parseFloat(String.valueOf(etHourHoliday.getText()));
        if (!String.valueOf(etHourNight.getText()).equals(""))
            prefLong_HourNight = Float.parseFloat(String.valueOf(etHourNight.getText()));
        if (!String.valueOf(etChildren.getText()).equals(""))
            prefLong_Children = Float.parseFloat(String.valueOf(etChildren.getText()));

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putFloat(PREF_HOUR_RATE, prefLong_HourRite);
        editor.putFloat(PREF_HOUR_FACT, prefLong_HourFact);
        editor.putFloat(PREF_HOUR_HOLIDAY, prefLong_HourHoliday);
        editor.putFloat(PREF_HOUR_NIGHT, prefLong_HourNight);
        editor.putFloat(PREF_CHILDREN, prefLong_Children);
        editor.apply();
    }

    void loadPref() {
        sPref = getPreferences(MODE_PRIVATE);
        float d;
        d = sPref.getFloat(PREF_HOUR_RATE, 0);
        if (d != 0)
            etHourRate.setText(String.valueOf(d));
        d = sPref.getFloat(PREF_HOUR_FACT, 0);
        if (d != 0)
            etHourFact.setText(String.valueOf(d));
        d = sPref.getFloat(PREF_HOUR_HOLIDAY, 0);
        if (d != 0)
            etHourHoliday.setText(String.valueOf(d));
        d = sPref.getFloat(PREF_HOUR_NIGHT, 0);
        if (d != 0)
            etHourNight.setText(String.valueOf(d));
        d = sPref.getFloat(PREF_CHILDREN, 0);
        if (d != 0)
            etChildren.setText(String.valueOf(d));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setsPref();
    }

}
