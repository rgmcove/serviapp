package co.com.tablesa.serviapp.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.com.tablesa.serviapp.R;
import co.com.tablesa.serviapp.entidades.Articulos;

public class ArticulosActivity extends AppCompatActivity {

    TextView thisTitulo;
    TextView thisSubtitulo;
    TextView thisPrecio;

    ImageView firstImage;
    ImageView secondImage;

    TextView thisDescripcion;

    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        final Articulos thisArticulo = (Articulos) getIntent().getSerializableExtra("articulo");

        thisTitulo = findViewById(R.id.tvThisTitulo);
        thisSubtitulo = findViewById(R.id.tvThisSubtitulo);
        thisPrecio = findViewById(R.id.tvThisPrecio);

        firstImage = findViewById(R.id.ivFirstImage);
        secondImage = findViewById(R.id.ivSecondImage);

        thisDescripcion = findViewById(R.id.tvThisDescripcion);

        agregar = findViewById(R.id.btnAgregar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("returnArticulo",thisArticulo);
                setResult(101, intent);
                finish();
            }
        });


        thisTitulo.setText(thisArticulo.getTitulo());
        thisSubtitulo.setText(thisArticulo.getSubtitulo());
        thisPrecio.setText(Double.toString(thisArticulo.getPrecio()));

        firstImage.setImageResource(thisArticulo.getImagen1());
        secondImage.setImageResource(thisArticulo.getImagen2());

        thisDescripcion.setText(thisArticulo.getDescripcion());
    }

    public void mapa(View view){

        Intent intent = new Intent(ArticulosActivity.this, MapaActivity.class);
        startActivity(intent);
    }
}
