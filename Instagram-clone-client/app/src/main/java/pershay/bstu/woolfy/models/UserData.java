package pershay.bstu.woolfy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class UserData implements Parcelable {

    @SerializedName("userId")
    public String UserId;

    public UserData(String userId, String firstname, String lastname, int age, String description,
                    String city, int followers, int follows) {
        UserId = userId;
        Firstname = firstname;
        Lastname = lastname;
        Age = age;
        Description = description;
        City = city;
        Followers = followers;
        Follows = follows;
    }

    @SerializedName("firstname")
    public String Firstname;

    @SerializedName("lastname")
    public String Lastname;

    @SerializedName("age")
    public int Age;

    @SerializedName("description")
    public String Description;

    @SerializedName("city")
    public String City;

    @SerializedName("followers")
    public int Followers;

    protected UserData(Parcel in) {
        UserId = in.readString();
        Firstname = in.readString();
        Lastname = in.readString();
        Age = in.readInt();
        Description = in.readString();
        City = in.readString();
        Followers = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        Follows = in.readInt();
        IdData = in.readInt();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @SerializedName("user")
    public User user;

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public int getAge() {
        return Age;
    }

    public String getDescription() {
        return Description;
    }

    public String getCity() {
        return City;
    }

    public int getFollowers() {
        return Followers;
    }

    public int getFollows() {
        return Follows;
    }

    @SerializedName("follows")
    public int Follows;

    @SerializedName("idData")
    public int IdData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(UserId);
        dest.writeString(Firstname);
        dest.writeString(Lastname);
        dest.writeInt(Age);
        dest.writeString(Description);
        dest.writeString(City);
        dest.writeInt(Followers);
        dest.writeParcelable(user, flags);
        dest.writeInt(Follows);
        dest.writeInt(IdData);
    }

}


