package com.example.user.coaltarproduction;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by User on 9/6/2017.
 */

public class CustomDialog extends Dialog {

    ListView commentlist;
    Context context;
    EditText commentedit;
    Firebase root,movie,a;
    ImageView commentbutton;
    String moviename;
    int i=1,j=1;
    Comment thiscomment;
    String name="Name";
    int image=R.drawable.img1_subs;
    ArrayList<Comment> comments=new ArrayList<>();
    Dialog dialog;
    String onecomment="I am a dummy comment by a dummy user who loves to comment in this app but he is brainless and dont know anything about android";
    SharedPreferences sharedPreferences;
    String accountname,prof_url;
    String prof_name;

    public CustomDialog(@NonNull Context context,String name) {
        super(context);
        this.context=context;
        this.moviename=name;
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        root.setAndroidContext(context);
        root=new Firebase("https://orderfoodfirebase.firebaseio.com/");

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.progress);
        commentedit=(EditText) findViewById(R.id.commentedit);
        commentbutton=(ImageView) findViewById(R.id.postcomment);
        sharedPreferences=context.getSharedPreferences("LoginVer",Context.MODE_PRIVATE);

        if (!(sharedPreferences.getString("name","").matches("")))
        accountname=sharedPreferences.getString("name","");
        if (!(sharedPreferences.getString("photo","").matches("")))
        prof_url=sharedPreferences.getString("photo","");
        commentlist=(ListView) findViewById(R.id.commentlist);


        /*Thread mythread=new Thread(){

            @Override
            public void run() {
                try {
                    dialog.show();
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
        dialog.hide();*/
        showdata();

      /* for (int k=0;k<11;k++) {
           thiscomment = new Comment(name, onecomment, image);
           comments.add(thiscomment);
       }
        CommentAdapter commentAdapter=new CommentAdapter(context,comments);
        commentlist.setAdapter(commentAdapter);*/


        commentbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("abc","click");
                addData();
            }
        });

    }

    public void showdata()
    {

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(moviename))
                {
                    movie=root.child(moviename);
                    movie.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            i=(int)dataSnapshot.getChildrenCount();
                            for (j =j;j<=i;j++)
                            {
                                Map<String, String> map = dataSnapshot.getValue(Map.class);
                                onecomment=String.valueOf(map.get("Comment"+String.valueOf(j)));
                                prof_name=String.valueOf(map.get("Commenter_name"+String.valueOf(i)));
                                thiscomment=new Comment(prof_name,onecomment,prof_url);
                                if (!((onecomment.equalsIgnoreCase("#!223323!$1@")) || onecomment.equals(null)))
                                {
                                    comments.add(thiscomment);
                                }

                                Log.e("comment"+j,onecomment);
                            }
                            CommentAdapter commentAdapter=new CommentAdapter(context,comments,accountname,prof_url);
                            commentlist.setAdapter(commentAdapter);
                            Log.e("Comments list size",""+comments.size());

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });



                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    public void addData()
    {

        if (! commentedit.getText().toString().matches("")) {
            movie = root.child(moviename);

            movie.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    i++;
                    j=i;
                    Firebase commentchilds = movie.child("Comment" + String.valueOf(i));
                    Firebase commentchildscommenters_name=movie.child("Commenter_name"+String.valueOf(i));
                    commentchilds.setValue(commentedit.getText().toString());
                    commentchildscommenters_name.setValue(accountname);
                    Log.e("Added comment",commentedit.getText().toString());
                    commentedit.setText("");
                    showdata();


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    }
}
