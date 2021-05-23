package com.josrangel.generatorcsv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Tutorial
 * https://stackoverflow.com/questions/11341931/how-to-create-a-csv-on-android
 */
public class MainActivity extends AppCompatActivity {
    private String csvName;
    private static final int CODE_PERMISION_STORAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        csvName = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv
    }

    public void generateCsv(View v) {
        checkPermissionStorage();
    }

    private void checkPermissionStorage() {
        int statusPermision = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (statusPermision == PackageManager.PERMISSION_GRANTED) {
            makeCVS();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODE_PERMISION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_PERMISION_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionStorage();
                } else {
                    showMessage(getResources().getString(R.string.fail_permission));
                }
                break;
            default:
        }
    }
    private void makeCVS(){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csvName));

            List<String[]> data = new ArrayList<>();
            data.add(new String[]{getString(R.string.country), getString(R.string.capital)});
            data.add(new String[]{"India", "New Delhi"});
            data.add(new String[]{"United States", "Washington D.C"});
            data.add(new String[]{"Germany", "Berlin"});
            writer.writeAll(data); // data is adding to csv
            writer.close();
            showMessage(getString(R.string.correct_file, csvName));
            startIntentCVS();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error: " + e.toString());
        }
    }
    /** Tutorial
     * https://sudarmuthu.com/blog/sharing-content-in-android-using-action_send-intent/
     * https://guides.codepath.com/android/Sharing-Content-with-Intents
     */
    private void startIntentCVS() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String uriPath=csvName;
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uriPath));
        Log.i("uirPath",uriPath);
        shareIntent.setType("text/csv");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.cvs_file)));
    }

    private void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}