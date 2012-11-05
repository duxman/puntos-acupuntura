package com.duxland.basedatosprueba.SQL;
import java.util.ArrayList;
import android.util.Log;

public class CDatos 
{
	private ArrayList<String> m_Claves;
	public ArrayList<String> m_Valores;
	
	public CDatos()
	{	
		m_Claves=new ArrayList<String>();
		m_Valores=new ArrayList<String>();
	}
	public CDatos(String c,String v)
	{
		this();
		addCampo(c,v);
	}
	public String getValorCampo(String c)
	{
		return getValorCampo(m_Claves.indexOf(c));
	}
	public String getValorCampo(int idx)
	{
		return m_Valores.get(idx);
	}
	public String getNombreCampo(int idx)
	{
		return m_Claves.get(idx);
	}
	public long size()
	{
		return m_Claves.size();
	}
	public Boolean addCampo(String c,String v)
	{
		Boolean rtn=false;
		try
		{			
			if(ExisteCampo(c))
			{
				int idx=m_Claves.indexOf(c);
				m_Valores.set(idx,v);
				rtn=true;
			}
			else
			{
				m_Claves.add(c);
				m_Valores.add(v);
				rtn=true;
			}
		}
		catch(Exception e)
		{
			Log.e("Campos ", "Error añadiendo un campo " + e);
			rtn=false;	
		}	
		return rtn;
	}
	public Boolean removeCampo(String  c)
	{
		Boolean rtn=false;
		try
		{
			if(ExisteCampo(c))
			{
				int idx=m_Claves.indexOf(c);
				m_Claves.remove(idx);
				m_Valores.remove(idx);
				rtn=true;
			}			
		}
		catch(Exception e)
		{
			Log.e("Campos ", "Error Eliminado un campo " + e);
			rtn=false;	
		}		
		return rtn;		
	}
	
	public Boolean ExisteCampo(String campo)
	{		
		return m_Claves.contains(campo);
	}	
}

