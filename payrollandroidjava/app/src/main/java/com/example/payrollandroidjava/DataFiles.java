package com.example.payrollandroidjava;

import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFiles {

    private static File dataFile;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static List<String[]> listData;


    static {
        listData = new ArrayList<>();
        dataFile = new File(Resurce.DATA_FILE_PATH);
        if (!(dataFile.exists() && dataFile.isFile())) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //long[] longTmp;
            String strTmp;
            bufferedReader = new BufferedReader(new FileReader(dataFile));
            while ((strTmp = bufferedReader.readLine()) != null){
                listData.add(strTmp.split(","));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List getListData() {
        return listData;
    }

    public String[] getLastData() {
        if (listData.size() != 0) {
            return listData.get(listData.size() - 1);
        } else return null;
    }

    public void writeData(float hourRate, float hourFact, float hourHoliday,float hourNight, float children){
        listData.add(new String[]{String.valueOf(hourRate), String.valueOf(hourFact), String.valueOf(hourHoliday), String.valueOf(hourNight), String.valueOf(children)});
    }

}
