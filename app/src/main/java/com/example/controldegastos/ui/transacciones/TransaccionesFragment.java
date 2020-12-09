package com.example.controldegastos.ui.transacciones;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controldegastos.AdapterTransacciones;
import com.example.controldegastos.ObjectTransacciones;
import com.example.controldegastos.R;
import com.example.controldegastos.sqlite.AdminSqLiteOpenHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransaccionesFragment extends Fragment {


    RecyclerView recycler;
    Spinner spinner;
    ArrayList<ObjectTransacciones> listTransacciones;
    ArrayList<String> sprTransacciones;
    Activity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transacciones, container, false);
        View viewRoot = container.getRootView();
        activity = getActivity();

        listTransacciones = new ArrayList<>();
        sprTransacciones = new ArrayList();

        changeStyle(viewRoot, activity);

        spinner = view.findViewById(R.id.sprTransacciones);
        recycler = view.findViewById(R.id.recyclerTransacciones);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarSpr(activity);
        ArrayAdapter<CharSequence> sprAdapter = new ArrayAdapter(getContext(), R.layout.spinner,sprTransacciones);
        spinner.setAdapter(sprAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filtro = adapterView.getItemAtPosition(i).toString();
                try {
                    llenarLista(activity,filtro);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                AdapterTransacciones adapter = new AdapterTransacciones(listTransacciones);
                recycler.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    private void llenarLista(Activity activity, String filtro) throws ParseException {

        AdminSqLiteOpenHelper admin = new AdminSqLiteOpenHelper(activity);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Double monto;
        String descripcion, fecha, hora, fechaCorte;

        listTransacciones.clear();

        StringBuilder query = new StringBuilder();
        query.append("select monto, descripcion,fecha,fechaCorte from transacciones where fechaCorte = ");
        query.append("'"+filtro+"'");
        query.append(" order by id desc");
        Cursor fila = BaseDeDatos.rawQuery(query.toString(), null);

        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();
            for (int i = 0; i < fila.getCount(); i++) {
                monto = fila.getDouble(fila.getColumnIndex("monto"));
                descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                fecha = fila.getString(fila.getColumnIndex("fecha"));
                fechaCorte = fila.getString(fila.getColumnIndex("fechaCorte"));

                Date fechaFinal = strToDate(fecha, 1);
                Date fechaCorteFinal = strToDate(fechaCorte, 0);
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                fecha = dateFormat.format(fechaFinal);
                fechaCorte = dateFormat.format(fechaCorteFinal);

                listTransacciones.add(new ObjectTransacciones(monto.toString(), descripcion, fecha, fechaCorte));
                fila.moveToNext();
            }
        }
        BaseDeDatos.close();

    }


    private void llenarSpr(Activity activity) {

        AdminSqLiteOpenHelper admin = new AdminSqLiteOpenHelper(activity);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String fechaCorte;
        StringBuilder query = new StringBuilder();

        query.append( "select distinct(fechaCorte) fechaCorte from transacciones order by fechaCorte desc");
        Cursor fila = BaseDeDatos.rawQuery(query.toString(), null);

        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();

            for (int i = 0; i < fila.getCount(); i++) {
                fechaCorte = fila.getString(fila.getColumnIndex("fechaCorte"));
                sprTransacciones.add(fechaCorte);
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
        int item_transacciones, disabled;
        int[][] states;
        int[] colors;
        ColorStateList csl;
        Drawable gradient;
        LinearLayout header;
        navView = viewRoot.findViewById(R.id.nav_view);
        appbar = viewRoot.findViewById(R.id.appbar);
        appbarTitle = viewRoot.findViewById(R.id.toolbar_title);
        header = viewRoot.findViewById(R.id.header);

        item_transacciones = ContextCompat.getColor(activity, R.color.item_transacciones);
        disabled = ContextCompat.getColor(activity, R.color.item_disabled);
        gradient = ContextCompat.getDrawable(activity, R.drawable.gradient_transacciones);
        states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };
        colors = new int[]{
                disabled, disabled, item_transacciones, disabled
        };
        csl = new ColorStateList(states, colors);

        //Cambio de estilo
        navView.setItemIconTintList(csl);
        navView.setItemTextColor(csl);
        appbar.setBackground(gradient);
        appbarTitle.setText("TRANSACCIONES");
        header.setBackground(gradient);


    }

}