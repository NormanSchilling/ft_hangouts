package com.exemple.hangouts.hangouts.BDD;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nono on 27/11/15.
 */
public class Contact implements Parcelable {
    private long        id;
    private String      name;
    private String      numero;

    private Contact(Parcel in) {
        id = in.readLong();
        name = in.readString();
        numero = in.readString();
    }

    public Contact(long id, String name, String numero)
    {
        super();
        this.id = id;
        this.name = name;
        this.numero =numero;
    }

    public Contact(String name, String numero)
    {
        super();
        this.name = name;
        this.numero =numero;
    }

    public long getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNumero()
    {
        return this.numero;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(numero);
    }
}
