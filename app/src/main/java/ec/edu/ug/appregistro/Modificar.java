package ec.edu.ug.appregistro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Modificar extends AppCompatActivity {

    private productos pr;
    private EditText etNombre;
    private EditText etPrecio;
    private EditText etStock;

    private databaseHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_activity);
        db = new databaseHandler(Modificar.this);
        int id = getIntent().getIntExtra("ID",0);


        etNombre = findViewById(R.id.nombre);
        etPrecio = findViewById(R.id.precio);
        etStock = findViewById(R.id.stock);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        pr = db.getByIdProduct(id);
        llenarCampos();
        btnUpdate.setOnClickListener(v->actualizarProducto());

    }

    private void llenarCampos(){
        etNombre.setText(pr.getNombre().toString());
        etPrecio.setText(String.valueOf(pr.getPrecio()));
        etStock.setText(String.valueOf(pr.getStock()));
    }

    private void actualizarProducto(){
        int idUpdate = pr.getId();
        String nombreUpdate = etNombre.getText().toString().trim();
        String precioUpdate = etPrecio.getText().toString();
        String stockUpdate = etStock.getText().toString();
        boolean estado = db.updateProducto(idUpdate, nombreUpdate, precioUpdate, stockUpdate);

        if (estado){
            Toast.makeText(this, "Producto Actualizado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }
}
