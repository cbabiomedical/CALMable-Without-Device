package com.example.calmable.fitbit.home;

import static com.example.calmable.PopUpOne.latLong;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.calmable.Home;
import com.example.calmable.R;
import com.example.calmable.data.APIService;
import com.example.calmable.data.AlertService;
import com.example.calmable.databinding.FragmentFitbitHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitbitHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitbitHomeFragment extends Fragment {

    File fitbitDataFile;
    private Handler mHandler;
    JSONArray activitiesDataSet;

    private APIService apiService;
    private FragmentFitbitHomeBinding _binding;
    private Pattern sPattern;

    public FitbitHomeFragment() {
        sPattern = Pattern.compile("^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d))$");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitbitHomeFragment newInstance() {
        FitbitHomeFragment fragment = new FitbitHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _binding = FragmentFitbitHomeBinding.inflate(getLayoutInflater());
        apiService = new APIService(this.requireContext());

        View root = _binding.getRoot();

        //Using date format to extract time from date
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

//        //Current time as end date
//        Calendar now = Calendar.getInstance();
//        String endTime = simpleDateFormat.format(now.getTime());
//        //getting last hour as start date
//        now.add(Calendar.MINUTE, -5);
//        String startTime = simpleDateFormat.format(now.getTime());
//
//        //binding the date values to the view
//        _binding.starttime.setText(startTime);
//        _binding.endtime.setText(endTime);


        // looping home fragment btn
        this.mHandler = new Handler();
        m_Runnable.run();

//        //validations for input fields
//        startTimeValidation();
//        endTimeValidation();
        // Inflate the layout for this fragment
        return root;
    }


    // refresh activity
    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

            //Current time as end date
            Calendar now = Calendar.getInstance();
            String endTime = simpleDateFormat.format(now.getTime());
            //getting last hour as start date
            now.add(Calendar.MINUTE, -5);
            String startTime = simpleDateFormat.format(now.getTime());

            //binding the date values to the view
            _binding.starttime.setText(startTime);
            _binding.endtime.setText(endTime);

            //Home fragment "Get Heart Rate" btn action
            _binding.loading.setVisibility(View.VISIBLE);
            getData(_binding.starttime.getText().toString(), _binding.endtime.getText().toString());

            //validations for input fields
            startTimeValidation();
            endTimeValidation();

            writeToFileFitbit();

            Toast.makeText(getActivity(), "in runnable", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(m_Runnable, 300000);
        }
    };



    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
        getActivity().onBackPressed();
    }

    private boolean isValid(CharSequence s) {
        return sPattern.matcher(s).matches();
    }

    /**
     * start time input validation
     */
    private void startTimeValidation() {
        _binding.starttime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.toString().isEmpty()) {
                    _binding.getratebtn.setEnabled(false);
                    _binding.starttime.setError(getString(R.string.starttime_required));
                } else if (!isValid(s.toString())) {
                    _binding.starttime.setError(getString(R.string.starttime_invalid));
                }
            }
        });
    }

    /**
     * end time input validation
     */
    private void endTimeValidation() {
        _binding.starttime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.toString().isEmpty()) {
                    _binding.getratebtn.setEnabled(false);
                    _binding.endtime.setError(getString(R.string.endtime_required));
                } else if (!isValid(s.toString())) {
                    _binding.endtime.setError(getString(R.string.endtime_required));
                }
            }
        });
    }

    /**
     * method to retrieve data from the API
     */
    private void getData(String startTime, String endTime) {
        //URL of the endpoint
        String url = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1min/time/" + startTime + "/" + endTime + ".json";

        apiService.get(url, new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //Extracting the required data from the response
                ResponseBody responseBody = response.body();
                String responseData = null;


                if (responseBody != null) {
                    responseData = responseBody.string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONObject activities = json.getJSONObject("activities-heart-intraday");
                        activitiesDataSet = activities.getJSONArray("dataset");

                        //Log.d("TAG", "----------onResponse:----------" + responseData);
                        Log.d("TAG", "----------onResponse:----------" + activities);
                        Log.d("TAG", "----------onResponse:----------" + activitiesDataSet);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    if (activitiesDataSet.length() > 0) {
                                        JSONObject element = activitiesDataSet.getJSONObject(activitiesDataSet.length() - 1);
                                        String time = element.getString("time");
                                        Integer heartRate = element.getInt("value");
                                        _binding.timestamp.setText(time);
                                        _binding.heart.setText(heartRate.toString());

                                    } else {
                                        AlertService.displaySnackBar(_binding.view, getText(R.string.no_data), false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                _binding.loading.setVisibility(View.INVISIBLE);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Exception ex = e;
            }
        });

    }

    private void writeToFileFitbit() {

        // add data to arraylist
        ArrayList<Object> fitbitDataList = new ArrayList<>();
        //heartRateList.add(finalHR);
        fitbitDataList.add(activitiesDataSet);

        //Writing data to file
        try {
            fitbitDataFile = new File(getActivity().getCacheDir() + "/fitbitData.txt");
            fitbitDataFile.createNewFile();
            if (!fitbitDataFile.exists()) {
                fitbitDataFile.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fitbitDataFile, true));
            int size = fitbitDataList.size();
            for (int i = 0; i < size; i++) {
                writer.write(fitbitDataList.get(i).toString());
                writer.newLine();
                writer.flush();

                //Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            }

            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // write hr to txt - end

    }


}