package no.ntnu.FantApp2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface FantAPI {
    @FormUrlEncoded
    @POST("auth/create")
    Call<ResponseBody> createUser(@Field("email") String email,
                                  @Field("uid") String uid,
                                  @Field("pwd") String password,
                                  @Field("firstName") String firstName,
                                  @Field("lastName") String lastName);

    @FormUrlEncoded
    @POST("fant/addItem")
    Call<ResponseBody> addItem(@Field("title") String title,
                               @Field("description") String description,
                               @Field("price") String price);

    @FormUrlEncoded
    @PUT("fant/purchase")
    Call<ResponseBody> purchase(@Field("itemid") String itemid);

    @GET("auth/login")
    Call<ResponseBody> login(@Query("uid") String uid, @Query("pwd") String password);

    @GET("fant/items")
    Call<ResponseBody> listItems();

    @GET("fant/users")
    Call<ResponseBody> listUsers();
}
