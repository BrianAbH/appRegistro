package ec.edu.ug.appregistro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DbProductos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTOS = "productos";


    public databaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableProducto = "CREATE TABLE " + TABLE_PRODUCTOS + "("
                + "id_producto INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_producto TEXT, "
                + "precio_producto TEXT,"
                + "stock_producto TEXT)";
        db.execSQL(createTableProducto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }

    void addProducto(productos productos) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre_producto", productos.getNombre());
        values.put("precio_producto", productos.getPrecio());
        values.put("stock_producto", productos.getStock());

        db.insert(TABLE_PRODUCTOS, null, values);
        db.close();
    }

    public ArrayList<productos> getallProductList() {
        ArrayList<productos> listaProductos = new ArrayList<productos>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                productos productos = new productos();
                productos.setId(Integer.parseInt(cursor.getString(0)));
                productos.setNombre(cursor.getString(1));
                productos.setPrecio(cursor.getInt(2));
                productos.setStock(cursor.getInt(3));
                // Adding student to list
                listaProductos.add(productos);
            } while (cursor.moveToNext());
        }
        return listaProductos;
    }

    public productos getByIdProduct(int id) {
        productos producto = new productos();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS + " WHERE id_producto = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setPrecio(cursor.getInt(2));
            producto.setStock(cursor.getInt(3));
        }
        return producto;
    }

    public boolean updateProducto(int id, String nombre, String precio, String stock){
        boolean estado;
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            String updateQuery = "UPDATE " + TABLE_PRODUCTOS + " SET nombre_producto = '" + nombre +
                    "', precio_producto = '" + precio + "', stock_producto = '" + stock
                    +   "' WHERE id_producto = "+ id +" ";
            db.execSQL(updateQuery);
            estado = true;
            Log.d("query", updateQuery);
        }catch (Exception e){
            Log.d("error", e.toString());
            estado = false;
        }finally {
            db.close();
        }

        return estado;
    }

    public boolean deleteProducto(int id){
        boolean estado;
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            String updateQuery = "DELETE FROM " + TABLE_PRODUCTOS + "  WHERE id_producto = "+ id +" ";
            db.execSQL(updateQuery);
            estado = true;
            Log.d("query", updateQuery);
        }catch (Exception e){
            Log.d("error", e.toString());
            estado = false;
        }finally {
            db.close();
        }
        return estado;
    }



}
