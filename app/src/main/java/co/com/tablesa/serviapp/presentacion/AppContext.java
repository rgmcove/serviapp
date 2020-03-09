package co.com.tablesa.serviapp.presentacion;

import android.app.Application;
import android.content.Context;

public class AppContext extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        context=this;
    }

    public static Context getContext(){
        return context;
    }

}
