package com.duxland.basedatosprueba;
import java.util.ArrayList;
import java.util.List;

import com.duxland.basedatosprueba.GlobalClass;
import com.duxland.basedatosprueba.R;
import com.duxland.basedatosprueba.R.string;
import com.duxland.basedatosprueba.SQL.CDatos;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CObjeto;
import com.duxland.basedatosprueba.SQL.CSQL;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class CHtmlViewer extends Activity 
{	
	GlobalClass global;
	private WebView web;
	private String TablaUsada;
	private String CampoId;
	CObjeto ObjetoDatos;	
	List<String> CamposSearch;
	List<String> CamposTitulo;
	List<String> CamposDescripcion;
	String Formato;
	String CampoMostrar;
	CListDatos datositems;
	CSQL DatosSQL;
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htmlviewer_lay);               
        global=GlobalClass.getInstance();
        
        CamposSearch=new ArrayList<String>();
    	CamposTitulo=new ArrayList<String>();
    	CamposDescripcion=new ArrayList<String>();
    	
        CrearInterfaz();       
    }
	private void CargarDatos()
	{
		DatosSQL.setNOM_TABLA(TablaUsada);
		CListDatos dat=new CListDatos();
		dat=DatosSQL.getFila(Integer.parseInt(CampoId));
		String summary = GenerarHTML(dat.get(0));
		web.loadData(summary, "text/html","UTF-8" );
	}
	public void RellenarObjSearch()
    {
		ObjetoDatos=global.getListaObjetos().get(TablaUsada);
    	CamposSearch=ObjetoDatos.getCamposSearch();
    	CamposDescripcion=ObjetoDatos.getCamposDescripcion();
    	CamposTitulo=ObjetoDatos.getCamposTitulo();
    	CampoMostrar=ObjetoDatos.getCampoLista();
    	Formato=ObjetoDatos.getFormato();
    }
	private String GenerarHTML(CDatos dat)
	{			
		StringBuilder sb=new StringBuilder();
		sb.append("<HTML><HEAD><meta name='tipo_contenido'  content='text/html;' http-equiv='content-type' charset='utf-8'></HEAD><BODY><Table>");					
		sb.append("<tr><td aling='center'><b><h2>");
		for(String s:CamposTitulo)
		{			
			sb.append(dat.getValorCampo(s));							
			sb.append("&nbsp;&nbsp;&nbsp;");
		}
		sb.append("</h2></b></td></tr>");
		
		sb.append("<tr><td aling='center'><UL>");
		for(String s:CamposDescripcion)
		{			
			sb.append("<p><li>");
			sb.append(dat.getValorCampo(s));
			sb.append("</li></p>");			
		}
		sb.append("</UL></td></tr>");
		
		sb.append("</Table></BODY></HTML>");
				
		return sb.toString(); 				
	}
	private void CrearInterfaz()
	{		
		Bundle bundle=getIntent().getExtras();
		TablaUsada=bundle.getString("Tabla");
		CampoId=bundle.getString("Id");
		DatosSQL= new CSQL(global.getBaseDatos(),TablaUsada);
		RellenarObjSearch();
		web=(WebView)findViewById(R.id.VisorHTML);		
		CargarDatos();
	}	
}
