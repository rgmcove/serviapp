package co.com.tablesa.serviapp.logica;

import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import co.com.tablesa.serviapp.modelo.enumeracion.EnumUrl;

public class ConexionRestLogic  extends AsyncTask<LatLng, Long, String> {

    private static final String URL_CONSULTAR;

    static{
        URL_CONSULTAR= EnumUrl.ENUM_URL.getValor()+"?";
    }

    @Override
    protected String doInBackground(LatLng... latLngs) {
        StringBuffer respuestaRuta= null ;//null
        Log.i("info", "123"+respuestaRuta);
        try {
            StringBuffer peticion= new StringBuffer();
            Log.i("info", "bufferedReaderyuu"+peticion);
            peticion.append(URL_CONSULTAR);
            Log.i("info", "urlconsultar"+URL_CONSULTAR);
            peticion.append("origin=");
            Log.i("info", "ori"+"origin=");
            peticion.append(latLngs[0].latitude);
            peticion.append(",");
            peticion.append(latLngs[0].longitude);
            Log.i("info", "longi"+latLngs[0].longitude);
            peticion.append("&");
            peticion.append("destination=");
            peticion.append(latLngs[1].latitude);
            Log.i("info", "12344"+latLngs[1].latitude);
            peticion.append(",");
            peticion.append(latLngs[1].longitude);
            Log.i("info", "12384"+latLngs[1].longitude);
            peticion.append("&");
            peticion.append("languaje=");
            peticion.append(EnumUrl.LENGUAJE_ESPANOL.getValor());
            Log.i("info", "123874"+peticion);
            peticion.append("&");
            peticion.append("mode=");
            peticion.append(EnumUrl.MODO_CONDUCIENDO.getValor());
            peticion.append("&");
            peticion.append("key=");
            Log.i("info", "peticion1 "+peticion);
            peticion.append("AIzaSyB3L_y-afL_ImmWCbsMYfCgigmp3x_-xgw");
            Log.i("info", "peticion3 "+peticion);
            URL url = new URL(peticion.toString());
            Log.i("info", "peticion4 "+peticion);
            HttpsURLConnection httpsURLConnection= (HttpsURLConnection)
                                                    url.openConnection();
            Log.i("info", "httpsURLConnection "+httpsURLConnection);
            Log.i("info", "url"+url);

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.connect();

            BufferedReader bufferedReader= new
                    BufferedReader(
                                                    new InputStreamReader(httpsURLConnection.
                                                            getInputStream()));
            Log.i("info", "bufferedReader"+bufferedReader);
            String lineaLeida;

            respuestaRuta= new StringBuffer();
            Log.i("info", "respuestaRuta"+respuestaRuta);
            while((lineaLeida=bufferedReader.readLine())!=null){
                respuestaRuta.append(lineaLeida);
                Log.i("info", "respuestaRuta2"+respuestaRuta);
            }

            bufferedReader.close();
            httpsURLConnection.disconnect();


        }catch(Exception exception){
            Log.e("Error","Error consultando ruta:"+exception.getMessage());
        }


        Log.i("info", "viene de respuestaruta"+respuestaRuta);
        return respuestaRuta.toString();
    }
}
