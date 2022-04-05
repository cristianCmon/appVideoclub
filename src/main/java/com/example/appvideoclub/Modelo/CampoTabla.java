package com.example.appvideoclub.Modelo;

public class CampoTabla {
    private int columnId;
    private int rowId;
    private String columName;

    private String tipo;
    private Object valor;

    public CampoTabla() {
    }

    public CampoTabla(int columnId, int rowId, String columName, String tipo, Object valor) {
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
