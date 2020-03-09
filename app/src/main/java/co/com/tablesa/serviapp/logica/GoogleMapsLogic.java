package co.com.tablesa.serviapp.logica;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.List;

import co.com.tablesa.serviapp.modelo.dto.RutaDTO;

public class GoogleMapsLogic implements IGoogleMapsLogic{

    @Override
    public RutaDTO obtenerRuta(LatLng pOrigen, LatLng pDestino) throws Exception {

        if(pOrigen== null){
               throw new Exception("El origenes requerido para calcular la ruta");
        }

        if(pDestino == null){
            throw new Exception("El destino es requerido para calcular la ruta");
        }

        String rutaConsultar= new ConexionRestLogic().
                                execute(pOrigen,pDestino).get();

        if(rutaConsultar == null || rutaConsultar.equals("")){
            throw new Exception("Error calculando la ruta en Google Directions");
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement arbolJson= jsonParser.parse(rutaConsultar);

        List<LatLng> puntosPintar=null;
        RutaDTO rutaDTO = null;

        if(arbolJson.isJsonObject()){
            JSONObject jsonObject = new JSONObject(rutaConsultar);
            JSONArray rutas = jsonObject.getJSONArray("routes");
            JSONObject rutasPintar= rutas.getJSONObject(0);

            JSONObject overviewPolyLines= rutasPintar.
                                        getJSONObject("overview_polyline");
            String puntosCodificados = overviewPolyLines.
                                        getString("points");
            puntosPintar= PolyUtil.decode(puntosCodificados);
            rutaDTO = new RutaDTO();
            rutaDTO.setlPuntos(puntosPintar);

        }

        return rutaDTO;
    }
}
