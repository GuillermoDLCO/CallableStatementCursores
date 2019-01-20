package cs;

import datos.Conexion;
import java.sql.*;
import oracle.jdbc.*;

public class TestCursores {
    
    public static void main(String args[]){
        
        OracleCallableStatement oraCallStmt = null;
        OracleResultSet deptResultSet = null;
        
        try {
            Connection con = Conexion.getConnection();
            
            oraCallStmt = (OracleCallableStatement) con.prepareCall("{? = call ref_cursor_package.get_dept_ref_cursor(?)}");
            // Definicion de los 2 parametros "?"
            oraCallStmt.registerOutParameter(1, OracleTypes.CURSOR);//Segundo argumento parametro es el tipo de dato
            oraCallStmt.setInt(2, 150);//Se declara 2do param. de Query y se inicializa en 200
            oraCallStmt.execute();
            
            deptResultSet = (OracleResultSet) oraCallStmt.getCursor(1);
            while (deptResultSet.next()) {                
                System.out.print("Id_departamento: " + deptResultSet.getInt(1));
                System.out.print(", Nombre_departamento: " + deptResultSet.getString(2));
                System.out.print(", Ubicación_id: " + deptResultSet.getString(3));
                System.out.println();

            }
            oraCallStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
