package es.android.pokemon.repositorio.implementacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import es.android.pokemon.entidad.Pokemon;
import es.android.pokemon.repositorio.interfaz.Repositorio;

public class RepositorioSQLiteImpl extends SQLiteOpenHelper implements Repositorio<Pokemon> {
    public RepositorioSQLiteImpl(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pokemon.db";

    public RepositorioSQLiteImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static class ContratoPokemon{
        private ContratoPokemon() {}
        public static class EntradaPokemon implements BaseColumns {
            public static final String NOMBRE_TABLA = "Pokemon";
            public static final String NOMBRE = "nombre";
            public static final String FOTO = "foto";
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE POKEMON (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "NOMBRE TEXT NOT NULL," +
                "DESCRIPTION TEXT NOT NULL," +
                "IMAGEN TEXT NOT NULL)");

        Pokemon pokemon1 = new Pokemon("Pikachu","pikachu.jpg");
        this.save(pokemon1, db);
        Pokemon pokemon2 = new Pokemon("Flygon","flygon.jpg");
        this.save(pokemon2, db);
        Pokemon pokemon3 = new Pokemon("Victini","victini.jpg");
        this.save(pokemon3, db);
        Pokemon pokemon4 = new Pokemon("Psyduck","psyduck.jpg");
        this.save(pokemon4, db);
        Pokemon pokemon5 = new Pokemon("Bastiodon","bastiodon.jpg");
        this.save(pokemon5, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Optional<Pokemon> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Pokemon> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                ContratoPokemon.EntradaPokemon.NOMBRE_TABLA, // Nombre de la tabla
                null, // Lista de Columnas a consultar
                null, // Columnas para la cláusula WHERE
                null, // Valores a comparar con las columnas del WHERE
                null, // Agrupar con GROUP BY
                null, // Condición HAVING para GROUP BY
                null // Cláusula ORDER BY
        );

        List<Pokemon> pokemons = new LinkedList<>();
        while(c.moveToNext()){
            @SuppressLint("Range")
            String name = c.getString(
                    c.getColumnIndex(ContratoPokemon.EntradaPokemon.NOMBRE));
            @SuppressLint("Range")
            String foto = c.getString(
                    c.getColumnIndex(ContratoPokemon.EntradaPokemon.FOTO));
            pokemons.add(new Pokemon(name,foto));
        }

        return pokemons;
    }

    @Override
    public void save(Pokemon pokemon) {
        this.save(pokemon, null);
    }

    private void save(Pokemon pokemon, SQLiteDatabase db) {
        if(db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContratoPokemon.EntradaPokemon.NOMBRE, pokemon.getName());
        values.put(ContratoPokemon.EntradaPokemon.FOTO, pokemon.getFoto());
        db.insert(ContratoPokemon.EntradaPokemon.NOMBRE_TABLA, null, values);
    }

    @Override
    public void update(Pokemon pokemon) {
// Obtenemos la BBDD para escritura
        SQLiteDatabase db = getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(ContratoPokemon.EntradaPokemon.NOMBRE, pokemon.getName());
        values.put(ContratoPokemon.EntradaPokemon.FOTO, pokemon.getFoto());
        // Actualizar...
        db.update(ContratoPokemon.EntradaPokemon.NOMBRE_TABLA,
                values,
                "name=?",
                new String[] {pokemon.getName()});
    }

    @Override
    public void delete(Pokemon pokemon) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContratoPokemon.EntradaPokemon.NOMBRE_TABLA,
                "name=?",
                new String[] {pokemon.getName()});
    }
}
