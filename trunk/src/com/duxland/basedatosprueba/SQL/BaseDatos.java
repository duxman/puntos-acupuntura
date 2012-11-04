package com.duxland.basedatosprueba.SQL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.R.bool;
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
	private String getMD5Checksum(String filename) throws Exception 
	{
	       byte[] b = createChecksum(filename);
	       String result = "";

	       for (int i=0; i < b.length; i++) {
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       }
	       return result;
	}
	private String getMD5Checksum(InputStream fis) throws Exception 
	{
	       byte[] b = createChecksum(fis);
	       String result = "";

	       for (int i=0; i < b.length; i++) {
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       }
	       return result;
	}
	private byte[] createChecksum(String filename) throws Exception {
	       InputStream fis =  new FileInputStream(filename);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }
	private byte[] createChecksum(InputStream fis) throws Exception 
	{	       
	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

	private Boolean CompararMD5()
	{
		Boolean rtn=false;
		
		
		return rtn;
		
	}
	/**
	* Crea una base de datos vac�a en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException
	{	 
		boolean dbExist = checkDataBase();				
		if(!dbExist)		
		{
			//Llamando a este m�todo se crea la base de datos vac�a en la ruta por defecto del sistema
			//de nuestra aplicaci�n por lo que podremos sobreescribirla con nuestra base de datos.
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
	* Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicaci�n.
	* @return true si existe, false si no existe
	*/
	private boolean checkDataBase()
	{
		SQLiteDatabase checkDB = null;
		boolean rtn=false;
		String MD5_2="";
		String MD5_1="";
	 
		try
		{		
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
			InputStream myInput = context.getAssets().open(DB_NAME);
			MD5_1=getMD5Checksum(myInput);
			MD5_2=getMD5Checksum(myPath);
						
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			Log.i(DB_NAME, "La base de datos no existe todav�a " + e);
		}
		if(checkDB != null)
		{		 
			checkDB.close();
			if(MD5_1==MD5_2)
				rtn=true;
			else
				rtn=false;				
		}
		return rtn;
	}
	/**
	* Copia nuestra base de datos desde la carpeta assets a la reci�n creada
	* base de datos en la carpeta de sistema, desde d�nde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException
	{
	 
		try 
		{				
			//Abrimos el fichero de base de datos como entrada
			InputStream myInput = context.getAssets().open(DB_NAME);	 			
			//Ruta a la base de datos vac�a reci�n creada
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
			
			
			
			//Abrimos la base de datos vac�a como salida
			
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
			//si llegamos aqui es porque la base de datos no existe todav�a.
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