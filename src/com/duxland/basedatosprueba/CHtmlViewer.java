package com.duxland.basedatosprueba;
import com.duxland.basedatosprueba.GlobalClass;
import com.duxland.basedatosprueba.R;
import com.duxland.basedatosprueba.SQL.CDatos;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CSQL;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class CHtmlViewer extends Activity 
{	
	GlobalClass global;
	private WebView web;	
	private String CampoId;	
	CListDatos datositems;
	CSQL DatosSQL;
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htmlviewer_lay);               
        global=GlobalClass.getInstance();               
    	
        CrearInterfaz();       
    }
	private void CargarDatos()
	{			
		CListDatos dat=new CListDatos();
		
		dat=DatosSQL.getFila(Integer.parseInt(CampoId));
		
		String summary = GenerarHTML(dat.get(0));
		web.loadData(summary, "text/html","UTF-8" );
	}
	
	private String GenerarHTML(CDatos dat)
	{			
		StringBuilder sb=new StringBuilder();
		sb.append("<HTML><HEAD><meta name='tipo_contenido'  content='text/html;' http-equiv='content-type' charset='utf-8'></HEAD><BODY><Table>");					
		sb.append("<tr><td aling='center'><b><h2>");
		sb.append(dat.getValorCampo("nombre"));									
		sb.append("</h2></b></td></tr>");					
		sb.append(dat.getValorCampo("descripcion"));											
		sb.append("</td></tr></Table></BODY></HTML>");
				
		return sb.toString(); 				
	}
	private void CrearInterfaz()
	{		
		web=(WebView)findViewById(R.id.VisorHTML);		
		
		Bundle bundle=getIntent().getExtras();		
		CampoId=bundle.getString("Id");
		String BaseNombre=bundle.getString("BaseNombre");
							  
	    setTitle(((String) getTitle())+"["+BaseNombre+"]");
		
		global.getBaseConsulta().open();
		DatosSQL= new CSQL(global.getBaseConsulta().getBase(),"datos");		
		
		CargarDatos();
	}	
}
