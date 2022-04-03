package com.example.appvideoclub.Modelo;

import java.lang.reflect.TypeVariable;

public class CampoResulset {
    int columnId;
    int rowId;
    String columName;

    String tipo;
    Object valor;

    public CampoResulset() {
    }

    public CampoResulset(int columnId,int rowId, String columName,  String tipo, Object valor) {
        this.columnId = columnId;
        this.columName = columName;
        this.rowId=rowId;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public String getColumName() {
        return columName;
    }

    public void setColumName(String columName) {
        this.columName = columName;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
