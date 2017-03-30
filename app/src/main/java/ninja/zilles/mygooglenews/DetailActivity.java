package ninja.zilles.mygooglenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import static android.R.attr.author;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mAuthorTextView;
    private TextView mBodyTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get references to the views.
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mAuthorTextView = (TextView) findViewById(R.id.authorTextView);
        mBodyTextView = (TextView) findViewById(R.id.bodyTextView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();

        /* Code if we were passing this as individual extras (don't do this) */
//        String title = intent.getStringExtra(NewsAdapter.TITLE);
//        String author = intent.getStringExtra(NewsAdapter.AUTHOR);
//        String body = intent.getStringExtra(NewsAdapter.BODY);
//        String imgURL = intent.getStringExtra(NewsAdapter.IMG_URL);
//        mTitleTextView.setText(title);
//        mAuthorTextView.setText(author);
//        mBodyTextView.setText(body);
//        Picasso.with(mImageView.getContext()).load(imgURL).into(mImageView);

        /* Code if we were pass a NewsArticle as JSON (don't do this) */
//        String json = intent.getStringExtra(NewsAdapter.JSON);
//        Gson gson = new Gson();
//        NewsArticle newsArticle = gson.fromJson(json, NewsArticle.class);

        /* Code for receiving the Parcelable NewsArticle */
        NewsArticle newsArticle = intent.getParcelableExtra(NewsAdapter.NEWS_ARTICLE);

        mTitleTextView.setText(newsArticle.getTitle());
        mAuthorTextView.setText(newsArticle.getAuthor());
        mBodyTextView.setText(newsArticle.getDescription());
        Picasso.with(mImageView.getContext()).load(newsArticle.getUrlToImage()).into(mImageView);
    }
}
