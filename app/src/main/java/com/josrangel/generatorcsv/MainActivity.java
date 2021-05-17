package com.josrangel.generatorcsv;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String csvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        csvName = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv

    }

    public void generateCsv(View v){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csvName));

            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"Country", "Capital"});
            data.add(new String[]{"India", "New Delhi"});
            data.add(new String[]{"United States", "Washington D.C"});
            data.add(new String[]{"Germany", "Berlin"});
            writer.writeAll(data); // data is adding to csv
            writer.close();
            showMessage("Your file was generate correctly in " + csvName);
            //callRead();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error: " + e.toString());
        }
    }

    private void showMessage(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

}