package com.example.miapp1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnImprimir = null;
    EditText txtMensaje = null,textViewMessage=null;
    CheckBox check = null;
    CalendarView miCalendario = null;
    String miFecha = "";
    ImageButton imgButton = null, imageButtonMsg = null;
    String msgMensaje = "";

    String defaultLocal = "+507";
    int limiteCodeLocal = defaultLocal.length();
    int limitePhone = 8;

    @SuppressLint({"MissingInflatedId","SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm");
        String hora = formatoFecha.format(new Date());

        btnImprimir = findViewById(R.id.btnHola);
        txtMensaje = findViewById(R.id.textView);
        textViewMessage = findViewById(R.id.textViewMessage);
        check = findViewById(R.id.checkBox);
        imgButton = (ImageButton) findViewById(R.id.imageButton);
        imageButtonMsg = findViewById(R.id.imageButtonMsg);

        miCalendario = findViewById(R.id.calendarView);
        long eventOccursOn = miCalendario.getDate();
        Date temporary = new Date(eventOccursOn);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");


        btnImprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msgMensaje += " "+txtMensaje.getText();
                msgMensaje += ", "+check.isChecked();
                msgMensaje += ", "+hora;
                msgMensaje += ", "+miFecha;
                Toast.makeText(getApplicationContext(), msgMensaje, Toast.LENGTH_LONG).show();

                msgMensaje = "";
                //Snackbar snackbar = Snackbar.make(v, "This is Simple Snackbar, " + miFecha, Snackbar.LENGTH_SHORT);
                //snackbar.show();
                Snackbar snack = Snackbar.make(v, "Hola Mundo, estoy en clase de Android "
                        , Snackbar.LENGTH_LONG);
                snack.show();

            }
        });

        miCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                miFecha = dayOfMonth + "/" + (month+1) + "/" + year;
                //Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent puente = new Intent(getApplicationContext()
                        , MainActivity2.class);
                startActivity(puente);
            }
        });

        imageButtonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberMessage = textViewMessage.getText().toString().trim();
                sendMessageWhatsapp(numberMessage);
            }
        });

    }

    private void sendMessageWhatsapp(String phone){

        String myphone = phone;

        if (myphone.length() == (limiteCodeLocal + limitePhone)) {

            String numeroPhone = myphone.substring(myphone.lastIndexOf(defaultLocal));
            System.out.println("numeroPhone = " + numeroPhone);

            Handler handler = new Handler();

            handler.post(new Runnable() {
                public void run() {
                    String url = "https://api.whatsapp.com/send?phone=" + phone;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    finish();
                }

            });
        } else {
            Toast.makeText(getApplicationContext(), "FAVOR INGRESE UN CONTACTO VÁLIDO", Toast.LENGTH_SHORT).show();
        }

    }
}