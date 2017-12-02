package com.example.user.coaltarproduction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 9/7/2017.
 */

public class CommentAdapter extends BaseAdapter {

    ArrayList<Comment> comments=new ArrayList<>();
    Context context;
    String name,prof_url;
    public CommentAdapter(Context context,ArrayList<Comment> comments,String name,String prof_url)
    {
        this.context=context;
        this.comments=comments;
        this.name=name;
        this.prof_url=prof_url;
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CircleImageView circleImageView;
        TextView commentname;
        TextView comment;

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.eachcomment,null);
        circleImageView=(CircleImageView)convertView.findViewById(R.id.circlecommentimg);
        commentname=(TextView) convertView.findViewById(R.id.commentersname);
        comment=(TextView) convertView.findViewById(R.id.onecomment);

        comment.setText(comments.get(position).getComment());
        Picasso.with(context).load(prof_url).into(circleImageView);
        commentname.setText(name);
        return convertView;
    }
}
