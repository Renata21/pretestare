package org.example;

public class Apartamente {
    int CodApartament;
    int etaj;
    int nrCamere;
    int Pret;
    int metriPatrati;
    int CodAgent;

    public Apartamente(int codApartament, int etaj, int nrCamere, int pret, int metriPatrati, int codAgent) {
        CodApartament = codApartament;
        this.etaj = etaj;
        this.nrCamere = nrCamere;
        Pret = pret;
        this.metriPatrati = metriPatrati;
        CodAgent = codAgent;
    }

    public Apartamente(int etaj, int nrCamere, int pret, int metriPatrati) {
        this.etaj = etaj;
        this.nrCamere = nrCamere;
        Pret = pret;
        this.metriPatrati = metriPatrati;
    }

    public int getCodApartament() {
        return CodApartament;
    }

    public void setCodApartament(int codApartament) {
        CodApartament = codApartament;
    }

    public int getEtaj() {
        return etaj;
    }

    public void setEtaj(int etaj) {
        this.etaj = etaj;
    }

    public int getNrCamere() {
        return nrCamere;
    }

    public void setNrCamere(int nrCamere) {
        this.nrCamere = nrCamere;
    }

    public int getPret() {
        return Pret;
    }

    public void setPret(int pret) {
        Pret = pret;
    }

    public int getMetriPatrati() {
        return metriPatrati;
    }

    public void setMetriPatrati(int metriPatrati) {
        this.metriPatrati = metriPatrati;
    }

    public int getCodAgent() {
        return CodAgent;
    }

    public void setCodAgent(int codAgent) {
        CodAgent = codAgent;
    }
}
