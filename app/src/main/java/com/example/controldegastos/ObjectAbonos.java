package com.example.controldegastos;

public class ObjectAbonos {

    private String monto;
    private String fecha;

    public String getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    private String fechaCorte;

    public ObjectAbonos(String monto, String fecha, String fechaCorte) {
        this.monto = monto;
        this.fecha = fecha;
        this.fechaCorte = fechaCorte;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
