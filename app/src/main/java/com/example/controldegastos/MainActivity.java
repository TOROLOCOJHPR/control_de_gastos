package com.example.controldegastos;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.window = getWindow();
        int item_inicio,disabled;
        int [][] states;
        int [] colors;
        ColorStateList csl;


        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        item_inicio = ContextCompat.getColor(this, R.color.item_home);
        disabled = ContextCompat.getColor(this, R.color.item_disabled);
        states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };
        colors = new int[]{
                item_inicio, item_inicio, item_inicio, disabled
        };
        csl = new ColorStateList(states, colors);

        navView.setItemIconTintList(csl);
        navView.setItemTextColor(csl);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /*AdminSqLiteOpenHelper dbHelper = new AdminSqLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO abonos (monto,fecha, fechaCorte) VALUES (100.00,'06-10-2020 16:53:00','12-10-2020')"
                    + ",(100.00,'06-10-2020 16:55:00','12-10-2020')"
                    + ",(1400.00,'08-10-2020 08:13:00','12-10-2020')"
                    + ",(800.00,'11-10-2020 10:16:00','12-10-2020')"
                    + ",(1000.00,'13-10-2020 07:00:00','12-11-2020')"
                    + ",(200.00,'15-10-2020 14:06:00','12-11-2020')"
                    + ",(120.00,'17-10-2020 07:05:00','12-11-2020')"
                    + ",(700.00,'17-10-2020 15:17:00','12-11-2020')"
                    + ",(1000.00,'18-10-2020 08:37:00','12-11-2020')"
                    + ",(500.00,'20-10-2020 11:16:00','12-11-2020')"
                    + ",(800.00,'23-10-2020 15:20:00','12-11-2020')"
                    + ",(600.00,'24-10-2020 09:42:00','12-11-2020')"
                    + ",(800.00,'25-10-2020 09:37:00','12-11-2020')"
                    + ",(1600.00,'26-10-2020 08:53:00','12-11-2020')"
                    + ",(800.00,'30-10-2020 09:30:00','12-11-2020')"
                    + ",(800.00,'31-10-2020 10:34:00','12-11-2020')"
            );

            db.execSQL("INSERT INTO transacciones (monto,descripcion,fecha,fechaCorte) VALUES (65.00,'Farmacia Guadalajara chucherias','30-09-2020 17:49:26','12-10-2020')"
                    + ",(1287.94,'Bodega barrancos mandado','01-10-2020 13:56:44','12-10-2020')"
                    + ",(64.00,'Farmacia Guadalajara gel antibacterial','03-10-2020 21:36:33','12-10-2020')"
                    + ",(38.00,'Farmacia Guadalaraja tostitos','03-10-2020 21:36:05','12-10-2020')"
                    + ",(324.46,'Ley barrancos desechable cumpleaños Jessica','04-10-2020 11:41:08','12-10-2020')"
                    + ",(200.00,'DQ barrancos pastel Jessica','04-10-2020 12:00:19','12-10-2020')"
                    + ",(1599.00,'Servicio Hyundai i10','08-10-2020 12:36:26','12-10-2020')"
                    + ",(600.00,'Gasolina villa bonita','11-10-2020 10:51:57','12-10-2020')"
                    + ",(84.78,'Fruteria el manjar','13-10-2020 08:45:25','12-11-2020')"
                    + ",(699.00,'TotalPlay','13-10-2020 16:22:14','12-11-2020')"
                    + ",(33.00,'Fruteria el manjar','14-10-2020 14:49:55','12-11-2020')"
                    + ",(582.29,'Bodega tres rios mandado','15-10-2020 15:40:31','12-11-2020')"
                    + ",(118.00,'Japac septiembre','17-10-2020 08:56:18','12-11-2020')"
                    + ",(500.00,'Gasolina villa bonita','17-10-2020 15:40:10','12-11-2020')"
                    + ",(60.00,'Washup','18-10-2020 08:40:14','12-11-2020')"
                    + ",(600.00,'Gasolina villa bonita','18-10-2020 09:04:02','12-11-2020')"
                    + ",(125.50,'OXXO barrancos','18-10-2020 14:33:31','12-11-2020')"
                    + ",(546.00,'Disco duro SSD 120 Juan Pablo','20-10-2020 16:54:33','12-11-2020')"
                    + ",(300.00,'Bodega tres rios mandado','20-10-2020 08:25:33','12-11-2020')"
                    + ",(50.00,'Recarga','23-10-2020 02:59:24','12-11-2020')"
                    + ",(630.23,'Gasolina santa fe','23-10-2020 15:44:41','12-11-2020')"
                    + ",(23.00,'La cuarta santa fe','23-10-2020 15:54:56','12-11-2020')"
                    + ",(60.00,'Car wash','24-10-2020 08:49:48','12-11-2020')"
                    + ",(410.10,'Gasolina santa fe','24-10-2020 09:53:01','12-11-2020')"
                    + ",(75.50,'OXXO jose kumate','24-10-2020 21:07:34','12-11-2020')"
                    + ",(535.11,'Gasolina villa bonita','25-10-2020 09:51:07','12-11-2020')"
                    + ",(54.98,'Modatelas','25-10-2020 16:48:28','12-11-2020')"
                    + ",(51.98,'Waldos','25-10-2020 17:01:48','12-11-2020')"
                    + ",(400.00,'Gasolina villa bonita','26-10-2020 09:15:44','12-11-2020')"
                    + ",(60.00,'Car wash','26-10-2020 09:29:01','12-11-2020')"
                    + ",(19.00,'Fruteria el manjar','26-10-2020 15:18:15','12-11-2020')"
                    + ",(1374.00,'CFE depa #5','26-10-2020 16:05:51','12-11-2020')"
                    + ",(50.00,'Recarga','29-10-2020 18:09:51','12-11-2020')"
                    + ",(195.00,'USB multiple','30-10-2020 09:52:52','12-11-2020')"
                    + ",(400.01,'Gasolina santa fe','30-10-2020 19:50:20','12-11-2020')"
                    + ",(381.80,'Ley barrancos cumpleaños suegra','31-10-2020 12:14:35','12-11-2020')"
                    + ",(580.00,'Alborada carniceria cumpleaños suegra','31-10-2020 12:31:15','12-11-2020')"

            );

        }
        db.close();*/


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }


}