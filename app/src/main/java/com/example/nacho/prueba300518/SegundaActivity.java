package com.example.nacho.prueba300518;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SegundaActivity extends AppCompatActivity {
    private TextView tv_nomCompleto, tv_Detpartamento, tv_ValorCargo, tv_TipoEq, tv_ValorEq;
    private AutoCompleteTextView acv_Serie;
    private ListView lv_eqCargo;
    private ArrayAdapter<Equipos> adCargo;
    private ArrayAdapter<Equipos> adEq;
    private Usuario user;
    private Equipos selecAutoComplete;
    private Equipos selecListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        tv_nomCompleto = (TextView) findViewById(R.id.tv_nomCompleto);
        tv_Detpartamento = (TextView) findViewById(R.id.tv_Detpartamento);
        tv_ValorCargo = (TextView) findViewById(R.id.tv_ValorCargo);
        tv_TipoEq = (TextView) findViewById(R.id.tv_TipoEq);
        tv_ValorEq = (TextView) findViewById(R.id.tv_ValorEq);
        lv_eqCargo = (ListView) findViewById(R.id.lv_eqCargo);
        acv_Serie = (AutoCompleteTextView) findViewById(R.id.acv_Serie);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = BaseDatos.buscarUsuario(bundle.getString("usuario"));
        }

        tv_nomCompleto.setText(user.getNombre()+" "+user.getApellido());
        tv_Detpartamento.setText(user.getDepartamento());
        actualizarAdapterListView();
        actualizarAdapterAutoComplete();
        acv_Serie.setThreshold(5);
        tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());

        lv_eqCargo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecListView = (Equipos) user.getCargo().get(position);
            }
        });
        lv_eqCargo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selecListView = (Equipos) user.getCargo().get(position);
                SegundaActivity.this.quitarEquipo();
                return true;
            }
        });

        acv_Serie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecAutoComplete = (Equipos) acv_Serie.getAdapter().getItem(position);
                tv_TipoEq.setText(selecAutoComplete.getDescripcion());
                tv_ValorEq.setText("$"+selecAutoComplete.getValor());
            }
        });
    }

    protected void onClic (View v) {
        if (v.getId() == R.id.ibtn_AddCargo) {
            this.agregaEquipo();
        } else if (v.getId() == R.id.ibtn_DelCargo) {
            this.quitarEquipo();
        } else if (v.getId() == R.id.btn_Volver) {
            SegundaActivity.this.finish();
        }
    }



    private void actualizarAdapterAutoComplete(){
        adEq = new ArrayAdapter<Equipos>(SegundaActivity.this,android.R.layout.simple_list_item_1,BaseDatos.getEquipos());
        acv_Serie.setAdapter(adEq);
    }
    private void actualizarAdapterListView (){
        adCargo = new ArrayAdapter<Equipos>(this, android.R.layout.simple_list_item_1, user.getCargo());
        lv_eqCargo.setAdapter(adCargo);
    }

    private void quitarEquipo () {
        if (selecListView != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SegundaActivity.this);
            builder.setTitle("Confirmar");
            builder.setIcon(R.drawable.ic_action_name);
            builder.setMessage("Esta seguro de que desea quitarle el cargo.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    user.quitarequipo(selecListView.getSerie());
                    BaseDatos.getEquipos().add(selecListView);
                    actualizarAdapterListView();
                    actualizarAdapterAutoComplete();
                    tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());
                    selecListView = null;
                }
            });
            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    private void agregaEquipo () {
        if (selecAutoComplete != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SegundaActivity.this);
            builder.setTitle("Confirmar");
            builder.setIcon(R.drawable.ic_action_add);
            builder.setMessage("Esta seguro de que desea Agregar el Cargo.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    user.agregarequipo(selecAutoComplete);
                    BaseDatos.equipoAsignado(selecAutoComplete.getSerie());
                    actualizarAdapterListView();
                    actualizarAdapterAutoComplete();
                    tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());
                    acv_Serie.setText("");
                    selecAutoComplete = null;
                }
            });
            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private int sumarCargo() {
        int sum = 0;
        if (!lv_eqCargo.getAdapter().isEmpty()) {
            for (int x = 0; x < lv_eqCargo.getAdapter().getCount(); ++x) {
                Equipos eq = (Equipos) lv_eqCargo.getAdapter().getItem(x);
                sum += eq.getValor();
            }
        }
        return sum;
    }
}
