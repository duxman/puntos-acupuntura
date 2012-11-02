package com.duxland.basedatosprueba.Datos;

import com.duxland.basedatosprueba.SQL.CDatos;

public class CDiagnosticos
{				
	private String m_Nombre;
	private String m_Descripcion;	
	
	public static final String KEY_ID = "_id";
	public final static String KEY_COL1 = "DiagNom";
	public final static String KEY_COL2 = "DiagDes";
		 
	public CDiagnosticos(String nom, String  des) 
	{
		super();		
		this.setNombre(nom);
		this.setDescripcion(des);
	}
	public CDiagnosticos() 
	{
		super();
		this.setNombre("");
		this.setDescripcion("");
	}
	public CDiagnosticos(CDatos c)
	{
		super();
		setNombre(c.getValorCampo(KEY_COL1));
		setDescripcion(c.getValorCampo(KEY_COL2));		
	}
	public CDatos getDatos()
	{
		CDatos rtn=new CDatos();
		rtn.addCampo(KEY_COL1, getNombre());
		rtn.addCampo(KEY_COL2, getDescripcion());
		return rtn;				
	}

	public String getNombre() 
	{
		return m_Nombre;
	}

	public void setNombre(String m_Nombre) 
	{
		this.m_Nombre = m_Nombre;
	}

	public String getDescripcion() 
	{
		return m_Descripcion;
	}

	public void setDescripcion(String m_Descripcion) 
	{
		this.m_Descripcion = m_Descripcion;
	}
		 
}
