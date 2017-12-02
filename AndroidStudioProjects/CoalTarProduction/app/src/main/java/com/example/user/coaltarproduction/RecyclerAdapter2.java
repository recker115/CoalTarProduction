package com.example.user.coaltarproduction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 8/24/2017.
 */

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.VideoAdapter> {

   ArrayList<Movie> movies=new ArrayList<>();
    ArrayList<Movie> moviefilter=new ArrayList<>();



    int i=0;
    String[] times={"2 months ago","3 months ago","3 months ago","4 months ago","4 months ago","6 months ago","6 months ago","8 months ago",
            "8 months ago","1 year ago","1 year ago"};
   Context context;
    private String[] urls={"xPgLaYHZNdQ","S6FmMwU","T9dG8nFo374","G-7WyU797pw&t=28s","EcCsgm1sT1w&t=3s","lojKaaSsSTA&t=4s",
            "mtryAonVlLk","MhJ7aEsUSG0","nY0SpQbPsTE","vqY7xGm8tbE","QbVAKR-FUZM"};


    RecyclerAdapter2(ArrayList<Movie> movies, Context context)
    {
        this.movies=movies;
        this.moviefilter=movies;
        this.context=context;
    }

    @Override
    public VideoAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.from(parent.getContext()).inflate(R.layout.moviecard,parent,false);
        VideoAdapter adapter=new VideoAdapter(view);

        return adapter;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter holder, final int position) {
        Log.e("movie name",(movies.get(position).getMoviename()));
        try {
            holder.imageView.setImageResource(movies.get(position).getThumbnail());
            holder.textView.setText(movies.get(position).getMoviename());
            holder.textView2.setText(times[position]);

        }catch (Exception e)
        {}


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HoldsTheLibrary hold=new HoldsTheLibrary();
                Movie movie = new Movie(movies.get(position).getMoviename(), movies.get(position).getThumbnail(),movies.get(i).getUrl());

               /* if (!((HoldsTheLibrary.builder.toString()).contains(movie.getMoviename())))
                {

                    Log.e("Builder", HoldsTheLibrary.builder.toString());

                HoldsTheLibrary.movielib.add(movie);
                Log.e("Updated list", "" + HoldsTheLibrary.movielib.size());
                notifyDataSetChanged();
            }*/
                HoldsTheLibrary.builder.append(movies.get(position).getMoviename() + "   ");
                Intent intent=new Intent(context,ForYoutubePlay.class);
                intent.putExtra("Url",movies.get(position).getUrl());
                intent.putExtra("Movie name",movies.get(position).getMoviename());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }





    public  class VideoAdapter extends RecyclerView.ViewHolder{
       public   ImageView imageView;
       public  TextView textView;
        public  TextView textView2;
        public  CardView cardView;



       public VideoAdapter(View itemView) {
           super(itemView);

           imageView=(ImageView) itemView.findViewById(R.id.thumbnail);
           textView=(TextView) itemView.findViewById(R.id.moviename);
           textView2=(TextView) itemView.findViewById(R.id.time);
           cardView=(CardView) itemView.findViewById(R.id.cardview);



       }
   }

}
