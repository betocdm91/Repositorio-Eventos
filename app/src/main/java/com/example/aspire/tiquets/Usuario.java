package com.example.aspire.tiquets;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aspire on 18/08/2017.
 */

public class Usuario implements Parcelable {

    String usuario, contrasenia;

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    //constructor sobrecargado
    public Usuario(Parcel in) {//in:intent
        this.usuario = in.readString();
        this.contrasenia = in.readString();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /////////Parceleabe/////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.usuario);
    }


    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
