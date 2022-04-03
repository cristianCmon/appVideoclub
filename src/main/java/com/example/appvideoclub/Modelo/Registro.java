package com.example.appvideoclub.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Registro {
    private CampoResulset campo;
    private final ArrayList<CampoResulset> registro;
    private TipodatosResultset tipo;

    public Registro(ResultSet rs) throws SQLException {
        registro=new ArrayList<>();
        int cont=0;
        try {
            tipo = new TipodatosResultset(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

            try {

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    switch (tipo.gettipodato(rs.getMetaData().getColumnTypeName(i))) {
                        case "String":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getString(i));
                            break;
                        case "Integer":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getInt(i));
                            break;
                        case "Timestamp":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getTimestamp(i));
                            break;
                        case "bite[]":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getBytes(i));
                            break;
                        case "Date":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getDate(i));
                            break;
                        case "Float":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getFloat(i));
                            break;
                        case "BigDecimal":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getBigDecimal(i));
                            break;
                        case "long":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getLong(i));
                            break;
                        case "Time":
                            campo = new CampoResulset(i-1,rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getTime(i));
                            break;

                    }
                    registro.add(i-1, campo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    public ArrayList<CampoResulset> getRegistro() {
        return registro;
    }

    public TipodatosResultset getTipo() {
        return tipo;
    }
}
