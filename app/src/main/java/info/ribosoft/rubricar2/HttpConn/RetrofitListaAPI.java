package info.ribosoft.rubricar2.HttpConn;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// here we create the interface between our app and the php file that receives our commands
// and responds according to requests
public interface RetrofitListaAPI {
    @FormUrlEncoded
    @POST("addressbook.php")
    // requests the entire database organizing it as required by the RecyclerLista
    Call<ArrayList<RecyclerLista>> getAllLista(@Field("sel_menu") String sel_menu);

    @FormUrlEncoded
    @POST("addressbook.php")
    // requests a database record organizing it as required by RecyclerLista
    Call<ArrayList<RecyclerLista>> getContatto(@Field("sel_menu") String sel_menu,
        @Field("id_contact") String id_contact);

    @FormUrlEncoded
    @POST("addressbook.php")
    // adds a record in the database and returns the confirmation as a response
    Call<ResponsData> getInsert(@Field("sel_menu") String sel_menu,
        @Field("firstname") String firstname, @Field("surname") String surname,
        @Field("phone") String phone);

    @FormUrlEncoded
    @POST("addressbook.php")
    // modifies a record in the database and returns the confirmation as a response
    Call<ResponsData> getUpdate(@Field("sel_menu") String sel_menu,
        @Field("id_contact") String id_contact, @Field("firstname") String firstname,
        @Field("surname") String surname, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("addressbook.php")
    // deletes a record in the database and returns the confirmation as a response
    Call<ResponsData> getDelete(@Field("sel_menu") String sel_menu,
        @Field("id_contact") String id_contact);
}
