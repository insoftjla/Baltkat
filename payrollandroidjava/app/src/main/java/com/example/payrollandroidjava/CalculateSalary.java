package com.example.payrollandroidjava;

public class CalculateSalary {

    private static double
            hourFact,
            hourHoliday,
            hourRate,
            children,
            hourNight;

    double getAccrued() {
        if (hourOvertime() > 0) {
            return salary() + getSalaryNight() + salaryHoliday() + salaryOvertime();
        } else return salary() + getSalaryNight() + salaryHoliday();
    }

    double hourOvertime() {
        return hourFact - hourRate;
    }

    double salary() {
        return Resurce.SALARY_NORM / getHourRate() * hourFact;
    }


    double salaryOvertime() {
        return (2.0D * Resurce.SALARY_NORM / getHourRate()) * 0.75D + (Resurce.SALARY_NORM / getHourRate() * (hourOvertime() - 2.0D));
    }

    double hourHoliday() {
        return hourHoliday;
    }

    double salaryHoliday() {
        return Resurce.SALARY_NORM / getHourRate() * hourHoliday();
    }

    double children() {
        return children;
    }

    double withheld() {
        return (getAccrued() - children()) * Resurce.NDFL_RITE;
    }

    double hourNight() {
        return hourNight;
    }

    double getHourRate() {
        return hourRate;
    }

    double getSalaryNight() {
        return Resurce.SALARY_NORM / getHourRate() * hourNight() * 0.2D;

    }

    void setHourFact(double hourFact) {
        CalculateSalary.hourFact = hourFact;
    }

    void setHourHoliday(double hourHoliday) {
        CalculateSalary.hourHoliday = hourHoliday;
    }

    void setHourRate(double hourRate) {
        CalculateSalary.hourRate = hourRate;
    }

    void setChildren(double children) {
        CalculateSalary.children = children;
    }

    void setHourNight(double hourNight) {
        CalculateSalary.hourNight = hourNight;
    }
}
