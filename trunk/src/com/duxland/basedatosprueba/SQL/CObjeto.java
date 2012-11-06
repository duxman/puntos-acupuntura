package com.duxland.basedatosprueba.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CObjeto 
{
	private String NombreTabla;
	private List<String> Campos;
	private List<String> CamposSearch;
	private List<String> CamposDescripcion;
	private List<String> CamposTitulo;
	private String CampoLista;
	private String Formato;
	
	public CObjeto(String Tabla)
	{
		setNombreTabla(Tabla);
		setCampos(new ArrayList<String>());
		setCamposSearch(new ArrayList<String>());
		setCamposTitulo(new ArrayList<String>());
		setCamposDescripcion(new ArrayList<String>());
		setFormato("");
		setCampoLista("");
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
		{
			setCampoLista(Nombre);
		}
	}
	public void addCampoTitulo(String Formatos)
	{		
		setCamposTitulo(Arrays.asList(Formatos.split("\\s+")));		
	}
	public void addCampoDescripcion(String Formatos)
	{		
		setCamposDescripcion(Arrays.asList(Formatos.split("\\s+")));		
	}
	public List<String> getCampos() 
	{
		return Campos;
	}
	public List<String> getCamposSearch() 
	{
		return CamposSearch;
	}	
	public List<String> getCamposTitulo() 
	{
		return CamposTitulo;
	}
	void setCampos(List<String> campos) 
	{
		Campos = campos;
	}
	void setCamposSearch(List<String> camposSearch) 
	{
		CamposSearch = camposSearch;
	}
	void setCamposTitulo(List<String> camposShow) 
	{
		CamposTitulo = camposShow;
	}
	public String getCampoLista() {
		return CampoLista;
	}
	public void setCampoLista(String campoLista) {
		CampoLista = campoLista;
	}
	public String getFormato() {
		return Formato;
	}
	public void setFormato(String formato) {
		Formato = formato;
	}
	public List<String> getCamposDescripcion() {
		return CamposDescripcion;
	}
	public void setCamposDescripcion(List<String> camposDescripcion) {
		CamposDescripcion = camposDescripcion;
	}	
}
