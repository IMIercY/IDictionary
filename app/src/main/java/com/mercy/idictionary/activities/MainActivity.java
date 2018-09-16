package com.mercy.idictionary.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mercy.idictionary.R;
import com.mercy.idictionary.adapters.WordAdapter;
import com.mercy.idictionary.database.DatabaseUtils;
import com.mercy.idictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WordAdapter.ItemClickListener, SearchView.OnQueryTextListener {

    private WordAdapter adapter;
    private List<Word> wordsToShow = new ArrayList<>();
    private List<Word> allWords = new ArrayList<>();
    private ContentLoadingProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        SearchView searchView = findViewById(R.id.main_search_view);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        adapter = new WordAdapter(allWords);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(this);

        if (isFirstRun()) {
            Log.d("app", "is first run = " + isFirstRun());
            loadDataFromFirebase();
        } else {
            allWords = DatabaseUtils.getInstance(getApplicationContext()).getAllItems();
            adapter.setData(allWords);
        }

    }

    public void loadDataFromFirebase() {
        progressBar.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        db.collection("words").orderBy("titleFire").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        allWords.clear();
                        for (QueryDocumentSnapshot query : querySnapshot) {
                            allWords.add(query.toObject(Word.class));
                        }
                        adapter.setData(allWords);
                        progressBar.hide();
                        saveDataToLocalDatabase(allWords);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.hide();
                        Toast.makeText(getApplicationContext(), R.string.err_loading_word, Toast.LENGTH_LONG).show();
                        Log.d("app", "addOnFailureListener ", e.fillInStackTrace());
                    }
                });
    }

    public void saveDataToLocalDatabase(List<Word> words) {
        DatabaseUtils.getInstance(getApplicationContext()).insertAllItems(words);
    }

//    private boolean isNetworkConnected() {
//        boolean connected = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            isConnected = connectivityManager.getActiveNetworkInfo().isConnected();
//        }
//        return connected;
//    }

    public boolean isFirstRun() {
        boolean firstRun = false;
        SharedPreferences preferences = getSharedPreferences("com.mercy.idictionary", MODE_PRIVATE);
        if (preferences.getBoolean("firstRun", true)) {
            preferences.edit().putBoolean("firstRun", false).apply();
            firstRun = true;
        }
        return firstRun;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (wordsToShow.size() != 0) {
            startActivity(new Intent(getApplicationContext(), DetailActivity.class)
                    .putExtra("word", wordsToShow.get(position)));
            overridePendingTransition(R.anim.enter_slide_to_left, R.anim.fix_enter_exit);
        }
        Log.d("app", " click position " + position + "/ adapter size " + wordsToShow.size());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("app", "onNewIntent "+query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        wordsToShow = adapter.filter(allWords, query);
        adapter.setData(wordsToShow);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        wordsToShow = adapter.filter(allWords, newText);
        adapter.setData(wordsToShow);
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wordsToShow.size() == 0) {
            wordsToShow = DatabaseUtils.getInstance(getApplicationContext()).getAllItems();
            adapter.setData(wordsToShow);
        }
    }

    // end of file
}
