
package ControllerData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.ConexionBD;

public class Producto {
    private String nombre;
    private int id;
    private double temperatura;
    private double valorBase;
   

    public Producto(String nombre, int id, double temperatura, double valorBase) {
        this.nombre = nombre;
        this.id = id;
        this.temperatura = temperatura;
        this.valorBase = valorBase;
        
        
    }
    public Producto(){
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }
   
    
    
    public double calcularCostoDeAlmacenamiento(){
            double costo =0;
            if (temperatura<=20){
                costo= getValorBase()*1.2;
                } else  {
                costo= getValorBase()*1.1; 
                 }
            return costo;
            
            }
     
    public String toString() {
        return this.getClass().getName() + "{" + ", id=" + id +"nombre=" + nombre + ", temperatura=" + temperatura + ", valorBase=" + valorBase + ", costo=" + calcularCostoDeAlmacenamiento() + '}';
    }
    
     
   public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        String sql = "SELECT * FROM productos;"; //Sentencia sql
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Producto p;
            
            while (rs.next()) {
                p = new Producto();
                
                p.setId(rs.getInt("Id"));
                p.setNombre(rs.getString("Nombre"));
                p.setTemperatura(rs.getDouble("Temperatura"));
                p.setValorBase(rs.getDouble("ValorBase"));
                p.calcularCostoDeAlmacenamiento();
                listaProductos.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar productos:" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return listaProductos;
    }

    public boolean guardarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "INSERT INTO productos(Nombre,Temperatura,ValorBase,Costo)"
                + "VALUES('" + this.nombre + "'," + this.temperatura + ",'" + this.valorBase + "'," + calcularCostoDeAlmacenamiento() + ");";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE productos SET Nombre='"
                + this.nombre + "',Temperatura=" + this.temperatura
                + ",ValorBase= " + this.valorBase + " WHERE id=" + this.id + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean eliminarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM productos WHERE id=" + this.id + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
    

}