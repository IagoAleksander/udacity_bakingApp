package com.iaz.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable
{
    private String videoURL;

    private String description;

    private String id;

    private String shortDescription;

    private String thumbnailURL;

    public String getVideoURL ()
    {
        return videoURL;
    }

    public void setVideoURL (String videoURL)
    {
        this.videoURL = videoURL;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getShortDescription ()
    {
        return shortDescription;
    }

    public void setShortDescription (String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL ()
    {
        return thumbnailURL;
    }

    public void setThumbnailURL (String thumbnailURL)
    {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [videoURL = "+videoURL+", description = "+description+", id = "+id+", shortDescription = "+shortDescription+", thumbnailURL = "+thumbnailURL+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeString(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.thumbnailURL);

    }

    private Step(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readString();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
