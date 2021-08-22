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

import info.ribosoft.rubricar2.HttpConn.ResponsData;
import info.ribosoft.rubricar2.HttpConn.RetrofitListaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        EditText edtInsNome = findViewById(R.id.edtNome);
        EditText edtInsCognome = findViewById(R.id.edtCognome);
        EditText edtInsTelefono = findViewById(R.id.edtTelefono);

        Button btnInsSalva = findViewById(R.id.btnInsSalva);
        btnInsSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                String urlWeb = context.getString(R.string.urlWeb);

                // pass our base url to the retrofit2 constructor
                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlWeb)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                // instantiate our retrofit API class
                RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);
                // submit fields for a new record
                Call<ResponsData> call = retrofitListaAPI.getInsert("4",
                    edtInsNome.getText().toString(),
                    edtInsCognome.getText().toString(),
                    edtInsTelefono.getText().toString());
                // asynchronously send the request
                call.enqueue(new Callback<ResponsData>() {
                    @Override
                    public void onResponse(Call<ResponsData> call, Response<ResponsData> response) {
                        // displays confirmation of record insertion
                        Toast.makeText(InsertActivity.this, response.body().
                            getRisultato(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponsData> call, Throwable t) {
                        Toast.makeText(InsertActivity.this, "ERRORE: " + t.toString(),
                            Toast.LENGTH_SHORT).show();
                    }
                });
                // return to main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}