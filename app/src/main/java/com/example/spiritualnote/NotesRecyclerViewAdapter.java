package com.example.spiritualnote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesViewHolder> {

    private List<Note> notesList;

    NotesRecyclerViewAdapter(List<Note> notesList) {

        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesRecyclerViewAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerViewAdapter.NotesViewHolder holder, int position) {
        Note currentNote = notesList.get(position);
        holder.day.setText(currentNote.getDay());
        holder.date.setText(currentNote.getDate());
        holder.morningState.setText(currentNote.getMorningState());
        holder.eveningState.setText(currentNote.getEveningState());
        holder.nightState.setText(currentNote.getNightState());
        holder.echuaristState.setText(currentNote.getEucharistState());
        holder.confessState.setText(currentNote.getConfessState());
        holder.bibleState.setText(currentNote.getBibleState());
        holder.notes.setText(currentNote.getNotes());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterList(ArrayList<Note> filteredNotesList){

        notesList = filteredNotesList;
        notifyDataSetChanged();

    }


    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView day;
        TextView morningState;
        TextView eveningState;
        TextView nightState;
        TextView echuaristState;
        TextView confessState;
        TextView bibleState;
        TextView notes;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            date           = itemView.findViewById(R.id.dateInNote);
            day            = itemView.findViewById(R.id.dayInNote);
            morningState   = itemView.findViewById(R.id.morningState);
            eveningState   = itemView.findViewById(R.id.eveningState);
            nightState     = itemView.findViewById(R.id.nightState);
            echuaristState = itemView.findViewById(R.id.echuaristState);
            confessState   = itemView.findViewById(R.id.confessState);
            bibleState     = itemView.findViewById(R.id.bibleState);
            notes          = itemView.findViewById(R.id.notesInRecordedNotes);

        }
    }

}
