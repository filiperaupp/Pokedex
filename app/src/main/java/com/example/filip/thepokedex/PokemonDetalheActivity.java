package com.example.filip.thepokedex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class PokemonDetalheActivity extends AppCompatActivity {

    PokemonsDetalheTask mTask;
    Pokemon mPokemonAtivo;
    ArrayList<Pokemon> mPokemons;
    int numeroPokemon;

    TextView txtTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detalhe);
        search();
        new DownloadImageTask((ImageView) findViewById(R.id.imageTeste))
                .execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
        numeroPokemon = (Integer) getIntent().getSerializableExtra("pokemonAtivo".toString());
        txtTeste = findViewById(R.id.txtTeste);
        //mPokemonAtivo = mPokemonAtivo.getByid(numeroPokemon);
        //mPokemonAtivo = PokemonHttp.loadPokemonDetail(numeroPokemon);


    }

    private void search() {
        if (mPokemons == null) {
            mPokemons = new ArrayList<Pokemon>();
        }

        if (mTask == null) {
            if (PokemonHttp.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(getApplicationContext(), "......", Toast.LENGTH_LONG).show();
        }
    }

    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new PokemonsDetalheTask();
            mTask.execute();
        }

    }

    //INNER CLASS ASICRONA
    class PokemonsDetalheTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Pokemon> doInBackground(Void... strings) {
            ArrayList<Pokemon> pokemonUnico = new ArrayList<>();
            mPokemonAtivo = PokemonHttp.loadPokemonDetail(numeroPokemon);
            pokemonUnico.add(mPokemonAtivo);
            return pokemonUnico;
        }
        @Override
        protected void onPostExecute(ArrayList<Pokemon> pokemons) {
            super.onPostExecute(pokemons);
            //     showProgress(false);
            if (pokemons != null) {
                mPokemons.clear();
                mPokemons.addAll(pokemons);
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
