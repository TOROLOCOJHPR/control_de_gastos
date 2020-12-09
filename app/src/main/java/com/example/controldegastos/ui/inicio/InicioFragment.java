package com.example.controldegastos.ui.inicio;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.controldegastos.R;
import com.example.controldegastos.sqlite.AdminSqLiteOpenHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InicioFragment extends Fragment {

    private TextInputEditText monto_abono;
    private TextInputLayout monto_abono_TIL;

    private TextInputEditText monto_transaccion, descripcion_transaccion;
    private TextInputLayout monto_transaccion_TIL, descripcion_transaccion_TIL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        View viewRoot = container.getRootView();

        changeStyle(viewRoot,getActivity());

        monto_abono = view.findViewById(R.id.monto_abono);
        monto_abono_TIL = view.findViewById(R.id.monto_abono_TIL);
        monto_transaccion = view.findViewById(R.id.monto_transaccion);
        descripcion_transaccion = view.findViewById(R.id.descripcion_transaccion);
        monto_transaccion_TIL = view.findViewById(R.id.monto_transaccion_TIL);
        descripcion_transaccion_TIL = view.findViewById(R.id.descripcion_transaccion_TIL);
        Button btn_registrar = view.findViewById(R.id.btn_registrar);
        Button btn_registar_transaccion = view.findViewById(R.id.btnRegistrarTransaccion);


        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarAbono(view);
            }
        });

        btn_registar_transaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarTransaccion(view);
            }
        });

        TotalDisponible(viewRoot);

        return view;
    }

    public void RegistrarAbono(View view) {
        AdminSqLiteOpenHelper admin = new AdminSqLiteOpenHelper
                (getActivity());
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String monto = monto_abono.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = new Date();

        if (!monto.isEmpty()) {
            monto_abono_TIL.setError(null);
            ContentValues registro = new ContentValues();

            registro.put("monto", monto);
            registro.put("fecha", dateFormat.format(date));
            registro.put("fechaCorte", fechaCorte());

            BaseDeDatos.insert("abonos", null, registro);
            BaseDeDatos.close();

            monto_abono.setText("");
            ocultarTeclado();
            Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            monto_abono_TIL.setError("Campo vacío");
        }
    }

    public void RegistrarTransaccion(View view) {
        AdminSqLiteOpenHelper admin = new AdminSqLiteOpenHelper
                (getActivity());
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String montoTransaccion = monto_transaccion.getText().toString();
        String descripcionTransaccion = descripcion_transaccion.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        DateFormat format = android.text.format.DateFormat.getDateFormat(getContext());
        Date date = new Date();

        if (!montoTransaccion.isEmpty()) {
            monto_transaccion_TIL.setError(null);
            if (!descripcionTransaccion.isEmpty()) {
                descripcion_transaccion_TIL.setError(null);
                ContentValues registro = new ContentValues();

                registro.put("monto", montoTransaccion);
                registro.put("descripcion", descripcionTransaccion);
                registro.put("fecha", dateFormat.format(date));
                registro.put("fechaCorte", fechaCorte());

                BaseDeDatos.insert("transacciones", null, registro);
                BaseDeDatos.close();
                monto_transaccion.setText("");
                descripcion_transaccion.setText("");
                ocultarTeclado();
                Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
            } else {
                descripcion_transaccion_TIL.setError("Campo vacío");
            }

        } else {
            monto_transaccion_TIL.setError("Campo vacío");

        }
    }

    private void ocultarTeclado() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void TotalDisponible(View viewRoot) {
        //traer datos para sumas totales
        Double totalTransacciones, totalAbonos, totalDisponible, negativo;
        Double credito = 2000.00;

        TextView tv_credito, tv_negativo, tv_positivo;

        AdminSqLiteOpenHelper db = new AdminSqLiteOpenHelper
                (getActivity());
        SQLiteDatabase BaseDeDatos = db.getReadableDatabase();

        Cursor abono = BaseDeDatos.rawQuery
                ("select sum(monto) total from abonos", null);
        abono.moveToFirst();
        totalAbonos = abono.getDouble(abono.getColumnIndex("total"));

        Cursor transaccion = BaseDeDatos.rawQuery
                ("select sum(monto) total from transacciones", null);
        transaccion.moveToFirst();
        totalTransacciones = transaccion.getDouble(transaccion.getColumnIndex("total"));
        BaseDeDatos.close();

        totalDisponible = credito - (totalTransacciones - totalAbonos);
        totalDisponible = Math.round(totalDisponible * 100d) / 100d;

        negativo = (credito - totalDisponible);
        negativo = Math.round(negativo * 100d) / 100d;

        tv_credito = viewRoot.findViewById(R.id.tv_credito);
        tv_negativo = viewRoot.findViewById(R.id.tv_negativo);
        tv_positivo = viewRoot.findViewById(R.id.tv_positivo);


        tv_credito.setText(credito.toString());
        tv_negativo.setText(negativo.toString());
        tv_positivo.setText(totalDisponible.toString());
    }

    private String fechaCorte() {
        String fechaCorte;
        int dia, mes, anio;
        int diaCorte = 12;

        Calendar cal = Calendar.getInstance();

        anio = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH) + 1;
        dia = cal.get(Calendar.DAY_OF_MONTH);

        if (dia > diaCorte) {
            if (mes == 12) {
                mes = 1;
                anio = anio + 1;
            } else {
                mes = mes + 1;
            }
        }

        fechaCorte = String.valueOf(diaCorte) + "-" + String.valueOf(mes) + "-" + String.valueOf(anio);

        return fechaCorte;
    }

    private void changeStyle(View viewRoot, Activity activity){
        AppBarLayout appbar;
        BottomNavigationView navView;
        TextView appbarTitle;
        int item_inicio,disabled;
        int [][] states;
        int [] colors;
        ColorStateList csl;
        Drawable gradient;
        LinearLayout header;


        navView = viewRoot.findViewById(R.id.nav_view);
        appbar = viewRoot.findViewById(R.id.appbar);
        appbarTitle = viewRoot.findViewById(R.id.toolbar_title);
        header = viewRoot.findViewById(R.id.header);

        item_inicio = ContextCompat.getColor(activity, R.color.item_home);
        disabled = ContextCompat.getColor(activity, R.color.item_disabled);
        gradient =ContextCompat.getDrawable(activity, R.drawable.gradient_inicio);
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
        appbarTitle.setText("INICIO");
        header.setBackground(gradient);



    }
}
