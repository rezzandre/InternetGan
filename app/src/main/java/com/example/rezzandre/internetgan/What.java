package com.example.rezzandre.internetgan;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rezzandre on 20/10/17.
 */

public class What extends AsyncTaskLoader<String> {
    private String link;

    public What(Context context, String link) {
        super(context);
        this.link = link;
    }

    @Override
    public String loadInBackground() {
        InputStream in;

        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();

            in = connection.getInputStream();

            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = buff.readLine()) != null) {
                sb.append(line + "\n");
            }

            buff.close();
            in.close();

            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Errors, try change protocol to http or https";
    }
    protected void onStartLoading() {
        forceLoad();
    }

}
