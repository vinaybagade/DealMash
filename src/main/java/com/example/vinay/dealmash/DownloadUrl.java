package com.example.vinay.dealmash;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by vinay on 3/7/17.
 */

public class DownloadUrl {
    public String readUrl(String strurl) throws IOException {
        String data="";
        InputStream inputStream=null;
        HttpsURLConnection httpsURLConnection=null;

        try {
            URL url= new URL(strurl);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
            inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer= new StringBuffer();
            while ((data=bufferedReader.readLine())!=null){
                stringBuffer.append(data);
            }
            data=stringBuffer.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
            httpsURLConnection.disconnect();
        }

    return data;
    }
}
