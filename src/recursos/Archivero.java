package recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import interfaces.FiltroFolder;

public class Archivero implements FiltroFolder
{
	// C:\Users\delux\OneDrive - Universidad Autonoma de Nuevo León\Hans\Hans Facu\Semestre 6\Optimization
		// Facu\Semestre 6\Optimization
		// CAMPOS DE CLASE
		int cont = 0;
		int contadorGlobal = 0;

		public void verArchivos() {
			File path = new File(IntroducirDireccion());
			//
			System.out.println("Current Path: " + path.getAbsolutePath() + "\n\n");

			if (path.isDirectory()) {
				// Se obtiene el nombre de los archivos que se encuentran en esa carpeta
				String[] nombres_archivos = path.list();

				if (nombres_archivos.length == 0) {
					System.out.println(getEspaciado(cont) + "Empty Folder");
				} else {
					try {
						// Se recorre cada nombre de la lista
						for (String name : nombres_archivos) {

							File f = new File(path.getAbsoluteFile(), name); // Constructor con (Parent, Child)

							// Antes de imprimir se verifica si el archivo es una carpeta
							if (f.isDirectory()) {
								// En caso que si sea carpeta, se inicia un subproceso para ver e imprimir el
								// contenido de esta carpeta
								subproceso(f, name);

							} else {
								// Se imprime el archivo
								System.out.println("* " + name + " (" + getMBSize(f) + "MB/" + getKBSize(f) + "KB)");
							}

						}
					} catch (NullPointerException e) {
						// TODO: handle exception
						System.out.println("DIRECCION INVALIDA");
					}
				}
			} else {
				System.out.println("La direccion que agrego no apunta a ninguna carpeta");
			}

		}
		
		public boolean isFolderEmpty() 
		{
			File path = new File(IntroducirDireccion());
			//
			System.out.println("Current Path: " + path.getAbsolutePath() + "\n\n");

			if (path.isDirectory()) {
				// Se obtiene el nombre de los archivos que se encuentran en esa carpeta
				String[] nombres_archivos = path.list();

				if (nombres_archivos.length == 0) {
					System.out.println(getEspaciado(cont) + "Empty Folder");
					return true;
				} else {
					return false;
				}
			}else {
				System.out.println("No es un folder");
				return true;
			}
		}
		
		//Metodo para verificar si un folder esta vacio
		public boolean isFolderEmpty(String folderRoute) 
		{
			File path = new File(folderRoute);
			//
			//System.out.println("Current Path: " + path.getAbsolutePath() + "\n\n");

			if (path.isDirectory()) {
				// Se obtiene el nombre de los archivos que se encuentran en esa carpeta
				String[] nombres_archivos = path.list();

				if (nombres_archivos.length == 0) {
					//System.out.println(getEspaciado(cont) + "Empty Folder");
					return true;
				} else {
					return false;
				}
			}else {
				System.out.println("No es un folder");
				return true;
			}
		}
		
		//Files/toyStore

		private void subproceso(File f, String name) {

			System.out.println("\n" + getEspaciado(cont) + "    [" + name + "]:");
			String[] archivos_subcarpeta = f.list();
			cont++;

			if (archivos_subcarpeta.length == 0) {
				System.out.println(getEspaciado(cont) + "Empty Folder");
			} else {
				try {

					for (String subName : archivos_subcarpeta) {
						File subF = new File(f.getAbsoluteFile(), subName);

						if (subF.isDirectory()) {
							subproceso(subF, subName);
						} else {
							System.out.println(getEspaciado(cont) + "|  *" + subName + " (" + getMBSize(subF) + "MB/"
									+ getKBSize(subF) + "KB)");
						}
					}
					cont--;

					System.out.println("");
				} catch (Exception e) {
					System.out.println(getEspaciado(cont) + "Empty Folder");
				}
			}

		}

		// Metodo con Buscador
		private void subproceso(File f, String name, String palabra) {

			// System.out.println("\n" + getEspaciado(cont) + " [" + name + "]:");
			String[] archivos_subcarpeta = f.list();
			cont++;

			if (archivos_subcarpeta.length == 0) {
				System.out.println(getEspaciado(cont) + "Empty Folder");
			} else {
				try {

					for (String subName : archivos_subcarpeta) {
						File subF = new File(f.getAbsoluteFile(), subName);

						if (subF.isDirectory()) {
							subproceso(subF, subName, palabra);
						} else {

							// System.out.println(getEspaciado(cont) + "| *"+subName + " (" +
							// getMBSize(subF) + "MB/" + getKBSize(subF) + "KB)");
							buscador(palabra, subF.getAbsolutePath().toString(), subName);
						}
					}
					cont--;

					System.out.println("");
				} catch (Exception e) {
					System.out.println(getEspaciado(cont) + "Empty Folder");
				}
			}

		}

		public String IntroducirDireccion() {
			Scanner entrada = new Scanner(System.in);
			System.out.print("Introduce la dirección de la carpeta: ");
			String direccion = entrada.nextLine();
			// "C:/Users/delux/OneDrive - Universidad Autonoma de Nuevo León/Hans/Gibran"

			return direccion;

		}

