package com.sd.payroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private CalculateSalary calcS = new CalculateSalary();

    private EditText
            etSalaryNorm,
            etHourRate,
            etHourFact,
            etHourHoliday,
            etHourNight,
            etChildren;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        etSalaryNorm = view.findViewById(R.id.etSalary);
        if (calcS.getSalaryNorm() != 0)
            etSalaryNorm.setText(String.valueOf(calcS.getSalaryNorm()));
        etHourRate = view.findViewById(R.id.etHourRate);
        if (calcS.getHourRate() != 0)
            etHourRate.setText(String.valueOf(calcS.getHourRate()));
        etHourFact = view.findViewById(R.id.etHourFact);
        if (calcS.getHourFact() != 0)
            etHourFact.setText(String.valueOf(calcS.getHourFact()));
        etHourHoliday = view.findViewById(R.id.etHourHoliday);
        if (calcS.getHourHoliday() != 0)
            etHourHoliday.setText(String.valueOf(calcS.getHourHoliday()));
        etHourNight = view.findViewById(R.id.etHourNight);
        if (calcS.getHourNight() != 0)
            etHourNight.setText(String.valueOf(calcS.getHourNight()));
        etChildren = view.findViewById(R.id.etChildren);
        if (calcS.getChildren() != 0)
            etChildren.setText(String.valueOf(calcS.getChildren()));

        return view;
    }

    private void apply() {
        /**     Проверяем EditText и присваеваем не введеным значениям 0 */

        try {
            calcS.setSalaryNorm(Double.parseDouble(String.valueOf(etSalaryNorm.getText()))); //Норма часов
        } catch (NumberFormatException e) {
            return;
        }
        try {
            calcS.setHourRate(Double.parseDouble(String.valueOf(etHourRate.getText()))); //Норма часов
        } catch (NumberFormatException e) {
            return;
        }
        try {
            calcS.setHourFact(Double.parseDouble(String.valueOf(etHourFact.getText()))); //Отработанно часов
        } catch (NumberFormatException e) {
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
    }


    @Override
    public void onPause() {
        super.onPause();
        apply();
    }
}
