package com.smartcarpark.smartcarparking;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcarpark.smartcarparking.network_action.PerformNetworkRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParkingLayout extends AppCompatActivity implements PerformNetworkRequest.AsyncResponse {
    private TextView a1, a2, a3, a4, a5, a6, a7, a8;
    private Map<String, TextView> slotsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        initializeViews();
        initializeMap();
        getSlotsInfo();
    }

    private void initializeMap() {
        slotsMap = new HashMap<>();
        slotsMap.put("A1", a1);
        slotsMap.put("A2", a2);
        slotsMap.put("A3", a3);
        slotsMap.put("A4", a4);
        slotsMap.put("A5", a5);
        slotsMap.put("A6", a6);
        slotsMap.put("A7", a7);
        slotsMap.put("A8", a8);
    }

    private void getSlotsInfo() {
        Map<String, String> params = new HashMap<>();
        params.put(Constants.API_ZONE_ID, getIntent().getStringExtra(Constants.INTENT_ZONE_ID));
        new PerformNetworkRequest(Constants.URL_GET_SLOTS_INFO_IN_ZONE, params, Constants.CODE_POST_REQUEST, getApplicationContext(), this).execute();
    }

    private void initializeViews() {
        a1 = findViewById(R.id.textView2);
        a2 = findViewById(R.id.textView3);
        a3 = findViewById(R.id.textView4);
        a4 = findViewById(R.id.textView0);
        a5 = findViewById(R.id.textView5);
        a6 = findViewById(R.id.textView6);
        a7 = findViewById(R.id.textView7);
        a8 = findViewById(R.id.textView8);
    }

    @Override
    public void processFinish(String output) {

        try {
            JSONObject object = new JSONObject(output);

            if (! object.getBoolean(Constants.ERROR_RESPONSE)) {
                displaySlots(object);
            } else {
                Toast.makeText(this, object.getString(Constants.MESSAGE_RESPONSE), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displaySlots(JSONObject object) throws JSONException {
        JSONArray jsonArray = object.getJSONArray(Constants.API_SLOTS_PER_ZONE);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject slotInfo = jsonArray.getJSONObject(i);
            if (slotInfo.getInt(Constants.API_IS_AVAILABLE) == 1) {
                slotsMap.get(slotInfo.getString(Constants.API_SLOT_ID)).getBackground().setColorFilter(Color.parseColor("#dbdbdb"), PorterDuff.Mode.DARKEN);
            }
        }
    }
}