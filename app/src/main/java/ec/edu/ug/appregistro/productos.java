package ec.edu.ug.appregistro;

public class productos {
    private int id_producto;
    private String nombre_Producto;
    private int precio_producto;
    private int stock_producto;

    public productos(){
    }

    public productos(String nombre_Producto, int precio_producto, int stock_producto){
        this.nombre_Producto = nombre_Producto;
        this.precio_producto = precio_producto;
        this.stock_producto = stock_producto;
    }

    public int getId(){
       return this.id_producto;
    }

    public String getNombre(){
        return this.nombre_Producto;
    }

    public int getPrecio(){
        return this.precio_producto;
    }

    public int getStock(){
        return this.stock_producto;
    }

    public void setId(int id_producto){
        this.id_producto = id_producto;
    }

    public void setNombre(String nombre_Producto){
        this.nombre_Producto = nombre_Producto;
    }

    public void setPrecio(int precio_producto){
        this.precio_producto = precio_producto;
    }

    public void setStock(int stock_producto){
        this.stock_producto = stock_producto;
    }

}
