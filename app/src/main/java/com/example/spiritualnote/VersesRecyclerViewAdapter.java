package com.example.spiritualnote;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VersesRecyclerViewAdapter  extends RecyclerView.Adapter<VersesRecyclerViewAdapter.VersesViewHolder>  {

    private List<FavVerses> versesList = new ArrayList<>();

    public VersesRecyclerViewAdapter(List<FavVerses> versesList) {
        this.versesList = versesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VersesRecyclerViewAdapter.VersesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verse, parent, false);
        return new VersesRecyclerViewAdapter.VersesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersesRecyclerViewAdapter.VersesViewHolder holder, final int position) {

        holder.newVerse.setText(versesList.get(position).favVerse);

    }

    @Override
    public int getItemCount() {
        return versesList.size();
    }



    public class VersesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView newVerse;
        CardView versesCaredView;
        public VersesViewHolder(@NonNull View itemView) {
            super(itemView);
            newVerse = itemView.findViewById(R.id.newVerse);
            versesCaredView = itemView.findViewById(R.id.verseCardView);
            versesCaredView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(this.getAdapterPosition(), 123, 0, "مـسـح");
            menu.add(this.getAdapterPosition(), 124, 1, "تـعـديـل");

        }
    }


    public void deleteVerse(int pos){

        versesList.remove(versesList.get(pos));
        notifyDataSetChanged();
    }

}
