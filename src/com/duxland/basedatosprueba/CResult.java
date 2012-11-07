package com.duxland.basedatosprueba;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class CResult extends TabActivity 
{	  
	GlobalClass global;
	TabHost tb;
	TabHost.TabSpec spectb;
	private String TipoId;
	private String CampoId;
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultview_lay);               
        global=GlobalClass.getInstance();               
        CrearInterfaz();
    }
    private void CrearInterfaz()
    {
    	tb=getTabHost();
        Intent intent;
        
        Bundle bundle=getIntent().getExtras();
        TipoId=bundle.getString("TipoId");
		CampoId=bundle.getString("Id");		
		        
        intent=new Intent().setClass(this, CHtmlViewer.class);
        
        intent.putExtra("TipoId", TipoId);
		intent.putExtra("Id", CampoId);
    	
        spectb=tb.newTabSpec("Buscar").setIndicator(CampoId).setContent(intent);       
        tb.addTab(spectb);                    
    }

}
