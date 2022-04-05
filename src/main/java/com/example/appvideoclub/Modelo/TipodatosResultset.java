package com.example.appvideoclub.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TipodatosResultset {
    HashMap tipodatos;

    

    public HashMap getTipodatos() {
        return tipodatos;
    }

    public TipodatosResultset(ResultSet resul) throws SQLException {
        tipodatos = new HashMap();
        int j;
        for (int i = 1; i <= resul.getMetaData().getColumnCount(); i++) {
            j = i;
            tipodatos.put(resul.getMetaData().getColumnName(j), gettipodato(resul.getMetaData().getColumnTypeName(j)) );
        }

        System.out.println(tipodatos);
    }


    public String gettipodato(String str) {
        switch (str) {
            case "VARCHAR", "LONGTEXT","TEXT","TINYTEXT","CHAR":
                return "String";
            case "DATETIME", "TIMESTAMP":
                return "Timestamp";
            case "TINYINT", "SMALLINT", "MEDIUMINT","BIGINT":
                return "Integer";
            case "LONGBLOB", "MEDIUMBLOB", "BLOB", "TINYBLOB", "VARBINARY", "BINARY":
                return "bite[]";
            case "DATE":
                return "Date";
            case "FLOAT":
                return "Float";
            case "DECIMAL":
                return "BigDecimal";
            case "INT", "INTEGER":
                return "long";
            case "TIME":
                return "Time";
        }
        return null;
    }

}