package com.example.filip.thepokedex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class DetalheAdapter   extends ArrayAdapter<Pokemon> {

    public DetalheAdapter (@NonNull Context context, @NonNull List<Pokemon> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Pokemon pokemonDetalheAtual= getItem(position);

        View listPokemons = convertView;
        if(convertView==null) {
            listPokemons= LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon_detalhe,null);
        }

        new DownloadImageTask((ImageView) listPokemons.findViewById(R.id.imageTeste))
            .execute(pokemonDetalheAtual.getUrlSprite());

        TextView txtId = listPokemons.findViewById(R.id.txtId);
        txtId.setText("ID: " + String.valueOf(pokemonDetalheAtual.getId()));
        TextView txtNome = listPokemons.findViewById(R.id.txtNome);
        txtNome.setText(pokemonDetalheAtual.getName());
        TextView txtTipo1 = listPokemons.findViewById(R.id.txtTipo1);
        txtTipo1.setText("Tipo(s): " +pokemonDetalheAtual.getType2()+", "+pokemonDetalheAtual.getType1());


        TextView txtHp = listPokemons.findViewById(R.id.txtHp);
        txtHp.setText("HP: " +pokemonDetalheAtual.getHp());
        TextView txtAtk = listPokemons.findViewById(R.id.txtAtk);
        txtAtk.setText("ATK: " +pokemonDetalheAtual.getAttack());
        TextView txtDef= listPokemons.findViewById(R.id.txtDef);
        txtDef.setText("DEF: " +pokemonDetalheAtual.getDefense());
        TextView txtSpeAtk = listPokemons.findViewById(R.id.txtSpeAtk);
        txtSpeAtk.setText("SPEATK: " +pokemonDetalheAtual.getSpecialAttack());
        TextView txtSpeDef = listPokemons.findViewById(R.id.txtSpeDef);
        txtSpeDef.setText("SPEDEF: " +pokemonDetalheAtual.getSpecialDefense());
        TextView txtSpeed = listPokemons.findViewById(R.id.txtSpeed);
        txtSpeed.setText("SPD: " +pokemonDetalheAtual.getSpeed());

        return listPokemons;
    }
//---------------------------------------------------------------------------------------------------------
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

