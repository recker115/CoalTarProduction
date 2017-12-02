package com.example.user.coaltarproduction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class ForYoutubePlay extends YouTubeBaseActivity {
     String url;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    ImageButton imageButton;
    String moviename;
    TextView textView;
    TextView comment;
    Dialog dialog;
    ImageView thumbsup,thumbsdown,share;
    RecyclerView recyclerView;
    ArrayList<Movie> moviesyout;
    TextView showrating;
    RatingBar ratingBar;
    String share_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_youtube_play);
        Intent intent=getIntent();
        url=intent.getStringExtra("Url");
        moviename=intent.getStringExtra("Movie name");
        textView=(TextView) findViewById(R.id.moviename);
        comment=(TextView) findViewById(R.id.comment);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.comment);
        thumbsup=(ImageView) findViewById(R.id.thumbsup);
        thumbsdown=(ImageView) findViewById(R.id.thumbsdown);
        share=(ImageView) findViewById(R.id.sharebutton);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        showrating=(TextView) findViewById(R.id.ratingshow);
        ratingBar=(RatingBar) findViewById(R.id.rating);
        moviesyout=new ArrayList<>();
        share_url=getIntent().getExtras().getString("Share_url");


        for (int i=0;i<(HoldsTheLibrary.totalmovies.size());i++)
        {
            if (!(HoldsTheLibrary.totalmovies.get(i).getMoviename()).equals(moviename))
                moviesyout.add(HoldsTheLibrary.totalmovies.get(i));
            Log.e("Total size",""+HoldsTheLibrary.totalmovies.size());

        }

        RecyclerAdapter2 recyclerAdapter2=new RecyclerAdapter2(moviesyout,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter2);


        Log.e("url",url);
        youTubePlayerView=(YouTubePlayerView) findViewById(R.id.youtubeplayerview);
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
               youTubePlayer.loadVideo(url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        textView.setText(moviename);
        youTubePlayerView.initialize("AIzaSyAdfym5Uxv9OznsHBsIJfCT4xt-LotFfSc",onInitializedListener);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog=new CustomDialog(ForYoutubePlay.this,moviename);
                customDialog.setCancelable(true);
                customDialog.show();
                customDialog.setCanceledOnTouchOutside(true);

            }
        });
        thumbsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                assert(R.id.thumbsup == imageView.getId());
                Integer integer = (Integer) imageView.getTag();
                integer = integer == null ? 0 : integer;
                switch(integer) {
                    case R.drawable.ic_thumb_up_black_24dp:
                        imageView.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                        imageView.setTag(R.drawable.ic_thumb_down_blue_24dp);
                        break;
                    case R.drawable.ic_thumb_up_blue_24dp:
                    default:
                        imageView.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                        imageView.setTag(R.drawable.ic_thumb_up_black_24dp);
                        break;
                }

            }
        });
        thumbsdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                assert(R.id.thumbsdown == imageView.getId());
                Integer integer = (Integer) imageView.getTag();
                integer = integer == null ? 0 : integer;
                switch(integer) {
                    case R.drawable.ic_thumb_down_black_24dp:
                        imageView.setImageResource(R.drawable.ic_thumb_down_blue_24dp);
                        imageView.setTag(R.drawable.ic_thumb_down_blue_24dp);
                        break;
                    case R.drawable.ic_thumb_down_blue_24dp:
                    default:
                        imageView.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                        imageView.setTag(R.drawable.ic_thumb_down_black_24dp);
                        break;
                }
            }
        });
        showrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getVisibility()==View.VISIBLE)
                    ratingBar.setVisibility(View.GONE);
                else
                    ratingBar.setVisibility(View.VISIBLE);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share_url);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForYoutubePlay.this,HomeActivity.class));
    }
    public void actfinish()
    {
        finish();
    }

}
