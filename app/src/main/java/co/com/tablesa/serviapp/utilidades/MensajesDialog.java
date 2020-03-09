package co.com.tablesa.serviapp.utilidades;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import co.com.tablesa.serviapp.R;


public class MensajesDialog extends DialogFragment {

    private String mensajeMostrar;
    private Class claseNavegar;
    private AppCompatActivity activityInvoca;

    public MensajesDialog(){

    }
    @SuppressLint("ValidFragment")
    public MensajesDialog(String pMensaje){
        this.mensajeMostrar=pMensaje;
    }

    @SuppressLint("ValidFragment")
    public MensajesDialog(String pMensaje,
                          AppCompatActivity pActivityInvoca,
                          Class pClaseNavegar){

        this.mensajeMostrar=pMensaje;
        this.activityInvoca=pActivityInvoca;
        this.claseNavegar=pClaseNavegar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage(mensajeMostrar);
        builder.setPositiveButton(R.string.aceptar,
                                    new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(claseNavegar!=null){
                    Intent intent = new Intent();
                    intent.setClass(activityInvoca, claseNavegar);
                    startActivity(intent);
                    activityInvoca.finish();
                }

                getDialog().dismiss();
            }
        });


        return builder.create();
    }
}
