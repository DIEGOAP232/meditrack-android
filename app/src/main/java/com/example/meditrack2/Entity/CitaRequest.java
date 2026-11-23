package com.example.meditrack2.Entity;

public class CitaRequest {

    private Integer idPaciente;
    private String tipoCita;
    private Integer idMedico;
    private Integer idEspecialidad;
    private Integer idServicio;
    private String fecha;
    private String hora;
    private String motivo;

    public CitaRequest(Integer idPaciente, String tipoCita,
                       Integer idMedico, Integer idEspecialidad,
                       Integer idServicio, String fecha,
                       String hora, String motivo) {
        this.idPaciente = idPaciente;
        this.tipoCita = tipoCita;
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
        this.idServicio = idServicio;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }
}