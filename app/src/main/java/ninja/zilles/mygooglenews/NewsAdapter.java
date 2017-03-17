package ninja.zilles.mygooglenews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zilles on 3/16/17.
 *
 * This fills the RecyclerView with the NewsArticle data that we loaded.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

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
        NewsArticle newsArticle = articles.get(position);

        holder.titleView.setText(newsArticle.getTitle());
        holder.authorView.setText(newsArticle.getAuthor());
        Picasso.with(holder.imageView.getContext())
                .load(newsArticle.getUrlToImage()).into(holder.imageView);
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
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView authorView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.titleTextView);
            authorView = (TextView) itemView.findViewById(R.id.authorTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
