package proy;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class Panel_Alta_Libros extends JPanel implements ActionListener {

	int C = 0;
	Conexion c;
	Panel_alta_Libros_ventana palv;
	private JPanel contentPane;
	JPanel panel;
	private JTextField campo_titulo;
	private JLabel lblTtulo_1;
	private JLabel lblAutor;
	private JLabel lblSinopsis;
	private JLabel lblIdioma;
	private JLabel lblPasta;
	private JLabel lblCopias;
	private JTextField campo_isbn;
	private JTextField campo_titulotxt;
	private JTextField campo_autor;
	private JTextField campo_idioma;
	private JTextField campo_copiasNo;
	JTextArea txtrCamposinopsis;
	private JTable table;
	private JButton btnBuscar;
	private JTextField campo_buscar;
	private JLabel lblConsultaSiClave;
	private JButton btnRefresh;
	JButton btnConsultar;
	JButton btnRegistrar;
	JComboBox comboBox;
	JButton btnEliminar;
	private JTextField campo_elimina_alt;

	public Panel_Alta_Libros() throws Throwable {

//		setBounds(100, 0, 550 + 106, 350);
		setBounds(110,10, 521, 290); //415 - 

		setBackground(new Color(240, 255, 255));
//		setBounds(110, 10, 521, 290);
		setLayout(null);

		JLabel lblAltaDeLibros = new JLabel("Alta de libros");
		lblAltaDeLibros.setBounds(10, 8, 77, 14);
		add(lblAltaDeLibros);

		JLabel lblTtulo = new JLabel("Clave T\u00EDtulo:");
		lblTtulo.setBounds(10, 33, 97, 14);
		add(lblTtulo);

		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(10, 58, 97, 14);
		add(lblIsbn);

		lblTtulo_1 = new JLabel("T\u00EDtulo:");
		lblTtulo_1.setBounds(10, 83, 97, 14);
		add(lblTtulo_1);

		lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 108, 97, 14);
		add(lblAutor);

		lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 144, 97, 14);
		add(lblSinopsis);

		lblIdioma = new JLabel("Idioma:");
		lblIdioma.setBounds(189, 61, 46, 14);
		add(lblIdioma);

		lblPasta = new JLabel("Pasta:");
		lblPasta.setBounds(189, 86, 46, 14);
		add(lblPasta);

		lblCopias = new JLabel("Copias:");
		lblCopias.setBounds(189, 111, 46, 14);
		add(lblCopias);

		lblConsultaSiClave = new JLabel("Consulta si clave de t\u00EDtulo existe:");
		lblConsultaSiClave.setBounds(120, 13, 180, 14);
		add(lblConsultaSiClave);

		campo_titulo = new JTextField();
		campo_titulo.setBounds(82, 33, 56, 25);
		campo_titulo.setText(String.valueOf(Id() + 1));
		campo_titulo.setEnabled(false);
		add(campo_titulo);
		campo_titulo.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 169, 402, 110);
		add(scrollPane);

		txtrCamposinopsis = new JTextArea();
		txtrCamposinopsis.setBackground(new Color(245, 245, 220));
		txtrCamposinopsis.setText(
				"Consulta en el campo \"Consulta si clave de t\u00EDtulo existe\" para verificar si tu t\u00EDtulo ha sido registrado anteriormente y as\u00ED recuperar datos del mismo libro.");
		txtrCamposinopsis.setForeground(Color.GRAY);
		txtrCamposinopsis.addMouseListener(new MouseAdapter() // borra lo que hay en el campo sinopsis
		{
			public void mouseClicked(MouseEvent e) {
				if (C == 0)
					txtrCamposinopsis.setText("");
				C++;
			}
		});
		txtrCamposinopsis.setLineWrap(true);
		txtrCamposinopsis.setWrapStyleWord(true);
		scrollPane.setViewportView(txtrCamposinopsis);

		campo_isbn = new JTextField();
		campo_isbn.setBackground(new Color(245, 245, 220));
		campo_isbn.setBounds(82, 58, 86, 25);
		campo_isbn.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();

				// Verificar si la tecla pulsada no es un digito
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		add(campo_isbn);
		campo_isbn.setColumns(10);

		campo_titulotxt = new JTextField();
		campo_titulotxt.setBackground(new Color(245, 245, 220));
		campo_titulotxt.setBounds(82, 83, 86, 25);
		add(campo_titulotxt);
		campo_titulotxt.setColumns(10);

		campo_autor = new JTextField();
		campo_autor.setBackground(new Color(245, 245, 220));
		campo_autor.setText("");
		campo_autor.setBounds(82, 108, 86, 25);
		add(campo_autor);
		campo_autor.setColumns(10);

		campo_idioma = new JTextField();
		campo_idioma.setBackground(new Color(245, 245, 220));
		campo_idioma.setBounds(245, 58, 86, 25);
		add(campo_idioma);
		campo_idioma.setColumns(10);

		campo_copiasNo = new JTextField();
		campo_copiasNo.setBackground(new Color(245, 245, 220));
		campo_copiasNo.setText("");
		campo_copiasNo.setBounds(245, 108, 86, 25);
		campo_copiasNo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();

				// Verificar si la tecla pulsada no es un digito
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		add(campo_copiasNo);
		campo_copiasNo.setColumns(10);

		campo_buscar = new JTextField();
		campo_buscar.setBounds(310, 8, 86, 25);
		campo_buscar.setBackground(new Color(244, 227, 255));
		campo_buscar.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();

				// Verificar si la tecla pulsada no es un digito
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		add(campo_buscar);
		campo_buscar.setColumns(10);

		btnConsultar = new JButton("Consulta libros");
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(Color.DARK_GRAY);
		btnConsultar.setBounds(422, 208, 89, 29);
		btnConsultar.addActionListener(this);
		add(btnConsultar);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBackground(Color.DARK_GRAY);
		btnRegistrar.setBounds(422, 248, 89, 31);
		btnRegistrar.addActionListener(this);
		add(btnRegistrar);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(356, 44, 155, 89);
		scrollPane_1.setEnabled(false);
		add(scrollPane_1);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "No. copias", "ISBN" }));
		table.setEnabled(false);
		scrollPane_1.setViewportView(table);

