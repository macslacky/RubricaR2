package info.ribosoft.rubricar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import info.ribosoft.rubricar2.HttpConn.RecyclerItemClickListener;
import info.ribosoft.rubricar2.HttpConn.RecyclerLista;
import info.ribosoft.rubricar2.HttpConn.RecyclerListaAdapter;

import info.ribosoft.rubricar2.HttpConn.ResponsData;
import info.ribosoft.rubricar2.HttpConn.RetrofitListaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<RecyclerLista> recyclerListaArray;
    private RecyclerListaAdapter recyclerListaAdapter;
    private RecyclerView courseAddressBook;
    private String urlWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        urlWeb = context.getString(R.string.urlWeb);

        // creating new array list
        recyclerListaArray = new ArrayList<>();

        courseAddressBook = findViewById(R.id.rcwLista);
        courseAddressBook.addOnItemTouchListener(
            new RecyclerItemClickListener(context, courseAddressBook,
                new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                    RecyclerLista rcDati = recyclerListaArray.get(position);
                    intent.putExtra("id_contact", rcDati.getId_contatto());
                    startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    // long click
                }
            })
        );
        leggiLista(context);
    }

    private void leggiLista(Context context) {
        // pass our base url to the retrofit2 constructor
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(urlWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        // instantiate our retrofit API class
        RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);
        // require all database records
        Call<ArrayList<RecyclerLista>> call = retrofitListaAPI.getAllLista("0");
        // asynchronously send the request
        call.enqueue(new Callback<ArrayList<RecyclerLista>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerLista>> call,
                Response<ArrayList<RecyclerLista>> response) {
                if (response.isSuccessful()) {
                    // adds the data to our array list
                    recyclerListaArray = response.body();
                    for (int i=0; i<recyclerListaArray.size(); i++) {
                        recyclerListaAdapter = new RecyclerListaAdapter(recyclerListaArray,
                            context);
                        // set the layout manager for our recycler view
                        LinearLayoutManager manager = new LinearLayoutManager(context);
                        // setting layout manager for our recycler view
                        courseAddressBook.setLayoutManager(manager);
                        // set the adapter to our recycler view
                        courseAddressBook.setAdapter(recyclerListaAdapter);
                    }
                } else
                    Toast.makeText(context, "ERRORE Response " + response.body().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerLista>> call, Throwable t) {
                Toast.makeText(context, "ERRORE " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // link the menu to the activity
    public boolean onCreateOptionsMenu(Menu menu) {
        // this adds items to the action bar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // the system calls the method when the user selects a menu item
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // identifies the item selected by the user
        int id = menuItem.getItemId();

        if (id==R.id.mainAggiungi) {
            Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
            startActivity(intent);
        } else if (id==R.id.mainInfo) {
            Log.i("******MAIN MENU******", "******INFO APP******");
        } else {
            Log.i("******MAIN MENU******", "******ALTRO******");
        }
        return false;
    }
}