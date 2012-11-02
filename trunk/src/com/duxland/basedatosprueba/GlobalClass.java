package com.duxland.basedatosprueba;
import com.duxland.basedatosprueba.SQL.BaseDatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public  class GlobalClass
{
	private BaseDatos base;
	private String miAplicacion;
	private String miDB;
	private static GlobalClass instance;
	private Context c;
	    
	static 
    {
        instance = new GlobalClass();
    }
 
    public static GlobalClass getInstance() 
    {
        return GlobalClass.instance;
    }
	
	public GlobalClass()
	{			
	}
	
	public SQLiteDatabase getBaseDatos() 
	{
		return base.database;
	}	
	private void setBase(BaseDatos base) 
	{
		this.base = base;
	}

	public Context getContext() 
	{
		return c;
	}

	public void setContext(Context con) 
	{
		c = con;
		miAplicacion=c.getString(R.string.app_dir); 		
        miDB=c.getString(R.string.bd_name);
        setBase(new BaseDatos(c,miAplicacion,miDB)); //creamos el objeto de tipo BaseDatos
        base.open();
	}
	
}
