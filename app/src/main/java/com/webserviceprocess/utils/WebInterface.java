package com.webserviceprocess.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class WebInterface {

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                return cm.getActiveNetworkInfo().isConnected();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static ApiResponse doPostString(String url, String param) {

        ApiResponse apiResponse = null;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
            httpResponse = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        apiResponse = new ApiResponse();
        apiResponse.status_code = httpResponse.getStatusLine().getStatusCode();

        if (apiResponse.status_code == HttpStatus.SC_OK) {
            httpEntity = httpResponse.getEntity();
            try {
                apiResponse.response = new String(
                        EntityUtils.toString(httpEntity));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpEntity = null;
        httpResponse = null;
        httpPost = null;
        httpClient = null;

        return apiResponse;
    }


    public static ApiResponse doPost(String url, List<BasicNameValuePair> param) {

        ApiResponse apiResponse = null;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            httpResponse = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        apiResponse = new ApiResponse();
        apiResponse.status_code = httpResponse.getStatusLine().getStatusCode();

        if (apiResponse.status_code == HttpStatus.SC_OK) {
            httpEntity = httpResponse.getEntity();
            try {
                apiResponse.response = new String(
                        EntityUtils.toString(httpEntity));
                // Log.d("mytag", apiResponse.response +
                // "you get from serverresponse......!! " +httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpEntity = null;
        httpResponse = null;
        httpPost = null;
        httpClient = null;

        return apiResponse;
    }

    public static ApiResponse doPost(String url) {

        ApiResponse apiResponse = null;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            // httpPost.setEntity(new UrlEncodedFormEntity(param));
            httpResponse = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        apiResponse = new ApiResponse();
        apiResponse.status_code = httpResponse.getStatusLine().getStatusCode();

        if (apiResponse.status_code == HttpStatus.SC_OK) {
            httpEntity = httpResponse.getEntity();
            try {
                apiResponse.response = new String(
                        EntityUtils.toString(httpEntity));
                // Log.d("mytag", apiResponse.response +
                // "you get from serverresponse......!! " +httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpEntity = null;
        httpResponse = null;
        httpPost = null;
        httpClient = null;

        return apiResponse;
    }

    public static String doWS(String url, String json) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the
            // content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
