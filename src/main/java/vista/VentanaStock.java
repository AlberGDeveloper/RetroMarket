package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;
import javax.imageio.ImageIO;

import modelo.CompruebaAcceso;
import modelo.Conexion;
import modelo.ExportClientes;
import modelo.ExportPedidos;
import modelo.Exportxls;
import controlador.Controlador;

public class VentanaStock extends JFrame {
	private BufferedImage backgroundImage;
	private DefaultTableModel tableModel;
	private JTable table;
	private JPanel tablePanel;
	private Controlador controlador;
	private Exportxls modeloExport;
	private ExportClientes modeloExport2;
	private ExportPedidos modeloExport3;

	public VentanaStock() {
		InsertJuegos insertjuegos = null;
		LoginView loginview = null;
		CompruebaAcceso compruebaacceso = null;
		Conexion conexion = null;
		VentanaprincipalApp ventanaPrincipal = null;
		VentanaClientes ventanaclientes = null;
		VentanaPedido ventanaPedido = null;
		//modeloExport = new Exportxls();
		controlador = new Controlador(insertjuegos, loginview, compruebaacceso, conexion, ventanaPrincipal, this,
				ventanaclientes, ventanaPedido, modeloExport, modeloExport2, modeloExport3);

		// Configuración de la ventana y otros componentes
		setTitle("Stock de Videojuegos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1124, 868); // Establecer el tamaño de la ventana
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla

		// Cargar la imagen de fondo desde el classpath
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/images/fondomockup2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Configurar el fondo del JFrame
		setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (backgroundImage != null) {
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				}
			}
		});

		// Inicializar tabla
		tableModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 5) { // Suponiendo que la columna de la imagen es la sexta (índice 5)
					return Object.class;
				}
				return Object.class;
			}
		};
		table = new JTable(tableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

				// Ajustar la altura de las filas
				if (getValueAt(row, 5) != null && getValueAt(row, 5) instanceof ImageIcon) {
					setRowHeight(row, 300); // Ajusta este valor según sea necesario
				} else {
					setRowHeight(row, 20); // Altura estándar para filas sin imagen
				}
				return c;
			}
		};
		table.setBackground(new Color(241, 234, 209));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 350));

		// Inicializar el JPanel que contendrá la tabla
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBackground(new Color(241, 234, 209));

		// Calcular el tamaño preferido del panel
		int panelWidth = (int) (1000 * 0.8);
		int panelHeight = scrollPane.getPreferredSize().height;

		// Establecer el tamaño preferido del panel
		tablePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		// Agregar botones
		JButton backButton = new JButton("Volver");
		backButton.setVerticalAlignment(SwingConstants.BOTTOM);
		backButton.setBackground(new Color(241, 234, 209));
		ImageIcon backIcon = new ImageIcon(getClass().getResource("/images/iconback.png"));
		backButton.setIcon(backIcon);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaprincipalApp ventanaPrincipal = new VentanaprincipalApp();
				ventanaPrincipal.setVisible(true);
			}
		});

		// Botón para insertar
		JButton insertButton = new JButton("Insertar");
		insertButton.setBackground(new Color(241, 234, 209));
		ImageIcon backIcon2 = new ImageIcon(getClass().getResource("/images/inserticon.png"));
		insertButton.setIcon(backIcon2);
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				InsertJuegos insertJuegos = new InsertJuegos();
				insertJuegos.setVisible(true);
			}
		});

		// Panel de botones
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(128, 0, 0));
		buttonPanel.setBorder(null);
		FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		flowLayout.setVgap(10);
		flowLayout.setHgap(150);
		buttonPanel.add(backButton);
		buttonPanel.add(insertButton);

		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(tablePanel, BorderLayout.CENTER);

		// Crear un JPanel para el botón "Exportar"
		JPanel exportPanel = new JPanel(new GridBagLayout());
		exportPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10); // Reducir el margen inferior para subir el botón
		gbc.anchor = GridBagConstraints.SOUTHEAST;

		// Crear un botón "Exportar"
		JButton exportButton = new JButton();
		ImageIcon exporticon = new ImageIcon(getClass().getResource("/images/exporticon.png"));
		exportButton.setBorderPainted(false);
		exportButton.setContentAreaFilled(false);
		exportButton.setFocusPainted(false);
		exportButton.setIcon(exporticon);
		exportButton.setPreferredSize(new Dimension(exporticon.getIconWidth(), exporticon.getIconHeight()));
		exportPanel.add(exportButton, gbc);
		add(exportPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);

		exportButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        TableModel tableModel = obtenerTableModel();
		        Exportxls exportJuegos = new Exportxls(tableModel);
		        Thread exportThread = new Thread(exportJuegos);
		        exportThread.start();
		    }
		});


		// Conexión a la base de datos y carga de datos en un hilo separado
		new Thread(() -> loadStockData()).start();
	}

	public TableModel obtenerTableModel() {
		return table.getModel();
	}

	// Método para cargar los datos desde la base de datos
	private void loadStockData() {
		String url = "jdbc:mysql://localhost:3306/tienda videojuegos g3"; // Nombre de la base de datos sin guiones															
		String user = "root";
		String password = "";

		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM videojuegos");

			// Obtener datos de la base de datos y actualizar la tabla
			tableModel.setRowCount(0);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<>();

			// Obtener nombres de columnas
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			tableModel.setColumnIdentifiers(columnNames);

			// Obtener datos de filas
			while (resultSet.next()) {
				Vector<Object> row = new Vector<>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					if (columnIndex == 6) { // Suponiendo que la columna de la imagen es la sexta (índice 5)
						byte[] imageBytes = resultSet.getBytes(columnIndex);
						if (imageBytes != null) {
							ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
							BufferedImage bufferedImage = ImageIO.read(bais);
							ImageIcon icon = new ImageIcon(bufferedImage);
							row.add(icon);
						} else {
							row.add("IMAGEN NO DISPONIBLE"); // Texto si no hay imagen
						}
					} else {
						row.add(resultSet.getObject(columnIndex));
					}
				}
				tableModel.addRow(row);
			}

			// Ajustar el tamaño de las columnas después de cargar los datos
			SwingUtilities.invokeLater(() -> {
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					if (i == 5) { // Índice de la columna de imagen
						column.setPreferredWidth(300); // Ajusta este valor según sea necesario
					} else {
						column.setPreferredWidth(100);
					}
				}
			});

			// Establecer el renderizador de celdas para la columna de la imagen
			if (table.getColumnCount() > 5) {
				table.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());
			}

			// Cerrar los recursos al final
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	// Renderizador personalizado para manejar tanto imágenes como texto
	private static class ImageRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value instanceof ImageIcon) {
				JLabel label = new JLabel((ImageIcon) value);
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			} else {
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		}
	}
}

