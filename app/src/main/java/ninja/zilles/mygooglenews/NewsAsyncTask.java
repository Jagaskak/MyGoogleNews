package ninja.zilles.mygooglenews;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by zilles on 3/14/17.
 *
 * This class (1) takes a URL, (2) makes an HTTP request, (3) parses the resulting JSON
 * into a NewsCollection, and (4) returns the array of NewsArticles.
 *
 * This class requires a Context in its constructor so that we can make Toasts.
 */

public class NewsAsyncTask extends AsyncTask<URL, Void, NewsArticle[]> {

    Context context;

    public NewsAsyncTask(Context context) {
        this.context = context;
    }

    // This function is done on the background thread.
    @Override
    protected NewsArticle[] doInBackground(URL... params) {

        try {
            URL url = params[0];
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));

            Gson gson = new Gson();
            NewsCollection newsCollection = gson.fromJson(inStreamReader, NewsCollection.class);
            return newsCollection.getArticles();

        } catch (IOException e) {
            Log.d("NewsAsyncTask", "failed to get data from network", e);
            return null;
        }
    }

    // This code is run on the UI thread
    @Override
    protected void onPostExecute(NewsArticle[] newsArticles) {
        if (newsArticles == null) {
            Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
            return;
        }
        for (NewsArticle newsArticle : newsArticles) {
            Log.d("NEWS", newsArticle.getTitle());
        }
    }
}
