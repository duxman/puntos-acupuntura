package com.duxland.basedatosprueba;

import java.util.ArrayList;
import com.duxland.basedatosprueba.SQL.CDatos;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CSQL;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class CSearch extends Activity implements OnClickListener 
{
	GlobalClass global;
	Button btnSearch;

	ListView listView;
	String TablaUsada;
	Boolean ObjSearch=false;
	ArrayList<String> CamposSearch;
	String CampoMostrar;
	public void CreaInterfaz()
	{  
		CamposSearch=new ArrayList<String>();		
		listView = (ListView) findViewById(R.id.listsearch);
		btnSearch=(Button) findViewById(R.id.BtnSearch);
		btnSearch.setOnClickListener(this);
		Rellenar("");
	}
	/** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) 
    {       
    	super.onCreate(savedInstanceState);
	    global=GlobalClass.getInstance();
	    setContentView(R.layout.searchlay);
	    TablaUsada="Diagnosticos";
	    ObjSearch=false;	    
	    CampoMostrar="";
	    CreaInterfaz();    	                              
    }
    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	//generates a Menu from a menu resource file
    	//R.menu.main_menu represents the ID of the XML resource file
    	inflater.inflate(R.menu.searchmenu, menu);
    	return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item)
    { 	
    	//check selected menu item
    	// R.id.exit is @+id/exit
    	if(item.getItemId() == R.id.exit)
    	{
    		//close the Activity
    		this.finish();
    		return true;
    	}
    	else if(item.getItemId()==R.id.sdiagnosticos)
    	{
    		TablaUsada="Diagnosticos";
    		CamposSearch.clear();
    		ObjSearch=false;
    	}    	
    	else if(item.getItemId()==R.id.spuntos)
    	{
    		TablaUsada="Puntos";
    		CamposSearch.clear();
    		ObjSearch=false;
    	}
    	Rellenar("");    	
    	return false;
    }	
    
    public ArrayList<String> RellenarObjSearch()
    {
    	ArrayList<String> rtn=new ArrayList<String>();
    	CSQL objetos = new CSQL(global.getBaseDatos(),"Objetos");    	
    	
    	CListDatos dobj=new CListDatos();
    	
    	String _idPadre=objetos.getList("Nombre='"+TablaUsada+"'").get(0).getValorCampo("_id");    	
    	dobj=objetos.getList(" Padre="+_idPadre);
    	for(CDatos d:dobj)
    	{
    		if(d.getValorCampo("TipoObj")=="CS")
    			rtn.add(d.getValorCampo("Nombre"));
    		else if(d.getValorCampo("Mostrar")=="S")
    			CampoMostrar=d.getValorCampo("Nombre");
    	}    	
    	if(rtn.size()>0)
    		ObjSearch=true;
    	return rtn;    	    	    	   
    }
    public void Rellenar(String Filtro)
    {
    	String where="";
    	CSQL Datos = new CSQL(global.getBaseDatos(),TablaUsada);
    	ArrayList<String> tmp=new ArrayList<String>();
    	if(!ObjSearch)
    	{    		
    		CamposSearch=RellenarObjSearch();
    	}
    	if(Filtro!="")
    		where=GeneraWhere(Filtro);    	    	
    	tmp=Datos.getArrayDatos(CampoMostrar,where);
    	 
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, (String[]) tmp.toArray());    
    	listView.setAdapter(adapter);    	
    }
    private String GeneraWhere(String Filtro)
    {
    	String where="";
    	Boolean primero=true;
		for(String s:CamposSearch)
		{
			if(primero)
			{
				where="(";
				primero=false;
			}
			else
			{
				where+=" OR ";    			
			}    		
			where+=s+" like '%"+Filtro+"%'";
		}
		where+=")";
    	return where;
    }        
	public void onClick(View v) 
	{
		if(v==btnSearch)
		{
			
		}
		// TODO Auto-generated method stub
		
	}	
}
