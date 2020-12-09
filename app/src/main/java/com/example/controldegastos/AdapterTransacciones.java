package com.example.controldegastos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTransacciones extends RecyclerView.Adapter<AdapterTransacciones.ViewHolderTransacciones> {

    ArrayList<ObjectTransacciones> listTransacciones;

    public AdapterTransacciones(ArrayList<ObjectTransacciones> listTransacciones){
        this.listTransacciones = listTransacciones;
    }

    @NonNull
    @Override
    public ViewHolderTransacciones onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transacciones_list_item,parent,false);
        return new ViewHolderTransacciones(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderTransacciones holder, int position) {

        SpannableStringBuilder spannable = new SpannableStringBuilder("Descripcion: ");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(holder.itemView.getContext() , R.color.text_title_item)),0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(18,true),0,spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.insert(spannable.length(),listTransacciones.get(position).getDescripcion());


    holder.monto.setText(listTransacciones.get(position).getMonto());
        //holder.descripcion.setText(listTransacciones.get(position).getDescripcion());
        holder.descripcion.setText(spannable);
        holder.fecha.setText(listTransacciones.get(position).getFecha());
        holder.fechaCorte.setText(listTransacciones.get(position).getFechaCorte());
    }

    @Override
    public int getItemCount() {
        return listTransacciones.size();
    }

    public class ViewHolderTransacciones extends RecyclerView.ViewHolder {
        TextView monto;
        TextView descripcion;
        TextView fecha;
        TextView fechaCorte;

        public ViewHolderTransacciones(@NonNull View itemView) {
            super(itemView);
            monto = itemView.findViewById(R.id.monto);
            descripcion = itemView.findViewById(R.id.descripcion);
            fecha = itemView.findViewById(R.id.fecha);
            fechaCorte = itemView.findViewById(R.id.fechaCorte);
        }

    }
}
