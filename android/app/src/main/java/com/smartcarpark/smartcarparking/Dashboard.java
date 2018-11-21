package com.smartcarpark.smartcarparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcarpark.smartcarparking.network_action.PerformNetworkRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, PerformNetworkRequest.AsyncResponse {

    private ProgressBar pbSlotsAvailable, pbBgl12, pbBgl13, pbBgl14, pbBgl15, pbBgl16, pbBgl17;
    private TextView tvSlotsAvailable, tvBgl12, tvBgl13, tvBgl14, tvBgl15, tvBgl16, tvBgl17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        initializeViews();
        displayZonesInfo();
        pbBgl12.setOnClickListener(this);
        pbBgl13.setOnClickListener(this);
        pbBgl14.setOnClickListener(this);
        pbBgl15.setOnClickListener(this);
        pbBgl16.setOnClickListener(this);
        pbBgl17.setOnClickListener(this);
    }

    private void initializeViews() {
        pbSlotsAvailable = findViewById(R.id.pb_slots_available);
        pbBgl12 = findViewById(R.id.pb_bgl12);
        pbBgl13 = findViewById(R.id.pb_bgl13);
        pbBgl14 = findViewById(R.id.pb_bgl14);
        pbBgl15 = findViewById(R.id.pb_bgl15);
        pbBgl16 = findViewById(R.id.pb_bgl16);
        pbBgl17 = findViewById(R.id.pb_bgl17);

        //pbBgl12.getProgressDrawable().

        tvSlotsAvailable = findViewById(R.id.tv_slots_available);
        tvBgl12 = findViewById(R.id.tv_bgl12);
        tvBgl13 = findViewById(R.id.tv_bgl13);
        tvBgl14 = findViewById(R.id.tv_bgl14);
        tvBgl15 = findViewById(R.id.tv_bgl15);
        tvBgl16 = findViewById(R.id.tv_bgl16);
        tvBgl17 = findViewById(R.id.tv_bgl17);
    }

    private void displayZonesInfo() {
        new PerformNetworkRequest(Constants.URL_GET_ALL_ZONES_INFO, null, Constants.CODE_GET_REQUEST, getApplicationContext(), this).execute();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ParkingLayout.class);
        String zoneId = null;
        switch (v.getId()) {
            case R.id.pb_bgl12:
                zoneId = "bgl12";
                break;

            case R.id.pb_bgl13:
                zoneId = "bgl13";
                break;

            case R.id.pb_bgl14:
                zoneId = "bgl14";
                break;

            case R.id.pb_bgl15:
                zoneId = "bgl15";
                break;
            case R.id.pb_bgl16:
                zoneId = "bgl16";
                break;

            case R.id.pb_bgl17:
                zoneId = "bgl17";
                break;
        }
        intent.putExtra(Constants.INTENT_ZONE_ID, zoneId);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject object = new JSONObject(output);

            if (! object.getBoolean(Constants.ERROR_RESPONSE)) {
                populateProgressBars(object);
            } else {
                Toast.makeText(this, object.getString(Constants.MESSAGE_RESPONSE), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateProgressBars(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.API_ZONES);
        int totalSlotsAvailable = 0;
        int totalSlots = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject zoneInfo = jsonArray.getJSONObject(i);
            int slotsAvailable = zoneInfo.getInt(Constants.API_SLOTS_AVAILABLE);
            int slotsOccupied = zoneInfo.getInt(Constants.API_SLOTS_OCCUPIED);
            int totalSlotsPerZone = slotsAvailable + slotsOccupied;

            switch (zoneInfo.getString(Constants.API_ZONE_ID)) {
                case "bgl12":
                    pbBgl12.setClickable(true);
                    pbBgl12.setMax(totalSlotsPerZone);
                    pbBgl12.setProgress(slotsAvailable);
                    tvBgl12.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;

                case "bgl13":
                    pbBgl13.setClickable(true);
                    pbBgl13.setMax(totalSlotsPerZone);
                    pbBgl13.setProgress(slotsAvailable);
                    tvBgl13.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;

                case "bgl14":
                    pbBgl14.setClickable(true);
                    pbBgl14.setMax(totalSlotsPerZone);
                    pbBgl14.setProgress(slotsAvailable);
                    tvBgl14.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;

                case "bgl15":
                    pbBgl15.setClickable(true);
                    pbBgl15.setMax(totalSlotsPerZone);
                    pbBgl15.setProgress(slotsAvailable);
                    tvBgl15.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;

                case "bgl16":
                    pbBgl16.setClickable(true);
                    pbBgl16.setMax(totalSlotsPerZone);
                    pbBgl16.setProgress(slotsAvailable);
                    tvBgl16.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;

                case "bgl17":
                    pbBgl17.setClickable(true);
                    pbBgl17.setMax(totalSlotsPerZone);
                    pbBgl17.setProgress(slotsAvailable);
                    tvBgl17.setText(getSlotsAvailableInfo(slotsAvailable, totalSlotsPerZone));
                    break;
            }
            totalSlotsAvailable += slotsAvailable;
            totalSlots += totalSlotsPerZone;
        }
        tvSlotsAvailable.setText(getSlotsAvailableInfo(totalSlotsAvailable, totalSlots));
    }

    private String getSlotsAvailableInfo(int slotsAvailable, int totalSlots) {
        return new StringBuilder().append(slotsAvailable).append("/").append(totalSlots).toString();
    }
}
