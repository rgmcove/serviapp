package co.com.tablesa.serviapp.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import co.com.tablesa.serviapp.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText txtUsuario;
    EditText txtClave;
    private CallbackManager mCallbackManager;

    private int FACEBOOK_SIGN_IN = 64206;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);

        Button fab = findViewById(R.id.btnIniciar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
                accionIngresar(view);
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("INFO", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.i("pantalla", "Aqui en onSucces");
                goHome();
            }

            @Override
            public void onCancel() {
                Log.i("INFO", "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("ERROR", "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FACEBOOK_SIGN_IN){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
            Log.i("onActivityResulT", "Aqui en onActivity Result");
        }
    }

    public void accionIngresar(final View view){
        try {
            if (txtUsuario.getText().toString().equals("")) {
                Snackbar.make(view, "Digite el usuario", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (txtClave.getText().toString().equals("")) {
                Snackbar.make(view, "Digite la clave", Snackbar.LENGTH_LONG).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(txtUsuario.getText().toString(), txtClave.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Snackbar.make(view, "Sesion iniciada correctamente", Snackbar.LENGTH_LONG).show();
                                Log.i("INFO", "Sesion iniciada correctamente");
                                goHome();
                            }else{
                                Snackbar.make(view, "No ha sido posible iniciar sesión " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                                Log.e("ERROR", "No ha sido posible iniciar sesión " + task.getException().getMessage());
                            }
                        }
                    });
        }catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.i("INFO", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("INFO", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("INFO", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String sbUser = user.getDisplayName();
            Toast.makeText(LoginActivity.this, "Hola " + sbUser,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "No ha sido posible iniciar la sesion",Toast.LENGTH_LONG).show();
        }
    }

    public void signOut() {
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUI(null);
    }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
