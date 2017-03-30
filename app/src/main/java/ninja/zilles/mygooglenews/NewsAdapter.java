package ninja.zilles.mygooglenews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.id.message;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by zilles on 3/16/17.
 *
 * This fills the RecyclerView with the NewsArticle data that we loaded.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";
    public static final String BODY = "BODY";
    public static final String IMG_URL = "IMG_URL";
    public static final String JSON = "JSON";
    public static final String NEWS_ARTICLE = "NEWS_ARTICLE";
    ArrayList<NewsArticle> articles;

    public NewsAdapter(ArrayList<NewsArticle> articles) {
        this.articles = articles;
    }

    /**
     * This function is called only enough times to cover the screen with views.  After
     * that point, it recycles the views when scrolling is done.
     * @param parent the intended parent object (our RecyclerView)
     * @param viewType unused in our function (enables having different kinds of views in the same RecyclerView)
     * @return the new ViewHolder we allocate
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View newsListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(newsListItem);
    }

    /**
     * This function gets called each time a ViewHolder needs to hold data for a different
     * position in the list.  We don't need to create any views (because we're recycling), but
     * we do need to update the contents in the views.
     * @param holder the ViewHolder that knows about the Views we need to update
     * @param position the index into the array of NewsArticles
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsArticle newsArticle = articles.get(position);

        holder.titleView.setText(newsArticle.getTitle());
        holder.authorView.setText(newsArticle.getAuthor());
        Picasso.with(holder.imageView.getContext())
                .load(newsArticle.getUrlToImage()).into(holder.imageView);

        // Attach a click listener that launches a new Activity
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Code for launching an Implicit Intent to view a URL in a browser
//                Uri uri = Uri.parse(newsArticle.getUrl());
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
//                v.getContext().startActivity(browserIntent);

                // Code for launching an Explicit Intent to go to another Activity in
                // the same App.
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                /* Code for passing data as a collection of primitives; this is fine
                 * if you are only passing a few values. */
//                intent.putExtra(TITLE, newsArticle.getTitle());
//                intent.putExtra(AUTHOR, newsArticle.getAuthor());
//                intent.putExtra(BODY, newsArticle.getDescription());
//                intent.putExtra(IMG_URL, newsArticle.getUrlToImage());

                /* Code for passing data as JSON (DON'T DO THIS; PARCELABLE IS MORE EFFICIENT) */
//                Gson gson = new Gson();
//                String json = gson.toJson(newsArticle);
//                intent.putExtra(JSON, json);

                /* Pass data as a Parcelable Plain-Old Java Object (POJO) */
                intent.putExtra(NEWS_ARTICLE, newsArticle);

                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * RecyclerView wants to know how many list items there are, so it knows when it gets to the
     * end of the list and should stop scrolling.
     * @return the number of NewsArticles in the array.
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     *
     * Added the View 'view' so we'd have something to attach a click listener to.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleView;
        public TextView authorView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            titleView = (TextView) itemView.findViewById(R.id.titleTextView);
            authorView = (TextView) itemView.findViewById(R.id.authorTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}



