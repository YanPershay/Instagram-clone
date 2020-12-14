package pershay.bstu.woolfy.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.utils.SqliteHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText etUsername, etFirstname, etLastname, etAge, etDescription, etCity;
    private SqliteHelper helper;
    private String userId;
    private ImageView imgSaveChanges, imgBackArrow;

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if(helper == null)
            helper = new SqliteHelper(getApplicationContext());

        etUsername = findViewById(R.id.etUsername);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etAge = findViewById(R.id.etAge);
        etDescription = findViewById(R.id.etDescription);
        etCity = findViewById(R.id.etCity);
        imgSaveChanges = findViewById(R.id.saveChanges);
        imgBackArrow = findViewById(R.id.imgBackArrow);

        setDataToEt();
        getUserId();

        imgSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setDataToEt(){
        Bundle extras = getIntent().getExtras();
        etFirstname.setText(extras.getString(getString(R.string.firstname)));
        etLastname.setText(extras.getString(getString(R.string.lastname)));
        etDescription.setText(extras.getString(getString(R.string.description)));
        etCity.setText(extras.getString(getString(R.string.city)));
        etAge.setText(extras.getString(getString(R.string.age)));
    }

    public void getUserId(){
        Cursor res = helper.getData();
        while(res.moveToNext()){
            userId = res.getString(0);
        }
    }

    private void updateUserData(){
        final String lastname = etLastname.getText().toString();
        final String firstname = etFirstname.getText().toString();
        final int age = Integer.parseInt(etAge.getText().toString());
        final String city = etCity.getText().toString();
        final String description = etDescription.getText().toString();

        UserData userData = new UserData(userId, firstname, lastname, age, description, city, 0, 0);

        JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

        Call<ResponseBody> call = jsonPlaceHolderApi.updateUserdata(userId, userData);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(EditProfileActivity.this, "Response : "+response.code(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getApplicationContext());
                dlgAlert.setMessage("Error: " + t.getMessage());
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }
}
