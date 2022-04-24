package es.android.pokemon.entidad;

public class Pokemon {
    private String name;
    private String foto;


    public Pokemon(String name, String foto) {

    }

    public String getName() {
        return name;
    }

    public String getFoto() {
        return foto;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return "It's " + getName() + "!";
    }
}