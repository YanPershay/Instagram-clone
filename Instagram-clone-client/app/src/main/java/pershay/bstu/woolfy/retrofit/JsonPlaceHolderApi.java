package pershay.bstu.woolfy.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.models.UserData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("posts/{id}")
    Call<List<Post>> downloadImage(@Path("id")String id);

    @GET("userdata/{id}")
    Call<UserData> getUserDataById(@Path("id")String userId);

    @GET("user/{id}")
    Call<User> getUserById(@Path("id")String userId);

    @GET("/user/username={username}&password={password}")
    Call<User> checkCredentials(@Path("username")String username, @Path("password")String password);

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    Call<Post> uploadImage(@Body Post post);

    @POST("user")
    Call<User> addUser(@Body User user);

    @POST("userdata")
    Call<UserData> addUserData(@Body UserData userData);

    @PUT("userdata/{id}")
    Call<ResponseBody> updateUserdata(@Path("id")String id, @Body UserData userData);

    @DELETE("posts/{postId}")
    Call<ResponseBody> deletePost(@Path("postId")int postId);

}
