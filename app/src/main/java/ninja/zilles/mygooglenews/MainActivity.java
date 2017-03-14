package ninja.zilles.mygooglenews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Note: to get this code to work, we added the following line to app/build.gradle
 *
 *     compile 'com.google.code.gson:gson:2.7'
 *
 * and the following line to app/src/main/AndroidManifest.xml
 *
 *     <uses-permission android:name="android.permission.INTERNET" />
 *     
 */
public class MainActivity extends AppCompatActivity {

    public static final String urlString =
            "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = new URL(urlString + NewsApi.API_KEY);

            // Fetch the news on a background thread
            new NewsAsyncTask(this).execute(url);

        } catch (MalformedURLException e) {
            // OK to dump stack trace here.  Should never fire once we've debugged app.
            e.printStackTrace();
        }
    }
}
