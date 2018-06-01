package com.example.nacho.prueba300518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_Usuario, et_Clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDatos.getEquipos().add(new Equipos("111111", "computador1", 10000));
        BaseDatos.getEquipos().add(new Equipos("222222", "computador2", 20000));
        BaseDatos.getEquipos().add(new Equipos("333333", "computador3", 10000));
        BaseDatos.getEquipos().add(new Equipos("444444", "computador4", 20000));
        BaseDatos.getUsuarios().add(new Usuario("nacho1", "gabriel1", "herrera1", "ventas1", "hola"));
        BaseDatos.getUsuarios().add(new Usuario("nacho2", "gabriel2", "herrera2", "ventas2", "hola"));
        et_Usuario = (EditText) findViewById(R.id.et_Usuario);
        et_Clave = (EditText) findViewById(R.id.et_Clave);

    }

    protected void onClic (View v) {
        if (v.getId() == R.id.btn_Aceptar) {
            
            Usuario usu = BaseDatos.buscarUsuario(et_Usuario.getText().toString());
            if (usu == null){
                et_Usuario.setError("Usuario no encontrado");
            }else {
                if (usu.getClave().equals(et_Clave.getText().toString())){
                    Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                    intent.putExtra("usuario", usu.getUsuario());
                    startActivity(intent);
                    et_Usuario.setText("");
                    et_Clave.setText("");
                } else {
                    et_Clave.setError("clave incorrecta");
                    Toast.makeText(this, "clave incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
            
        }else if (v.getId() == R.id.btn_Cancelar){
            MainActivity.this.finish();
        }

    }
}
