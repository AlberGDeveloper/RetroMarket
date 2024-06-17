package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJuegos extends JFrame {
	private JTextField nombreField;
	private JTextField unidadesField;
	private JTextField precioField;
	private JTextField idField;
	private JTextField plataformaField;
	private JLabel imagenLabel;
	private byte[] imagenBytes;
	private JPanel panelComponentes;

	public InsertJuegos() {
		super("Insertar Videojuego");

		setSize(1124, 868); // Establecer el tamaño de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla

		// Crear un panel que contendrá los componentes
		panelComponentes = new JPanel();
		panelComponentes.setPreferredSize(new Dimension(800, 600));
		panelComponentes.setLayout(new GridBagLayout());
		panelComponentes.setOpaque(false);

		// Configuración para el fondo
		setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon(getClass().getResource("/images/fondomockupinsertsc.png"));
				if (img.getImage() != null) {
					g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
				} else {
					System.err.println("No se pudo cargar la imagen de fondo.");
				}
			}
		});

		getContentPane().add(panelComponentes);
		agregarComponentes(panelComponentes);
	}

	private void agregarComponentes(JPanel panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// Campos y etiquetas
		JLabel idLabel = new JLabel("Id:");
		idField = new JTextField(20);
		idField.setPreferredSize(new Dimension(400, 20));
		idField.setOpaque(false);

		GridBagConstraints gbcIdLabel = new GridBagConstraints();
		gbcIdLabel.gridx = 0;
		gbcIdLabel.gridy = 0;
		gbcIdLabel.insets = new Insets(5, 5, 5, 5);
		gbcIdLabel.anchor = GridBagConstraints.WEST;
		panel.add(idLabel, gbcIdLabel);

		GridBagConstraints gbcIdField = new GridBagConstraints();
		gbcIdField.gridx = 1;
		gbcIdField.gridy = 0;
		gbcIdField.insets = new Insets(5, 5, 5, 5);
		gbcIdField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(idField, gbcIdField);

		JLabel plataformaLabel = new JLabel("Plataforma:");
		plataformaField = new JTextField(20);
		plataformaField.setPreferredSize(new Dimension(400, 20));
		plataformaField.setOpaque(false);

		GridBagConstraints gbcPlataformaLabel = new GridBagConstraints();
		gbcPlataformaLabel.gridx = 0;
		gbcPlataformaLabel.gridy = 1;
		gbcPlataformaLabel.insets = new Insets(5, 5, 5, 5);
		gbcPlataformaLabel.anchor = GridBagConstraints.WEST;
		panel.add(plataformaLabel, gbcPlataformaLabel);

		GridBagConstraints gbcPlataformaField = new GridBagConstraints();
		gbcPlataformaField.gridx = 1;
		gbcPlataformaField.gridy = 1;
		gbcPlataformaField.insets = new Insets(5, 5, 5, 5);
		gbcPlataformaField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(plataformaField, gbcPlataformaField);

		JLabel precioLabel = new JLabel("Precio en €:");
		precioField = new JTextField(10);
		precioField.setPreferredSize(new Dimension(400, 20));
		precioField.setOpaque(false);

		GridBagConstraints gbcPrecioLabel = new GridBagConstraints();
		gbcPrecioLabel.gridx = 0;
		gbcPrecioLabel.gridy = 2;
		gbcPrecioLabel.insets = new Insets(5, 5, 5, 5);
		gbcPrecioLabel.anchor = GridBagConstraints.WEST;
		panel.add(precioLabel, gbcPrecioLabel);

		GridBagConstraints gbcPrecioField = new GridBagConstraints();
		gbcPrecioField.gridx = 1;
		gbcPrecioField.gridy = 2;
		gbcPrecioField.insets = new Insets(5, 5, 5, 5);
		gbcPrecioField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(precioField, gbcPrecioField);

		JLabel unidadesLabel = new JLabel("Stock:");
		unidadesField = new JTextField(10);
		unidadesField.setPreferredSize(new Dimension(400, 20));
		unidadesField.setOpaque(false);

		GridBagConstraints gbcUnidadesLabel = new GridBagConstraints();
		gbcUnidadesLabel.gridx = 0;
		gbcUnidadesLabel.gridy = 3;
		gbcUnidadesLabel.insets = new Insets(5, 5, 5, 5);
		gbcUnidadesLabel.anchor = GridBagConstraints.WEST;
		panel.add(unidadesLabel, gbcUnidadesLabel);

		GridBagConstraints gbcUnidadesField = new GridBagConstraints();
		gbcUnidadesField.gridx = 1;
		gbcUnidadesField.gridy = 3;
		gbcUnidadesField.insets = new Insets(5, 5, 5, 5);
		gbcUnidadesField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(unidadesField, gbcUnidadesField);

		JLabel nombreLabel = new JLabel("Título:");
		nombreField = new JTextField(20);
		nombreField.setPreferredSize(new Dimension(400, 20));
		nombreField.setOpaque(false);

		GridBagConstraints gbcNombreLabel = new GridBagConstraints();
		gbcNombreLabel.gridx = 0;
		gbcNombreLabel.gridy = 4;
		gbcNombreLabel.insets = new Insets(5, 5, 5, 5);
		gbcNombreLabel.anchor = GridBagConstraints.WEST;
		panel.add(nombreLabel, gbcNombreLabel);

		GridBagConstraints gbcNombreField = new GridBagConstraints();
		gbcNombreField.gridx = 1;
		gbcNombreField.gridy = 4;
		gbcNombreField.insets = new Insets(5, 5, 5, 5);
		gbcNombreField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(nombreField, gbcNombreField);

		// Botón para seleccionar imagen
		JButton seleccionarImagenButton = new JButton("Seleccionar Imagen");
		seleccionarImagenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarImagen();
			}
		});

		GridBagConstraints gbcSeleccionarImagenButton = new GridBagConstraints();
		gbcSeleccionarImagenButton.gridx = 0;
		gbcSeleccionarImagenButton.gridy = 5;
		gbcSeleccionarImagenButton.gridwidth = 2;
		gbcSeleccionarImagenButton.insets = new Insets(5, 5, 5, 5);
		gbcSeleccionarImagenButton.anchor = GridBagConstraints.CENTER;
		panel.add(seleccionarImagenButton, gbcSeleccionarImagenButton);

		// Label para mostrar el estado de la imagen seleccionada
		imagenLabel = new JLabel("No hay imagen seleccionada");
		imagenLabel.setVisible(false); // Ocultar la etiqueta inicialmente
		GridBagConstraints gbcImagenLabel = new GridBagConstraints();
		gbcImagenLabel.gridx = 0;
		gbcImagenLabel.gridy = 6;
		gbcImagenLabel.gridwidth = 2;
		gbcImagenLabel.insets = new Insets(5, 5, 5, 5);
		gbcImagenLabel.anchor = GridBagConstraints.CENTER;
		panel.add(imagenLabel, gbcImagenLabel);

		// Botón para añadir el videojuego
		JButton addButton = new JButton("Añadir videojuego");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Crear un nuevo hilo para añadir el videojuego
				new Thread(() -> aniadirVideojuego()).start();
			}
		});

		GridBagConstraints gbcAddButton = new GridBagConstraints();
		gbcAddButton.gridx = 0;
		gbcAddButton.gridy = 7;
		gbcAddButton.gridwidth = 2;
		gbcAddButton.insets = new Insets(5, 5, 5, 5);
		gbcAddButton.anchor = GridBagConstraints.CENTER;
		panel.add(addButton, gbcAddButton);

		// Botón para volver
		JButton backButton = new JButton("Volver a Stock");
		backButton.addActionListener(e -> volverAVentanaStock());

		GridBagConstraints gbcBackButton = new GridBagConstraints();
		gbcBackButton.gridx = 0;
		gbcBackButton.gridy = 8;
		gbcBackButton.gridwidth = 2;
		gbcBackButton.insets = new Insets(5, 5, 5, 5);
		gbcBackButton.anchor = GridBagConstraints.CENTER;
		panel.add(backButton, gbcBackButton);
	}

	private void seleccionarImagen() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				imagenBytes = Files.readAllBytes(selectedFile.toPath());
				ImageIcon icon = new ImageIcon(imagenBytes);
				Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				imagenLabel.setIcon(new ImageIcon(img));
				imagenLabel.setText(""); // Eliminar el texto de "No hay imagen seleccionada"
				imagenLabel.setVisible(true); // Mostrar la etiqueta
				actualizarLayoutConImagen();
			} catch (Exception ex) {
				ex.printStackTrace();
				imagenLabel.setText("Error al cargar la imagen");
				imagenLabel.setVisible(true); // Mostrar la etiqueta aunque haya un error
			}
		}
	}

	private void actualizarLayoutConImagen() {
		// Revalidar y repintar el panel para reflejar los cambios
		panelComponentes.revalidate();
		panelComponentes.repaint();
	}

	private void aniadirVideojuego() {
		String titulo = nombreField.getText();
		String id = idField.getText();
		String plataforma = plataformaField.getText();
		String unidades = unidadesField.getText();
		String precio = precioField.getText();

		if (titulo.isEmpty() || id.isEmpty() || plataforma.isEmpty() || unidades.isEmpty() || precio.isEmpty()
				|| imagenBytes == null) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(this, "Todos los campos y la imagen son requeridos.");
			});
			return;
		}

		String sql = "INSERT INTO videojuegos (ID_Videojuego, Plataforma, Precio, Stock, Titulo, Imagen) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda videojuegos g3",
				"root", ""); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, id);
			statement.setString(2, plataforma);
			statement.setString(3, precio);
			statement.setString(4, unidades);
			statement.setString(5, titulo);
			statement.setBytes(6, imagenBytes);

			int filasAfectadas = statement.executeUpdate();
			SwingUtilities.invokeLater(() -> {
				if (filasAfectadas > 0) {
					JOptionPane.showMessageDialog(this, "Se ha añadido el videojuego correctamente.");
				} else {
					JOptionPane.showMessageDialog(this, "No se pudo añadir el videojuego.");
				}
			});
		} catch (SQLException ex) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(this, "Error al añadir el videojuego: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			});
		}
	}

	private void volverAVentanaStock() {
		dispose();
		VentanaStock ventanaStock = new VentanaStock();
		ventanaStock.setVisible(true);
	}
}
