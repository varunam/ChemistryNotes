package com.chemistrynotes.varunam.chemistrynotes;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadPdf extends AsyncTask<String, Void, String> {

    private Context context;

    public DownloadPdf(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            downloadFile(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings[0];
    }

    private void downloadFile(String url) throws IOException {
        int count;
        try {
            URL downloadUrl = new URL(url);
            URLConnection urlConnection = downloadUrl.openConnection();
            urlConnection.connect();
            InputStream downloadStream = new BufferedInputStream(downloadUrl.openStream(),8192);
            OutputStream output = new FileOutputStream("/sdcard/newpdf.pdf");
            byte data[] = new byte[1024];
            long total = 0;
            while((count=downloadStream.read(data))!=-1)
            {
                total = total+count;
                output.write(data,0,count);
            }
            output.flush();
            output.close();
            downloadStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        Toast.makeText(context,string,Toast.LENGTH_LONG).show();
    }
}
