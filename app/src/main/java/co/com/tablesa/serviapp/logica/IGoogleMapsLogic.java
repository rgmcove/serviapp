package co.com.tablesa.serviapp.logica;


import com.google.android.gms.maps.model.LatLng;

import co.com.tablesa.serviapp.modelo.dto.RutaDTO;

public interface IGoogleMapsLogic {
    RutaDTO obtenerRuta(LatLng pOrigen, LatLng pDestino) throws Exception;
}
