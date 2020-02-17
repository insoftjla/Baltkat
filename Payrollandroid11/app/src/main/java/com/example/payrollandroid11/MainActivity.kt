package com.example.payrollandroid11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val salaryNorm = 28750.0f
        val children = 1400.0f
        var salaryNight = 0.0f
        var salaryHoliday = 0.0f
        var salaryOvertime = 0.0f

        val hourRate = scanner.nextFloat()
        print("Отработанно часов:  ")
        val hourFact = scanner.nextFloat()
        print("Праздничные час.:   ")
        val hourHoliday = scanner.nextFloat()
        print("Ночные час.:        ")
        val hourNight = scanner.nextFloat()
        val hourOvertime = hourFact - hourRate
        println("Норма:          " + hourRate + "/" + hourRate / 8.0f)
        println("Отработано:     " + hourFact + "/" + hourFact / 8.0f)
        val salary = salaryNorm / hourRate * hourFact
        println("Оклад:          $salary")
        if (hourOvertime > 0.0f) {
            salaryOvertime =
                ((2.0f * salaryNorm / hourRate).toDouble() * 0.75 + (salaryNorm / hourRate * (hourOvertime - 2.0f)).toDouble()).toFloat()
            println("Сверхурочные:   $salaryOvertime")
        }

        if (hourHoliday > 0.0f) {
            salaryHoliday = salaryNorm / hourRate * hourHoliday
            tvHourHoliday
            println("Праздники:      $salaryHoliday")
        }

        if (hourNight > 0.0f) {
            salaryNight = ((salaryNorm / hourRate * hourNight).toDouble() * 0.2).toFloat()
            println("Ночные:         $salaryNight")
        }

        val wage = salary + salaryNight + salaryHoliday + salaryOvertime
        val NDFL = ((wage - children).toDouble() * 0.13).toFloat()
        println("ИТОГО:          $wage")
        println("НДФЛ:           $NDFL")
        println("Чистыми:        " + (wage - NDFL))

    }
}
