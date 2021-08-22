package info.ribosoft.rubricar2.HttpConn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.ribosoft.rubricar2.R;

public class RecyclerListaAdapter extends RecyclerView.Adapter<RecyclerListaAdapter
    .RecyclerListaHolder>{
    // creating a variable for our array list and context
    private ArrayList<RecyclerLista> courseDataArrayList;
    private Context context;

    // creating a constructor class
    public RecyclerListaAdapter(ArrayList<RecyclerLista> recyclerDataArrayList, Context context) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerListaAdapter.RecyclerListaHolder onCreateViewHolder(@NonNull ViewGroup parent,
        int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent,
            false);
        return new RecyclerListaAdapter.RecyclerListaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListaAdapter.RecyclerListaHolder holder,
        int position) {
        // Set the data to textview from our modal class
        RecyclerLista modal = courseDataArrayList.get(position);
        holder.courseIdContatto.setText(modal.getId_contatto());
        holder.courseNome.setText(modal.getNome());
        holder.courseCognome.setText(modal.getCognome());
        holder.courseTelefono.setText(modal.getTelefono());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View
    public class RecyclerListaHolder extends RecyclerView.ViewHolder {
        // creating variables for our views
        private TextView courseIdContatto, courseNome, courseCognome, courseTelefono;

        public RecyclerListaHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids
            courseIdContatto = itemView.findViewById(R.id.lstIdContatto);
            courseNome = itemView.findViewById(R.id.lstNome);
            courseCognome = itemView.findViewById(R.id.lstCognome);
            courseTelefono = itemView.findViewById(R.id.lstTelefono);
        }
    }
}
