package com.example.proyectoentrevista.network.AlmacenarDatos;

public class AlmacenarDatosBody {
    public String name;
    public String last;
    public String gender;
    public String email;
    public String dni;
    public String loanStatus;

    public AlmacenarDatosBody(String name, String last, String gender, String email, String dni, String loanStatus){
        this.name=name;
        this.last=last;
        this.gender=gender;
        this.email=email;
        this.dni=dni;
        this.loanStatus=loanStatus;
    }
}
