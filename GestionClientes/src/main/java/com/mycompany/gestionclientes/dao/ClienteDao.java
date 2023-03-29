
package com.mycompany.gestionclientes.dao;

import com.mycompany.gestionclientes.models.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;

/* La funcion de esta clase es que nos permita por un lado recibir objetos de la clase Cliente y guardarla en la DB
 en la tabla Cliente y a su vez leer la tabla cliente, transformar esos datos a objetos como la clase Cliente*/

public class ClienteDao{

    public Connection conectar(){
        String db = "java";
        String usuario = "root";
        String password = "123456";
        String host = "localhost";
        String port = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false";

        Connection conexion = null;

        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(connectionUrl, usuario, password);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conexion;
        

};

    public void agregar(Cliente cliente){
           

        
        try {
            
            Connection conexion = conectar();
                /* Para controlar el error de el codigo de abajo, lo metemos en este trycatch para que por ahora no ver manejos de errores como tal */
                String sql = "insert into Cliente (nombre, apellido, telefono, email) values ('"+cliente.getNombre() + "', '" +cliente.getApellido() + "', '"
                 +cliente.getTelefono() + " ', '" +cliente.getEmail() + " ')";
                Statement statement = (Statement) conexion.createStatement();

                statement.execute(sql);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
            

    };

    

    public List<Cliente> listar(){
     List<Cliente> listado = new ArrayList<>();
        
        try {
            Connection conexion = conectar();
           
            
            /* Para controlar el error de el codigo de abajo, lo metemos en este trycatch para que por ahora no ver manejos de errores como tal */
            String sql = "select * from `Cliente`;";
            
            Statement statement = (Statement) conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setTelefono(resultado.getString("telefono"));
                listado.add(cliente);
            }
            
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listado;
        

};

public void eliminar(String id){
   
    
    try {
        Connection conexion = conectar();

        /* Para controlar el error de el codigo de abajo, lo metemos en este trycatch para que por ahora no ver manejos de errores como tal */
        String sql = "DELETE FROM Cliente WHERE id = " + id;
        
        Statement statement = (Statement) conexion.createStatement();
        statement.execute(sql);
    
    } catch (Exception e) {
        e.printStackTrace();
    }
};

public void actualizar(Cliente cliente){
   
    
    try {
        Connection conexion = conectar();

        /* Para controlar el error de el codigo de abajo, lo metemos en este trycatch para que por ahora no ver manejos de errores como tal */
        String sql = "UPDATE `java`.`Cliente` SET `nombre` = '"+cliente.getNombre()+"', `apellido` = '"+cliente.getApellido()+"', `telefono` = '"+cliente.getTelefono()+"', `email` = '"+cliente.getEmail()+"' WHERE `id` = '"+cliente.getId()+"'";
        
        Statement statement = (Statement) conexion.createStatement();
        statement.execute(sql);
    
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void guardar(Cliente cliente) {
    if(StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())){
        agregar(cliente);
    } else{
        actualizar(cliente);
    }
};


}