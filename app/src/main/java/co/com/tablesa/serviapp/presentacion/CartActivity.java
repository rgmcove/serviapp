package co.com.tablesa.serviapp.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.com.tablesa.serviapp.R;
import co.com.tablesa.serviapp.entidades.Articulos;

public class CartActivity extends AppCompatActivity {

    ListView listAdapter;
    ArrayList<Articulos> misarticulos;
    Button borrar;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        borrar = findViewById(R.id.btnBorrar);
        total = findViewById(R.id.tvPrecioTotal);

        listAdapter = findViewById(R.id.lvListCart);
        misarticulos = (ArrayList<Articulos>) getIntent().getSerializableExtra("carrito");

        ArticuloAdapter articuloAdapter = new ArticuloAdapter();
        listAdapter.setAdapter(articuloAdapter);

        total.setText("Total: $" + realizarSuma(misarticulos));
    }

    public double realizarSuma(List<Articulos> carrito) {
        double sumaTotal = 0.0;

        for (int i=0; i<carrito.size(); i++) {
            sumaTotal += misarticulos.get(i).getPrecio();
        }

        return sumaTotal;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("returnCarrito", misarticulos);
        setResult(102, intent);
        finish();
        //Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public class ArticuloAdapter extends ArrayAdapter<Articulos> {

        ArticuloAdapter() {
            super(CartActivity.this, R.layout.list_cart, misarticulos );
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_cart, parent, false);

            TextView titulo = view.findViewById(R.id.tvCartTitulo);
            TextView subtitulo = view.findViewById(R.id.tvCartCategoria);
            TextView precio = view.findViewById(R.id.tvCartPrecio);
            Button borrar = view.findViewById(R.id.btnBorrar);

            Articulos miarticulo = misarticulos.get(position);

            titulo.setText(miarticulo.getTitulo());
            subtitulo.setText(miarticulo.getSubtitulo());
            precio.setText(Double.toString(miarticulo.getPrecio()));

            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Removemos cada articulo segun su posicion.
                    misarticulos.remove(position);
                    notifyDataSetChanged();
                    //Si elimina una elemento de la lista, volvemos a contar el total
                    total.setText("Total: $" + realizarSuma(misarticulos));
                }
            });
            return view;
        }
    }

}
