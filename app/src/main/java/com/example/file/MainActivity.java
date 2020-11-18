package com.example.file;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 100;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textInformation);

        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                REQUEST_CODE
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {

            boolean isGranted = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    isGranted = true;
                } else if (result == PackageManager.PERMISSION_DENIED) {
                    isGranted = false;
                }
            }

            if (isGranted) {
                File publicDirectory = getDownloadPublicDirectory();
                readFromExternalFile(publicDirectory.getAbsolutePath(), "ДЗ.txt");
            }
        }
    }

    private void readFromExternalFile(String dir, String filename) {
        try {
            File file = new File(dir, filename);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    stringBuffer.append(read + "\n");
                }
                textView.setText(stringBuffer.toString());
            } else {
                textView.setText("Файл '" + file.getName() + "' не найден!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDownloadPublicDirectory() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return file;
    }

}