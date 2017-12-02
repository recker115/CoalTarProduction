package com.example.user.coaltarproduction;

/**
 * Created by User on 9/6/2017.
 */

public class Comment {
    String name;
    String comment;
    String image;
    Comment(String name,String comment,String image)
    {
        this.name=name;
        this.image=image;
        this.comment=comment;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
