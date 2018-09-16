package com.bitm.basis.retrofitexample;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitm.basis.retrofitexample.api_response.UserResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    ImageView imageView;
    TextView nameView, phoneView, emailView;
    String name, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameView = findViewById(R.id.name_view);
        phoneView = findViewById(R.id.phone_no_view);
        emailView = findViewById(R.id.email_view);
        imageView = findViewById(R.id.image_view);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
    }

    public void getUser(View view) {
        dialog.show();
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);

        Call<UserResponse> call = apiEndPoint.getUser();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                       // Toast.makeText(MainActivity.this, userResponse.getResults().get(0).getEmail(), Toast.LENGTH_SHORT).show();
                        name = userResponse.getResults().get(0).getName().getTitle()+" "+ userResponse.getResults().get(0).getName().getFirst()+" "+ userResponse.getResults().get(0).getName().getLast();
                        phone = String.valueOf(userResponse.getResults().get(0).getDob().getAge());
                        email = userResponse.getResults().get(0).getEmail();

                        nameView.setText(name);
                        phoneView.setText(phone);
                        emailView.setText(email);
                        Picasso.get().load( userResponse.getResults().get(0).getPicture().getLarge()).into(imageView);

                    }

                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
