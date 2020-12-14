package pershay.bstu.woolfy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {

    public User(String username, String email, String password) {
        Username = username;
        Email = email;
        Password = password;
    }

    @SerializedName("userId")
    public String UserId;

    @SerializedName("username")
    public String Username;

    @SerializedName("email")
    public String Email;

    @SerializedName("password")
    public String Password;

    public String getUserId() {
        return UserId;
    }

    public String getUsername() {
        return Username;
    }

    public User() {

    }

    protected User(Parcel in) {
        UserId = in.readString();
        Username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserId);
        dest.writeString(Username);
    }
}
