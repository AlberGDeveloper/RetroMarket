package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;

public class VentanaprincipalApp extends JFrame {
	Controlador controlador = new Controlador();
	private static VentanaprincipalApp ventanaActual;
	private JPanel contentPane;

	public VentanaprincipalApp() {
		ventanaActual = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 800, 600);
		contentPane = new BackgroundPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 50, 10, 10);
		setSize(1124, 868);

		// Botón Stock con icono
		JButton btnStock = new JButton();
		ImageIcon stockIcon = new ImageIcon(getClass().getResource("/images/icono_stock.png"));
		btnStock.setIcon(stockIcon);
		btnStock.setBorderPainted(false); // Elimina el borde para que parezca un icono
		btnStock.setContentAreaFilled(false); // Elimina el relleno
		btnStock.setFocusPainted(false); // Elimina el foco al hacer clic
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 0; // Fila 0
		gbc.insets = new Insets(120, 10, 10, 5);
		contentPane.add(btnStock, gbc);
		btnStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ocultar la ventana principal actual
				setVisible(false);
				// Mostrar la ventana de stock
				controlador.mostrarVentanaStock();
			}
		});

		// Botón Clientes con icono
		JButton btnClientes = new JButton();
		ImageIcon clientesIcon = new ImageIcon(getClass().getResource("/images/icono_clientes.png"));
		btnClientes.setIcon(clientesIcon);
		btnClientes.setBorderPainted(false); // Elimina el borde para que parezca un icono
		btnClientes.setContentAreaFilled(false); // Elimina el relleno del botón
		btnClientes.setFocusPainted(false); // Elimina el "focus" al hacer clic
		gbc.gridx = 1; // Columna 1
		gbc.gridy = 0; // Fila 0
		gbc.insets = new Insets(120, 10, 10, 5);
		contentPane.add(btnClientes, gbc);
		btnClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ocultar la ventana principal actual
				setVisible(false);
				// Mostrar la ventana de clientes
				controlador.mostrarVentanaClientes();
			}
		});

		// Botón Pedido con icono
		JButton btnPedido = new JButton();
		ImageIcon pedidoIcon = new ImageIcon(getClass().getResource("/images/icono_pedidos.png"));
		btnPedido.setIcon(pedidoIcon);
		btnPedido.setBorderPainted(false); // Elimina el borde para que parezca un icono
		btnPedido.setContentAreaFilled(false); // Elimina el relleno del botón
		btnPedido.setFocusPainted(false); // Elimina el "focus" al hacer clic
		gbc.gridx = 2; // Columna 1
		gbc.gridy = 0; // Fila 0
		gbc.insets = new Insets(120, 10, 10, 5);
		contentPane.add(btnPedido, gbc);
		btnPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				controlador.mostrarVentanaPedidos();
			}
		});
	}

	class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel() {
			try {
				backgroundImage = new ImageIcon(getClass().getResource("/images/fondomockup.png")).getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
			} else {
				System.err.println("No se pudo cargar la imagen de fondo.");
			}
		}
	}

	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
	 * VentanaprincipalApp frame = new VentanaprincipalApp();
	 * frame.setVisible(true); }); }
	 */
}
