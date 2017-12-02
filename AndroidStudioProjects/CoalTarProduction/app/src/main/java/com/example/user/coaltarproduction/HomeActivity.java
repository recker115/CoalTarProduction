package com.example.user.coaltarproduction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Integer> thumbnails=new ArrayList<>();
    ArrayList<Movie> movies=new ArrayList<>();
    ArrayList<Movie> moviesnew=new ArrayList<>();
    ArrayList<Movie> playlist=new ArrayList<>();
   ArrayList<ArrayList<Movie>> movieset=new ArrayList<ArrayList<Movie>>();
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    ImageView image;
    ActionBarDrawerToggle toggle;

    Toolbar toolbar;

    public static String[] videolinks= {"xPgLaYHZNdQ","S6FmMwU","T9dG8nFo374","G-7WyU797pw&t=28s","EcCsgm1sT1w&t=3s","lojKaaSsSTA&t=4s",
            "mtryAonVlLk","MhJ7aEsUSG0","nY0SpQbPsTE","vqY7xGm8tbE","QbVAKR-FUZM"};
    String[] recentvids={};
    public static SearchView searchView;
    String[] vid_description={"LASHYA","SUICIDE - A Tale of Two Friends","WHO ARE YOU teaser","DEBATE","C O N S C I E N C E (2017)","THE HABIT \"অভ্যেস\"","DOCUMENTRY ON VANGARH FORT ","The Habit, First Look",
            "MATIVRAM","RING RING","BATIKBABU"};
    int[] vid_img={R.drawable.vid1_img,R.drawable.vid2_img,R.drawable.vid4_img,R.drawable.vid5_img,R.drawable.vid6_img,
            R.drawable.vid7_img,R.drawable.vid8_img,R.drawable.vid9_img,R.drawable.vid10_img,R.drawable.vid11_img,R.drawable.vid12_img};
    ArrayList<String> videolinka=new ArrayList<>();
    ThePagerAdapter thePagerAdapter;
    TextView textTop;
    TabItem tab1,tab2,tab3;
    private NavigationView navigationView;
    TextView name;
    private DrawerLayout drawerlayout;
    ImageView likebutton;
    View view1,view2,view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //searchView=(SearchView) findViewById(R.id.search);
        FragmentManager fm=getSupportFragmentManager();
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         //tab1= (TabItem)findViewById(R.id.tab1);
        //tab2= (TabItem)findViewById(R.id.tab2);
        //tab3= (TabItem)findViewById(R.id.tab3);
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        drawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        shared=getSharedPreferences("LoginVer",MODE_PRIVATE);
        toggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close);
       // HoldsTheLibrary.videolinks=this.videolinks;

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
                    drawerlayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerlayout.openDrawer(GravityCompat.START);
                }
            }
        });
        if (toolbar.getChildAt(0) instanceof ImageButton) {
            Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(0).getLayoutParams();
            lp.gravity = Gravity.CENTER_VERTICAL;
        }
        toggle.syncState();

        editor=shared.edit();
        View headerView = navigationView.inflateHeaderView(R.layout.header);
        image= (ImageView)headerView.findViewById(R.id.prof);

        if (shared.getString("photo","")==null || shared.getString("photo","").matches(""))
            image.setImageResource(R.drawable.user1_img);
        else
        {
            Log.e("photo",shared.getString("photo",""));
            Picasso.with(this)
                    .load(shared.getString("photo",""))
                    .resize(500, 500)
                    .centerCrop()
                    .into(image);
        }
        name=(TextView) headerView.findViewById(R.id.name);
        if (shared.getString("name","")==null || shared.getString("name","").equals(""))
            name.setText("");
        else
        {
            Log.e("nameshared",shared.getString("name",""));
            name.setText(shared.getString("name",""));
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    /*case R.id.item_1:

                        break;
                    case R.id.item_2:
                        break;
                    case R.id.item_3:
                        break;
                    case R.id.item_4:
                        break;
                    case R.id.item_5:
                        Intent intent=new Intent(HomeActivity.this,HelpActivity.class);
                        startActivity(intent);
                        break;*/
                    case R.id.item_6:
                        editor.putString("Mobile","");
                        editor.putString("name","");
                        editor.putString("photo","");
                        editor.apply();
                        Intent intent2=new Intent(HomeActivity.this,Login.class);
                        startActivity(intent2);
                        break;

                }


                return false;
            }
        });


        for (int i=0;i<11;i++)
        {
            videolinka.add(videolinks[i]);
        }
        for (int i=0;i<11;i++)
        {
            Movie movie=new Movie(vid_description[i],vid_img[i],videolinks[i]);
            movies.add(movie);
        }
        HoldsTheLibrary.totalmovies=movies;
        for (int i=0;i<4;i++)
        {
            Movie movie=new Movie(vid_description[i],vid_img[i],videolinks[i]);
            moviesnew.add(movie);
        }
        movieset.add(movies);
        movieset.add(moviesnew);



        tabLayout=(TabLayout) findViewById(R.id.tablayout);
       view1 = getLayoutInflater().inflate(R.layout.tab1, null);
        //view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.my1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));


      view2 = getLayoutInflater().inflate(R.layout.tab2, null);
        //view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.my2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));


       view3 = getLayoutInflater().inflate(R.layout.tab3, null);
        //view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.my3);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        textTop=(TextView) findViewById(R.id.texttop);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        thePagerAdapter=new ThePagerAdapter(fm,HomeActivity.this,movies,moviesnew);
        viewPager.setAdapter(thePagerAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition())
                {
                    case 0:

                        ((ImageView)view1.findViewById(R.id.tab1img)).setImageResource(R.drawable.ic_home_black_24dp);
                        tabLayout.getTabAt(0).setCustomView(view1);
                        ((ImageView)view2.findViewById(R.id.tab2img)).setImageResource(R.drawable.ic_whatshot_black1_24dp);
                        tabLayout.getTabAt(1).setCustomView(view2);
                        ((ImageView)view3.findViewById(R.id.tab3img)).setImageResource(R.drawable.ic_video_library_black1_24dp);
                        tabLayout.getTabAt(2).setCustomView(view3);
                        viewPager.setCurrentItem(0);
                        textTop.setText("Home");

                        break;
                    case 1:
                        ((ImageView)view2.findViewById(R.id.tab2img)).setImageResource(R.drawable.ic_whatshot_black_24dp);
                        tabLayout.getTabAt(1).setCustomView(view2);
                        ((ImageView)view1.findViewById(R.id.tab1img)).setImageResource(R.drawable.ic_home_black1_24dp);
                        tabLayout.getTabAt(0).setCustomView(view1);
                        ((ImageView)view3.findViewById(R.id.tab3img)).setImageResource(R.drawable.ic_video_library_black1_24dp);
                        tabLayout.getTabAt(2).setCustomView(view3);

                        viewPager.setCurrentItem(1);
                        textTop.setText("Recent Videos");


                        break;
                    case 2:
                        ((ImageView)view3.findViewById(R.id.tab3img)).setImageResource(R.drawable.ic_video_library_black_24dp);
                        tabLayout.getTabAt(2).setCustomView(view3);
                        ((ImageView)view2.findViewById(R.id.tab2img)).setImageResource(R.drawable.ic_whatshot_black1_24dp);
                        tabLayout.getTabAt(1).setCustomView(view2);
                        ((ImageView)view1.findViewById(R.id.tab1img)).setImageResource(R.drawable.ic_home_black1_24dp);
                        tabLayout.getTabAt(0).setCustomView(view1);
                        viewPager.setCurrentItem(2);
                        textTop.setText("Library");
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
