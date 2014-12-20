package com.neotecsoft.woici;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class buscar extends ActionBarActivity {
    LinearLayout lista1, lista2;
    View items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buscar);
        getSupportActionBar().hide();

        lista1 = (LinearLayout)findViewById(R.id.col1);
        lista2 = (LinearLayout)findViewById(R.id.col2);
        cargar_datos_negocios(R.drawable.l1,"Baco",R.drawable.us2,"Marisa Meyer", lista1);
        cargar_datos_negocios(R.drawable.l2,"Kleine Neipe",R.drawable.us4,"Alejandra Cuello", lista2);
        cargar_datos_negocios(R.drawable.l3,"Kleine",R.drawable.us5,"Marcela Morales", lista1);
        cargar_datos_negocios(R.drawable.l4,"Neipe",R.drawable.us6,"Cintia Sanchez", lista2);
        cargar_datos_negocios(R.drawable.l5,"KN",R.drawable.us7,"Laura Ojeda", lista1);



    }
    public void cargar_datos_negocios(int background,String nombre_lugar,int imagen_persona, String nombre_persona , LinearLayout parent){
        items = getLayoutInflater().inflate(R.layout.item_principal_card, null);
        ((ImageView)items.findViewById(R.id.imagen_lugar_poster)).setImageResource(background);
        ((ImageView)items.findViewById(R.id.imagen_persona)).setImageResource(imagen_persona);
        ((TextView)items.findViewById(R.id.nombre_persona)).setText(nombre_persona);
        ((TextView)items.findViewById(R.id.textView_nombre_lugar)).setText(nombre_lugar);
        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(buscar.this,PerfilNegocio.class);
                startActivity(i);
            }
        });

        parent.addView(items);
    }


}
