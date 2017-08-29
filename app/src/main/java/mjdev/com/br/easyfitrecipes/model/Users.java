package mjdev.com.br.easyfitrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luana.martinez on 26/07/2017.
 */

public class Users implements Parcelable {

    public String guid;
    public String usuario;
    public String senha;

    public Users() {
    }

    protected Users(Parcel in) {
        setGuid(in.readString());
        setUsuario(in.readString());
        setSenha(in.readString());
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getGuid());
        dest.writeString(getUsuario());
        dest.writeString(getSenha());
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
