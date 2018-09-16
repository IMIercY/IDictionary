package com.mercy.idictionary.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercy.idictionary.R;
import com.mercy.idictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MainViewHolder> {

    private List<Word> data;
    private ItemClickListener itemClickListener;

    public WordAdapter(List<Word> data) {
        this.data = data;
    }

    public void setData(List<Word> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_word, parent,
                false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Word firebaseModel = data.get(position);
        holder.title.setText(firebaseModel.getTitleFire());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Word> filter(List<Word> allWord, String keyword) {
        if(keyword.isEmpty()){
            return allWord;
        }
        List<Word> newWord = new ArrayList<>();
        for(Word word: allWord){
            if(word.getTitleFire().contains(keyword)){
                newWord.add(word);
            }
        }
        return newWord;
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
            if(itemClickListener != null){
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    // end of file
}
