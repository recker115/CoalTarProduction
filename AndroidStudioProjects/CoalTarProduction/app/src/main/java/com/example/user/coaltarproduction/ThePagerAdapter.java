package com.example.user.coaltarproduction;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by User on 8/24/2017.
 */

public class ThePagerAdapter extends FragmentPagerAdapter {
    int i=0;
    Context context;
    ArrayList<Movie> movies=new ArrayList<>();
    ArrayList<Movie> moviesnew=new ArrayList<>();
    public ArrayList<Movie> lib=new ArrayList<>();


    public ThePagerAdapter(FragmentManager fm,Context context,ArrayList<Movie> movies,ArrayList<Movie> moviesnew)
    {
        super(fm);
        this.context=context;
        this.movies=movies;
        this.moviesnew=moviesnew;
    }


    public Fragment getItem(int position) {
        Fragment fragment=new TheRecyclerFragment();
        Bundle args=new Bundle();
        if (position==0)
        args.putParcelableArrayList("VideoUrls",movies);
        else if (position==1)
            args.putParcelableArrayList("VideoUrls",moviesnew);
        else if(lib.size()>0)
        {
            args.putParcelableArrayList("VideoUrls",lib);

        }
        else {
            HoldsTheLibrary hold=new HoldsTheLibrary();
            args.putParcelableArrayList("VideoUrls",HoldsTheLibrary.movielib);
            Log.e("Updated list new",""+HoldsTheLibrary.movielib.size());
            if (i>0)
            Log.e("Updated list",HoldsTheLibrary.movielib.get(position).getMoviename());
            i++;
        }
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
