package com.duxland.basedatosprueba.SQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class BaseDatos extends SQLiteOpenHelper
{
	//Ruta por defecto de las bases de datos en el sistema Android
	private String DB_PATH;
	private String DB_NAME;	 
	public SQLiteDatabase database;	 
	private final Context context;
	 
   
	public BaseDatos(Context c,String PaqueteName,String DatabaseName)
    {		
		super(c, DatabaseName, null, 1);		
		DB_PATH=Environment.getExternalStorageDirectory ()+"/"+PaqueteName+"/databases/";
		DB_NAME=DatabaseName;
    	this.context = c;    	     	              
    }
	/**
	* Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException
	{	 
		boolean dbExist = checkDataBase();	 
		if(!dbExist)		
		{
			//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
			//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
			this.getReadableDatabase();	 
			try 
			{	 
				copyDataBase();
			} 
			catch (IOException e) 
			{
				Log.e(DB_NAME, "Error copiando Base de Datos " + e);   
				throw new Error("Error copiando Base de Datos");
			}
		}	 
	}
	/**
	* Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
	* @return true si existe, false si no existe
	*/
	private boolean checkDataBase()
	{
		SQLiteDatabase checkDB = null;
	 
		try
		{		
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);		 
		}
		catch(SQLiteException e)
		{		 			
			//si llegamos aqui es porque la base de datos no existe todavía.
			Log.i(DB_NAME, "La base de datos no existe todavía " + e);
		}
		if(checkDB != null)
		{		 
			checkDB.close();		 
		}
		return checkDB != null ? true : false;
	}
	/**
	* Copia nuestra base de datos desde la carpeta assets a la recién creada
	* base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException
	{
	 
		try 
		{				
			//Abrimos el fichero de base de datos como entrada
			InputStream myInput = context.getAssets().open(DB_NAME);	 
			//Ruta a la base de datos vacía recién creada
			String outFileName = DB_PATH + DB_NAME;
			File folder = new File(DB_PATH);
			boolean success = false;
	        if(!folder.exists()){
	            success = folder.mkdirs();
	        }
	        if (!success){ 
	            Log.d("Directorio ","Folder not created.");
	        }
	        else{
	            Log.d("Directorio ","Folder created!");
	        }
			
			
			
			//Abrimos la base de datos vacía como salida
			
			FileOutputStream myOutput = new FileOutputStream(outFileName);
		 
			//Transferimos los bytes desde el fichero de entrada al de salida
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer))>0)
			{
				myOutput.write(buffer, 0, length);
			}	 
			//Liberamos los streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} 
		catch (IOException e) 
		{
			Log.e(DB_NAME,"Error Copiando la Base de Datos " +  e );
		}
	}
	public void open() throws SQLException
	{
		 
		//Abre la base de datos
		try 
		{
			createDataBase();
		} 
		catch (IOException e) 
		{
			Log.e(DB_NAME,"Ha sido imposible crear la Base de Datos" +  e );
			throw new Error("Ha sido imposible crear la Base de Datos");
		}
		 
		try
		{		
			String myPath = DB_PATH + DB_NAME;
			database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);		 
		}
		catch(SQLiteException e)
		{		 			
			//si llegamos aqui es porque la base de datos no existe todavía.
			Log.i(DB_NAME, "Error abriendo la base de datos " + e);
		}				
	}
	@Override
	public synchronized void close() 
	{
		if(database != null)
				database.close();
		super.close();
	}
	 
	@Override public void onCreate(SQLiteDatabase arg0) 
	{
		// TODO Auto-generated method stub	
	}

	@Override public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
		// TODO Auto-generated method stub	
	}
   
}