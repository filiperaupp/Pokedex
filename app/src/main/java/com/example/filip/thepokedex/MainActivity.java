package com.example.filip.thepokedex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PokemonsTask mTask;
    public static ArrayList<Pokemon> mPokemons;
    ListView mListPokemons;
    ArrayAdapter<Pokemon> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListPokemons = findViewById(R.id.mListPokemons);
        search();

        mListPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentResult = new Intent(MainActivity.this,PokemonDetalheActivity.class);
                intentResult.putExtra("pokemonAtivo",i+1 );
                MainActivity.this.startActivity(intentResult);
                //Toast.makeText(getApplicationContext(),itenClicado.getFrase().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search() {
        if (mPokemons == null) {
            mPokemons = new ArrayList<Pokemon>();
        }

        mAdapter = new PokemonAdapter(getApplicationContext(), mPokemons);
        mListPokemons.setAdapter(mAdapter);
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
            mTask = new PokemonsTask();
            mTask.execute();
        }
    }

    //INNER CLASS ASICRONA
    class PokemonsTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Pokemon> doInBackground(Void... strings) {
            ArrayList<Pokemon> previsaoList = PokemonHttp.loadPokemons();
            return previsaoList;
        }
        @Override
        protected void onPostExecute(ArrayList<Pokemon> pokemons) {
            super.onPostExecute(pokemons);
            //     showProgress(false);
            if (pokemons != null) {
                mPokemons.clear();
                mPokemons.addAll(pokemons);
                mAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }

}
