package com.example.user.coaltarproduction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 8/24/2017.
 */

public class Movie implements Parcelable {
    String moviename;
    int thumbnail;
    String url;

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Movie(String moviename, int thumbnail,String url)
    {
        this.moviename=moviename;
        this.thumbnail=thumbnail;
        this.url=url;
    }

    protected Movie(Parcel in) {
        moviename = in.readString();
        thumbnail = in.readInt();
        url=in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(moviename);
        parcel.writeInt(thumbnail);
    }
}
