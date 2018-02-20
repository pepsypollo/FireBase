package com.example.itp1dam.firebase;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PHP php;

    private Spinner lista;
    private Button bRegistro;
    private Button bElimReg;
    private ImageButton bEnviar;
    private TextView user;

    private EditText etTitulo;
    private EditText etMensaje;

    private Dialog dLogin;
    private Button bDCancel;
    private Button bDRegister;
    private EditText etUser;
    private EditText etPass;

    private static final String LOGTAG = "android-fcm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        php = new PHP();

        lista = (Spinner) findViewById (R.id.sListaUsers);
        bRegistro = (Button) findViewById (R.id.bRegistro);
        bElimReg = (Button) findViewById (R.id.bElimReg);
        user = (TextView) findViewById (R.id.tUsuario);
        bEnviar = (ImageButton) findViewById (R.id.bEnviar);

        bRegistro.setOnClickListener(this);
        bElimReg.setOnClickListener(this);
        bEnviar.setOnClickListener(this);

        etTitulo = (EditText) findViewById (R.id.etTitulo);
        etMensaje = (EditText) findViewById (R.id.etMensaje);

        // dialog de registro
        dLogin = new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dLogin.setContentView(R.layout.login_layout);

        etUser = (EditText) dLogin.findViewById (R.id.etUser);
        etPass = (EditText) dLogin.findViewById (R.id.etPass);
        bDCancel = (Button) dLogin.findViewById(R.id.bDCancel);
        bDRegister = (Button) dLogin.findViewById (R.id.bDRegister);

        bDCancel.setOnClickListener(this);
        bDRegister.setOnClickListener(this);

        lista.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"fuk","succ"}));

        // Recibido Firebase
        if (getIntent().getExtras() != null) {
            Log.d(LOGTAG, "DATOS RECIBIDOS (INTENT)");
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Object value = bundle.get(key);
                    Log.d(LOGTAG, String.format("%s %s (%s)", key, value.toString(), value.getClass().getName()));
                }
            }
        }

    }

    public void actualizarLista(){
        try {
            String[] username = php.getUserNames();
            lista.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, username));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTokenAplicacion() {
        // Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("----------------------------------------------------------------------------------");
        Log.d(LOGTAG, "El token de la aplicación es: " + refreshedToken);
        return refreshedToken;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bRegistro:
                dLogin.show();
                break;
            case R.id.bElimReg:
                actualizarLista();
                getTokenAplicacion();
                break;
            case R.id.bDCancel:
                dLogin.cancel();
                break;
            case R.id.bDRegister:
                user.setText(etUser.getText());
                dLogin.cancel();
                break;
            case R.id.bEnviar:
                try {
                    php.enviar("f", etTitulo.getText().toString(), etMensaje.getText().toString());
                } catch (PHPException e){
                    System.out.println("Error de envio");
                }
                System.out.println("fuk of");
                break;
        }
    }
}
