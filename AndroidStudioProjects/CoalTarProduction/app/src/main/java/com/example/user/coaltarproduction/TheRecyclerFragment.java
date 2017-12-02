package com.example.user.coaltarproduction;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by User on 8/24/2017.
 */

public class TheRecyclerFragment extends Fragment{

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ListView listView;
    LinearLayoutManager manager;
    //RecyclerView.LayoutManager manager;
    //android.support.v7.widget.SearchView searchView;
    ProgressBar progressBar;
    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 3;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;
     android.support.v7.widget.SearchView searchview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.the_recycler_fragment,container,false);
         recyclerView=(RecyclerView) view.findViewById(R.id.recyler);
       // listView=(ListView) view.findViewById(R.id.lists);
       // searchView=(android.support.v7.widget.SearchView)view.findViewById(R.id.search2);
        Bundle args = getArguments();

        manager=new LinearLayoutManager(getActivity());
       // LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        //recyclerView.setItemViewCacheSize(1000);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerAdapter=new RecyclerAdapter(args.<Movie>getParcelableArrayList("VideoUrls"),getActivity());
        recyclerView.setAdapter(recyclerAdapter);




        setUserVisibleHint(true);
       // myAdapter=new MyAdapter(getActivity(),args.<Movie>getParcelableArrayList("VideoUrls"));
        /*listView.setAdapter(myAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
            listView.startNestedScroll(View.OVER_SCROLL_ALWAYS);
        }*/


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sview,menu);
        MenuItem item=menu.findItem(R.id.menusearch);

       searchview=(android.support.v7.widget.SearchView) item.getActionView();
        final int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) searchview.findViewById(android.support.v7.appcompat.R.id.search_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorwhite)));
        }
        searchview.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView v1=(ImageView) searchview.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
               //ImageView v3=(ImageView) searchview.findViewById(android.support.v7.appcompat.R.id.se);
                TextView v2=(TextView) searchview.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v1.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorwhite)));
                    v2.setTextColor(Color.WHITE);
                   // v3.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorwhite)));
                    searchview.setQueryHint("Search Movies");

                    searchview.setQueryHint(Html.fromHtml("<font color = #ffffff size=1 >" + getResources().getString(R.string.hintSearchMess) + "</font>"));
                    searchview.setDrawingCacheBackgroundColor(Color.WHITE);
                }
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setBackgroundColor(Color.TRANSPARENT);
                return false;

            }
        });

       /* int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) searchview.findViewById(searchImgId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        v.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
        searchview.setMaxWidth(Integer.MAX_VALUE);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    searchview.setBackground(new ColorDrawable(Color.BLACK));
                return false;
            }
        };

        *//*ImageView searchButton = (ImageView) searchview.findViewById(android.support.v7.appcompat.R.id.search_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorwhite)));
        }*//*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        searchview.setBackground(new ColorDrawable(Color.WHITE));
        item.expandActionView();*/
        //searchview.setLayoutParams(new android.app.ActionBar.LayoutParams(Gravity.LEFT));
        searchview.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });





    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser)
        {

        }
    }
}
