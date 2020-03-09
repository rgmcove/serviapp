package co.com.tablesa.serviapp.modelo.dto;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class RutaDTO {


    private List<LatLng> lPuntos;
    private String indicaciones;

    public List<LatLng> getlPuntos() {
        return lPuntos;
    }

    public void setlPuntos(List<LatLng> lPuntos) {
        this.lPuntos = lPuntos;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
