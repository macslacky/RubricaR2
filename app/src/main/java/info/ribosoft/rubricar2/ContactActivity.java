package info.ribosoft.rubricar2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.ribosoft.rubricar2.HttpConn.RecyclerLista;
import info.ribosoft.rubricar2.HttpConn.ResponsData;
import info.ribosoft.rubricar2.HttpConn.RetrofitListaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactActivity extends AppCompatActivity {
    private ArrayList<RecyclerLista> recyclerListaArray;
    private EditText edtModNome, edtModCognome, edtModTelefono;
    private String urlWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idContact;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Context context = getApplicationContext();
        urlWeb = context.getString(R.string.urlWeb);

        // creating new array list
        recyclerListaArray = new ArrayList<>();

        TextView txtModIdContatto = findViewById(R.id.txtIdModContatto);
        edtModNome = findViewById(R.id.edtModNome);
        edtModCognome = findViewById(R.id.edtModCognome);
        edtModTelefono = findViewById(R.id.edtModTelefono);

        Intent intent = getIntent();
        idContact = intent.getStringExtra("id_contact");
        txtModIdContatto.setText(idContact);
        leggiNominativo(idContact);

        Button btnModRubrica = findViewById(R.id.btnModRubrica);
        btnModRubrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnModSalva = findViewById(R.id.btnModSalva);
        btnModSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass our base url to the retrofit2 constructor
                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlWeb)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                // instantiate our retrofit API class
                RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);
                // submit data to update a record
                Call<ResponsData> call = retrofitListaAPI.getUpdate("2",
                    txtModIdContatto.getText().toString(), edtModNome.getText().toString(),
                    edtModCognome.getText().toString(), edtModTelefono.getText().toString());
                // asynchronously send the request
                call.enqueue(new Callback<ResponsData>() {
                    @Override
                    public void onResponse(Call<ResponsData> call, Response<ResponsData> response) {
                        // displays confirmation of record update
                        Toast.makeText(ContactActivity.this, response.body().getRisultato(),
                            Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponsData> call, Throwable t) {
                        Toast.makeText(ContactActivity.this, "ERRORE: " + t.toString(),
                            Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnModCancella = findViewById(R.id.btnModCancella);
        btnModCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass our base url to the retrofit2 constructor
                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlWeb)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                // instantiate our retrofit API class
                RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);
                // submit data to delete a record
                Call<ResponsData> call = retrofitListaAPI.getDelete("3",
                    txtModIdContatto.getText().toString());
                // asynchronously send the request
                call.enqueue(new Callback<ResponsData>() {
                    @Override
                    public void onResponse(Call<ResponsData> call, Response<ResponsData> response) {
                        // displays confirmation of record delete
                        Toast.makeText(ContactActivity.this, response.body().getRisultato(),
                            Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponsData> call, Throwable t) {
                        Toast.makeText(ContactActivity.this, "ERRORE: " + t.toString(),
                            Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void leggiNominativo(String idContact) {
        // pass our base url to the retrofit2 constructor
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(urlWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        // instantiate our retrofit API class
        RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);
        // requires fields from a record
        Call<ArrayList<RecyclerLista>> call = retrofitListaAPI.getContatto("1", idContact);
        // asynchronously send the request
        call.enqueue(new Callback<ArrayList<RecyclerLista>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerLista>> call,
                Response<ArrayList<RecyclerLista>> response) {
                if (response.isSuccessful()) {
                    // adds the data to our array list
                    recyclerListaArray = response.body();
                    // retrieves the data of the fields to display them
                    RecyclerLista rcContatto = recyclerListaArray.get(0);
                    edtModNome.setText(rcContatto.getNome());
                    edtModCognome.setText(rcContatto.getCognome());
                    edtModTelefono.setText(rcContatto.getTelefono());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerLista>> call, Throwable t) {
                Toast.makeText(ContactActivity.this, "ERRORE: " + t.toString(),
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}