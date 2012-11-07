package com.duxland.basedatosprueba;

import java.util.ArrayList;

import com.duxland.basedatosprueba.R.id;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CSQL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CListaBases extends Activity implements OnItemClickListener
{
	GlobalClass global;
	CSQL DatosSQL;
	ListView listview;
	CListDatos datositems;
	
	@Override public void onCreate(Bundle savedInstanceState) 
    {       
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.listabases_lay);    	
	    global=GlobalClass.getInstance();
	    
	    listview=(ListView)findViewById(id.listBases);
	    listview.setOnItemClickListener(this);
	    CargarDatos();
    }
	private void CargarDatos()
	{				
		global.getBaseDatos().open();
		DatosSQL= new CSQL(global.getBaseDatos().getBase(),"bases");
		datositems=new CListDatos();
		datositems=DatosSQL.getList("");
		
		ArrayList<String> tmp=new ArrayList<String>();
    	tmp=DatosSQL.getArrayDatos("nombre",datositems);
    	
    	if(tmp.size()>0)
    	{
	    	String[] array=new String[tmp.size()];
	    	for(int i=0;i<tmp.size();i++)
	    		array[i]=tmp.get(i);
	    		    	
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);    
	    	listview.setAdapter(adapter);
    	}	    	
    	global.getBaseDatos().close();
	}
	@Override public boolean onCreateOptionsMenu(Menu menu)
    {    	    
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.searchmenu, menu);
    	return true;
    }
	@Override public boolean onOptionsItemSelected(MenuItem item)
    { 	
    	if(item.getItemId() == R.id.exit)
    	{
    		this.finish();
    		return true;
    	}
    	else if(item.getItemId() == R.id.refresh)
    	{
    		CargarDatos();
    		return true;
    	}     	    	    	    	    
    	return false;
    }	
	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion, long arg3) 
	{
		String BaseNombretmp=datositems.get(posicion).getValorCampo("nombre");
		String BaseRutatmp=datositems.get(posicion).getValorCampo("ruta");
		
		Intent intent = new Intent(global.getContext(), CSearch.class);			
		
		intent.putExtra("BaseNombre", BaseNombretmp);
		intent.putExtra("BaseRuta", BaseRutatmp);

	    startActivity(intent);		
		
	}
}
