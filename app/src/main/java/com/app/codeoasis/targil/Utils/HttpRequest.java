package com.app.codeoasis.targil.Utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class HttpRequest {
    private static String TAG = "HttpRequest";

    public HttpRequest() {
    }

    public String getJSONString(String address) {
        String response = null;
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "getJSONString MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "getJSONString ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "getJSONString IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "getJSONString Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "convertStreamToString: Exception: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "convertStreamToString: Exception: " + e.getMessage());
            }
        }
        return sb.toString();
    }
}
