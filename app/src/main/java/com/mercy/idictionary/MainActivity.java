package com.mercy.idictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mercy.idictionary.Adapter.MainAdapter;
import com.mercy.idictionary.Model.FirebaseModel;
import com.mercy.idictionary.Model.MainModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private List<MainModel> data = new ArrayList<>();
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new MainAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("Dic", "Befor Query");
        loadDataFromFirebase();
        String[] array_data = getResources().getStringArray(R.array.word_in_dictionary);
//        for (int i=0; i<array_data.length; i++) {
//            data.add(new MainModel(array_data[i], null));
//        for (String last_data : array_data) {
//            data.add(new MainModel(last_data, null));
//            adapter.setData(data);
//        }
    }

    public void loadDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("words").orderBy("titleFire").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                onProcessLoadData(querySnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void onProcessLoadData ( QuerySnapshot querySnapshot ) {
        List<FirebaseModel> firebaseModels = new ArrayList<>();
        for (QueryDocumentSnapshot query : querySnapshot) {

            firebaseModels.add(query.toObject(FirebaseModel.class));
        }
        FirebaseModel firebaseModel = firebaseModels.get(2);
        Log.d("Dic", ""+firebaseModel.getTitleFire());
        adapter.setData(firebaseModels);
    }


//    public void data () {
//      first style
//        MainModel a = new MainModel();
//        MainModel b = new MainModel();
//        a.setTitle("A,a");
//        data.add(a);
//        b.setTitle("B,b");
//        data.add(b);
//      second style
//        data.add(new MainModel("A,a" ,null));
//        data.add(new MainModel("B,b", null));
//        adapter.setData(data);
//    }


}
