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
    public String image;

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        setIdRecipes(in.readString());
        setTitle(in.readString());
        setCategory(in.readString());
        setIngredients(in.readString());
        setDescription(in.readString());
        setImage(in.readString());
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getIdRecipes());
        dest.writeString(getTitle());
        dest.writeString(getCategory());
        dest.writeString(getIngredients());
        dest.writeString(getDescription());
        dest.writeString(getImage());
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdRecipes() { return idRecipes;  }

    public void setIdRecipes(String idRecipes) { this.idRecipes = idRecipes; }
}
