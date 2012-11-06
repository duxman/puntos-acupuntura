package com.duxland.basedatosprueba;
import java.util.Hashtable;
import com.duxland.basedatosprueba.SQL.BaseDatos;
import com.duxland.basedatosprueba.SQL.CDatos;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CObjeto;
import com.duxland.basedatosprueba.SQL.CSQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public  class GlobalClass
{
	private BaseDatos base;
	private String miAplicacion;
	private String miDB;
	private Hashtable< String , CObjeto>ListaObjetos;
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
        ListaObjetos=new Hashtable<String, CObjeto>(); 
        CargarObjetos();
	}
	private void CargarObjetos()
	{
		CSQL objetos = new CSQL(getBaseDatos(),"Objetos");
		CListDatos objTabla=new CListDatos();
		objTabla=objetos.getList("TipoObj='T'");		
		for(CDatos d:objTabla)
		{			
			CObjeto tmp= new CObjeto(d.getValorCampo("Nombre"));
			
			tmp.addCampoTitulo(d.getValorCampo("CamposTitulo"));
			tmp.addCampoDescripcion(d.getValorCampo("CamposDescripcion"));
			
			CListDatos objCampo=new CListDatos();
			objCampo=objetos.getList("Padre="+d.getValorCampo("_id"));
			
			for(CDatos d1:objCampo)
			{
				String Nomtmp=d1.getValorCampo("Nombre");
				String Tipotmp=d1.getValorCampo("TipoObj");
				String VerTmp=d1.getValorCampo("Mostrar");				
				
				tmp.addCampo(Nomtmp, Tipotmp, VerTmp);				
			}					
			getListaObjetos().put(tmp.getNombreTabla(), tmp);
		}
		
	}

	public Hashtable< String , CObjeto> getListaObjetos() 
	{
		return ListaObjetos;
	}
}

