package pershay.bstu.woolfy.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.main.MainActivity;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.utils.SqliteHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton btnLogin;
    TextView linkSignup, tvUserid, tvUsername;
    EditText etUsername, etPassword;
    SqliteHelper helper;
    ProgressBar loginProgressBar;

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); //disable screenshots

        helper = new SqliteHelper(this);
        btnLogin = findViewById(R.id.btnLogin);
        linkSignup = findViewById(R.id.linkSignup);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void checkCredentials(){
        loginProgressBar.setVisibility(View.VISIBLE);
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);
        Call<User> call = jsonPlaceHolderApi.checkCredentials(username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(LoginActivity.this, "Response: "+response.code(), Toast.LENGTH_SHORT).show();
                if(response.code() == 200){
                    User user = response.body();
                    boolean isInserted = helper.insertData(user.UserId, user.Username);
                    if(isInserted){
                        Toast.makeText(LoginActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Insert error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
                    dlgAlert.setMessage("Incorrect! Check your username or password.");
                    dlgAlert.setTitle("Fail");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                loginProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
                dlgAlert.setMessage("Error: " + t.getMessage());
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }
}
