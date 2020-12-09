package com.example.controldegastos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAbonos extends RecyclerView.Adapter<AdapterAbonos.ViewHolderAbonos> {

    ArrayList<ObjectAbonos> listAbonos;

    public AdapterAbonos(ArrayList<ObjectAbonos> listAbonos){
        this.listAbonos = listAbonos;
    }

    @NonNull
    @Override
    public ViewHolderAbonos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.abonos_list_item,parent,false);
        return new ViewHolderAbonos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderAbonos holder, int position) {
        holder.monto.setText(listAbonos.get(position).getMonto());
        holder.fecha.setText(listAbonos.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return listAbonos.size();
    }

    public class ViewHolderAbonos extends RecyclerView.ViewHolder {
        TextView monto;
        TextView fecha;

        public ViewHolderAbonos(@NonNull View itemView) {
            super(itemView);
            monto = itemView.findViewById(R.id.monto);
            fecha = itemView.findViewById(R.id.fecha);
        }

    }
}
