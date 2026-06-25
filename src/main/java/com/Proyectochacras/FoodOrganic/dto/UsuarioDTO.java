package com.Proyectochacras.FoodOrganic.dto;

public class UsuarioDTO {
    private String username;
    private String email;
    private String bio;
    private String profilePic;
    private String fechaRegistro;
    private String rol;

    public UsuarioDTO() {} // Constructor vacío necesario para Spring

    // Constructor
public UsuarioDTO(String username, String email, String bio, String profilePic, String fechaRegistro, String rol) {
    this.username = username;
    this.email = email;
    this.bio = bio;
    this.profilePic = profilePic;
    this.fechaRegistro = fechaRegistro;
    this.rol = rol;
}

    // Getters y setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
