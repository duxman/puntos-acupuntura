package com.duxland.basedatosprueba.SQL;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CSQL 
{
	public static final String KEY_ID = "_id";
	public String NOM_TABLA ="";	
	
	//Array de strings para su uso en los diferentes m�todos
	
	private List<CDatos> Datos ;	
	private String[] cols; 
	SQLiteDatabase db;
	
	public CSQL(SQLiteDatabase BaseDatos,String nomtabla)
	{	
		super();
		NOM_TABLA=nomtabla;		
		db=BaseDatos;			
		setCols(GetColums());			
	}
		
	public long Inserta(CDatos datos) 
	{
		ContentValues newValues = new ContentValues();
		for(int i=0;i<datos.size();i++)
			newValues.put(datos.getNombreCampo(i), datos.getValorCampo(i));
		
		return db.insert(NOM_TABLA, null, newValues);
	}	
		 
	/**
	* BORRAR ALARMA CON _id = _rowIndex
	* */
	public boolean Remove(long _rowIndex) 
	{
		return db.delete(NOM_TABLA, "_id =" + _rowIndex, null) > 0;
	}	 
	/**
	* ACTUALIZAR ALARMA _id = _rowIndex
	* */
	public boolean Update(Integer _rowIndex,  CDatos datos) 
	{
		ContentValues newValues = new ContentValues();
		for(int i=0;i<datos.size();i++)
			newValues.put(datos.getNombreCampo(i), datos.getValorCampo(i));
		return db.update(NOM_TABLA, newValues,"_id =" + _rowIndex, null) > 0;
	}
	private String[] GetColums()
	{	
		String Sql="SELECT *  FROM " + NOM_TABLA +" LIMIT 1";
		Cursor result=db.rawQuery(Sql, null);
		return result.getColumnNames();	
	}
	
	public CListDatos getFila(long _rowIndex) 
	{
		CListDatos rtn = new CListDatos();
		String Sql="SELECT *  FROM " + NOM_TABLA +" WHERE " + KEY_ID +" = " +_rowIndex;
		Cursor result=db.rawQuery(Sql, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) 
		{
			Log.e(NOM_TABLA,"Tabla Vacia");		 
		} 
		else 
		{
			if (result.moveToFirst()) 			
			{	
				CDatos dat=new CDatos();
				for(int i=0;i<result.getColumnCount();i++)
				{
					dat.addCampo(result.getColumnName(i),result.getString(i));					
				}
				rtn.add(dat);
			}
		}
		return rtn;
	}
	public CListDatos getList()
	{
		return getList("");
	}
	public CListDatos getList(String Where)
	{
		CListDatos rtn = new CListDatos();
		String Sql;
		if(Where!="")
			Sql="SELECT *  FROM " + NOM_TABLA +" WHERE " + Where;
		else
			Sql="SELECT *  FROM " + NOM_TABLA;
			
		Cursor result=db.rawQuery(Sql, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) 
		{
			Log.e(NOM_TABLA,"Tabla Vacia");		 
		} 
		else 
		{
			if (result.moveToFirst()) 			
			{	
				do 
				{
					CDatos dat=new CDatos();
					for(int i=0;i<result.getColumnCount();i++)
					{
						dat.addCampo(result.getColumnName(i),result.getString(i));					
					}
					rtn.add(dat);
				} while(result.moveToNext());			
			}
		}
		return rtn;
	}
	public ArrayList<String> getArrayDatos(String columna,CListDatos datos)
	{
		ArrayList<String> rtn=new ArrayList<String>();		
		for(CDatos d:datos)
		{		
			rtn.add(d.getValorCampo(columna));			
		}		
		return rtn; 	
	}
	public ArrayList<String> getArrayDatos(String columna,String Where)
	{
		CListDatos tmp = new CListDatos();			
		tmp=getList(Where);
		return getArrayDatos(columna,tmp);		
	}

	public String[] getCols() 
	{
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public List<CDatos> getDatos() {
		return Datos;
	}

	public void setDatos(List<CDatos> datos) {
		Datos = datos;
	}		 	
}
