package com.exemple.hangouts.hangouts.BDD;

/**
 * Created by nono on 27/11/15.
 */
public class Contact
{
    private long        id;
    private String      name;
    private String      numero;

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
}
