package ec.edu.ug.appregistro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ec.edu.ug.appregistro.adaptadores.MovimientoAdapter;
import ec.edu.ug.appregistro.adaptadores.ProductoAdapter;
import ec.edu.ug.appregistro.clasesDb.Movimientos;
import ec.edu.ug.appregistro.clasesDb.productos;
import ec.edu.ug.appregistro.db.databaseHandler;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerViewMovimientos;

    private databaseHandler dbHelper;
    private EditText etNombre,etPrecio,etStock;

    private ArrayList<productos> productos;
    private ArrayList<Movimientos> movimientos;
    private ProductoAdapter adapter;
    private MovimientoAdapter adapterMovimientos;

    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStock = findViewById(R.id.etStock);
        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerViewMovimientos = findViewById(R.id.recyclerMovimientos);
        btnGuardar = findViewById(R.id.btnGuardar);
        dbHelper = new databaseHandler(MainActivity.this);

        btnGuardar.setOnClickListener(v->{
            añadirProducto();
            mostrarProductos();
        });

        mostrarMovimiento();

        cargarMovimiento();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarProductos();
    }

    private void mostrarProductos(){
        productos = dbHelper.getallProductList();
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new ProductoAdapter(productos);
        recyclerView.setAdapter(adapter);
    }

    private void mostrarMovimiento(){

        movimientos = dbHelper.getAllMovimientos();
        adapterMovimientos = new MovimientoAdapter(movimientos);
        recyclerViewMovimientos.setLayoutManager(
                new LinearLayoutManager(this)
        );
        recyclerViewMovimientos.setAdapter(adapterMovimientos);
    }

    public void cargarMovimiento(){

        movimientos.clear();
        movimientos.addAll(dbHelper.getAllMovimientos());

        adapterMovimientos.notifyDataSetChanged();
    }

    private void añadirProducto(){
        if (etNombre.getText().toString().isEmpty() || etPrecio.getText().toString().isEmpty() || etStock.getText().toString().isEmpty()){
            etNombre.setError("Este campo no puede quedar vacio");
            etPrecio.setError("Este campo no puede quedar vacio");
            etStock.setError("Este campo no puede quedar vacio");
        }else{
            dbHelper.addProducto(
                    new productos(etNombre.getText().toString(),
                    Integer.parseInt(etPrecio.getText().toString()),
                    Integer.parseInt(etStock.getText().toString())));
            Toast.makeText(this,"Producto Guardado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }

    private void limpiarCampos(){
        etNombre.setText("");
        etPrecio.setText("");
        etStock.setText("");
    }
}