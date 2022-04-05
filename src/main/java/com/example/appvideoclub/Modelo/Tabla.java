package com.example.appvideoclub.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tabla {

    private ArrayList<ArrayList<CampoTabla>> tabla;

    private TipodatosResultset tipo;
    private int size;

    public Tabla(ResultSet rs) {
        tabla = new ArrayList<>();


        this.tipo = null;
         CampoTabla campo = null;

        try {
            tipo = new TipodatosResultset(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs.beforeFirst();
            while (rs.next()) {

                ArrayList<CampoTabla> regTemp=new ArrayList<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

                    switch (tipo.gettipodato(rs.getMetaData().getColumnTypeName(i))) {
                        case "String":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getString(i));
                            break;
                        case "Integer":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getInt(i));
                            break;
                        case "Timestamp":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getTimestamp(i));
                            break;
                        case "bite[]":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getBytes(i));
                            break;
                        case "Date":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getDate(i));
                            break;
                        case "Float":
                            campo = new CampoTabla(i-1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getFloat(i));
                            break;
                        case "BigDecimal":
                            campo = new CampoTabla(i-1 , rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getBigDecimal(i));
                            break;
                        case "long":
                            campo = new CampoTabla(i-1 , rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getLong(i));
                            break;
                        case "Time":
                            campo = new CampoTabla(i -1, rs.getRow(), rs.getMetaData().getColumnName(i), tipo.gettipodato(rs.getMetaData().getColumnTypeName(i)), rs.getTime(i));
                            break;

                    }
                    regTemp.add(campo);

                }


                tabla.add(regTemp);
                //regTemp=null;
            }
            } catch(SQLException e){
                e.printStackTrace();
            }
        this.size= tabla.size();
    }


    public ArrayList<CampoTabla> getRegistro(int row){
        ArrayList<CampoTabla>registro=new ArrayList<>();
        registro=tabla.get(row);
        return registro;
    }



    public ArrayList<ArrayList<CampoTabla>> getTabla() {
        return tabla;
    }

    public TipodatosResultset getTipo() {
        return tipo;
    }
    public CampoTabla getCampo(int row, int col){

        CampoTabla campi=null;

        campi = tabla.get(row).get(col);
        //System.out.println(campi);//.get(col).getValor());

        System.out.println(tabla);
        return campi;
    }

    public int getSize() {
        return size;
    }
}