package pershay.bstu.woolfy.security;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.main.MainActivity;

public class CreatePasswordActivity extends AppCompatActivity {

    EditText editText, editText2;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        editText = findViewById(R.id.editTextTextPassword);
        editText2 = findViewById(R.id.editTextTextPassword2);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = editText.getText().toString();
                String pass2 = editText2.getText().toString();

                if(pass1.equals("") || pass2.equals("")){
                    Toast.makeText(CreatePasswordActivity.this, "Пароль не введен!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass1.equals(pass2)){
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", pass1);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(CreatePasswordActivity.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}