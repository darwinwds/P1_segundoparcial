/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

/**
 *
 * @author Darwin W. Diaz Simon
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import Participantes.Casilla;
import Participantes.Tablero;
import Utiles.ManejadorFicheros;
import Utiles.Literales;
import Utiles.Solucionador;

public class Gui extends JFrame {

	/// PROPIEDADES
	private JPanel contenedor;
	private static final long serialVersionUID = 1L;
	
	private JFormattedTextField[][] casillas = new JFormattedTextField[9][9];
	private MaskFormatter mascara;
	
	private JSeparator separadorHorizontal1;
	private JSeparator separadorHorizontal2;
	private JSeparator separadorVertical1;
	private JSeparator separadorVertical2;
	
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	
	private JMenuItem mnItemNuevo;
	private JMenuItem mnItemCargarCSV;
	private JMenuItem mnItemExportarCSV;
	private JMenuItem mnItemFuncionamiento;
	private JMenuItem mnItemInfo;
	
	private JButton btnSolucionar;
	private JButton btnLimpiar;
	
	private JFileChooser selectorDeFichero;
	
	private JLabel lblLogo;
	
	private Tablero tablero;
	private Solucionador solucionador;
	private ManejadorFicheros manejadorDeFicheros;
	
	private ActionListener actionNuevo;
	private ActionListener actionCargarCSV;
	private ActionListener actionExportarCSV;
	private ActionListener actionFuncionamiento;
	private ActionListener actionInfo;
	private ActionListener actionSolucionar;
	
	private FileNameExtensionFilter filtroExtension;
	
	/// CONSTRUCTOR
	public Gui() {
		
		this.tablero = new Tablero();
		tablero.inicializarTablero();
		
		this.selectorDeFichero = new JFileChooser();
		this.filtroExtension = new FileNameExtensionFilter(Literales.CSV_EXTENSION_TEXTO, Literales.CSV_EXTENSION);
		this.selectorDeFichero.setFileFilter(this.filtroExtension);
		
		this.solucionador = new Solucionador();
		this.manejadorDeFicheros = new ManejadorFicheros();
		
		this.inicializarActionListeners();
		this.cargarConfiguracionBasica();
		
	}
	
	/// METODOS
	
	
	private void cargarConfiguracionBasica() {
		this.inicializarPanel();
		this.inicializarMenu();
		this.inicializarTextFields();
		this.inicializarSeparadores();
		this.inicializarBotones();
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Literales.JFRAME_X, Literales.JFRAME_Y, Literales.JFRAME_ANCHO, Literales.JFRAME_LARGO);
		setContentPane(contenedor);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Inicializa el contenedor principal
	 */
	private void inicializarPanel() {
		this.contenedor = new JPanel();
		this.contenedor.setBorder(new EmptyBorder(Literales.JPANEL_EMPTY_BORDER, Literales.JPANEL_EMPTY_BORDER, Literales.JPANEL_EMPTY_BORDER, Literales.JPANEL_EMPTY_BORDER));
		this.contenedor.setLayout(null);
	}
	
	/**
	 * Inicializar el menu superior
	 */
	private void inicializarMenu() {
		this.menuBar = new JMenuBar();
		this.menuBar.setBounds(Literales.MENU_X, Literales.MENU_Y, Literales.MENU_ANCHO, Literales.MENU_LARGO);
		this.contenedor.add(menuBar);
		
		this.inicializarMenuArchivo();
		
		
		
	}
	
	/**
	 * Inicializa la pestan±a Archivo del menu
	 */
	private void inicializarMenuArchivo() {
		this.mnArchivo = new JMenu(Literales.MENU_ARCHIVO);
		this.menuBar.add(this.mnArchivo);
		
		this.mnItemNuevo = new JMenuItem(Literales.MENU_ITEM_NUEVO);
		this.mnItemNuevo.addActionListener(this.actionNuevo);
		this.mnArchivo.add(this.mnItemNuevo);
		
		this.mnArchivo.addSeparator();
		
		this.mnItemCargarCSV = new JMenuItem(Literales.MENU_ITEM_CARGAR_CSV);
		this.mnItemCargarCSV.addActionListener(this.actionCargarCSV);
		this.mnArchivo.add(this.mnItemCargarCSV);
		
		this.mnItemExportarCSV = new JMenuItem(Literales.MENU_ITEM_EXPORTAR_CSV);
		this.mnItemExportarCSV.addActionListener(this.actionExportarCSV);
		this.mnArchivo.add(this.mnItemExportarCSV);
	}
	
	
	/**
	 * Inicializa los JTextField
	 */
	private void inicializarTextFields() {
		
		try {
			this.mascara = new MaskFormatter("#");
		} catch (ParseException e) {
			this.mostrarMensajeDeError(e.getMessage());
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.casillas[i][j] = new JFormattedTextField(this.mascara);
				this.casillas[i][j].setColumns(Literales.TEXTFIELD_COLUMNAS);
				this.casillas[i][j].setBounds(this.calcularPosicionHorizontal(j), this.calcularPosicionVertical(i), Literales.TEXTFIELD_DIAMETRO, Literales.TEXTFIELD_DIAMETRO);
				this.casillas[i][j].setFont(Literales.TEXTFIELD_FUENTE);
				this.casillas[i][j].setHorizontalAlignment(JTextField.CENTER);

				this.contenedor.add(this.casillas[i][j]);
			}
		}
	}
	
	/**
	 * Inicializa los JSeparators
	 */
	private void inicializarSeparadores() {
		
		this.separadorVertical1 = new JSeparator();
		this.separadorVertical1.setOrientation(SwingConstants.VERTICAL);
		this.separadorVertical1.setForeground(Literales.SEPARADOR_COLOR_FOREGROUND);
		this.separadorVertical1.setBackground(Literales.SEPARADOR_COLOR_BACKGROUND);
		this.separadorVertical1.setBounds(113, 32, Literales.SEPARADOR_VERTICAL_ANCHO, Literales.SEPARADOR_VERTICAL_LARGO);
		this.contenedor.add(this.separadorVertical1);
		
		this.separadorVertical2 = new JSeparator();
		this.separadorVertical2.setOrientation(SwingConstants.VERTICAL);
		this.separadorVertical2.setForeground(Literales.SEPARADOR_COLOR_FOREGROUND);
		this.separadorVertical2.setBackground(Literales.SEPARADOR_COLOR_BACKGROUND);
		this.separadorVertical2.setBounds(223, 32, Literales.SEPARADOR_VERTICAL_ANCHO, Literales.SEPARADOR_VERTICAL_LARGO);
		this.contenedor.add(this.separadorVertical2);
		
		this.separadorHorizontal1 = new JSeparator();
		this.separadorHorizontal1.setForeground(Literales.SEPARADOR_COLOR_FOREGROUND);
		this.separadorHorizontal1.setBackground(Literales.SEPARADOR_COLOR_BACKGROUND);
		this.separadorHorizontal1.setBounds(10, 137, Literales.SEPARADOR_HORIZONTAL_ANCHO, Literales.SEPARADOR_HORIZONTAL_LARGO);
		this.contenedor.add(this.separadorHorizontal1);
		
		this.separadorHorizontal2 = new JSeparator();
		this.separadorHorizontal2.setForeground(Literales.SEPARADOR_COLOR_FOREGROUND);
		this.separadorHorizontal2.setBackground(Literales.SEPARADOR_COLOR_BACKGROUND);
		this.separadorHorizontal2.setBounds(10, 248, Literales.SEPARADOR_HORIZONTAL_ANCHO, Literales.SEPARADOR_HORIZONTAL_LARGO);
		this.contenedor.add(this.separadorHorizontal2);
	}
	
	/**
	 * Inicializar JButtons
	 */
	private void inicializarBotones() {
		
		this.btnSolucionar = new JButton(Literales.BTN_SOLUCIONAR);
		this.setFont(Literales.BOTON_FUENTE);
		this.btnSolucionar.setBounds(340, 296, Literales.BOTON_ANCHO, Literales.BOTON_LARGO);
		this.btnSolucionar.addActionListener(this.actionSolucionar);
		this.contenedor.add(this.btnSolucionar);
		
		this.btnLimpiar = new JButton(Literales.BTN_LIMPIAR);
		this.setFont(Literales.BOTON_FUENTE);
		this.btnLimpiar.setBounds(340, 331, Literales.BOTON_ANCHO, Literales.BOTON_LARGO);
		this.btnLimpiar.addActionListener(this.actionNuevo);
		this.contenedor.add(this.btnLimpiar);
	}
	
	
	
	/**
	 * Inicializa los action listener que se lanzan pulsando botones
	 */
	private void inicializarActionListeners() {
		
		// BOTON NUEVO
		this.actionNuevo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablero.inicializarTablero();
				pintarTablero(tablero.getCasillas());
			}
		};
		
		// BOTON CARGAR CSV
		this.actionCargarCSV = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String ruta = elegirFicheroConVentana();
				
				// Si el usuario no pulsa cancelar
				if (!ruta.equals(Literales.CANCEL_JOPTIONCHOOSED)) {
					try {
						
						int[][] matriz = manejadorDeFicheros.leerCSVSimple(ruta, manejadorDeFicheros.leerPropiedad("SEPARADOR_CSV"));
						tablero.inicializarTablero();
						tablero.insertarValores(matriz);
						pintarTablero(tablero.getCasillas());
					} catch (NumberFormatException | IOException ex) {
						mostrarMensajeDeError(ex.getMessage());
					}
				}
				
			}
		};
		
		// ACTION EXPORTAR CSV
		this.actionExportarCSV = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String ruta 				= elegirRutaGuardadoFicheroCSV();
				if (!ruta.equals(Literales.CANCEL_JOPTIONCHOOSED)) {
					if (!ruta.endsWith(".csv")) {
						ruta += ".csv";
					}
					manejadorDeFicheros.guardarCSV(ruta, leerContenidoDelTablero());
				}
				
			}
		};
		
		
		
		
		// BOTON SOLUCIONAR
		this.actionSolucionar = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				actualizarCamposTablero();
				Tablero nuevoTablero = solucionador.solucionarSudoku(tablero);
				if (nuevoTablero == null) {
					mostrarMensajeDeError(Literales.INCOMPLETO);
				}
				else {
					tablero = nuevoTablero;
					pintarTablero(tablero.getCasillas());
				}
			}
		};
	}
	
	
	
	
	
	private void mostrarMensajeDeError(String mensaje) {
		
		JOptionPane.showMessageDialog(this.contenedor, mensaje, Literales.ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	public void pintarTablero(Casilla[][] matriz) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String texto = "";
				if (matriz[i][j].getValor() != 0) {
					texto = String.valueOf(matriz[i][j].getValor());
				}
				this.casillas[i][j].setText(texto);
				
				if (matriz[i][j].isEditable()) {
					this.casillas[i][j].setForeground(Literales.TEXTFIELD_COLOR_VARIABLE);
				}
				else {
					this.casillas[i][j].setForeground(Literales.TEXTFIELD_COLOR_FIJO);
				}
			}
		}
	}
	
	
	private int[][] leerContenidoDelTablero() {
		int[][] matriz = new int[9][9];
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9 ; j++) {
				matriz[i][j] = this.tablero.getCasillas()[i][j].getValor();
			}
		}
		
		return matriz;
	}
	
	
	private void actualizarCamposTablero() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String valor = this.casillas[i][j].getText();
				int valorReal;
				if (valor.equals("") || valor.equals(" ")) {
					valorReal = 0;
				}
				else {
					valorReal = Integer.parseInt(valor);
				}
				
				this.tablero.getCasillas()[i][j].setValor(valorReal);
				if (this.tablero.getCasillas()[i][j].getValor() == 0) {
					this.tablero.getCasillas()[i][j].setEditable(true);
				}
				else {
					this.tablero.getCasillas()[i][j].setEditable(false);
				}
			}
		}
	}
	
	
	private String elegirFicheroConVentana() {
		
		String ruta = "";
		int seleccion = this.selectorDeFichero.showOpenDialog(this.contenedor);
		 
		 if (seleccion == JFileChooser.APPROVE_OPTION) {
			 ruta = this.selectorDeFichero.getSelectedFile().getAbsolutePath();
		 }
		 else {
			 ruta = Literales.CANCEL_JOPTIONCHOOSED;
		 }
		 
		 return ruta;
	}
	
	
	private String elegirRutaGuardadoFicheroCSV() {
		
		String ruta = "";
		int seleccion = this.selectorDeFichero.showSaveDialog(this.contenedor);
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			ruta = this.selectorDeFichero.getSelectedFile().getAbsolutePath();
		}
		else {
			ruta = Literales.CANCEL_JOPTIONCHOOSED;
		}
		
		return ruta;
	}
	
	
	private int calcularPosicionHorizontal(int x) {
		
		int resultado = 0;
		
		switch(x) {
		case 0:
			resultado = 10;
			break;
		case 1:
			resultado = 45;
			break;
		case 2:
			resultado = 80;
			break;
		case 3:
			resultado = 120;
			break;
		case 4:
			resultado = 155;
			break;
		case 5:
			resultado = 190;
			break;
		case 6:
			resultado = 230;
			break;
		case 7:
			resultado = 265;
			break;
		case 8:
			resultado = 300;
			break;
		}
		
		return resultado;
	}

	
	private int calcularPosicionVertical(int y) {
		
		int resultado = 0;
		
		switch(y) {
		case 0:
			resultado = 32;
			break;
		case 1:
			resultado = 67;
			break;
		case 2:
			resultado = 102;
			break;
		case 3:
			resultado = 143;
			break;
		case 4:
			resultado = 178;
			break;
		case 5:
			resultado = 213;
			break;
		case 6:
			resultado = 254;
			break;
		case 7:
			resultado = 289;
			break;
		case 8:
			resultado = 324;
			break;
		}
		
		return resultado;
	}
	
}
