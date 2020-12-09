package com.example.controldegastos;

public class ObjectTransacciones {

    private String monto;
    private String descripcion;
    private String fecha;

    public String getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    private String fechaCorte;

    public ObjectTransacciones(String monto, String descripcion, String fecha, String fechaCorte) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fechaCorte = fechaCorte;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
