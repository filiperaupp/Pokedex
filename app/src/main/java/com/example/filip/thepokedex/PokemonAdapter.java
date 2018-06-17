package com.example.filip.thepokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter  extends ArrayAdapter<Pokemon> {

    public PokemonAdapter (@NonNull Context context, @NonNull List<Pokemon> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Pokemon pokemonAtual= getItem(position);

        View listPokemons = convertView;
        if(convertView==null) {
            listPokemons= LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon,null);

        }
        TextView txtIdPolemon= listPokemons.findViewById(R.id.txtIdPokemon);
        TextView txtNomePokemon = listPokemons.findViewById(R.id.txtNomePokemon);


        txtIdPolemon.setText(String.valueOf(pokemonAtual.getId()));
        txtNomePokemon.setText(pokemonAtual.getName());

        return listPokemons;
    }
}
