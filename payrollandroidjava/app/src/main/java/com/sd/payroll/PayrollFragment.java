package com.sd.payroll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class PayrollFragment extends Fragment {

    private CalculateSalary calcS = new CalculateSalary();
    private DecimalFormat decimalFormat;

    private Toast
            toastHourRate,
            toastHourFact;

    private TextView
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


    @SuppressLint({"SetTextI18n", "ShowToast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payroll, container, false);

        toastHourRate = Toast.makeText(getActivity(), Resurce.TOEST_HOURRATE, Toast.LENGTH_SHORT);
        toastHourFact = Toast.makeText(getActivity(), Resurce.TOEST_HOURFACT, Toast.LENGTH_SHORT);

        calcS = new CalculateSalary();

        tvSalaryNorm = view.findViewById(R.id.tvSalaryNorm);

        tvSalary = view.findViewById(R.id.tvSalary);
        tvSalaryOvertime = view.findViewById(R.id.tvSalaryOvertime);
        tvSalaryHoliday = view.findViewById(R.id.tvSalaryHoliday);
        tvSalaryNight = view.findViewById(R.id.tvSalaryNight);
        tvAccrued = view.findViewById(R.id.tvWage);
        tvWithheld = view.findViewById(R.id.tvWithheld);
        tvNDFL = view.findViewById(R.id.tvNDFL);
        tvChildrenResult = view.findViewById(R.id.tvChildrenResult);
        tvLoose = view.findViewById(R.id.tvLoose);

        calculate();

        return view;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculate() {

        if (calcS.getHourRate() <= 0) {
            toastHourRate.show();
            return;
        }
        if (calcS.getHourFact() <= 0) {
            toastHourFact.show();
            return;
        }



/**      Проверяем переработку, если есть считаем и выводим резудьтат */
        if (calcS.hourOvertime() > 0) {
            tvSalaryOvertime.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.salaryOvertime()) + Resurce.CURRENCY);
        } else {
            tvSalaryOvertime.setText("NULL");
        }

/**      Проверяем праздничные, если есть считаем и выводим резудьтат */
        if (calcS.getHourHoliday() > 0) {
            tvSalaryHoliday.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.salaryHoliday()) + Resurce.CURRENCY);
        } else {
            tvSalaryHoliday.setText("NULL");
        }

/**      Проверяем ночные, если есть считаем и выводим резудьтат */
        if (calcS.getHourNight() > 0) {
            tvSalaryNight.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getSalaryNight()) + Resurce.CURRENCY);
        } else {
            tvSalaryNight.setText("NULL");
        }

        tvSalaryNorm.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getSalaryNorm()) + Resurce.CURRENCY);
        tvAccrued.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getAccrued()) + Resurce.CURRENCY); // Начислено
        tvSalary.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.salary()) + Resurce.CURRENCY); // Оклад
        tvChildrenResult.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getChildren()) + Resurce.CURRENCY); // Вычет на ребенка
        tvWithheld.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.withheld()) + Resurce.CURRENCY); // Удержано
        tvNDFL.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getAccrued() * Resurce.NDFL_RITE) + Resurce.CURRENCY); // НДФЛ
        tvLoose.setText(String.format(Resurce.LOCALE, Resurce.STRING_FORMAT, calcS.getAccrued() - calcS.getAccrued() * Resurce.NDFL_RITE) + Resurce.CURRENCY); // Итого
    }

}
