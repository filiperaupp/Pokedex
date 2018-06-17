package com.example.filip.thepokedex;

public class Pokemon {
    private int id;
    private String name;
    private String type1, type2;

    //stats
    private String hp;
    private String attack;
    private String defense;
    private String speed;
    private String specialAttack;
    private String specialDefense;

    //image
    private String urlSprite;

    public Pokemon(){}

    public Pokemon(int id_pokemon, String name){
        this.id = id_pokemon;
        this.name = name;
    }

    public void setRest(String type1, String type2, String hp, String attack, String defense, String speed, String specialAttack, String specialDefense, String urlSprite) {
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.urlSprite = urlSprite;
    }

    public Pokemon getByid(int id){
        return MainActivity.mPokemons.get(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getHp() {
        return hp;
    }

    public String getAttack() {
        return attack;
    }

    public String getDefense() {
        return defense;
    }

    public String getSpeed() {
        return speed;
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public String getSpecialDefense() {
        return specialDefense;
    }

    public String getUrlSprite() {
        return urlSprite;
    }
}
