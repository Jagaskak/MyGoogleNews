package ninja.zilles.mygooglenews;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Note: to get this code to work, we added the following line to app/build.gradle
 *
 *     compile 'com.google.code.gson:gson:2.7'
 *     compile 'com.android.support.constraint:constraint-layout:1.0.2'
 *     compile 'com.android.support:recyclerview-v7:25.2.0'
 *
 *     compile 'com.squareup.picasso:picasso:2.5.2'
 *
 *     (You should use Gson and RecyclerView in your assignment.)
 *     (I used constraint layout for the news_list_item; you don't need to use it.)
 *     (Picasso is the image loading library.)
 *
 * and the following line to app/src/main/AndroidManifest.xml
 *
 *     <uses-permission android:name="android.permission.INTERNET" />
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final String urlString =
            "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=";

    // mNewsAdapter connects mArticles to mRecyclerView
    private RecyclerView mRecyclerView;
    private ArrayList<NewsArticle> mArticles = new ArrayList<>();
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initially, mArticles is an empty ArrayList.  We populate it with NewsAsyncTask.
        mNewsAdapter = new NewsAdapter(mArticles);
        mRecyclerView.setAdapter(mNewsAdapter);

        // Construct URL and request data...
        try {
            URL url = new URL(urlString + NewsApi.API_KEY);

            // Fetch the news on a background thread; it will populate mArticles.
            new NewsAsyncTask(this).execute(url);

        } catch (MalformedURLException e) {
            // OK to dump stack trace here.  Should never fire once we've debugged app.
            e.printStackTrace();
        }
    }

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

            // Pop up a Toast if we failed to get data.
            if (newsArticles == null) {
                Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
                return;
            }

            // Empty the ArrayList of articles (mArticles) and fill it with the ones we loaded.
            // (In this code, the emptying isn't necessary, because this function is only called
            // once, but it is good practice.)
            mArticles.clear();
            for (NewsArticle newsArticle : newsArticles) {
                Log.d("NEWS", newsArticle.getTitle());
//                Log.d("LINK", newsArticle.getUrlToImage());

                mArticles.add(newsArticle);
            }

            // Poke mNewsAdapter to let it know that its data changed.
            mNewsAdapter.notifyDataSetChanged();
        }
    }
}
