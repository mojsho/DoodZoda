package com.example.mojtaba.doodzoda.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class RequestParser {
    private String Url;
    private Context mContext;

    RequestParser(String req) {
        this.Url = req;
    }

    String getUrlContents()
    {
        StringBuilder content = new StringBuilder();

        // many of these calls can throw exceptions, so i've just
        // wrapped them all in one try/catch statement.
        try
        {
            // create a url object
            URL url = new URL(Url);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
