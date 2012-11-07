package com.duxland.basedatosprueba;

import java.util.ArrayList;
import com.duxland.basedatosprueba.SQL.*;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.TextView;
import android.widget.Toast;


public class CSearch extends Activity implements OnClickListener,OnItemClickListener 
{	
	static final int MENUDINAMIC=19900;
	GlobalClass global;
	Button btnSearch;
	EditText textSearch;
	ListView listView;
	String TipoId;
	String BaseNombre;
	String BaseRuta;	
							
	CListDatos datositems;
	CSQL DatosSQL;
	public void CreaInterfaz()
	{  				
		listView = (ListView) findViewById(R.id.listsearch);
		listView.setOnItemClickListener(this);
					
		btnSearch=(Button) findViewById(R.id.BtnSearch);
		btnSearch.setOnClickListener(this);
		
		textSearch=(EditText)findViewById(R.id.EditSearch);
		
		Bundle bundle=getIntent().getExtras();
		TipoId="1";		
        BaseNombre=bundle.getString("BaseNombre");
		BaseRuta=bundle.getString("BaseRuta");	
						
		global.setBaseConsulta(BaseRuta);
		
		datositems=new CListDatos();
		
		Rellenar("");
		
	}
	/** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) 
    {       
    	super.onCreate(savedInstanceState);    	    
	    global=GlobalClass.getInstance();	    
	    setContentView(R.layout.searchlay);
        CreaInterfaz();
        
	    String Titulo=(String) getTitle();
	    Titulo+="["+BaseNombre+"]";
	    setTitle(Titulo);
    }        
    @Override public boolean onCreateOptionsMenu(Menu menu)
    {    	    	
    	global.getBaseConsulta().open();
	    DatosSQL= new CSQL(global.getBaseConsulta().getBase(),"tipos");
	    CListDatos dats=DatosSQL.getList();	    
    	for(CDatos d:dats)
    	{    		
    		int i=MENUDINAMIC+Integer.parseInt( d.getValorCampo("_id"));
    		menu.add(Menu.NONE,i,Menu.NONE,d.getValorCampo("_id")+".- "+d.getValorCampo("nombre"));    		    		
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
    	else if(item.getItemId() == R.id.refresh)
    	{
    		Rellenar("");
    		return true;
    	}  
    	else 
    	{    		
    		TipoId=String.valueOf(item.getItemId()-MENUDINAMIC);
    		datositems.clear();    		
    		Rellenar("");
    	}    	
    	return false;
    }	    
    
    public void Rellenar(String Filtro)
    {    	
    	String where="";    	
    	global.getBaseConsulta().open();
	    DatosSQL= new CSQL(global.getBaseConsulta().getBase(),"datos");	         	        	
    	
	    if(Filtro!="")
    		where=GeneraWhere(Filtro);
    	else
    		where=GeneraWhere();
    		
    	
    	datositems=DatosSQL.getList(where);
    	
    	ArrayList<String> tmp=new ArrayList<String>();
    	tmp=DatosSQL.getArrayDatos("nombre",datositems);
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
    private String GeneraWhere()
    {
    	String where ="tipoid= "+TipoId;    			
    	return where;
    }
    private String GeneraWhere(String Filtro)
    {
    	String where ="tipoid= "+TipoId+" AND (( nombre like '%"+Filtro+"%' OR nombre  like '"+Filtro+"%' OR nombre  like '%"+Filtro+"' ) ";
    		   where+=" OR ( descripcion like '%"+Filtro+"%' OR descripcion  like '"+Filtro+"%' OR descripcion  like '%"+Filtro+"' ) )";    			
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
		Intent intent = new Intent(global.getContext(), CHtmlViewer.class);
		intent.putExtra("TipoId", TipoId);
		intent.putExtra("Id", datositems.get(position).getValorCampo("_id"));		
		intent.putExtra("BaseNombre", BaseNombre);
	    startActivity(intent);						
	}	
}
