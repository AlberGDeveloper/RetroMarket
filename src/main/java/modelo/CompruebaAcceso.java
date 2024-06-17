package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.Controlador;
import vista.LoginView;

public class CompruebaAcceso implements Runnable {
	private LoginView loginView;
	private boolean accesoPermitido;

	public CompruebaAcceso(LoginView loginView) {
		this.loginView = loginView;
	}

	@Override
	public void run() {
		Connection connection = Conexion.getConnection();
		Controlador controlador = new Controlador();
		String username = loginView.getUsernameField();
		char[] password = loginView.getPasswordFieldValue();

		accesoPermitido = verificarCredenciales(connection, username, password);

		if (accesoPermitido) {
			System.out.println("Credenciales correctas. Accediendo a la siguiente vista...Desde hilo CompruebaAcceso");
			controlador.mostrarVistaprincipal();
			loginView.setVisible(false);
		} else {
			System.out.println("Credenciales incorrectas. No se puede acceder a la siguiente vista. Thread WORKS!");
		}
	}

	public boolean isAccesoPermitido() {
		return accesoPermitido;
	}

	private boolean verificarCredenciales(Connection connection, String username, char[] password) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			String sql = "SELECT * FROM propietario WHERE Nombre = ? AND Passwd = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, new String(password));

			resultSet = statement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static void validarCredenciales(LoginView loginView) {
		CompruebaAcceso accesoRunnable = new CompruebaAcceso(loginView);
		Thread accesoThread = new Thread(accesoRunnable);
		accesoThread.start();
		try {
			accesoThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
