package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ReportHome extends AppCompatActivity {

    BarChart barChartdaily;
    private Context context;
    AppCompatButton monthly, yearly, weekly;
    File fileName, localFile;
    FirebaseUser mUser;
    TextView tvDate;
    String text;
    StorageReference storageReference;

    ArrayList<String> list = new ArrayList<>();
    ArrayList<Float> floatList = new ArrayList<>();

    File fileName1, fileNamea, fileNamem, fileNamea2;
    File localFile1, localFilea, localFilem, localFilea2;
    String text1, texta, textm, texta2;

    //for Scatter chart 1
    ArrayList<String> list1 = new ArrayList<>();
    ArrayList<Float> floatList1 = new ArrayList<>();
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<Float> floatLista = new ArrayList<>();

    //for Scatter chart 2
    ArrayList<String> listm = new ArrayList<>();
    ArrayList<Float> floatListm = new ArrayList<>();
    ArrayList<String> lista2 = new ArrayList<>();
    ArrayList<Float> floatLista2 = new ArrayList<>();

    ArrayList<IScatterDataSet> dataSets = new ArrayList<>();

    ArrayList<IScatterDataSet> dataSetsm = new ArrayList<>();

    private ScatterChart chart, chart1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_home);

        barChartdaily = (BarChart) findViewById(R.id.barChartDaily);
        monthly = findViewById(R.id.monthly);
        yearly = findViewById(R.id.yearly);
        weekly = findViewById(R.id.weekly);

        tvDate = (TextView) findViewById(R.id.tvDate);

        Date realDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = sdf.format(realDate);
        tvDate.setText(date);

        NavigationBar();

        //Writing data to file
        try {
            fileName = new File(getCacheDir() + "/reportDaily.txt");
            String line = "";
            FileWriter fw;
            fw = new FileWriter(fileName);
            BufferedWriter output = new BufferedWriter(fw);
            int size = dataValues1().size();
            for (int i = 0; i < size; i++) {
//                output.write(dataValues1().get(i).toString() + "\n");

                output.write("X "+ dataValues1().get(i).getX()+
                        " Y " + Arrays.toString(dataValues1().get(i).getYVals()) + "\n");
            }
            output.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        // Uploading file created to firebase storage
        StorageReference storageReference1 = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReference1.child("reportDaily.txt");
            InputStream stream = new FileInputStream(new File(fileName.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportDaily.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportDaily.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        final int delay = 5000;

//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                StorageReference storageReference = FirebaseStorage.getInstance().getReference(mUser.getUid() + "/reportDaily.txt");
//                //downloading the uploaded file and storing in arraylist
//                try {
//                    localFile = File.createTempFile("tempFile", ".txt");
//                    text = localFile.getAbsolutePath();Log.d("Values", String.valueOf(dataVals));
//                    Log.d("Bitmap", text);
//                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
////                            Toast.makeText(ConcentrationReportDaily.this, "Success", Toast.LENGTH_SHORT).show();
//
//                            try {
//                                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(localFile.getAbsolutePath()));
//
//                                Log.d("FileName", localFile.getAbsolutePath());
//
//                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                                String line = "";
//
//                                Log.d("First", line);
//                                if ((line = bufferedReader.readLine()) != null) {
//                                    list.add(line);
//                                }
//                                while ((line = bufferedReader.readLine()) != null) {
//
//                                    list.add(line);
//                                    Log.d("Line", line);
//                                }
//
//                                Log.d("List", String.valueOf(list));
//
//                                for (int i = 0; i < list.size(); i++) {
//                                    floatList.add(Float.parseFloat(list.get(i)));
//                                    Log.d("FloatArrayList", String.valueOf(floatList));
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
////                            //Stacked BarChart
////                            int[] colorClassArray = new int[]{Color.GREEN,Color.RED};
////                            BarDataSet barDataSet = new BarDataSet(dataValues1(),"Stress Time");
////                            barDataSet.setColors(colorClassArray);
////                            barDataSet.setStackLabels(new String[]{"Not Stressed", "Stressed", "Not Stressed"});
////                            String[] daysS = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
////                            XAxis xAxis = barChartdaily.getXAxis();
////                            xAxis.setValueFormatter(new IndexAxisValueFormatter(daysS));
////
////                            BarData barData = new BarData(barDataSet);
////                            barChartdaily.setData(barData);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                            Toast.makeText(ConcentrationReportDaily.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                } catch (IOException exception) {
//                    exception.printStackTrace();
//                }
//            }
//
//            //Downloading file and displaying chart
//        }, delay);

        //Stacked BarChart
        int[] colorClassArray = new int[]{Color.rgb(1,135,134),Color.rgb(255,51,51)};
        Log.d("DataValues print", "--------String------");
        BarDataSet barDataSet = new BarDataSet(dataValues1(),"Time");
        barDataSet.setColors(colorClassArray);
        barDataSet.setStackLabels(new String[]{"Relaxed Time", "Stressed"});
        String[] daysS = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        XAxis xAxis = barChartdaily.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(daysS));

        BarData barData = new BarData(barDataSet);
        barChartdaily.setData(barData);
        barChartdaily.animateXY(1500, 1500);


        //1 -----> Scatter chart
        ArrayList<Float> obj1 = new ArrayList<>(
                Arrays.asList(30f, 86f, 10f, 50f, 20f, 60f, 80f));

        ArrayList<Float> obja = new ArrayList<>(
                Arrays.asList(50f, 56f, 20f, 40f, 50f, 40f, 89f));// Avearage Array listr to write data to file

        try {
            fileName1 = new File(getCacheDir() + "/reportWhereami_job.txt");  //Writing data to file
            String line = "";
            FileWriter fw;
            fw = new FileWriter(fileName1);
            BufferedWriter output = new BufferedWriter(fw);
            int size = obj1.size();
            for (int i = 0; i < size; i++) {
                output.write(obj1.get(i).toString() + "\n");
//                Toast.makeText(this, "Success Writing", Toast.LENGTH_SHORT).show();
            }
            output.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        //Avg
        try {
            fileNamea = new File(getCacheDir() + "/reportWhereami_jobAvg.txt");  //Writing data to file
            String line = "";
            FileWriter fwa;
            fwa = new FileWriter(fileNamea);
            BufferedWriter outputa = new BufferedWriter(fwa);
            int size = obja.size();
            for (int i = 0; i < size; i++) {
                outputa.write(obja.get(i).toString() + "\n");
//                Toast.makeText(this, "Success Writing", Toast.LENGTH_SHORT).show();
            }
            outputa.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        // Uploading file created to firebase storage
        StorageReference storageReference11 = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReference11.child("reportWhereami_job.txt");
            InputStream stream = new FileInputStream(new File(fileName1.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //Avg

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        // Uploading file created to firebase storage
        StorageReference storageReference1a = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReference1a.child("reportWhereami_jobAvg.txt");
            InputStream stream = new FileInputStream(new File(fileNamea.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Handler handler2 = new Handler();
        final int delay2 = 5000;

        handler2.postDelayed(new Runnable() {

            @Override
            public void run() {
                storageReference = FirebaseStorage.getInstance().getReference();
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                mUser.getUid();

                StorageReference storageReference1a = storageReference.child("users/" + mUser.getUid());
                StorageReference storageReferenceb = storageReference1a.child("reportWhereami_job.txt");
                StorageReference storageReferencea = storageReference1a.child("reportWhereami_jobAvg.txt");

                //download and read the file

                try {
                    localFile1 = File.createTempFile("tempFile", ".txt");
                    localFilea = File.createTempFile("tempFilea", ".txt");

                    text1 = localFile1.getAbsolutePath();
                    texta = localFilea.getAbsolutePath();

                    Log.d("Bitmap", text1);
                    Log.d("Bitmap", texta);

                    storageReferenceb.getFile(localFile1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Success", Toast.LENGTH_SHORT).show();

                            try {
                                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(localFile1.getAbsolutePath()));

                                Log.d("FileName", localFile1.getAbsolutePath());

                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                String line = "";

                                Log.d("First", line);
                                if ((line = bufferedReader.readLine()) != null) {
                                    list1.add(line);
                                }
                                while ((line = bufferedReader.readLine()) != null) {

                                    list1.add(line);
                                    Log.d("Line", line);
                                }

                                Log.d("List", String.valueOf(list1));

                                for (int i = 0; i < list1.size(); i++) {
                                    floatList1.add(Float.parseFloat(list1.get(i)));
                                    Log.d("FloatArrayList", String.valueOf(floatList1));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            List<Entry> scatterEntries = new ArrayList<>();
                            for (int j = 0; j < floatList1.size(); ++j) {
                                scatterEntries.add(new Entry(j, floatList1.get(j)));
                            }


                            chart = findViewById(R.id.chart1);
                            chart.getDescription().setEnabled(false);
                            chart.setDrawGridBackground(false);
                            chart.setTouchEnabled(true);
                            chart.setMaxHighlightDistance(50f);
                            chart.setDragEnabled(true);
                            chart.setScaleEnabled(true);
                            chart.setMaxVisibleValueCount(200);
                            chart.setPinchZoom(true);
                            Legend l = chart.getLegend();

                            YAxis yl = chart.getAxisLeft();
                            yl.setAxisMinimum(0f);
                            chart.getAxisRight().setEnabled(false);
                            XAxis xl = chart.getXAxis();
                            xl.setDrawGridLines(false);
                            String[] daysS = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(daysS));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                            xAxis.setGranularity(1);
                            xAxis.setCenterAxisLabels(true);

                            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(Legend.LegendOrientation.VERTICAL);
                            l.setDrawInside(false);
                            l.setXOffset(5f);

                            ScatterDataSet set1 = new ScatterDataSet(scatterEntries, "You");
                            set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
                            set1.setColor(ColorTemplate.COLORFUL_COLORS[0]);


                            set1.setScatterShapeSize(8f);

                            dataSets.add(set1); // add the data sets

                            ScatterData data = new ScatterData(dataSets);
                            chart.setData(data);
                            chart.invalidate();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                    storageReferencea.getFile(localFilea).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Success", Toast.LENGTH_SHORT).show();

                            try {
                                InputStreamReader inputStreamReadera = new InputStreamReader(new FileInputStream(localFilea.getAbsolutePath()));

                                Log.d("FileName", localFilea.getAbsolutePath());

                                BufferedReader bufferedReadera = new BufferedReader(inputStreamReadera);
                                String line = "";

                                Log.d("First", line);
                                if ((line = bufferedReadera.readLine()) != null) {
                                    lista.add(line);
                                }
                                while ((line = bufferedReadera.readLine()) != null) {

                                    lista.add(line);
                                    Log.d("Line", line);
                                }

                                Log.d("List", String.valueOf(lista));

                                for (int i = 0; i < lista.size(); i++) {
                                    floatLista.add(Float.parseFloat(lista.get(i)));
                                    Log.d("FloatArrayList", String.valueOf(floatLista));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            List<Entry> scatterEntriesa = new ArrayList<>();
                            for (int j = 0; j < floatLista.size(); ++j) {
                                scatterEntriesa.add(new Entry(j, floatLista.get(j)));
                            }

                            ScatterDataSet seta = new ScatterDataSet(scatterEntriesa, "Other");
                            seta.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                            seta.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
                            seta.setScatterShapeHoleRadius(3f);

                            seta.setColor(ColorTemplate.COLORFUL_COLORS[1]);

                            seta.setScatterShapeSize(8f);

                            dataSets.add(seta);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            //Downloading file and displaying chart
        }, delay2);

        //2 ------> Scatter chart

        ArrayList<Float> objm = new ArrayList<>(
                Arrays.asList(80f, 86f, 10f, 50f, 20f, 60f, 80f));

        ArrayList<Float> obja1 = new ArrayList<>(
                Arrays.asList(25f, 56f, 20f, 40f, 50f, 40f, 89f));// Avearage Array listr to write data to file

        try {
            fileNamem = new File(getCacheDir() + "/reportWhereami_age.txt");  //Writing data to file
            String line = "";
            FileWriter fwm;
            fwm = new FileWriter(fileNamem);
            BufferedWriter outputm = new BufferedWriter(fwm);
            int size = objm.size();
            for (int i = 0; i < size; i++) {
                outputm.write(objm.get(i).toString() + "\n");
//                Toast.makeText(this, "Success Writing", Toast.LENGTH_SHORT).show();
            }
            outputm.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        //Avg
        try {
            fileNamea2 = new File(getCacheDir() + "/reportWhereami_ageAvg.txt");  //Writing data to file
            String line = "";
            FileWriter fwa2;
            fwa2 = new FileWriter(fileNamea2);
            BufferedWriter outputa2 = new BufferedWriter(fwa2);
            int size = obja1.size();
            for (int i = 0; i < size; i++) {
                outputa2.write(obja1.get(i).toString() + "\n");
//                Toast.makeText(this, "Success Writing", Toast.LENGTH_SHORT).show();
            }
            outputa2.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        // Uploading file created to firebase storage
        StorageReference storageReference2 = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReference2.child("reportWhereami_age.txt");
            InputStream stream = new FileInputStream(new File(fileNamem.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Avg

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        // Uploading file created to firebase storage
        StorageReference storageReferencea2 = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReferencea2.child("reportWhereami_ageAvg.txt");
            InputStream stream = new FileInputStream(new File(fileNamea2.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportWhereamI.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final Handler handler1 = new Handler();
        final int delay1 = 5000;

        handler1.postDelayed(new Runnable() {

            @Override
            public void run() {

                //download and read the file

                storageReference = FirebaseStorage.getInstance().getReference();
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                mUser.getUid();

                StorageReference storageReference1a = storageReference.child("users/" + mUser.getUid());
                StorageReference storageReferencem = storageReference1a.child("reportWhereami_job.txt");
                StorageReference storageReferencea2 = storageReference1a.child("reportWhereami_jobAvg.txt");

                try {
                    localFilem = File.createTempFile("tempFilem", ".txt");
                    localFilea2 = File.createTempFile("tempFilea2", ".txt");

                    textm = localFilem.getAbsolutePath();
                    texta2 = localFilea2.getAbsolutePath();

                    Log.d("Bitmap", textm);
                    Log.d("Bitmap", texta2);

                    storageReferencem.getFile(localFilem).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(ConcentrationReportWhereamI.this, "Success", Toast.LENGTH_SHORT).show();

                            try {
                                InputStreamReader inputStreamReaderm = new InputStreamReader(new FileInputStream(localFilem.getAbsolutePath()));

                                Log.d("FileName", localFilem.getAbsolutePath());

                                BufferedReader bufferedReaderm = new BufferedReader(inputStreamReaderm);
                                String line = "";

                                Log.d("First", line);
                                if ((line = bufferedReaderm.readLine()) != null) {
                                    listm.add(line);
                                }
                                while ((line = bufferedReaderm.readLine()) != null) {

                                    listm.add(line);
                                    Log.d("Line", line);
                                }

                                Log.d("List", String.valueOf(listm));

                                for (int i = 0; i < listm.size(); i++) {
                                    floatListm.add(Float.parseFloat(listm.get(i)));
                                    Log.d("FloatArrayList", String.valueOf(floatListm));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            List<Entry> scatterEntriesm = new ArrayList<>();
                            for (int j = 0; j < floatListm.size(); ++j) {
                                scatterEntriesm.add(new Entry(j, floatListm.get(j)));
                            }


                            chart1 = findViewById(R.id.chart3);
                            chart1.getDescription().setEnabled(false);
                            chart1.setDrawGridBackground(false);
                            chart1.setTouchEnabled(true);
                            chart1.setMaxHighlightDistance(50f);
                            chart1.setDragEnabled(true);
                            chart1.setScaleEnabled(true);
                            chart1.setMaxVisibleValueCount(200);
                            chart1.setPinchZoom(true);
                            Legend l1 = chart1.getLegend();

                            YAxis yl1 = chart1.getAxisLeft();
                            yl1.setAxisMinimum(0f);
                            chart1.getAxisRight().setEnabled(false);
                            XAxis xl1 = chart1.getXAxis();
                            xl1.setDrawGridLines(false);
                            String[] daysS1 = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                            XAxis xAxis1 = chart1.getXAxis();
                            xAxis1.setValueFormatter(new IndexAxisValueFormatter(daysS1));
                            xAxis1.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                            xAxis1.setGranularity(1);
                            xAxis1.setCenterAxisLabels(true);

                            l1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l1.setOrientation(Legend.LegendOrientation.VERTICAL);
                            l1.setDrawInside(false);
                            l1.setXOffset(5f);

                            ScatterDataSet setm = new ScatterDataSet(scatterEntriesm, "You");
                            setm.setScatterShape(ScatterChart.ScatterShape.SQUARE);
                            setm.setColor(ColorTemplate.COLORFUL_COLORS[0]);


                            setm.setScatterShapeSize(8f);

                            dataSetsm.add(setm); // add the data sets

                            ScatterData datam = new ScatterData(dataSetsm);
                            chart1.setData(datam);
                            chart1.invalidate();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                    storageReferencea2.getFile(localFilea2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(ConcentrationReportWhereamI.this, "Success", Toast.LENGTH_SHORT).show();

                            try {
                                InputStreamReader inputStreamReadera2 = new InputStreamReader(new FileInputStream(localFilea2.getAbsolutePath()));

                                Log.d("FileName", localFilea2.getAbsolutePath());

                                BufferedReader bufferedReadera2 = new BufferedReader(inputStreamReadera2);
                                String line = "";

                                Log.d("First", line);
                                if ((line = bufferedReadera2.readLine()) != null) {
                                    lista2.add(line);
                                }
                                while ((line = bufferedReadera2.readLine()) != null) {

                                    lista2.add(line);
                                    Log.d("Line", line);
                                }

                                Log.d("List", String.valueOf(lista2));

                                for (int i = 0; i < lista2.size(); i++) {
                                    floatLista2.add(Float.parseFloat(lista2.get(i)));
                                    Log.d("FloatArrayList", String.valueOf(floatLista2));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            List<Entry> scatterEntriesa2 = new ArrayList<>();
                            for (int j = 0; j < floatLista2.size(); ++j) {
                                scatterEntriesa2.add(new Entry(j, floatLista2.get(j)));
                            }

                            ScatterDataSet seta2 = new ScatterDataSet(scatterEntriesa2, "Other");
                            seta2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                            seta2.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
                            seta2.setScatterShapeHoleRadius(3f);

                            seta2.setColor(ColorTemplate.COLORFUL_COLORS[1]);

                            seta2.setScatterShapeSize(8f);

                            dataSetsm.add(seta2);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ReportHome.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            //Downloading file and displaying chart
        }, delay1);


        // On click listener of weekly button
        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportWeekly.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        // On click listener of monthly button
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportMonthly.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        // On click listener of yearly button
        yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportYearly.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    //Putting data to the array of stacked bar chart
    private ArrayList<BarEntry> dataValues1() {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, new float[]{17, 7}));
        dataVals.add(new BarEntry(1, new float[]{8, 16}));
        dataVals.add(new BarEntry(2, new float[]{15, 9}));
        dataVals.add(new BarEntry(3, new float[]{24,0}));
        dataVals.add(new BarEntry(4, new float[]{22, 2}));
        dataVals.add(new BarEntry(5, new float[]{10,14}));
        dataVals.add(new BarEntry(6, new float[]{18,6}));

        Log.d("Values0", String.valueOf(dataVals.get(0).getX()));
        Log.d("Values1", String.valueOf(Arrays.toString(dataVals.get(1).getYVals())));

        for (int i = 0; i < dataVals.size(); i++) {
            System.out.println(Arrays.toString(dataVals.get(i).getYVals()));
        }
        return dataVals;
    }

    public class MyBarDataset extends BarDataSet {

        private List<Float> credits;

        MyBarDataset(List<BarEntry> yVals, String label, List<Float> credits) {
            super(yVals, label);
            this.credits = credits;
        }

        @Override
        public int getColor(int index) {
            float c = credits.get(index);

            if (c > 80) {
                return mColors.get(0);
            } else if (c > 60) {
                return mColors.get(1);
            } else if (c > 40) {
                return mColors.get(2);
            } else if (c > 20) {
                return mColors.get(3);
            } else {
                return mColors.get(4);
            }

        }
    }

    private class MyXAxisValueFormatter extends ValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
            Log.d("Tag", "monthly clicked hi");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }

    }

    public abstract class ValueFormatter implements IAxisValueFormatter, IValueFormatter {


        @Override
        @Deprecated
        public String getFormattedValue(float value, AxisBase axis) {
            return getFormattedValue(value);
        }


        @Override
        @Deprecated
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return getFormattedValue(value);
        }


        public String getFormattedValue(float value) {
            return String.valueOf(value);
        }


        public String getAxisLabel(float value, AxisBase axis) {
            return getFormattedValue(value);
        }


        public String getBarLabel(BarEntry barEntry) {
            return getFormattedValue(barEntry.getY());
        }


        public String getBarStackedLabel(float value, BarEntry stackedEntry) {
            return getFormattedValue(value);
        }


        public String getPointLabel(Entry entry) {
            return getFormattedValue(entry.getY());
        }


        public String getPieLabel(float value, PieEntry pieEntry) {
            return getFormattedValue(value);
        }


        public String getRadarLabel(RadarEntry radarEntry) {
            return getFormattedValue(radarEntry.getY());
        }


        public String getBubbleLabel(BubbleEntry bubbleEntry) {
            return getFormattedValue(bubbleEntry.getSize());
        }


        public String getCandleLabel(CandleEntry candleEntry) {
            return getFormattedValue(candleEntry.getHigh());
        }

    }

//
//    public void monthly(View v) {
//        Intent intent2 = new Intent(this, ConcentrationReportMonthly.class);
//        startActivity(intent2);
//
//    }
//
//    public void yearly(View view) {
//        Intent intent2 = new Intent(this, ConcentrationReportYearly.class);
//        startActivity(intent2);
//    }
//
//    public void weekly(View view) {
//        Intent intent2 = new Intent(this, ConcentrationReportWeekly.class);
//        startActivity(intent2);
//    }

    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.relax:
                        startActivity(new Intent(getApplicationContext(), Relax.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.journal:
                        startActivity(new Intent(getApplicationContext(), Journal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenge:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }


    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}