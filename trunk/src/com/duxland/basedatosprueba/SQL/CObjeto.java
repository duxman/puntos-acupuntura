package com.duxland.basedatosprueba.SQL;

import java.util.ArrayList;

public class CObjeto 
{
	private String NombreTabla;
	private ArrayList<String> Campos;
	private ArrayList<String> CamposSearch;
	private ArrayList<String> CamposShow;
	
	public CObjeto(String Tabla)
	{
		setNombreTabla(Tabla);
		setCampos(new ArrayList<String>());
		setCamposSearch(new ArrayList<String>());
		setCamposShow(new ArrayList<String>());
	}
	public String getNombreTabla() 
	{
		return NombreTabla;
	}
	public void setNombreTabla(String nombreTabla) 
	{
		NombreTabla = nombreTabla;
	}	
	public void addCampo(String Nombre ,String Tipo,String Ver)
	{
		getCampos().add(Nombre);
		
		if(Tipo.equals("CS"))
			getCamposSearch().add(Nombre);
		
		if(Ver.equals("S"))
			getCamposShow().add(Nombre);			
	}
	public ArrayList<String> getCampos() 
	{
		return Campos;
	}
	public ArrayList<String> getCamposSearch() 
	{
		return CamposSearch;
	}	
	public ArrayList<String> getCamposShow() 
	{
		return CamposShow;
	}
	void setCampos(ArrayList<String> campos) 
	{
		Campos = campos;
	}
	void setCamposSearch(ArrayList<String> camposSearch) 
	{
		CamposSearch = camposSearch;
	}
	void setCamposShow(ArrayList<String> camposShow) 
	{
		CamposShow = camposShow;
	}	
}
