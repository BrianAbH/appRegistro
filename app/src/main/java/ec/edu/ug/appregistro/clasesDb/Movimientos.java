package ec.edu.ug.appregistro.clasesDb;

public class Movimientos {

    private int id;

    private String nombre_producto;

    private String fecha;


    public Movimientos(int id, String nombre_producto, String fecha){
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.fecha = fecha;
    }

    public Movimientos(){}


    public int getIdMovimiento(){
        return this.id;
    }

    public String getNombreMovimiento(){
        return this.nombre_producto;
    }

    public String getFechaMovimiento(){
        return this.fecha;
    }

    public void setIdMovimiento(int id){
        this.id = id;
    }

    public void setNombreMovimiento(String nombre_producto){
        this.nombre_producto = nombre_producto;
    }

    public void setFechaMovimiento(String fecha){
        this.fecha = fecha;
    }



}
