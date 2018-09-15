package com.mercy.idictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mercy.idictionary.Model.FirebaseModel;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_detail);
        TextView contents = findViewById(R.id.tv_contents);

        Gson gson = new Gson();
        String getData = getIntent().getStringExtra("data");
        FirebaseModel firebaseModel = gson.fromJson(getData, FirebaseModel.class);
        contents.setText(firebaseModel.getContentsFire());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_slide_to_right, R.anim.exit_slide_to_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//        @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
