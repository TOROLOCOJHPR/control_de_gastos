package com.example.controldegastos.ui.abonos;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controldegastos.AdapterAbonos;
import com.example.controldegastos.ObjectAbonos;
import com.example.controldegastos.R;
import com.example.controldegastos.sqlite.AdminSqLiteOpenHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AbonosFragment extends Fragment {

    RecyclerView recycler;
    ArrayList<ObjectAbonos> listAbonos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_abonos, container, false);
        View viewRoot = container.getRootView();

        changeStyle(viewRoot, getActivity());

        listAbonos = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerAbonos);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            llenarLista();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AdapterAbonos adapter = new AdapterAbonos(listAbonos);
        recycler.setAdapter(adapter);

        return view;
    }

    private void llenarLista() throws ParseException {

        AdminSqLiteOpenHelper admin = new AdminSqLiteOpenHelper
                (getActivity());
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Double monto;
        String fecha, fechaCorte, hora;

        Cursor fila = BaseDeDatos.rawQuery
                ("select monto,fecha,fechaCorte from abonos order by id desc", null);

        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();
            for (int i = 0; i < fila.getCount(); i++) {
                monto = fila.getDouble(fila.getColumnIndex("monto"));
                fecha = fila.getString(fila.getColumnIndex("fecha"));
                fechaCorte = fila.getString(fila.getColumnIndex("fechaCorte"));
                Date fechaFinal = strToDate(fecha, 1);
                Date fechaCorteFinal = strToDate(fechaCorte, 0);
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                fecha = dateFormat.format(fechaFinal);
                fechaCorte = dateFormat.format(fechaCorteFinal);


                listAbonos.add(new ObjectAbonos(monto.toString(), fecha, fechaCorte));
                fila.moveToNext();
            }
        }
        BaseDeDatos.close();

    }

    public Date strToDate(String s, int hora) throws ParseException {
        String pattern;
        if (hora == 1) {
            pattern = "dd-MM-yyyy hh:mm:ss";
        } else {
            pattern = "dd-MM-yyyy";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date formatOut = format.parse(s);
        return formatOut;
    }

    private void changeStyle(View viewRoot, Activity activity) {
        AppBarLayout appbar;
        BottomNavigationView navView;
        TextView appbarTitle;
        int item_inicio, disabled;
        int[][] states;
        int[] colors;
        ColorStateList csl;
        Drawable gradient;
        LinearLayout header;

        navView = viewRoot.findViewById(R.id.nav_view);
        appbar = viewRoot.findViewById(R.id.appbar);
        appbarTitle = viewRoot.findViewById(R.id.toolbar_title);
        header = viewRoot.findViewById(R.id.header);

        item_inicio = ContextCompat.getColor(activity, R.color.item_abonos);
        disabled = ContextCompat.getColor(activity, R.color.item_disabled);
        gradient = ContextCompat.getDrawable(activity, R.drawable.gradient_abonos);
        states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };
        colors = new int[]{
                disabled, disabled, item_inicio, disabled
        };
        csl = new ColorStateList(states, colors);

        //Cambio de estilo
        navView.setItemIconTintList(csl);
        navView.setItemTextColor(csl);
        appbar.setBackground(gradient);
        appbarTitle.setText("ABONOS");
        header.setBackground(gradient);

    }

}