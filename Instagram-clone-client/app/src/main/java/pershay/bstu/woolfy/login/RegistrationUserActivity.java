package pershay.bstu.woolfy.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationUserActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    AppCompatButton btnUserData;
    TextView linkBackToLogin;
    ProgressBar registerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); //disable screenshots

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnUserData = findViewById(R.id.btnAddUserdata);
        linkBackToLogin = findViewById(R.id.linkBackToLogin);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        registerProgressBar.setVisibility(View.INVISIBLE);
        linkBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmailValid(etEmail.getText().toString())){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegistrationUserActivity.this);
                    dlgAlert.setMessage("Please, select other email");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                else{
                    addUser();
                }

            }
        });

    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    private void addUser(){
        registerProgressBar.setVisibility(View.VISIBLE);
       final String email = etEmail.getText().toString();
       final String username = etUsername.getText().toString();
       final String password = etPassword.getText().toString();

       User user = new User(username, email, password);

       JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

       Call<User> call = jsonPlaceHolderApi.addUser(user);

       call.enqueue(new Callback<User>() {
           @Override
           public void onResponse(Call<User> call, Response<User> response) {
               if(response.code() == 400){
                   AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegistrationUserActivity.this);
                   dlgAlert.setMessage("Please, select other username");
                   dlgAlert.setTitle("Error");
                   dlgAlert.setPositiveButton("OK", null);
                   dlgAlert.setCancelable(true);
                   dlgAlert.create().show();
                   return;
               }

               Intent intent = new Intent(RegistrationUserActivity.this, RegistrationUserDataActivity.class);
               intent.putExtra("id", response.body().UserId);
               startActivity(intent);
               registerProgressBar.setVisibility(View.INVISIBLE);
           }

           @Override
           public void onFailure(Call<User> call, Throwable t) {
               AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(RegistrationUserActivity.this);
               dlgAlert.setMessage("Error: " + t.getMessage());
               dlgAlert.setTitle("Fail");
               dlgAlert.setPositiveButton("OK", null);
               dlgAlert.setCancelable(true);
               dlgAlert.create().show();
           }
       });
    }
}
