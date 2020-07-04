package com.sd.payroll;

public class CalculateSalary {

    private static double
            salaryNorm,
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
        return getHourFact() - getHourRate();
    }

    double salary() {
        return salaryNorm / getHourRate() * getHourFact();
    }


    double salaryOvertime() {
        return (2.0D * salaryNorm / getHourRate()) * 0.75D + (salaryNorm / getHourRate() * (hourOvertime() - 2.0D));
    }

    double getHourHoliday() {
        return hourHoliday;
    }

    double salaryHoliday() {
        return salaryNorm / getHourRate() * getHourHoliday();
    }

    double getChildren() {
        return children;
    }

    double withheld() {
        return (getAccrued() - getChildren()) * Resurce.NDFL_RITE;
    }

    double getHourNight() {
        return hourNight;
    }

    double getHourRate() {
        return hourRate;
    }

    double getSalaryNight() {
        return salaryNorm / getHourRate() * getHourNight() * 0.2D;

    }

    double getHourFact() {
        return hourFact;
    }

    double getSalaryNorm() {
        return salaryNorm;
    }

    void setSalaryNorm(double salaryNorm) {
        CalculateSalary.salaryNorm = salaryNorm;
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
