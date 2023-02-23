package recursos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaces.FiltroFolder;

public class TextEngine 
{
	//---------------------------CAMPOS DE CLASE---------------------------

	//Campos para escritura de archivos
	private File f;
	private FileWriter w;
	private BufferedWriter bw;
	private PrintWriter wr;
	
	//Objeto para vertificar el folder
	private FiltroFolder F = new Archivero();
	
	//Campos para guardar la direccion del archivo txt
	private String fileName;
	private String route ;
	private String folderRoute;

	//Campo para manejar URL
	private URL url;

	
	//---------------------------CONSTRUCTORES---------------------------
	public TextEngine(String route, String fileName) 
	{
		this.fileName = fileName;
		this.route = route + fileName;
		this.folderRoute = route;
			
		System.out.println("Ruta -> " + this.route);
		System.out.println("Folder -> " + this.folderRoute);
	}
	
	
	//URL
	public TextEngine(URL url) 
	{
		this.url = url;			
	}
	

	
	
	//---------------------------METODOS---------------------------
	
	//Verificar si el folder de la direccion esta vacio
	public boolean verifyFolder() 
	{
		if(F.isFolderEmpty(folderRoute)) {
			System.out.println("Folder is empty");
			return true;
		}else {
			System.out.println("Folder is not empty");
			return false;
		}
	}
	
	
	//Crear el archivo con la direccion proporcionada
	public void createFile() 
	{

		//Se escribe secuencia de numeros en el archivo de texto
		try {
					
		f = new File(route);
		w = new FileWriter(f);
		bw = new BufferedWriter(w);
		wr = new PrintWriter(bw);
				
		wr.close();
		bw.close();
					
					
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ha sucedido un error: " + e);
		}
		
	}
	
	
	//Escribe en el archivo de txt
	public void writeData(Object item) 
	{

		try {
			f = new File(route);
			w = new FileWriter(f, true);
			bw = new BufferedWriter(w);
			wr = new PrintWriter(bw);
				
			wr.write(item.toString());
				
			wr.close();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Lee la informacion del archivo de txt y la regresa en un ArrayList
	public ArrayList<String> readData() 
	{
		Path ruta = Paths.get(route);		
		ArrayList<String> lectura = new ArrayList<String>();

		// Validar ruta
		if (Files.exists(ruta)) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(route), "utf-8"));
				String linea = null;
				int i = 1;

				//Lee cada linea
				while ((linea = br.readLine()) != null) {

					lectura.add(linea);
					System.out.println(linea);
					System.out.println("");
					
				}				
				i++;
				
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Error! el archivo o ruta no existen!");		
			
		}
		return lectura;		
	}
	
	
	//URL -> Lee la informacion del archivo de txt y la regresa en un ArrayList desde una direccion URL
	public ArrayList<String> readDatabyURL() 
	{
		
		int cont = 0;
		ArrayList<String> lectura = new ArrayList<String>();

		// Validar ruta
		if (url != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
				String linea = null;
				int i = 1;
				//Lee cada linea
				while ((linea = br.readLine()) != null) {
					lectura.add(linea);
					System.out.println(linea);
					System.out.println("");
					
				}
				i++;

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Error! el archivo o ruta no existen!\nRuta -> " + url);		
			
		}
		return lectura;		
	}
	
	
	//Sobreescribe lo que esta escrito en el txt
	public void writeNewData(Object item) 
	{

		try {
			f = new File(route);
			w = new FileWriter(f);
			bw = new BufferedWriter(w);
			wr = new PrintWriter(bw);

			wr.write(item.toString());
						
			wr.close();
			bw.close();
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





