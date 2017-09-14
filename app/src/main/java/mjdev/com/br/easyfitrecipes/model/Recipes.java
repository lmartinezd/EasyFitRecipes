package mjdev.com.br.easyfitrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luana.martinez on 25/07/2017.
 */

public class Recipes implements Parcelable {

    public String idRecipes;
    public String title;
    public String category;
    public String ingredients;
    public String description;
    public byte[] image;

    public Recipes() {}

    public Recipes(Parcel in) {
        this.idRecipes = in.readString();
        this.title = in.readString();
        this.category = in.readString();
        this.ingredients = in.readString();
        this.description = in.readString();
        this.image = in.createByteArray();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idRecipes);
        dest.writeString(this.title);
        dest.writeString(this.category);
        dest.writeString(this.ingredients);
        dest.writeString(this.description);
        dest.writeByteArray(this.image);
    }

    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel source) {return new Recipes(source);}

        @Override
        public Recipes[] newArray(int size) {return new Recipes[size];}
    };

    public String getIdRecipes() {
        return idRecipes;
    }

    public void setIdRecipes(String idRecipes) {
        this.idRecipes = idRecipes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
