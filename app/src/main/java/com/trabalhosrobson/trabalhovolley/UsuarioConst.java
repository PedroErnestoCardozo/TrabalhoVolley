package com.trabalhosrobson.trabalhovolley;

import android.graphics.Bitmap;

/**
 * Created by Liver on 05/05/2018.
 */

public class UsuarioConst {

    String nome, sobrenome, email;
    Bitmap img;

    public UsuarioConst(String nome, String sobrenome, String email, Bitmap img) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getImg() {
        return img;
    }
}
