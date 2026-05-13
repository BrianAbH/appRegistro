package ec.edu.ug.appregistro.adaptadores;

import android.app.Activity;
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
import java.util.Date;

import ec.edu.ug.appregistro.MainActivity;
import ec.edu.ug.appregistro.Modificar;
import ec.edu.ug.appregistro.R;
import ec.edu.ug.appregistro.db.databaseHandler;
import ec.edu.ug.appregistro.clasesDb.productos;

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
        Button btnModificar, btnEliminar, btnVender;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtId);
            nombre = itemView.findViewById(R.id.txtNombre);
            precio = itemView.findViewById(R.id.txtPrecio);
            stock = itemView.findViewById(R.id.txtStock);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnVender = itemView.findViewById(R.id.btnVender);

            btnModificar.setOnClickListener(v->{
                context = itemView.getContext();
                Intent iModificar = new Intent(context, Modificar.class);
                iModificar.putExtra("ID", lista.get(getAdapterPosition()).getId());
                context.startActivity(iModificar);
            });

            btnEliminar.setOnClickListener(v->{
                context = itemView.getContext();

                databaseHandler db = new databaseHandler(context);

                new AlertDialog.Builder(context).setTitle(R.string.titulo_dialog)
                        .setMessage(R.string.mensaje_dialog)
                        .setPositiveButton(R.string.confirmacion_dialog, (dialog, which) -> {
                            int position = getAdapterPosition();
                            db.deleteProducto(lista.get(position).getId());
                            lista.remove(position);

                            notifyItemRemoved(position);

                            notifyItemRangeChanged(position, lista.size());
                            Toast.makeText(context,R.string.msj_toastD,Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.negacion_dialog, null)
                        .show();

            });

            btnVender.setOnClickListener(v->{
                context = itemView.getContext();
                Date date = new Date();
                databaseHandler db = new databaseHandler(context);
                int id = lista.get(getAdapterPosition()).getId();
                String nombre = lista.get(getAdapterPosition()).getNombre();
                String fecha = date.toString();
                db.venderProducto(id, nombre,fecha);
                lista.clear();
                lista.addAll(db.getallProductList());
                notifyDataSetChanged();
                if(context instanceof MainActivity){
                    ((MainActivity) context).cargarMovimiento();
                }
            });
        }
    }
}