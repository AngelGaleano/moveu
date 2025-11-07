package com.moveu.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String identificacion;
    private String telefono;
    private String password;

    // 👇 Nuevo campo
    private String horario;

    // ✅ Constructor vacío (requerido por JPA)
    public Usuario() {}

    // ✅ Constructor completo
    public Usuario(String nombre, String email, String identificacion, String telefono, String password, String horario) {
        this.nombre = nombre;
        this.email = email;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.password = password;
        this.horario = horario;
    }

    // ✅ Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}
