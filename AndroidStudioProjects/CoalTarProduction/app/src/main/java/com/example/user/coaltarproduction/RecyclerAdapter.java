package com.example.user.coaltarproduction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.sax.StartElementListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

/**
 * Created by User on 8/24/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoAdapter> implements Filterable{

   ArrayList<Movie> movies=new ArrayList<>();
    ArrayList<Movie> moviefilter=new ArrayList<>();



    int i=0;
    String[] times={"2 months ago","3 months ago","3 months ago","4 months ago","4 months ago","6 months ago","6 months ago","8 months ago",
            "8 months ago","1 year ago","1 year ago"};
   Context context;
    private String[] urls={"xPgLaYHZNdQ","S6FmMwU","T9dG8nFo374","G-7WyU797pw&t=28s","EcCsgm1sT1w&t=3s","lojKaaSsSTA&t=4s",
            "mtryAonVlLk","MhJ7aEsUSG0","nY0SpQbPsTE","vqY7xGm8tbE","QbVAKR-FUZM"};
    PleaseFilter pleaseFilter;

    RecyclerAdapter(ArrayList<Movie> movies,Context context)
    {
        this.movies=movies;
        this.moviefilter=movies;
        this.context=context;

        HoldsTheLibrary.times=times;
    }

    @Override
    public VideoAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.from(parent.getContext()).inflate(R.layout.custom_card_layout,parent,false);
        VideoAdapter adapter=new VideoAdapter(view);

        return adapter;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter holder, final int position) {
        Log.e("movie name",(movies.get(position).getMoviename()));
        Log.e("1st position",""+position);
        try {
            holder.imageView.setImageResource(movies.get(position).getThumbnail());
            holder.textView.setText(movies.get(position).getMoviename());
            //holder.textView2.setText(times[position]);

        }catch (Exception e)
        {}
        /*holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                assert(R.id.istalyk == imageView.getId());
                Integer integer = (Integer) imageView.getTag();
                integer = integer == null ? 0 : integer;
                switch(integer) {
                    case R.drawable.like1_black:
                        imageView.setImageResource(R.drawable.like1_red);
                        imageView.setTag(R.drawable.like1_red);
                        break;
                    case R.drawable.like1_red:
                    default:
                        imageView.setImageResource(R.drawable.like1_black);
                        imageView.setTag(R.drawable.like1_black);
                        break;
                }
            }
        });*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HoldsTheLibrary hold=new HoldsTheLibrary();
                Movie movie = new Movie(movies.get(position).getMoviename(), movies.get(position).getThumbnail(),movies.get(position).getUrl());

                if (!((HoldsTheLibrary.builder.toString()).contains(movie.getMoviename())))
                {

                    Log.e("Builder", HoldsTheLibrary.builder.toString());

                HoldsTheLibrary.movielib.add(movie);
                Log.e("Updated list", "" + HoldsTheLibrary.movielib.size());
                notifyDataSetChanged();
            }
                HoldsTheLibrary.builder.append(movies.get(position).getMoviename() + "   ");
                Intent intent=new Intent(context,ForYoutubePlay.class);
                intent.putExtra("Url",movies.get(position).getUrl());
                intent.putExtra("Share_url","https://www.youtube.com/watch?v="+movies.get(position).getUrl());
                intent.putExtra("Movie name",movies.get(position).getMoviename());
                ForYoutubePlay forYoutubePlay=new ForYoutubePlay();
                forYoutubePlay.actfinish();
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    @Override
    public Filter getFilter() {
        if (pleaseFilter== null) {
            pleaseFilter=new PleaseFilter();
        }
        return pleaseFilter;
    }
    public class PleaseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            ArrayList<Movie> filter=new ArrayList<>();
            if (constraint!=null && constraint.length()>0) {

                constraint = constraint.toString().toUpperCase();
                for (int i=0;i<moviefilter.size();i++)
                {
                    String name=moviefilter.get(i).getMoviename().toString().toUpperCase();
                    if (name.contains(constraint))
                    {
                        Movie c=new Movie(moviefilter.get(i).getMoviename(),moviefilter.get(i).getThumbnail(),moviefilter.get(i).getUrl());
                        filter.add(c);
                    }
                }

                results.values=filter;
                results.count=filter.size();
            }
            else {
                results.values=moviefilter;
                results.count=moviefilter.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movies=(ArrayList<Movie>) results.values;
            notifyDataSetChanged();

        }
    }



    public  class VideoAdapter extends RecyclerView.ViewHolder{
       public   ImageView imageView;
       public  TextView textView;
        public  TextView textView2;
        public  CardView cardView;
        public ImageView likebutton;


       public VideoAdapter(View itemView) {
           super(itemView);

           imageView=(ImageView) itemView.findViewById(R.id.youtubevideo);
           textView=(TextView) itemView.findViewById(R.id.vid_desc);
           //textView2=(TextView) itemView.findViewById(R.id.vid_time);
           cardView=(CardView) itemView.findViewById(R.id.cardview);
           //likebutton=(ImageView) itemView.findViewById(R.id.istalyk) ;


       }
   }

}
