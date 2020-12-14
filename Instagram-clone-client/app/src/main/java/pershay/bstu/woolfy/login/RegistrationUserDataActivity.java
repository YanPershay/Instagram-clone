package pershay.bstu.woolfy.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationUserDataActivity extends AppCompatActivity {

    EditText etFirstname, etLastname, etAge, etDescription, etCity;
    AppCompatButton btnAddUserdata;
    ProgressBar registerProgressBar;

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_data);

        etLastname = findViewById(R.id.etLastname);
        etFirstname= findViewById(R.id.etFirstname);
        etAge = findViewById(R.id.etAge);
        etDescription = findViewById(R.id.etDescription);
        etCity = findViewById(R.id.etCity);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        registerProgressBar.setVisibility(View.INVISIBLE);
        btnAddUserdata = findViewById(R.id.btnAddUserdata);

        btnAddUserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserData();
            }
        });
    }

    private void addUserData(){
        registerProgressBar.setVisibility(View.VISIBLE);
        final String lastname = etLastname.getText().toString();
        final String firstname = etFirstname.getText().toString();
        final int age = Integer.parseInt(etAge.getText().toString());
        final String city = etCity.getText().toString();
        final String description = etDescription.getText().toString();

        Bundle extras = getIntent().getExtras();
        final String userId = extras.getString("id");

        UserData userData = new UserData(userId, firstname, lastname, age, description, city, 0, 0);

        JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);
        Call<UserData> call = jsonPlaceHolderApi.addUserData(userData);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegistrationUserDataActivity.this);
                dlgAlert.setMessage("You successfully registered");
                dlgAlert.setTitle("Nice!");
                dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RegistrationUserDataActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
                registerProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegistrationUserDataActivity.this);
                dlgAlert.setMessage("Error: " + t.getMessage());
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                registerProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
