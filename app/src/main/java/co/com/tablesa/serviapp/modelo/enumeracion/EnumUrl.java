package co.com.tablesa.serviapp.modelo.enumeracion;

public enum EnumUrl {
    ENUM_URL("https://maps.googleapis.com/maps/api/directions/json"),
    LENGUAJE_ESPANOL("esp"),
    MODO_CONDUCIENDO("driving");

    String valor;

    private EnumUrl (String pValor){
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
}
