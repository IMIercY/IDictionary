package com.mercy.idictionary.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mercy.idictionary.DetailActivity;
import com.mercy.idictionary.Model.FirebaseModel;
import com.mercy.idictionary.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<FirebaseModel> data = new ArrayList<>();

    public void setData(List<FirebaseModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent,
                false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        FirebaseModel firebaseModel = data.get(position);
        holder.title.setText(firebaseModel.getTitleFire());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private MainViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_view_holder);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == itemView.getId()) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);

                int position = getAdapterPosition();
                FirebaseModel firebaseModel = data.get(position);

                Gson gson = new Gson();
                String putData = gson.toJson(firebaseModel);
                intent.putExtra("data", putData);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.getContext().startActivity(intent);
                    ((Activity) view.getContext()).overridePendingTransition(R.anim.enter_slide_to_left, R.anim.exit_slide_to_left);
                }


//                Gson gson = new Gson();
//                String getData = getIntent().getStringExtra("data");
//                FirebaseModel firebaseModel = gson.fromJson(getData, FirebaseModel.class);
//                contents.setText(firebaseModel.getContentsFire());
                Log.d("Dic", ""+position+firebaseModel.getContentsFire());
            }
        }
    }
}
