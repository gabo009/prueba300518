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
    private Button ibtn_DelCargo;
    private Button ibtn_AddCargo;
    private Usuario user;
    private ArrayAdapter<Equipos> adCargo;
    private ArrayAdapter<Equipos> adEq;
    private Equipos selecAuto;
    private Equipos selecList;
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
        actualizarAdapterList();
        actualizarAdapterAuto();
        acv_Serie.setThreshold(1);
        tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());

        lv_eqCargo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selecList = (Equipos) user.getCargo().get(position);
            }
        });

        lv_eqCargo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                selecList = (Equipos) user.getCargo().get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(SegundaActivity.this);
                builder.setTitle("Confirmar");
                builder.setIcon(R.drawable.ic_action_name);
                builder.setMessage("Esta seguro de que desea quitarle el cargo.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        quitarEquipo();
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });


        acv_Serie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selecAuto = (Equipos) acv_Serie.getAdapter().getItem(position);

                tv_TipoEq.setText(selecAuto.getDescripcion());
                tv_ValorEq.setText("$"+selecAuto.getValor());
            }
        });
    }

    protected void onClic (View v) {
        if (v.getId() == R.id.ibtn_AddCargo) {
            if (selecAuto != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SegundaActivity.this);
                builder.setTitle("Confirmar");
                builder.setIcon(R.drawable.ic_action_add);
                builder.setMessage("Esta seguro de que desea Agregar el Cargo.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        agregaEquipo();
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        } else if (v.getId() == R.id.ibtn_DelCargo) {
            if (selecList != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SegundaActivity.this);
                builder.setTitle("Confirmar");
                builder.setIcon(R.drawable.ic_action_name);
                builder.setMessage("Esta seguro de que desea quitarle el cargo.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        quitarEquipo();
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        } else if (v.getId() == R.id.btn_Volver) {
            SegundaActivity.this.finish();
        }
    }



    private void actualizarAdapterAuto(){
        adEq = new ArrayAdapter<Equipos>(SegundaActivity.this,android.R.layout.simple_list_item_1,BaseDatos.getEquipos());
        acv_Serie.setAdapter(adEq);
    }
    private void actualizarAdapterList (){
        adCargo = new ArrayAdapter<Equipos>(this, android.R.layout.simple_list_item_1, user.getCargo());
        lv_eqCargo.setAdapter(adCargo);
    }
    private void quitarEquipo () {

        user.quitarequipo(selecList.getSerie());

        BaseDatos.getEquipos().add(selecList);

        actualizarAdapterList();
        actualizarAdapterAuto();
        tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());
        selecList = null;

    }
    private void agregaEquipo () {

        user.agregarequipo(selecAuto);

        BaseDatos.equipoAsignado(selecAuto.getSerie());

        actualizarAdapterList();
        actualizarAdapterAuto();
        tv_ValorCargo.setText("valor total a cargo: $" + sumarCargo());
        acv_Serie.setText("");
        selecAuto = null;

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
