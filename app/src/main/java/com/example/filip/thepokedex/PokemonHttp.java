package com.example.filip.thepokedex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonHttp {

    public static String URL = "https://pokeapi.co/api/v2/pokedex/1";
    public static String URL_DETALHE;
    public static void setarUrlDetalhes(int idPokemon){
        URL_DETALHE = "https://pokeapi.co/api/v2/pokemon/"+idPokemon;
    }

    private static HttpURLConnection connectar(String urlWebservice) {
        final int SEGUNDOS = 10000;
        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        } catch (IOException e) {
            Log.d("ERRO", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Pokemon getPokemonFromJson(JSONObject json){
        int id_pokemon;
        String name_pokemon;
        Pokemon pokemonAtual = null;
        try {
            JSONObject pokemonSpecies = json.getJSONObject("pokemon_species");
            id_pokemon = json.getInt("entry_number");
            name_pokemon = pokemonSpecies.getString("name");
            pokemonAtual = new Pokemon(id_pokemon,name_pokemon);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return pokemonAtual;
    }

    public static ArrayList<Pokemon> readJson(JSONObject json) {
        ArrayList<Pokemon> arrayList = new ArrayList<>();
        try {
            JSONArray jsonPokemons= json.getJSONArray("pokemon_entries");
            for (int i=0; i<100; i++) {
                JSONObject onePokemon = jsonPokemons.getJSONObject(i);
                arrayList.add(getPokemonFromJson(onePokemon));
            }

        } catch (JSONException e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;

    }

    public static ArrayList<Pokemon> loadPokemons() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Pokemon>  pokemonsList =readJson(json);
                return pokemonsList;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }
//--------------------------------------------------------------------------------
    public static ArrayList<Pokemon> loadPokemonDetail(int numeroPokemon) {
        try {
            setarUrlDetalhes(numeroPokemon);
            HttpURLConnection connection = connectar(URL_DETALHE);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                Pokemon  pokemonFull= readDetailJson(json, numeroPokemon);
                ArrayList agoraVaiOPokemon = new ArrayList();
                agoraVaiOPokemon.add(pokemonFull);
                return agoraVaiOPokemon;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }
    public static Pokemon getPokemonDetailFromJson(JSONObject json, int numeroPokemon){
        Pokemon pokemonAtual = new Pokemon();
        pokemonAtual = pokemonAtual.getByid(numeroPokemon-1);
        String hp;
        String attack;
        String defense;
        String speed;
        String specialAttack;
        String specialDefense;

        String type1, type2;
        //image
        String urlSprite;

        try {
            //stats
            JSONArray pokemonStats = json.getJSONArray("stats");
            JSONObject pokeSpeedObject = pokemonStats.getJSONObject(0);
            speed =  pokeSpeedObject.getString("base_stat");

            JSONObject pokeSpeDefdObject = pokemonStats.getJSONObject(1);
            specialDefense =  pokeSpeDefdObject.getString("base_stat");

            JSONObject pokeSpeAtkdObject = pokemonStats.getJSONObject(2);
            specialAttack=  pokeSpeAtkdObject.getString("base_stat");

            JSONObject pokeDefObject = pokemonStats.getJSONObject(3);
            defense =  pokeDefObject.getString("base_stat");

            JSONObject pokeAtkObject = pokemonStats.getJSONObject(4);
            attack =  pokeAtkObject.getString("base_stat");

            JSONObject pokeHpObject = pokemonStats.getJSONObject(5);
            hp =  pokeHpObject.getString("base_stat");

            //sprite
            JSONObject pokeSpriteObject = json.getJSONObject("sprites");
            urlSprite = pokeSpriteObject.getString("front_default");

            //types
            JSONArray pokeTypesArray = json.getJSONArray("types");
            JSONObject pokeSlot2Object = pokeTypesArray.getJSONObject(0);
            JSONObject pokeType2Object = pokeSlot2Object.getJSONObject("type");
            type2 = pokeType2Object.getString("name");

            JSONObject pokeSlot1Object = pokeTypesArray.getJSONObject(1);
            JSONObject pokeType1Object = pokeSlot2Object.getJSONObject("type");
            type1 = pokeType2Object.getString("name");

            pokemonAtual.setRest(type1,type2,hp,attack,defense,speed,specialAttack,specialDefense,urlSprite);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return pokemonAtual;
    }

    public static Pokemon readDetailJson(JSONObject json, int numeroPokemon) {
        Pokemon pokemonFull = new Pokemon();
        try {

            pokemonFull = getPokemonDetailFromJson(json,numeroPokemon);

        } catch (Exception e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return pokemonFull;
    }
//--------------------------------------------------------------------------------

    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
