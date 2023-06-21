package org.example;

public class Agenti {
    int CodAgent;
    String nume;
    String prenume;
    int varsta;
    String telefon;

    public Agenti(int codAgent, String nume, String prenume, int varsta, String telefon) {
        CodAgent = codAgent;
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.telefon = telefon;
    }

    public Agenti(String nume, String prenume, int varsta, String telefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.telefon = telefon;
    }

    public int getCodAgent() {
        return CodAgent;
    }

    public void setCodAgent(int codAgent) {
        CodAgent = codAgent;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
