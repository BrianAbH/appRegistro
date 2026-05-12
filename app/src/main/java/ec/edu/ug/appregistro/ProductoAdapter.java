package ec.edu.ug.appregistro;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    static ArrayList<productos> lista;
    public ProductoAdapter(ArrayList<productos> lista) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        productos p = lista.get(position);
        holder.id.setText("id: " + String.valueOf(p.getId()));
        holder.nombre.setText("Nombre: " + p.getNombre());
        holder.precio.setText("Precio: " + String.valueOf(p.getPrecio()));
        holder.stock.setText("Stock: " + String.valueOf(p.getStock()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id,nombre, precio,stock;
        Button btnModificar, btnEliminar;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtId);
            nombre = itemView.findViewById(R.id.txtNombre);
            precio = itemView.findViewById(R.id.txtPrecio);
            stock = itemView.findViewById(R.id.txtStock);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnModificar.setOnClickListener(v->{
                context = itemView.getContext();
                Intent iModificar = new Intent(context, Modificar.class);
                iModificar.putExtra("ID", lista.get(getAdapterPosition()).getId());
                context.startActivity(iModificar);
            });

            btnEliminar.setOnClickListener(v->{
                context = itemView.getContext();

                databaseHandler db = new databaseHandler(context);

                new AlertDialog.Builder(context).setTitle("Confirmar Eliminación")
                        .setMessage("Estas seguro que quiere eliminar este producto")
                        .setPositiveButton("Si", (dialog, which) -> {
                            int position = getAdapterPosition();
                            db.deleteProducto(lista.get(position).getId());
                            lista.remove(position);

                            notifyItemRemoved(position);

                            notifyItemRangeChanged(position, lista.size());
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

        }
    }
}