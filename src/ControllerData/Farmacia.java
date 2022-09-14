
package ControllerData;

import Model.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Farmacia {
    private String nombree,direccion;
    private int nit;
    private double telefono;
    
    //public List<Almacen>;
    
    public Farmacia(){
        
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }
    
    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getTelefono() {
        return telefono;
    }

    public void setTelefono(double telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return this.getClass().getName() + "{" + ", nit=" + nit +" nombre=" + nombree + ", direccion=" + direccion + ", telefono=" + telefono + '}';
    }
        
    public List<Farmacia> listarFarmacias() {
        List<Farmacia> listaFarmacias = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        String sql = "SELECT * FROM farmacias;"; //Sentencia sql
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Farmacia f;
            while (rs.next()) {
                f = new Farmacia();
                f.setNit(rs.getInt("Nit"));
                f.setNombree(rs.getString("Nombre"));
                f.setDireccion(rs.getString("Direccion"));
                f.setTelefono(rs.getDouble("Telefono"));
                
                listaFarmacias.add(f);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar farmacias:" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return listaFarmacias;
    }
    
    public boolean guardarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "INSERT INTO Farmacias(Nombre,Direccion,Telefono)"
                + "VALUES('" + this.nombree + "','" + this.direccion + "'," + this.telefono + ");";
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

    public boolean actualizarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE Farmacias SET Nombre='"
                + this.nombree + "', Direccion='" + this.direccion
                + "',Telefono= " + this.telefono + " WHERE Nit=" + this.nit + ";";
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

    public boolean eliminarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM farmacias WHERE Nit=" + this.nit + ";";
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