		private String getEspaciado(int cont) {
			String espacio = "";

			for (int i = 0; i < cont; i++) {
				espacio += "\t";
			}

			return espacio;
		}

		public long getMBSize(File f) {
			// Get length of file in bytes
			long fileSizeInBytes = f.length();
			// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
			long fileSizeInKB = fileSizeInBytes / 1024;
			// Convert the KB to MegaBytes (1 MB = 102
			long fileSizeInMB = fileSizeInBytes / (1024 * 1024);

			return fileSizeInMB;
		}

		public long getKBSize(File f) {
			// Get length of file in bytes
			long fileSizeInBytes = f.length();
			// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
			long fileSizeInKB = fileSizeInBytes / 1024;

			return fileSizeInKB;
		}

		public void buscador(String palabra, String Direccion, String nombreArchivo) {
			ArrayList<Integer> lista = new ArrayList<Integer>();
			Path ruta = Paths.get(Direccion);
			int cont = 0;

			// Validar ruta
			if (Files.exists(ruta)) {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Direccion), "utf-8"));
					String linea = null;
					int i = 1;
					while ((linea = br.readLine()) != null) {

						String[] lineaActual = linea.split("");

						for (String word : lineaActual) {
							if (word.equals(palabra)) {
								// System.out.println("[" + i + "]: " + linea);
								cont++;
								contadorGlobal++;
								lista.add(i);

							}
						}
						i++;

					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Error! el archivo o ruta no existen!");
			}
			if (cont != 0) {
				System.out.println(" *Archivo: " + nombreArchivo);
				System.out.println("   La palabra [" + palabra + "] se encontro -> " + cont + " veces");
				System.out.print("   Lineas -> ");
				for (Integer linea : lista) {
					System.out.print("[" + linea + "] ");
				}
				// System.out.println("\n Direccion del archivo -> " + Direccion);
				System.out.println("\n\n");

			}

		}

		public void buscarEnArchivos(String palabra) {
			File path = new File(IntroducirDireccion());
			//
			System.out.println("Current Path: " + path.getAbsolutePath() + "\n\n");

			if (path.isDirectory()) {
				// Se obtiene el nombre de los archivos que se encuentran en esa carpeta
				String[] nombres_archivos = path.list();

				if (nombres_archivos.length == 0) {
					System.out.println(getEspaciado(cont) + "Empty Folder");
				} else {
					try {
						// Se recorre cada nombre de la lista
						for (String name : nombres_archivos) {

							File f = new File(path.getAbsoluteFile(), name); // Constructor con (Parent, Child)

							// Antes de imprimir se verifica si el archivo es una carpeta
							if (f.isDirectory()) {
								// En caso que si sea carpeta, se inicia un subproceso para ver e imprimir el
								// contenido de esta carpeta
								subproceso(f, name, palabra);

							} else {
								// Se imprime el archivo
								// System.out.println("* " + name + " (" + getMBSize(f) + "MB/" + getKBSize(f) +
								// "KB)");
								buscador(palabra, f.getAbsolutePath().toString(), name);
							}

						}
					} catch (NullPointerException e) {
						// TODO: handle exception
						System.out.println("DIRECCION INVALIDA");
					}

					if (contadorGlobal == 0) {
						System.out.println("NO SE ENCONTRO LA PALABRA EN NINGUN ARCHIVO");
					} else {
						System.out.println("LA PALABRA [" + palabra + "] APARECIO UN TOTAL DE " + contadorGlobal
								+ " VECES EN LA CARPETA");
					}
				}
			} else {
				System.out.println("La direccion que agrego no apunta a ninguna carpeta");
			}
		}

		public void buscador(String palabra) {
			ArrayList<String> lista = new ArrayList<String>();
			String p = IntroducirDireccion();
			Path ruta = Paths.get(p);
			int cont = 0;

			// Validar ruta
			if (Files.exists(ruta)) {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(p), "utf-8"));
					String linea = null;
					int i = 1;
					while ((linea = br.readLine()) != null) {

						String[] lineaActual = linea.split(" ");

						for (String word : lineaActual) {
							if (word.equals(palabra)) {
								// System.out.println("[" + i + "]: " + linea);
								cont++;
								lista.add("[" + i + "]: " + linea);

							}
						}
						i++;

					}
					br.close();

					if (cont != 0) {
						System.out.println("\n\n  La palabra [" + palabra + "] se encontro -> " + cont + " veces");
						System.out.println("  Lineas : ");
						for (String line : lista) {
							System.out.println("  *" + line);
						}
						// System.out.println("\n Direccion del archivo -> " + Direccion);
						System.out.println("\n\n");

					} else {
						System.out.println("LA PALABRA NO SE ENCUENTRA EN EL ARCHIVO");
					}

				} catch (IOException e) {
					System.out.println("LA DIRECCION QUE INGRESO NO APUNTA A NINGUN ARCHIVO .TXT");
				}

			} else {
				System.out.println("Error! el archivo o ruta no existen!");
			}

		}

}
