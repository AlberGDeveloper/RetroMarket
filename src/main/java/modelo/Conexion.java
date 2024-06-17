package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import vista.LoginView;

public class Conexion implements Runnable{
	private String username;
	private char[] password;
	private static Connection connection;

	public Conexion(String text, char[] password) {
		this.username = text;
		this.password = password;
	}

	 @Override
	    public void run() {
	        try {
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda videojuegos g3", "root", "");
	            System.out.println("Conexion realizada desde el hilo Conexion");
	            Properties pr = connection.getClientInfo();
	            System.out.println(pr);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static Connection getConnection() {
	        return connection;
	    }

	    public static void conectarABaseDeDatos() {
	        Conexion conexionRunnable = new Conexion("root", "".toCharArray());
	        Thread conexionThread = new Thread(conexionRunnable);
	        conexionThread.start();
	        try {
	            conexionThread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}