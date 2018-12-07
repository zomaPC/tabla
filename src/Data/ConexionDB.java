
package Data;

import Model.Alumno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ConexionDB {
    
    private static String TAG = ConexionDB.class.getSimpleName();
    private static Connection connection;
    private static Statement statement;
 
 public static Connection openConnection(){
    try {
 Class.forName("com.mysql.jdbc.Driver");
 connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea", "root", "1234567890");
 JOptionPane.showMessageDialog(null, "Conexion establecida");
 } catch (ClassNotFoundException | SQLException e) {
 JOptionPane.showMessageDialog(null, "Error de conexion " + e);
 }
 return connection;
 }
 
 public static Boolean insert(Alumno alumno){
     try {
         statement = openConnection().createStatement();
         int r = statement.executeUpdate("INSERT INTO tabla1 "
                 + "VALUES(" + 
                 "'" + alumno.getIdioma()+ "'," + 
                 "'" + alumno.getNombreIdioma()+ "')"         
         );
         System.out.println(String.valueOf(r));
         if (r == 1) 
             return true;
         
     } catch (SQLException ex) {
         System.out.println(TAG + " " + ex.getMessage());
     }
     return false;
 }
 public static Boolean validate(String Matricula){
        
        try{
            statement = openConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM tabla1 WHERE idIdioma ='" + Matricula +"'");
            result.next();
            
            int cantidad = result.getInt(1);
            if(cantidad > 0)
                return false;                    
        }catch (SQLException ex) {
            System.out.println(TAG + " " + ex.getMessage());
        }        
        return true;
    }
 public static Boolean delete(Alumno alumno){
     try{
            statement = openConnection().createStatement();
            statement.execute("DELETE FROM tabla1 WHERE idIdioma = '"+ alumno.getIdioma()+"'");            
            return true;
        }catch (SQLException ex) {
            System.out.println(TAG + " " + ex.getMessage());
            
        }
    return false;
 }
 public static boolean update(String Matricula, Alumno alumno){
     try{
    		String query = "UPDATE tabla1 SET idIdioma = '" + alumno.getIdioma()+ "',"
                        
                        + "nombreIdioma = '" + alumno.getNombreIdioma()+"'"
                        + "WHERE idIdioma  = '" + Matricula +"';";
		    System.out.println(query);
            statement = openConnection().createStatement();            
            statement.execute(query);
            return true;
        }catch (Exception ex) {
            System.out.println(TAG + " " + ex.getMessage());
            ex.printStackTrace();            
        }
     return false;
 }
 public static Alumno getInfoAlumno(String Matricula){
     Alumno alumno = null;
     try{
         statement = openConnection().createStatement();
         ResultSet result = statement.executeQuery("SELECT idIdioma,nombreIdioma"
                 + " FROM tabla1 WHERE  '" + Matricula + "';");
       if (result.first()){
           do {
               System.out.println(result.getString(1)+ "\t\t\t" + result.getString(2));
               
           }while(result.next());
       }
         
         
         //System.out.println(" " + result);
         
        /*if (!result.next())
              return alumno;
        
        
         alumno = new Alumno();
         String matricula = result.getString(1);
         String nombreidioma = result.getString(2);
         
         alumno.setIdioma(matricula);
         alumno.setNombreIdioma(nombreidioma);
         
       
         
         return alumno;
     */}catch (SQLException ex) {
         System.out.println(TAG + " " + ex.getMessage());
     
     }
     return alumno;
 }
}
