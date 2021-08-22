package info.ribosoft.rubricar2.HttpConn;

public class RecyclerLista {
    // string variables for our data
    String id_contatto, nome, cognome, telefono;

    public String getId_contatto() {return id_contatto;}
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getTelefono() {return telefono;}

    public void setId_contatto(String id_contatto) {this.id_contatto = id_contatto;}
    public void setNome(String nome) {this.nome = nome;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public RecyclerLista(String id_contatto, String nome, String cognome, String telefono) {
        this.id_contatto = id_contatto;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }
}
