package com.mercy.idictionary.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mercy.idictionary.Helpers.GestureHelper;
import com.mercy.idictionary.R;
import com.mercy.idictionary.models.Word;

public class DetailActivity extends AppCompatActivity {

    private GestureHelper gestureHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set button back visible
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_detail);
        TextView contents = findViewById(R.id.tv_contents);
        LinearLayout layout = findViewById(R.id.detail_layout);
        setGestureHelper();
        layout.setOnTouchListener(gestureHelper);

        Word word = (Word) getIntent().getSerializableExtra("word");
        setTitle(word.getTitleFire());
        contents.setText(word.getContentsFire());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fix_enter_exit, R.anim.exit_slide_to_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void setGestureHelper(){
        gestureHelper = new GestureHelper(getApplicationContext()){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                onBackPressed();
            }
        };
    }

}
