package com.company;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        // write your code here
        float
                salary,
                hourRate,// = (float) 136, //норма час.
                hourFact,// = (float) 128, // отработанно час.
                hourNight,// = (float) 26, // ночные час.
                salaryNorm = (float) 28750, // оклад
                hourHoliday,// = (float) 11.5, // праздничные час.
                children = 1400,
                hourOvertime,
                salaryNight = 0,
                salaryHoliday = 0,
                salaryOvertime = 0,
                wage,
                NDFL;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Норма часов:        ");
        hourRate = scanner.nextFloat();
        System.out.print("Отработанно часов:  ");
        hourFact = scanner.nextFloat();
        System.out.print("Праздничные час.:   ");
        hourHoliday = scanner.nextFloat();
        System.out.print("Ночные час.:        ");
        hourNight = scanner.nextFloat();

        hourOvertime = hourFact - hourRate;

        System.out.println("Норма:          " + hourRate + "/" + (hourRate / 8));
        System.out.println("Отработано:     " + hourFact + "/" + (hourFact / 8));

        salary = salaryNorm / hourRate * hourFact;
        System.out.println("Оклад:          " + salary);

        if (hourOvertime > 0) {
            salaryOvertime = (float) ((2 * salaryNorm / hourRate * 0.75) + (salaryNorm / hourRate * (hourOvertime - 2)));
            System.out.println("Сверхурочные:   " + salaryOvertime);
        }

        if (hourHoliday > 0) {
            salaryHoliday = salaryNorm / hourRate * hourHoliday;
            System.out.println("Праздники:      " + salaryHoliday);
        }

        if (hourNight > 0) {
            salaryNight = (float) (salaryNorm / hourRate * hourNight * 0.2);
            System.out.println("Ночные:         " + salaryNight);
        }

        wage = salary + salaryNight + salaryHoliday + salaryOvertime;
        NDFL = (float) ((wage - children) * 0.13);
        System.out.println("ИТОГО:          " + wage);
        System.out.println("НДФЛ:           " + NDFL);
        System.out.println("Чистыми:        " + (wage - NDFL));


        // ЗП = О/Дк * Дф + П - Н - У
        // О - оклад
        // Дк - дни по производственному календарю
        // П - примии
        // Н - Налог
        // У - удержания


    }
}
