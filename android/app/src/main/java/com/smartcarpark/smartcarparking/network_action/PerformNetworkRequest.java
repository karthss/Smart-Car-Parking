package com.smartcarpark.smartcarparking.network_action;

import android.content.Context;
import android.os.AsyncTask;

import com.smartcarpark.smartcarparking.Constants;

import java.util.Map;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    //the url where we need to send the request
    private String url;

    //the parameters
    private Map<String, String> params;

    //the request code to define whether it is a GET or POST
    private int requestCode;

    //the result that we get after executing the request
    private String result;

    //status of the response
    private boolean isError;

    private Context context;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    //constructor to initialize values
    public PerformNetworkRequest(String url, Map<String, String> params, int requestCode, Context context, AsyncResponse delegate) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.context = context;
        this.delegate = delegate;
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        delegate.processFinish(s);
        //progressBar.setVisibility(GONE);

    }

    //the network operation will be performed in background
    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == Constants.CODE_POST_REQUEST) {
            return requestHandler.sendPostRequest(url, params);
        }

        if (requestCode == Constants.CODE_GET_REQUEST) {
            return requestHandler.sendGetRequest(url);
        }

        return null;
    }
}