//		JButton btnRegresar = new JButton("Regresar");
//		btnRegresar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		btnRegresar.setForeground(Color.WHITE);
//		btnRegresar.setBackground(Color.DARK_GRAY);
//		btnRegresar.setBounds(422, 250, 89, 29);
//		add(btnRegresar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setBackground(Color.DARK_GRAY);
		btnBuscar.setBounds(414, 7, 97, 25);
		btnBuscar.addActionListener(this);
		add(btnBuscar);

		btnRefresh = new JButton("Refresh para nuevo titulo");
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(Color.DARK_GRAY);
		btnRefresh.setBounds(140, 34, 191, 23);
		btnRefresh.setEnabled(false);
		btnRefresh.addActionListener(this);
		add(btnRefresh);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Delgada", "Gruesa" }));
		comboBox.setBounds(245, 80, 86, 28);
		comboBox.setSelectedIndex(0);
		add(comboBox);
		
		JPanel panelElimina = new JPanel();
		panelElimina.setBackground(SystemColor.activeCaption);
		panelElimina.setBounds(82, 136, 429, 29);
		add(panelElimina);
		panelElimina.setLayout(null);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(330, 3, 89, 23);
		btnEliminar.addActionListener(this);
		panelElimina.add(btnEliminar);
		
		campo_elimina_alt = new JTextField();
		campo_elimina_alt.setBounds(184, 3, 120, 23);
		campo_elimina_alt.addKeyListener(new KeyAdapter()
		{
			   public void keyTyped(KeyEvent e)
			   {
			      char caracter = e.getKeyChar();

			      // Verificar si la tecla pulsada no es un digito
			      if(((caracter < '0') ||
			         (caracter > '9')) &&
			         (caracter != '\b' /*corresponde a BACK_SPACE*/))
			      {
			         e.consume();  // ignorar el evento de teclado
			      }
			   }
			});
		panelElimina.add(campo_elimina_alt);
		campo_elimina_alt.setColumns(10);
		
		JLabel lblTitulo_emilimar = new JLabel("clave de t\u00EDtulo para eliminar:");
		lblTitulo_emilimar.setBounds(10, 7, 156, 14);
		panelElimina.add(lblTitulo_emilimar);
		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when a selection has been made
				System.out.println(comboBox.getSelectedItem());

			}
		});

		palv = new Panel_alta_Libros_ventana();
		palv.setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		ResultSet rs = null;
		
