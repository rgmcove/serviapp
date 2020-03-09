package co.com.tablesa.serviapp.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.com.tablesa.serviapp.R;
import co.com.tablesa.serviapp.entidades.Articulos;

public class MainActivity extends AppCompatActivity {

    ListView milista;
    ArrayList<Articulos> articulos;
    ArrayList<Articulos> lista_compras;
    Button cart;
    TextView cantidadArticulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cantidadArticulos = findViewById(R.id.tvCantArticulos);
        cart = findViewById(R.id.cart);
        lista_compras = new ArrayList<>();

        Productos();

        milista = findViewById(R.id.MyList);
        ArticuloAdapter articuloAdapter = new ArticuloAdapter();
        milista.setAdapter(articuloAdapter);
        milista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ArticulosActivity.class);
                intent.putExtra("articulo", articulos.get(i));
                startActivityForResult(intent, 100);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("carrito", lista_compras);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100) {
            if(resultCode==101) {
                Articulos resultArticulo = (Articulos) data.getSerializableExtra("returnArticulo");

                lista_compras.add(resultArticulo);

                cantidadArticulos.setText("("+lista_compras.size()+")");


            }
            if(resultCode==102) {
                ArrayList<Articulos> resultCarrito = (ArrayList<Articulos>) data.getSerializableExtra("returnCarrito");

                lista_compras.clear();

                for (Articulos articulo:resultCarrito) {
                    lista_compras.add(articulo);
                }

                cantidadArticulos.setText("("+lista_compras.size()+")");
            }
        }
    }

    public class ArticuloAdapter extends ArrayAdapter<Articulos> {

        ArticuloAdapter() {
            super(MainActivity.this, R.layout.list, articulos );

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list, parent, false);



            TextView titulo = view.findViewById(R.id.tvTitulo);
            TextView subtitulo = view.findViewById(R.id.tvSubtitulo);
            TextView precio = view.findViewById(R.id.tvDescripcion);
            ImageView imagen = view.findViewById(R.id.ivImagen);


            Articulos miarticulo = articulos.get(position);

            titulo.setText(miarticulo.getTitulo());
            subtitulo.setText(miarticulo.getSubtitulo());
            precio.setText(Double.toString(miarticulo.getPrecio()));

            imagen.setImageResource(miarticulo.getImagen1());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        if (idItem == R.id.exit){
            LoginActivity loginActivity = new LoginActivity();
            loginActivity.signOut();

            return true;
        }
        return false;
    }

    public void Productos() {

        this.articulos = new ArrayList<>();

        this.articulos.add(new Articulos("K-3 SV E2205 TOP - FIVE CONTINENTS",
                "Cascos",
                "Casco de moto AGV K-3 SV E2205. Un casco que alcanza altos niveles de confort, seguridad y aerodinámica.",
                1500,
                R.drawable.agv,
                R.drawable.agv_2));

        articulos.add(new Articulos("DOMINAR 400 - BAJAJ",
                "Motos",
                "Si necesitas una moto para transportarte y divertirte al mismo tiempo,Dominar es la moto ideal, porque te ofrece el más alto rendimiento con más adrenalina, velocidad y potencia, debido a su diseño, estilo y a sus tecnologías patentadas a nivel mundial.",
                5400,
                R.drawable.domi_400,
                R.drawable.dominar_400));

        articulos.add(new Articulos("HRO 512",
                "Cascos",
                "El HRO 512 rompe con todos los esquemas y lleva el diseño y la protección a un nivel más alto, brindando al motociclista toda la seguridad que busca en un casco y que, además, refleje su personalidad.",
                450,
                R.drawable.hero,
                R.drawable.hero_2));

        articulos.add(new Articulos("SHOX EVO RECOIL HELMET",
                "Casco",
                "El nuevo casco Shox Assault Evo ofrece más estilo y protección que su antecesor con la ventaja adicional de un visor listo para Max Vision Pinlock y un acabado de calidad mejorado.",
                560,
                R.drawable.shox,
                R.drawable.shox_2));

        articulos.add(new Articulos("SUZUKI VSTROM 650",
                "Motos",
                "La Suzuki V-Strom 650 es una motocicleta deportiva de peso medio lanzada en 2004 con una postura de conducción estándar, inyección de combustible y un chasis de aluminio, ahora en su tercera generación desde el año 2017",
                8700,
                R.drawable.vstrom_650,
                R.drawable.vstrom_650_2));

        articulos.add(new Articulos("YANAHA TRACER 900",
                "MOTOS",
                "La Yamaha MT-09 Tracer es un motocicleta sport-touring introducida en 2015 de motor en línea tricilíndrica de 847 cc. El motor proveniente de la MT-09 es tipo \"crossplane\", un marco de aleación de aluminio, y horquilla invertida.",
                15700,
                R.drawable.tracer_900,
                R.drawable.tracer900_2));
    }
}
