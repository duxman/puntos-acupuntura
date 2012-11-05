package com.duxland.basedatosprueba;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TabHost;

public class CResult extends TabActivity 
{	  
	GlobalClass global;
	TabHost tb;
	TabHost.TabSpec spectb;
	
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
        
        Resources res=getResources();  	  	                                             
        intent=new Intent().setClass(this, CHtmlViewer.class);
        spectb=tb.newTabSpec("Buscar").setIndicator("",res.getDrawable(R.drawable.ssearch)).setContent(intent);       
        tb.addTab(spectb);                    
    }

}
