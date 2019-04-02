package com.iaz.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable
{
    private String image;

    private String servings;

    private String name;

    private ArrayList<Ingredient> ingredients;

    private String id;

    private ArrayList<Step> steps;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getServings ()
    {
        return servings;
    }

    public void setServings (String servings)
    {
        this.servings = servings;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients ()
    {
        return ingredients;
    }

    public void setIngredients (ArrayList<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public ArrayList<Step> getSteps ()
    {
        return steps;
    }

    public void setSteps (ArrayList<Step> steps)
    {
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", servings = "+servings+", name = "+name+", ingredients = "+ingredients+", id = "+id+", steps = "+steps+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.servings);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
//        dest.writeParcelableArray(ingredients, flags);
//        dest.writeString(str);
    }

    private Recipe (Parcel in) {
        this.image = in.readString();
        this.servings = in.readString();
        this.name = in.readString();
        this.id = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
//        in.readTypedList(ingredients, Ingredient.CREATOR);
//        in.readTypedList(steps, Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}