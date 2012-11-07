package com.duxland.basedatosprueba;

import com.duxland.basedatosprueba.SQL.CDatos;
import com.duxland.basedatosprueba.SQL.CListDatos;
import com.duxland.basedatosprueba.SQL.CSQL;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class CSearchMain extends TabActivity implements OnTabChangeListener
{
	  GlobalClass global;
	  TabHost tb;
	  TabHost.TabSpec spectb;
	  String BaseNombre;
	  String BaseRuta;	
	  CListDatos datositems;
	  CSQL DatosSQL;
	   
	     /** Called when the activity is first created. */
	    @Override    
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        Bundle bundle=getIntent().getExtras();	        
	        BaseNombre=bundle.getString("BaseNombre");
			BaseRuta=bundle.getString("BaseRuta");			
	        
	        global=GlobalClass.getInstance();
	        global.setContext(this);
	        global.setBaseConsulta(BaseRuta);
	        	        
	        
	        CrearInterfaz();
	    }
	    private void CrearInterfaz()
	    {	        

	        
	    	CargarDatos();
	        Refresh();
	    }
	    private void Refresh()
	    {
	    	tb=getTabHost();	    	
	        Intent intent;
	        int i=0;
	    	for(CDatos d:datositems)
	        {	        	
	        	StringBuilder sb=new StringBuilder();
	        	sb.append(d.getValorCampo("_id"));
	        	sb.append(".- ");
	        	sb.append(d.getValorCampo("nombre"));
	        	
	        	intent=new Intent().setClass(this, CSearch.class);
	        	intent.putExtra("TipoId", d.getValorCampo("_id"));
	        	
		        spectb=tb.newTabSpec(String.format("%04d", i)+".BasesDatos-"+d.getValorCampo("_id")).setIndicator(sb.toString()).setContent(intent);       
		        tb.addTab(spectb);
		        i++;
	        }	        	        	
	    }	    	    
	    private void CargarDatos()
		{				
	    	global.getBaseConsulta().open();
	    	DatosSQL= new CSQL(global.getBaseConsulta().getBase(),"tipos");
	    	datositems=new CListDatos();
	    	global.getBaseConsulta().close();	    	
		}
		public void onTabChanged(String tabId) 
		{
//			// TODO Auto-generated method stub
//			
//			Integer id=Integer.parseInt(tabId.substring(0,4));
//			String TipoId=datositems.get(id).getValorCampo("_id");
//			
//			
//        	intent.putExtra("TipoId", d.getValorCampo("_id"));
		}

}
