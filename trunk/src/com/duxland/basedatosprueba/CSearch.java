package com.duxland.basedatosprueba;

import java.util.ArrayList;
import java.util.List;

import com.duxland.basedatosprueba.SQL.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class CSearch extends Activity implements OnClickListener,OnItemClickListener 
{
	static final int MENUDINAMIC=19900;
	GlobalClass global;
	Button btnSearch;
	EditText textSearch;
	ListView listView;
	String TablaUsada;
	List<String> ListaTablas;
	CObjeto ObjetoDatos;
	Boolean ObjSearch=false;
	List<String> CamposSearch;
	String CampoMostrar;
	CListDatos datositems;
	CSQL DatosSQL;
	public void CreaInterfaz()
	{  
		CamposSearch=new ArrayList<String>();	
		ListaTablas=new ArrayList<String>();
		listView = (ListView) findViewById(R.id.listsearch);
		listView.setOnItemClickListener(this);
		btnSearch=(Button) findViewById(R.id.BtnSearch);
		btnSearch.setOnClickListener(this);
		textSearch=(EditText)findViewById(R.id.EditSearch);
		Rellenar("");
	}
	/** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) 
    {       
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.searchlay);
    	TablaUsada="Diagnosticos";
	    global=GlobalClass.getInstance();
	    datositems=new CListDatos();
	    DatosSQL= new CSQL(global.getBaseDatos(),TablaUsada);
	    
	    
	    ObjSearch=false;	    
	    CampoMostrar="";
	    CreaInterfaz();    	                              
    }
    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
    	
    	//generates a Menu from a menu resource file
    	//R.menu.main_menu represents the ID of the XML resource file
    	
    	
    	int i=MENUDINAMIC;
    	for(String s:global.getListaObjetos().keySet())
    	{    		
    		menu.add(Menu.NONE,i,Menu.NONE,s);
    		ListaTablas.add(s);
    		i++;
    	}
    	MenuInflater inflater = getMenuInflater();
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
    	else 
    	{
    		int id=item.getItemId()-MENUDINAMIC;    		
    		TablaUsada=ListaTablas.get(id);
    		DatosSQL.setNOM_TABLA(TablaUsada);
    		CamposSearch.clear();
    		RellenarObjSearch();
    		datositems.clear();
    		Rellenar("");
    	}    	    	    	    	
    	return false;
    }	
    
    public void RellenarObjSearch()
    {
    	ObjetoDatos=global.getListaObjetos().get(TablaUsada);
    	CamposSearch=ObjetoDatos.getCamposSearch();
    	CampoMostrar=ObjetoDatos.getCampoLista();
    	if(CamposSearch.size()>0)    	    	      
    		ObjSearch=true;    	   	  
    }
    public void Rellenar(String Filtro)
    {
    	String where="";    	
    	ArrayList<String> tmp=new ArrayList<String>();
    	if(!ObjSearch)
    	{    		
    		RellenarObjSearch();
    	}
    	if(Filtro!="")
    		where=GeneraWhere(Filtro);       	
    	datositems=DatosSQL.getList(where);
    	tmp=DatosSQL.getArrayDatos(CampoMostrar,datositems);
    	if(tmp.size()>0)
    	{
	    	String[] array=new String[tmp.size()];
	    	for(int i=0;i<tmp.size();i++)
	    		array[i]=tmp.get(i);
	    		
	    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);    
	    	listView.setAdapter(adapter);
    	}
    	else
    	{
    		Toast toast1 = Toast.makeText(this,"No se ha encontrado ningun resultado", Toast.LENGTH_LONG);
    	    toast1.show();	
    	}
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
			where+=" ( " + s+" like '%"+Filtro+"%' OR " + s+" like '"+Filtro+"%' OR " + s+" like '%"+Filtro+"' ) "  ;
		}
		where+=")";
    	return where;
    }        
	public void onClick(View v) 
	{
		if(v==btnSearch)
		{
			String text=textSearch.getText().toString();
			Rellenar(text);			
		}
		// TODO Auto-generated method stub
		
	}
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Intent intent = new Intent(global.getContext(), CResult.class);
		intent.putExtra("Tabla", TablaUsada);
		intent.putExtra("Id", datositems.get(position).getValorCampo("_id"));
	    startActivity(intent);						
	}	
}
