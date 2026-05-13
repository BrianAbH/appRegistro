package ec.edu.ug.appregistro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ec.edu.ug.appregistro.R;
import ec.edu.ug.appregistro.clasesDb.Movimientos;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.Holder> {

    static ArrayList<Movimientos> listaMovimiento;


    public MovimientoAdapter(ArrayList<Movimientos> listaMovimiento){this.listaMovimiento = listaMovimiento;}

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movimientos, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Movimientos m = listaMovimiento.get(position);
        holder.idMovimiento.setText("Id: " +String.valueOf(m.getIdMovimiento()));
        holder.nombreP.setText("Nombre del Producto: " + m.getNombreMovimiento());
        holder.fecha.setText("Fecha: "+m.getFechaMovimiento());

    }

    @Override
    public int getItemCount() {
        return listaMovimiento.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        TextView idMovimiento, nombreP, fecha;
        public Holder(@NonNull View itemView) {
            super(itemView);

            idMovimiento = itemView.findViewById(R.id.txtIdM);

            nombreP = itemView.findViewById(R.id.txtNombreM);

            fecha = itemView.findViewById(R.id.txtFecha);

        }
    }
}