//		ResultSet rstit = null;

		if (b == btnBuscar) {
			C = 1;
			campo_copiasNo.setText("");
			Statement st = null;
			int titulo = 0, IDt, tit_n = 0;
			c = new Conexion();
			try {
				st = c.conexion().createStatement();
				titulo = Integer.parseInt(campo_buscar.getText());
			} catch (Throwable e1) {
				JOptionPane.showMessageDialog(null,
						"Llena el campo \"Consulta si ISBN del libro existe\" correctamente");
				return;
			}

			try {
				ResultSet rs1 = st.executeQuery("Select * from item"/* where title_no = "+titulo */);

				while (rs1.next()) {

					IDt = rs1.getInt(1);
					if (IDt == titulo) {
						campo_autor.setEnabled(false);
//						campo_buscar.setEnabled(false);
//						campo_copiasNo.setEnabled(false);
						campo_copiasNo.setBackground(new Color(244,227,255));
						comboBox.setEnabled(false);
						campo_idioma.setEnabled(false);
						campo_isbn.setEnabled(false);
//						campo_pasta.setEnabled(false);
						campo_titulo.setEnabled(false);
						campo_titulotxt.setEnabled(false);
						txtrCamposinopsis.setEnabled(false);

//						campo_titulo.setText(String.valueOf(titulo));
						campo_isbn.setText(String.valueOf(titulo));

						CallableStatement cal = c.conexion().prepareCall("{call rellena_campos(?)}");
//						CallableStatement cal1 = conexion().prepareCall("{call rellena_campo_isbn(?)}");		//en realidad rellena title_no

//						cal1.setInt   (1, titulo);
						cal.setInt(1, titulo);

						rs = cal.executeQuery();

						while (rs.next()) {

							int i = 0;
							String tit = rs.getString(1);
							String aut = rs.getString(2);
							String idio = rs.getString(3);
							String cov = rs.getString(4);
							campo_titulotxt.setText(tit);
							campo_autor.setText(aut);
							campo_idioma.setText(idio);
							txtrCamposinopsis.setText(rs.getString(5));
//					        campo_pasta.setText(cov);
							System.out.println("cov = " + cov);
							if (cov.equalsIgnoreCase("Delgada"))
								i = 0;
							else
								i = 1;

							comboBox.setSelectedIndex(i);

							btnRefresh.setEnabled(true);
							ResultSet rstit = st.executeQuery("select title_no from item where isbn = " + titulo + "");
							while (rstit.next()) {
								if (IDt == titulo) {
									tit_n = rstit.getInt(1);
									campo_titulo.setText(String.valueOf(tit_n));
									return;

								}

							}
						}
					}

				}

				JOptionPane.showMessageDialog(null, "NO se encontr� el art�culo con el correspondiente ISBN");

			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Throwable e1) {
				JOptionPane.showMessageDialog(null,
						"Llena el campo \"Consulta si clave de t�tulo existe\" correctamente");
				return;
			}
		}
		if (b == btnConsultar) {

			palv.setVisible(true);
		}
		if (b == btnRefresh) {
			try {
				campo_titulo.setText(String.valueOf(Id() + 1));
				btnRefresh.setEnabled(false);
				campo_autor.setEnabled(true);
//				campo_buscar.setEnabled(false);
//				campo_copiasNo.setEnabled(false);
				campo_idioma.setEnabled(true);
				campo_isbn.setEnabled(true);
//				campo_pasta.setEnabled(true);
				campo_copiasNo.setBackground(new Color(245, 245, 220));
				comboBox.setEnabled(true);
				comboBox.getSelectedItem();
				campo_titulo.setEnabled(false);
				campo_titulotxt.setEnabled(true);
				txtrCamposinopsis.setEnabled(true);
				campo_autor.setText("");
//				campo_buscar.setEnabled(false);
//				campo_copiasNo.setEnabled(false);
				campo_idioma.setText("");
				campo_isbn.setText("");
//				campo_pasta.setText("");
				campo_titulotxt.setText("");
				campo_copiasNo.setText("");
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		if (b == btnRegistrar) {
			c = new Conexion();
			if (C == 0)
				txtrCamposinopsis.setText("");
			try {
				C = 0;
				CallableStatement cal = c.conexion().prepareCall("{call alta_libros(?,?,?,?,?,?,?,?)}");
//				try {
//					date = sdf.parse(expiraci�n_Adulto.getText());
//					} catch (Exception e2) {}
				cal.setInt(1, Integer.parseInt(campo_titulo.getText()));
				cal.setInt(2, Integer.parseInt(campo_isbn.getText()));
				cal.setString(3, campo_titulotxt.getText());
				cal.setString(4, campo_autor.getText());
				cal.setString(5, txtrCamposinopsis.getText());
				cal.setString(6, campo_idioma.getText());
//	            cal.setString(7, campo_pasta.getText());
				cal.setString(7, String.valueOf(comboBox.getSelectedItem()));
				cal.setInt(8, Integer.parseInt(campo_copiasNo.getText()));
//	            cal.setDate(9, d);
				cal.execute();
				int isbn = Integer.parseInt(campo_isbn.getText());
				int copis = Integer.parseInt(campo_copiasNo.getText());
				campo_autor.setText("");
//				campo_buscar.setEnabled(false);
//				campo_copiasNo.setEnabled(false);
				campo_idioma.setText("");
				campo_isbn.setText("");
//				campo_pasta.setText("");
				campo_titulotxt.setText("");
				campo_copiasNo.setText("");
				txtrCamposinopsis.setText(
						"Consulta en el campo \"Consulta si clave de t\u00EDtulo existe\" para verificar si tu t\u00EDtulo ha sido registrado anteriormente y as\u00ED recuperar datos del mismo libro.");
				Object datos1[] = { copis, isbn };
//				        Object datos1[] = {nom, ape, ocupa, sueldo};
				((DefaultTableModel) table.getModel()).addRow(datos1);
			} catch (SQLServerException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				return;
			} catch (Throwable e1) {
				JOptionPane.showMessageDialog(null, "Llena campos correctamente");
				return;
//	            e1.printStackTrace();
			}

			campo_titulo.setText(String.valueOf(Integer.parseInt(campo_titulo.getText()) + 1));
			JOptionPane.showMessageDialog(null, "Registro(s) correctamente guardado(s)");

		}
		if(b == btnEliminar) {
			c = new Conexion();
			try {
				CallableStatement cal = c.conexion().prepareCall("{call baja_libro(?)}");
				cal.setInt   (1, Integer.parseInt(campo_elimina_alt.getText()));
				
				cal.execute();
				
			} catch (SQLException sql) {
				
				JOptionPane.showMessageDialog(null, sql.getMessage());
				return;
			
			} catch (Throwable e2) {
				
				JOptionPane.showMessageDialog(null, "Llena campo correctamente");
				return;
			}
			JOptionPane.showMessageDialog(null, "T�tulo eliminado correctamente");
		}
	}
//public Connection conexion() throws Throwable {
//	
//	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//	 String connectionURL =   "jdbc:sqlserver://GABRIEL-PC\\SQLEXPRESS:1433;databaseName=LIBRARYY;user=Libreria;password=123;";
//	 Connection con = DriverManager.getConnection(connectionURL);
//	return con;
//
//	
//}
	private int Id() throws Throwable {
		c = new Conexion();
//		c = new Connect();
		int ID = 0;
		Statement st = c.conexion().createStatement();
		ResultSet rs = st.executeQuery("Select * from claves_tit ");

		while (rs.next())
			ID = rs.getInt(1);

		return ID;
	}
}
