package com.example.proyectoentrevista.network.AlmacenarDatos;

public class AlmacenarDatosBody {
    private String name;
    private String last;
    private String gender;
    private String email;
    private String dni;
    private String loanStatus;

    public AlmacenarDatosBody(String name, String last, String gender, String email, String dni, String loanStatus){
        this.name=name;
        this.last=last;
        this.gender=gender;
        this.email=email;
        this.dni=dni;
        this.loanStatus=loanStatus;
    }
}
