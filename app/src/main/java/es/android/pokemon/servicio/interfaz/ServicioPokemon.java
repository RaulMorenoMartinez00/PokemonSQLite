package es.android.pokemon.servicio.interfaz;

import java.util.List;

import es.android.pokemon.entidad.Pokemon;

public interface ServicioPokemon {

    List<Pokemon> generarPokemon(String recurso) throws Exception;

}
