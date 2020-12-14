package pershay.bstu.woolfy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post implements Parcelable {

    public Post(String userId, String caption, int likesCount, String image) {
        UserId = userId;
        Caption = caption;
        LikesCount = likesCount;
        Image = image;
    }

    public User getUser() {
        return user;
    }

    @SerializedName("postId")
    public int PostId;

    @SerializedName("userId")
    public String UserId;

    @SerializedName("caption")
    public String Caption;

    @SerializedName("likesCount")
    public int LikesCount;

    @SerializedName("image")
    public String Image;

    @SerializedName("dateCreated")
    public Date DateCreated;

    @SerializedName("commentId")
    public int CommentId;

    @SerializedName("user")
    public User user;

    protected Post(Parcel in) {
        PostId = in.readInt();
        UserId = in.readString();
        Caption = in.readString();
        LikesCount = in.readInt();
        Image = in.readString();
        CommentId = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getCaption() {
        return Caption;
    }

    public String getImage() {
        return Image;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(PostId);
        dest.writeString(UserId);
        dest.writeString(Caption);
        dest.writeString(Image);
        dest.writeInt(LikesCount);
        dest.writeInt(CommentId);
        dest.writeParcelable(user, flags);
    }
}
