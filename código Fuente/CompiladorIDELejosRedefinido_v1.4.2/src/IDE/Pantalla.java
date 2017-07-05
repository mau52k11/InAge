package IDE;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import com.borland.jbcl.layout.*;

import Lejos.Traductor;
import Lexico.AnalizadorLexico;

import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.*;
import java.text.NumberFormat;
import javax.swing.JOptionPane;

public class Pantalla extends JFrame {
  JPanel contentPane;
  Editor editor;
  ColorPR colorPR;
  Simulador simulador;
  Traductor traductor;
  
  int idioma = 1;

  private javax.swing.JScrollPane scroll;
  private final int NUEVO=0;
  private final int CERRAR=1;
  private final int CERRARTODO=2;
  private final int GUARDAR=3;
  private final int GUARDARTODO=4;
  private final int GUARDARCOMO=5;
  private final int IMPRIMIR=6;
  private final int DESHACER=7;
  private final int REHACER=8;
  private final int COPIAR=9;
  private final int PEGAR=10;
  private final int CORTAR=11;
  private final int ELIMINAR=12;
  private final int SELECCIONARTODO=13;
  private final int VERERRORES=14;
  private final int VIEWERRORES=15;
  private final int EJECUTAR=16;
  private final int SIMULAR=17;
  private final int AYUDA=18;
  private final int STATUSBAR=19;
  private final int MUNDO = 20;
  private final int OBJETOS = 21;
  private final int DESHGRAFICO = 22;
  private final int REHGRAFICO = 23;
  private final int TRADUCIR = 24;
  Messages m = new Messages();

  private boolean OVERTYPEMODE = false;

  int iError[][] = new int [50][2];
  int iWarning[][] = new int [50][2];
/* *****
Declaración de Menú
***** */

  JMenuBar jMenuBar1 = new JMenuBar();
// Menú de Archivo
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileNuevo = new JMenuItem();
  JMenuItem jMenuFileAbrir = new JMenuItem();
  JMenuItem jMenuFileCerrar = new JMenuItem();
  JMenuItem jMenuFileGuardar = new JMenuItem();
  JMenuItem jMenuFileGuardarComo = new JMenuItem();
  JMenuItem jMenuFileExit = new JMenuItem();
// Menú de Edición
  JMenu jMenuEditar = new JMenu();
  JMenuItem jMenuEditarDesh = new JMenuItem();
  JMenuItem jMenuEditarReh = new JMenuItem();
  JMenuItem jMenuEditarCortar = new JMenuItem();
  JMenuItem jMenuEditarCopiar = new JMenuItem();
  JMenuItem jMenuEditarPegar = new JMenuItem();
  JMenuItem jMenuEditarEliminar = new JMenuItem();
  JMenuItem jMenuEditarSelTodo = new JMenuItem();
// Menú de Vistas
  JMenu jMenuVer = new JMenu();
// Menú de Compilar
  JMenu jMenuComp = new JMenu();
  JMenuItem jMenuCompCompilar = new JMenuItem();
//Menú de Traducir
  JMenu jMenuTrad = new JMenu();
 JMenu jMenuTraduce = new JMenu();
 JMenuItem jMenuTraduceTraducir = new JMenuItem();
// Menú de Máquina Virtual
  JMenu jMenuMV = new JMenu();
// Menú de Archivo
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();


  JToolBar jToolBar = new JToolBar();
  JButton bNuevo = new JButton();
  JButton bAbrir = new JButton();
  JButton bCerrar = new JButton();
  JButton bGuardar = new JButton();
  JButton bGuardarTodo = new JButton();
  JButton bImprimir = new JButton();
  JButton bDeshacer = new JButton();
  JButton bRehacer = new JButton();
  JButton bCopiar = new JButton();
  JButton bPegar = new JButton();
  JButton bCortar = new JButton();
  JButton bCompilar = new JButton();
  JButton bTraducir = new JButton();
  JButton bAyuda = new JButton();
  ImageIcon imgAge2000;
  ImageIcon imgNuevo;
  ImageIcon imgAbrir;
  ImageIcon imgCerrar;
  ImageIcon imgGuardar;
  ImageIcon imgXGuardar;
  ImageIcon imgGuardarTodo;
  ImageIcon imgXGuardarTodo;
  ImageIcon imgImprimir;
  ImageIcon imgDeshacer;
  ImageIcon imgXDeshacer;
  ImageIcon imgRehacer;
  ImageIcon imgXRehacer;
  ImageIcon imgCopiar;
  ImageIcon imgXCopiar;
  ImageIcon imgPegar;
  ImageIcon imgCortar;
  ImageIcon imgXCortar;
  ImageIcon imgCompilar;
  ImageIcon imgMaqVirtual;
  ImageIcon imgAyuda;
  ImageIcon imgInfo;
  ImageIcon imgError;
  ImageIcon imgWarning;
  ImageIcon imgNoIcon;
  ImageIcon imgFoco;
  ImageIcon imgPared;
  ImageIcon imgObstaculo;
  ImageIcon imgTermometro;
  ImageIcon imgStop;
  ImageIcon imgStart;
  ImageIcon imgUndo;
  ImageIcon imgRedo;
  ImageIcon imgLimpiar;
  ImageIcon imgGoma;
  ImageIcon imgGomaPared;
  ImageIcon imgTraducir;

  Box statusBar;
  JPanel escritorio = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  Border border1;
  JSplitPane jSplitPane1 = new JSplitPane();
  BorderLayout borderLayout2 = new BorderLayout();

// Variables para desplegar Errores/Warnings de los programas
  JTextPane viewMsg = new JTextPane();
  private StyledDocument doc = viewMsg.getStyledDocument();
  private Style styleError = doc.addStyle("Errores", null);
  private Style styleWarning = doc.addStyle("Warnings", null);
  final NumberFormat nf = NumberFormat.getInstance();

  JCheckBoxMenuItem jCheckBoxVerErrores = new JCheckBoxMenuItem();
  JMenuItem jMenuFileCerrarTodo = new JMenuItem();
  JMenuItem jMenuFileGuardarTodo = new JMenuItem();
  TitledBorder titledBorder1;
  Border border2;
  JPanel panelArchivo = new JPanel();
  JPanel panelEditar = new JPanel();
  JPanel panelCompilar = new JPanel();
  JPanel panelMaqVirtual = new JPanel();
  JPanel panelAyuda = new JPanel();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  BoxLayout2 boxLayout23 = new BoxLayout2();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  JMenuItem jMenuFileImprimir = new JMenuItem();
  BoxLayout2 boxLayout24 = new BoxLayout2();
  JMenuItem jMenuHelpAyuda = new JMenuItem();
  BoxLayout2 boxLayout25 = new BoxLayout2();
  JPanel status1 = new JPanel();
  JPanel status2 = new JPanel();
  JPanel status3 = new JPanel();
  JLabel text1 = new JLabel();
  JLabel text2 = new JLabel();
  JLabel text3 = new JLabel();
  GridLayout gridLayout1 = new GridLayout();
  GridLayout gridLayout2 = new GridLayout();
  GridLayout gridLayout3 = new GridLayout();

  JMenuItem jMenuAbrirMundo = new JMenuItem();
  JMenuItem jMenuGuardarMundo = new JMenuItem();
  JMenuItem jMenuNuevoMundo = new JMenuItem();
  JButton bStart = new JButton();
  JButton bStop = new JButton();
  JPanel panelHerramientas = new JPanel();
  JButton bLuz = new JButton();
  JButton bObstaculo = new JButton();
  JButton bPared = new JButton();
  BoxLayout2 boxLayout26 = new BoxLayout2();
  JSplitPane jSplitPane2 = new JSplitPane();
  JButton bTemp = new JButton();
  JMenuItem jMenuMVStart = new JMenuItem();
  JMenuItem jMenuMVStop = new JMenuItem();
  JMenuItem jMenuMVCerrar = new JMenuItem();
  JButton bLimpiar = new JButton();
  JPanel panelLimpiar = new JPanel();
  BoxLayout2 boxLayout27 = new BoxLayout2();
  JButton bBorrar = new JButton();
  JButton bBorrarPared = new JButton();
  JButton bUndo = new JButton();
  JButton bRedo = new JButton();
  JMenuItem jMenuGuardar = new JMenuItem();
  JMenu jMenuLanguage = new JMenu();
  JMenuItem jMenuLangSpanish = new JMenuItem();
  JMenuItem jMenuLangEnglish = new JMenuItem();
  //Construct the frame
  public Pantalla() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception  {
    scroll = new javax.swing.JScrollPane();
    colorPR = new ColorPR();
    imgAge2000 = new ImageIcon("imagen/semaforo.gif");
    imgNuevo = new ImageIcon("imagen/nuevo16.gif");
    imgAbrir = new ImageIcon("imagen/abrir16.gif");
    imgCerrar = new ImageIcon("imagen/cerrar16.gif");
    imgGuardar = new ImageIcon("imagen/guardar16.gif");
    imgXGuardar = new ImageIcon("imagen/xguardar16.gif");
    imgGuardarTodo = new ImageIcon("imagen/guardartodo16.gif");
    imgXGuardarTodo = new ImageIcon("imagen/xguardartodo16.gif");
    imgImprimir = new ImageIcon("imagen/imprimir16.gif");
    imgDeshacer = new ImageIcon("imagen/deshacer16.gif");
    imgXDeshacer = new ImageIcon("imagen/xdeshacer16.gif");
    imgRehacer = new ImageIcon("imagen/rehacer16.gif");
    imgXRehacer = new ImageIcon("imagen/xrehacer16.gif");
    imgCopiar = new ImageIcon("imagen/copiar16.gif");
    imgXCopiar = new ImageIcon("imagen/xcopiar16.gif");
    imgPegar = new ImageIcon("imagen/pegar16.gif");
    imgCortar = new ImageIcon("imagen/cortar16.gif");
    imgXCortar = new ImageIcon("imagen/xcortar16.gif");
    imgCompilar = new ImageIcon("imagen/compilar16.gif");
    imgMaqVirtual = new ImageIcon("imagen/simular16.gif");
    imgAyuda = new ImageIcon("imagen/ayuda16.gif");
    imgInfo = new ImageIcon("imagen/info16.gif");
    imgError = new ImageIcon("imagen/bolaroja14.gif");
    imgWarning = new ImageIcon("imagen/bolaamarilla14.gif");
    imgNoIcon = new ImageIcon("imagen/no_icon16.gif");
    imgFoco = new ImageIcon("imagen/foco16.gif");
    imgPared = new ImageIcon("imagen/pared16.gif");
    imgObstaculo = new ImageIcon("imagen/cubo16.gif");
    imgTermometro = new ImageIcon("imagen/termometro16.gif");
    imgUndo = new ImageIcon("imagen/undo16.gif");
    imgRedo = new ImageIcon("imagen/redo16.gif");
    imgLimpiar = new ImageIcon("imagen/limpiar16.gif");
    imgGoma = new ImageIcon("imagen/goma16.gif");
    imgGomaPared = new ImageIcon("imagen/goma216.gif");
    imgStart = new ImageIcon("imagen/start16.gif");
    imgStop = new ImageIcon("imagen/stop16.gif");
    imgTraducir = new ImageIcon("imagen/LejosLogo.gif");

    StyleConstants.setIcon(styleWarning, imgWarning);
    StyleConstants.setIcon(styleError, imgError);

    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    border1 = new EtchedBorder(EtchedBorder.RAISED,new Color(82, 157, 235),new Color(40, 77, 115));
    titledBorder1 = new TitledBorder("");
    contentPane.setLayout(borderLayout1);
    statusBar = Box.createHorizontalBox();
    statusBar.setBounds(new Rectangle(24, 209, 372, 21));
    this.setLocale(java.util.Locale.getDefault());
    this.setSize(new Dimension(800, 600));
    this.setIconImage(imgAge2000.getImage());
    this.setTitle(m.getString("Pantalla.this.title")); //$NON-NLS-1$
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
          this_windowClosing(e);
      }
    });

// Archivo
    jMenuFile.setActionCommand(m.getString("Pantalla.jMenuFile.actionCommand")); //$NON-NLS-1$
    jMenuFile.setMnemonic('A');
    jMenuFile.setText(m.getString("Pantalla.jMenuFile.text")); //$NON-NLS-1$
    jMenuFileNuevo.setIcon(imgNuevo);
    jMenuFileNuevo.setMnemonic('N');
    jMenuFileNuevo.setText(m.getString("Pantalla.jMenuFileNuevo.text")); //$NON-NLS-1$
    jMenuFileNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(78, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileNuevo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileNuevo_actionPerformed(e);
      }
    });
    jMenuFileAbrir.setIcon(imgAbrir);
    jMenuFileAbrir.setMnemonic('A');
    jMenuFileAbrir.setText(m.getString("Pantalla.jMenuFileAbrir.text")); //$NON-NLS-1$
    jMenuFileAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(65, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileAbrir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileAbrir_actionPerformed(e);
      }
    });
    jMenuFileCerrar.setIcon(imgCerrar);
    jMenuFileCerrar.setMnemonic('C');
    jMenuFileCerrar.setText(m.getString("Pantalla.jMenuFileCerrar.text")); //$NON-NLS-1$
    jMenuFileCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(115, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileCerrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileCerrar_actionPerformed(e);
      }
    });
    jMenuFileGuardar.setDisabledIcon(imgXGuardar);
    jMenuFileGuardar.setIcon(imgGuardar);
    jMenuFileGuardar.setMnemonic('G');
    jMenuFileGuardar.setText(m.getString("Pantalla.jMenuFileGuardar.text")); //$NON-NLS-1$
    jMenuFileGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(71, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileGuardar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileGuardar_actionPerformed(e);
      }
    });
    jMenuFileGuardarComo.setDisabledIcon(imgXGuardarTodo);
    jMenuFileGuardarComo.setIcon(imgNoIcon);
    jMenuFileGuardarComo.setText(m.getString("Pantalla.jMenuFileGuardarComo.text")); //$NON-NLS-1$
    jMenuFileGuardarComo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileGuardarComo_actionPerformed(e);
      }
    });
    jMenuFileExit.setIcon(imgNoIcon);
    jMenuFileExit.setMnemonic('S');
    jMenuFileExit.setText(m.getString("Pantalla.jMenuFileExit.text")); //$NON-NLS-1$
// Editar
    jMenuEditar.setMnemonic('E');
    jMenuEditar.setText(m.getString("Pantalla.jMenuEditar.text")); //$NON-NLS-1$
    jMenuEditarDesh.setDisabledIcon(imgXDeshacer);
    jMenuEditarDesh.setIcon(imgDeshacer);
    jMenuEditarDesh.setMnemonic('D');
    jMenuEditarDesh.setText(m.getString("Pantalla.jMenuEditarDesh.text")); //$NON-NLS-1$
    jMenuEditarDesh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(90, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarDesh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarDesh_actionPerformed(e);
      }
    });
    jMenuEditarReh.setDisabledIcon(imgXRehacer);
    jMenuEditarReh.setIcon(imgRehacer);
    jMenuEditarReh.setMnemonic('R');
    jMenuEditarReh.setText(m.getString("Pantalla.jMenuEditarReh.text")); //$NON-NLS-1$
    jMenuEditarReh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(89, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarReh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarReh_actionPerformed(e);
      }
    });
    jMenuEditarCortar.setDisabledIcon(imgXCortar);
    jMenuEditarCortar.setIcon(imgCortar);
    jMenuEditarCortar.setMnemonic('T');
    jMenuEditarCortar.setText(m.getString("Pantalla.jMenuEditarCortar.text")); //$NON-NLS-1$
    jMenuEditarCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(88, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarCortar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarCortar_actionPerformed(e);
      }
    });
    jMenuEditarCopiar.setDisabledIcon(imgXCopiar);
    jMenuEditarCopiar.setIcon(imgCopiar);
    jMenuEditarCopiar.setMnemonic('C');
    jMenuEditarCopiar.setText(m.getString("Pantalla.jMenuEditarCopiar.text")); //$NON-NLS-1$
    jMenuEditarCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(67, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarCopiar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarCopiar_actionPerformed(e);
      }
    });
    jMenuEditarPegar.setIcon(imgPegar);
    jMenuEditarPegar.setMnemonic('P');
    jMenuEditarPegar.setText(m.getString("Pantalla.jMenuEditarPegar.text")); //$NON-NLS-1$
    jMenuEditarPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(86, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarPegar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarPegar_actionPerformed(e);
      }
    });
    jMenuEditarEliminar.setIcon(imgNoIcon);
    jMenuEditarEliminar.setMnemonic('E');
    jMenuEditarEliminar.setText(m.getString("Pantalla.jMenuEditarEliminar.text")); //$NON-NLS-1$
    jMenuEditarEliminar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarEliminar_actionPerformed(e);
      }
    });
    jMenuEditarSelTodo.setIcon(imgNoIcon);
    jMenuEditarSelTodo.setMnemonic('S');
    jMenuEditarSelTodo.setText(m.getString("Pantalla.jMenuEditarSelTodo.text")); //$NON-NLS-1$
    jMenuEditarSelTodo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(69, java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditarSelTodo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarSelTodo_actionPerformed(e);
      }
    });
// Vistas
    jMenuVer.setText(m.getString("Pantalla.jMenuVer.text")); //$NON-NLS-1$
// Compilar
    jMenuComp.setMnemonic('C');
    jMenuComp.setText(m.getString("Pantalla.jMenuComp.text")); //$NON-NLS-1$
    jMenuCompCompilar.setDisabledIcon(null);
    jMenuCompCompilar.setIcon(imgCompilar);
    jMenuCompCompilar.setMnemonic('E');
    jMenuCompCompilar.setText(m.getString("Pantalla.jMenuCompCompilar.text")); //$NON-NLS-1$
    jMenuCompCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(120, 0, false));
    jMenuCompCompilar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuCompCompilar_actionPerformed(e);
      }
    });
    
    
    
    
    
 // traducir
    jMenuTrad.setMnemonic('T');
    jMenuTrad.setText("Traducir");
    jMenuTraduceTraducir.setDisabledIcon(null);
    jMenuTraduceTraducir.setIcon(imgTraducir);
    jMenuTraduceTraducir.setMnemonic('L');
    jMenuTraduceTraducir.setText(m.getString("Pantalla.jMenuTraduceTraducir.text")); //$NON-NLS-1$
   // jMenuTraduceTraducir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(120, 0, false));
    jMenuTraduceTraducir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuTraduceTraducir_actionPerformed(e);
      }
    });

// Máquina Virtual
    jMenuMV.setText(m.getString("Pantalla.jMenuMV.text")); //$NON-NLS-1$

    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText(m.getString("Pantalla.jMenuHelp.text")); //$NON-NLS-1$
    jMenuHelpAbout.setIcon(imgInfo);
    jMenuHelpAbout.setText(m.getString("Pantalla.jMenuHelpAbout.text")); //$NON-NLS-1$
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    bAbrir.setIcon(imgAbrir);
    bAbrir.setMnemonic('0');
    bAbrir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileAbrir_actionPerformed(e);
      }
    });
    bAbrir.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
// Configuración de los botones de las barras de Acceso
    bAbrir.setBorder(BorderFactory.createRaisedBevelBorder());
    bAbrir.setToolTipText(m.getString("Pantalla.bAbrir.toolTipText")); //$NON-NLS-1$
    bAbrir.setBorderPainted(false);
    bAbrir.setFocusPainted(false);
    bCerrar.setIcon(imgCerrar);
    bCerrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileCerrar_actionPerformed(e);
      }
    });
    bCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bCerrar.setBorder(BorderFactory.createRaisedBevelBorder());
    bCerrar.setToolTipText(m.getString("Pantalla.bCerrar.toolTipText")); //$NON-NLS-1$
    bCerrar.setBorderPainted(false);
    bCerrar.setFocusPainted(false);
    bAyuda.setIcon(imgAyuda);
    bAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bAyuda.setAlignmentX((float) 0.0);
    bAyuda.setBorder(BorderFactory.createRaisedBevelBorder());
    bAyuda.setToolTipText(m.getString("Pantalla.bAyuda.toolTipText")); //$NON-NLS-1$
    bAyuda.setBorderPainted(false);
    bAyuda.setFocusPainted(false);
// Configuración del escritorio
    escritorio.setBackground(new Color(58, 110, 165));
    escritorio.setLayout(borderLayout2);
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setBorder(null);
    jSplitPane1.setBottomComponent(null);
    jSplitPane1.setContinuousLayout(true);
    jSplitPane1.setLeftComponent(jSplitPane2);
    jSplitPane1.setRightComponent(scroll);
    jSplitPane1.setTopComponent(null);
    jCheckBoxVerErrores.setText(m.getString("Pantalla.jCheckBoxVerErrores.text")); //$NON-NLS-1$
    jCheckBoxVerErrores.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jCheckBoxVerErrores_actionPerformed(e);
      }
    });
    jMenuFileCerrarTodo.setIcon(imgNoIcon);
    jMenuFileCerrarTodo.setMnemonic('T');
    jMenuFileCerrarTodo.setText(m.getString("Pantalla.jMenuFileCerrarTodo.text")); //$NON-NLS-1$
    jMenuFileCerrarTodo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileCerrarTodo_actionPerformed(e);
      }
    });
    jMenuFileGuardarTodo.setIcon(imgGuardarTodo);
    jMenuFileGuardarTodo.setMnemonic('D');
    jMenuFileGuardarTodo.setText(m.getString("Pantalla.jMenuFileGuardarTodo.text")); //$NON-NLS-1$
    jMenuFileGuardarTodo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileGuardarTodo_actionPerformed(e);
      }
    });
    bNuevo.setBorder(BorderFactory.createRaisedBevelBorder());
    bNuevo.setToolTipText(m.getString("Pantalla.bNuevo.toolTipText")); //$NON-NLS-1$
    bNuevo.setBorderPainted(false);
    bNuevo.setFocusPainted(false);
    bNuevo.setIcon(imgNuevo);
    bNuevo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileNuevo_actionPerformed(e);
      }
    });
    bNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bGuardar.setBorder(BorderFactory.createRaisedBevelBorder());
    bGuardar.setToolTipText(m.getString("Pantalla.bGuardar.toolTipText")); //$NON-NLS-1$
    bGuardar.setBorderPainted(false);
    bGuardar.setDisabledIcon(imgXGuardar);
    bGuardar.setFocusPainted(false);
    bGuardar.setIcon(imgGuardar);
    bGuardar.setMnemonic('0');
    bGuardar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileGuardar_actionPerformed(e);
      }
    });
    bGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    panelArchivo.setBorder(BorderFactory.createEtchedBorder());
    panelArchivo.setLayout(boxLayout21);
    panelEditar.setBorder(BorderFactory.createEtchedBorder());
    panelEditar.setLayout(boxLayout22);
    panelAyuda.setLayout(boxLayout23);
    bDeshacer.setBorder(BorderFactory.createRaisedBevelBorder());
    bDeshacer.setToolTipText(m.getString("Pantalla.bDeshacer.toolTipText")); //$NON-NLS-1$
    bDeshacer.setBorderPainted(false);
    bDeshacer.setDisabledIcon(imgXDeshacer);
    bDeshacer.setFocusPainted(false);
    bDeshacer.setIcon(imgDeshacer);
    bDeshacer.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarDesh_actionPerformed(e);
      }
    });
    bDeshacer.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bRehacer.setBorder(BorderFactory.createRaisedBevelBorder());
    bRehacer.setToolTipText(m.getString("Pantalla.bRehacer.toolTipText")); //$NON-NLS-1$
    bRehacer.setBorderPainted(false);
    bRehacer.setDisabledIcon(imgXRehacer);
    bRehacer.setFocusPainted(false);
    bRehacer.setIcon(imgRehacer);
    bRehacer.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarReh_actionPerformed(e);
      }
    });
    bRehacer.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bCopiar.setBorder(BorderFactory.createRaisedBevelBorder());
    bCopiar.setToolTipText(m.getString("Pantalla.bCopiar.toolTipText")); //$NON-NLS-1$
    bCopiar.setBorderPainted(false);
    bCopiar.setDisabledIcon(imgXCopiar);
    bCopiar.setFocusPainted(false);
    bCopiar.setIcon(imgCopiar);
    bCopiar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarCopiar_actionPerformed(e);
      }
    });
    bCopiar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bPegar.setBorder(BorderFactory.createRaisedBevelBorder());
    bPegar.setToolTipText(m.getString("Pantalla.bPegar.toolTipText")); //$NON-NLS-1$
    bPegar.setBorderPainted(false);
    bPegar.setDisabledIcon(null);
    bPegar.setFocusPainted(false);
    bPegar.setIcon(imgPegar);
    bPegar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarPegar_actionPerformed(e);
      }
    });
    bPegar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bCortar.setBorder(BorderFactory.createRaisedBevelBorder());
    bCortar.setToolTipText(m.getString("Pantalla.bCortar.toolTipText")); //$NON-NLS-1$
    bCortar.setBorderPainted(false);
    bCortar.setDisabledIcon(imgXCortar);
    bCortar.setFocusPainted(false);
    bCortar.setIcon(imgCortar);
    bCortar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuEditarCortar_actionPerformed(e);
      }
    });
    bCortar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bGuardarTodo.setBorder(BorderFactory.createRaisedBevelBorder());
    bGuardarTodo.setToolTipText(m.getString("Pantalla.bGuardarTodo.toolTipText")); //$NON-NLS-1$
    bGuardarTodo.setBorderPainted(false);
    bGuardarTodo.setDisabledIcon(imgXGuardarTodo);
    bGuardarTodo.setFocusPainted(false);
    bGuardarTodo.setIcon(imgGuardarTodo);
    bGuardarTodo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileGuardarTodo_actionPerformed(e);
      }
    });
    bGuardarTodo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    jMenuFileImprimir.setIcon(imgImprimir);
    jMenuFileImprimir.setMnemonic('I');
    jMenuFileImprimir.setText(m.getString("Pantalla.jMenuFileImprimir.text")); //$NON-NLS-1$
    jMenuFileImprimir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileImprimir_actionPerformed(e);
      }
    });
    bImprimir.setBorder(BorderFactory.createRaisedBevelBorder());
    bImprimir.setToolTipText(m.getString("Pantalla.bImprimir.toolTipText")); //$NON-NLS-1$
    bImprimir.setBorderPainted(false);
    bImprimir.setFocusPainted(false);
    bImprimir.setIcon(imgImprimir);
    bImprimir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileImprimir_actionPerformed(e);
      }
    });
    bImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    panelAyuda.setBorder(BorderFactory.createEtchedBorder());
    panelCompilar.setBorder(BorderFactory.createEtchedBorder());
    panelCompilar.setLayout(boxLayout24);
    bCompilar.setBorder(BorderFactory.createRaisedBevelBorder());
    bCompilar.setToolTipText(m.getString("Pantalla.bCompilar.toolTipText")); //$NON-NLS-1$
    bCompilar.setBorderPainted(false);
    bCompilar.setDisabledIcon(null);
    bCompilar.setFocusPainted(false);
    bCompilar.setIcon(imgCompilar);
    bCompilar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuCompCompilar_actionPerformed(e);
      }
    });
    bCompilar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      
     
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    
    bTraducir.setBorder(BorderFactory.createRaisedBevelBorder());
    bTraducir.setToolTipText(m.getString("Pantalla.bTraducir.toolTipText")); //$NON-NLS-1$
    bTraducir.setBorderPainted(false);
    bTraducir.setDisabledIcon(null);
    bTraducir.setFocusPainted(false);
    bTraducir.setIcon(imgTraducir);
    bTraducir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuTraduceTraducir_actionPerformed(e);
      }
    });
    bTraducir.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      
     
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    
    
    
    
    
    
    
    jMenuHelpAyuda.setIcon(imgAyuda);
    jMenuHelpAyuda.setText(m.getString("Pantalla.jMenuHelpAyuda.text")); //$NON-NLS-1$
    jMenuHelpAyuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(112, 0, false));
    panelMaqVirtual.setBorder(BorderFactory.createEtchedBorder());
    panelMaqVirtual.setLayout(boxLayout25);
    status1.setBorder(BorderFactory.createEtchedBorder());
    status1.setMaximumSize(new Dimension(20000, 20000));
    status1.setMinimumSize(new Dimension(20000, 20000));
    status1.setPreferredSize(new Dimension(4, 4));
    status1.setLayout(gridLayout1);
    status2.setBorder(BorderFactory.createEtchedBorder());
    status2.setMaximumSize(new Dimension(5000, 5000));
    status2.setMinimumSize(new Dimension(5000, 5000));
    status2.setPreferredSize(new Dimension(124, 19));
    status2.setLayout(gridLayout2);
    status3.setBorder(BorderFactory.createEtchedBorder());
    status3.setMaximumSize(new Dimension(7767, 7767));
    status3.setMinimumSize(new Dimension(7767, 7767));
    status3.setPreferredSize(new Dimension(124, 19));
    status3.setLayout(gridLayout3);
    text3.setPreferredSize(new Dimension(120, 15));
    text3.setRequestFocusEnabled(true);
    text2.setPreferredSize(new Dimension(120, 15));
    viewMsg.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        viewMsg_mouseClicked(e);
      }
    });
    viewMsg.setEditable(false);

    jMenuAbrirMundo.setIcon(imgNoIcon);
    jMenuAbrirMundo.setText(m.getString("Pantalla.jMenuAbrirMundo.text")); //$NON-NLS-1$
    jMenuAbrirMundo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuAbrirMundo_actionPerformed(e);
      }
    });
    jMenuGuardarMundo.setActionCommand(m.getString("Pantalla.jMenuGuardarMundo.actionCommand")); //$NON-NLS-1$
    jMenuGuardarMundo.setIcon(imgNoIcon);
    jMenuGuardarMundo.setText(m.getString("Pantalla.jMenuGuardarMundo.text")); //$NON-NLS-1$
    jMenuGuardarMundo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuGuardarMundo_actionPerformed(e);
      }
    });
    jMenuNuevoMundo.setIcon(imgNoIcon);
    jMenuNuevoMundo.setText(m.getString("Pantalla.jMenuNuevoMundo.text")); //$NON-NLS-1$
    jMenuNuevoMundo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuNuevoMundo_actionPerformed(e);
      }
    });
    bStart.setBorder(BorderFactory.createRaisedBevelBorder());
    bStart.setToolTipText(m.getString("Pantalla.bStart.toolTipText")); //$NON-NLS-1$
    bStart.setBorderPainted(false);
    bStart.setFocusPainted(false);
    bStart.setIcon(imgStart);
    bStart.setText(m.getString("Pantalla.bStart.text")); //$NON-NLS-1$
    bStart.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bStart_actionPerformed(e);
      }
    });
    bStop.setBorder(BorderFactory.createRaisedBevelBorder());
    bStop.setToolTipText(m.getString("Pantalla.bStop.toolTipText")); //$NON-NLS-1$
    bStop.setBorderPainted(false);
    bStop.setFocusPainted(false);
    bStop.setIcon(imgStop);
    bStop.setText(m.getString("Pantalla.bStop.text")); //$NON-NLS-1$
    bStop.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    bStop.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bStop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bStop_actionPerformed(e);
      }
    });
    bLuz.setBorder(BorderFactory.createRaisedBevelBorder());
    bLuz.setDebugGraphicsOptions(0);
    bLuz.setToolTipText(m.getString("Pantalla.bLuz.toolTipText")); //$NON-NLS-1$
    bLuz.setBorderPainted(false);
    bLuz.setFocusPainted(false);
    bLuz.setHorizontalTextPosition(SwingConstants.TRAILING);
    bLuz.setIcon(imgFoco);
    bLuz.setText(m.getString("Pantalla.bLuz.text")); //$NON-NLS-1$
    bLuz.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    bLuz.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bLuz.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bLuz_actionPerformed(e);
      }
    });
    panelHerramientas.setLayout(boxLayout26);
    bPared.setBorder(BorderFactory.createRaisedBevelBorder());
    bPared.setToolTipText(m.getString("Pantalla.bPared.toolTipText")); //$NON-NLS-1$
    bPared.setBorderPainted(false);
    bPared.setFocusPainted(false);
    bPared.setIcon(imgPared);
    bPared.setText(m.getString("Pantalla.bPared.text")); //$NON-NLS-1$
    bPared.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bPared.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bPared_actionPerformed(e);
      }
    });
    bObstaculo.setBorder(BorderFactory.createRaisedBevelBorder());
    bObstaculo.setToolTipText(m.getString("Pantalla.bObstaculo.toolTipText")); //$NON-NLS-1$
    bObstaculo.setBorderPainted(false);
    bObstaculo.setFocusPainted(false);
    bObstaculo.setIcon(imgObstaculo);
    bObstaculo.setText(m.getString("Pantalla.bObstaculo.text")); //$NON-NLS-1$
    bObstaculo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bObstaculo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bObstaculo_actionPerformed(e);
      }
    });
    panelHerramientas.setBorder(BorderFactory.createEtchedBorder());
    jSplitPane2.setBottomComponent(simulador);
    jSplitPane2.setLeftComponent(editor);
    jSplitPane2.setRightComponent(simulador);
    bTemp.setBorder(BorderFactory.createRaisedBevelBorder());
    bTemp.setToolTipText(m.getString("Pantalla.bTemp.toolTipText")); //$NON-NLS-1$
    bTemp.setBorderPainted(false);
    bTemp.setFocusPainted(false);
    bTemp.setIcon(imgTermometro);
    bTemp.setText(m.getString("Pantalla.bTemp.text")); //$NON-NLS-1$
    bTemp.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    bTemp.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bTemp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bTemp_actionPerformed(e);
      }
    });
    jMenuMVStart.setIcon(imgStart);
    jMenuMVStart.setText(m.getString("Pantalla.jMenuMVStart.text")); //$NON-NLS-1$
    jMenuMVStop.setIcon(imgStop);
    jMenuMVStop.setText(m.getString("Pantalla.jMenuMVStop.text")); //$NON-NLS-1$
    jMenuMVCerrar.setIcon(imgNoIcon);
    jMenuMVCerrar.setText(m.getString("Pantalla.jMenuMVCerrar.text")); //$NON-NLS-1$
    jMenuMVCerrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuMVCerrar_actionPerformed(e);
      }
    });
    jMenuMVCerrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuMVCerrar_actionPerformed(e);
      }
    });
    bLimpiar.setBorder(BorderFactory.createRaisedBevelBorder());
    bLimpiar.setToolTipText(m.getString("Pantalla.bLimpiar.toolTipText")); //$NON-NLS-1$
    bLimpiar.setBorderPainted(false);
    bLimpiar.setFocusPainted(false);
    bLimpiar.setIcon(imgLimpiar);
    bLimpiar.setSelectedIcon(null);
    bLimpiar.setText(m.getString("Pantalla.bLimpiar.text")); //$NON-NLS-1$
    bLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    bLimpiar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuNuevoMundo_actionPerformed(e);
      }
    });
    bLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    panelLimpiar.setLayout(boxLayout27);

    bBorrar.setBorder(BorderFactory.createRaisedBevelBorder());
    bBorrar.setToolTipText(m.getString("Pantalla.bBorrar.toolTipText")); //$NON-NLS-1$
    bBorrar.setBorderPainted(false);
    bBorrar.setFocusPainted(false);
    bBorrar.setIcon(imgGoma);
    bBorrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bBorrar_actionPerformed(e);
      }
    });
    bBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    panelLimpiar.setBorder(BorderFactory.createEtchedBorder());
    bBorrarPared.setBorder(BorderFactory.createRaisedBevelBorder());
    bBorrarPared.setToolTipText(m.getString("Pantalla.bBorrarPared.toolTipText")); //$NON-NLS-1$
    bBorrarPared.setBorderPainted(false);
    bBorrarPared.setFocusPainted(false);
    bBorrarPared.setHorizontalTextPosition(SwingConstants.TRAILING);
    bBorrarPared.setIcon(imgGomaPared);
    bBorrarPared.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bBorrarPared_actionPerformed(e);
      }
    });
    bBorrarPared.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bRedo.setBorder(BorderFactory.createRaisedBevelBorder());
    bRedo.setToolTipText(m.getString("Pantalla.bRedo.toolTipText")); //$NON-NLS-1$
    bRedo.setBorderPainted(false);
    bRedo.setFocusPainted(false);
    bRedo.setIcon(imgRedo);
    bRedo.setText(m.getString("Pantalla.bRedo.text")); //$NON-NLS-1$
    bRedo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bRedo_actionPerformed(e);
      }
    });
    bRedo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    bUndo.setBorder(BorderFactory.createRaisedBevelBorder());
    bUndo.setToolTipText(m.getString("Pantalla.bUndo.toolTipText")); //$NON-NLS-1$
    bUndo.setVerifyInputWhenFocusTarget(true);
    bUndo.setBorderPainted(false);
    bUndo.setFocusPainted(false);
    bUndo.setIcon(imgUndo);
    bUndo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bUndo_actionPerformed(e);
      }
    });
    bUndo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        botonEnter(e);
      }
      public void mouseExited(MouseEvent e) {
        botonExited(e);
      }
      public void mousePressed(MouseEvent e) {
        botonPressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        botonReleased(e);
      }
    });
    jMenuGuardar.setIcon(imgNoIcon);
    jMenuGuardar.setText(m.getString("Pantalla.jMenuGuardar.text")); //$NON-NLS-1$
    jMenuGuardar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuGuardar_actionPerformed(e);
      }
    });
    jToolBar.add(panelArchivo, null);
    panelArchivo.add(bNuevo, null);
    panelArchivo.add(bAbrir, null);
    panelArchivo.add(bCerrar, null);
    panelArchivo.add(bGuardar, null);
    panelArchivo.add(bGuardarTodo, null);
    panelArchivo.add(bImprimir, null);
    jToolBar.add(panelEditar, null);
    panelEditar.add(bDeshacer, null);
    panelEditar.add(bRehacer, null);
    panelEditar.add(bCopiar, null);
    panelEditar.add(bPegar, null);
    panelEditar.add(bCortar, null);
    jToolBar.add(panelCompilar, null);
    panelCompilar.add(bCompilar);
    panelCompilar.add(bTraducir);
    jToolBar.add(panelMaqVirtual, null);
    panelMaqVirtual.add(bStart, null);
    panelMaqVirtual.add(bStop, null);
    jToolBar.add(panelHerramientas, null);
    panelHerramientas.add(bPared,null);
    panelHerramientas.add(bObstaculo,null);
    panelHerramientas.add(bLuz,null);
    panelHerramientas.add(bTemp, null);
    jToolBar.add(panelLimpiar, null);
    panelLimpiar.add(bUndo, null);
    panelLimpiar.add(bRedo, null);
    panelLimpiar.add(bBorrar, null);
    panelLimpiar.add(bBorrar, null);
    panelLimpiar.add(bBorrarPared, null);
    panelLimpiar.add(bLimpiar, null);
    jToolBar.add(panelAyuda, null);
    panelAyuda.add(bAyuda);
// Menú - Archivo
    jMenuFile.add(jMenuFileNuevo);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileAbrir);
    jMenuFile.add(jMenuFileCerrar);
    jMenuFile.add(jMenuFileCerrarTodo);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileGuardar);
    jMenuFile.add(jMenuFileGuardarComo);
    jMenuFile.add(jMenuFileGuardarTodo);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileImprimir);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
// Menú - Editar
    jMenuEditar.add(jMenuEditarDesh);
    jMenuEditar.add(jMenuEditarReh);
    jMenuEditar.addSeparator();
    jMenuEditar.add(jMenuEditarCortar);
    jMenuEditar.add(jMenuEditarCopiar);
    jMenuEditar.add(jMenuEditarPegar);
    jMenuEditar.add(jMenuEditarEliminar);
    jMenuEditar.addSeparator();
    jMenuEditar.add(jMenuEditarSelTodo);
// Menú - Ver
// Menú - Compilar
    jMenuComp.add(jMenuCompCompilar);
    jMenuComp.add(jMenuTraduceTraducir);
// Menú - Máquina Virtual
    jMenuMV.add(jMenuNuevoMundo);
    jMenuMV.add(jMenuAbrirMundo);
    jMenuMV.add(jMenuGuardar);
    jMenuMV.add(jMenuGuardarMundo);
    jMenuMV.addSeparator();
// Menú - Ayuda...
    jMenuHelp.add(jMenuHelpAyuda);
    jMenuHelp.add(jMenuHelpAbout);
// Agregar en la Barra de Menú
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuEditar);
    jMenuBar1.add(jMenuVer);
    jMenuBar1.add(jMenuComp);
    jMenuBar1.add(jMenuMV);
    jMenuBar1.add(jMenuHelp);
    editor = new Editor();
// Monta todo los componentes: Menú, ventanas, barra de tareas, etc, sobre el contentPane
    this.setJMenuBar(jMenuBar1);
/*    contentPane.add(jToolBar, BorderLayout.NORTH);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(jPanel1, BorderLayout.CENTER);
    contentPane.add(jPanel2, BorderLayout.BEFORE_LINE_BEGINS);*/
    contentPane.add(jToolBar, BorderLayout.NORTH);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(escritorio, BorderLayout.CENTER);
    escritorio.add(jSplitPane1,  BorderLayout.CENTER);
    statusBar.add(status1, null);
    status1.add(text1, null);
    statusBar.add(status2, null);
    status2.add(text2, null);
    statusBar.add(status3, null);
    status3.add(text3, null);
    editor.setVisible(true);
//    viewMsg.setVisible(false);
    scroll.setVisible(false);
//    jSplitPane1.add(viewMsg, JSplitPane.RIGHT);
    jSplitPane1.add(scroll, JSplitPane.RIGHT);
    jSplitPane1.add(jSplitPane2, JSplitPane.LEFT);
    scroll.setViewportView(viewMsg);
    jMenuVer.add(jCheckBoxVerErrores);
    jSplitPane2.add(editor, JSplitPane.TOP);
    jMenuMV.add(jMenuMVStart);
    jMenuMV.add(jMenuMVStop);
    jMenuMV.addSeparator();
    jMenuMV.add(jMenuMVCerrar);
    jMenuLanguage.setText(m.getString("Pantalla.jMenuLanguage.text")); //$NON-NLS-1$
    
    jMenuBar1.add(jMenuLanguage);

    
    jMenuLangSpanish.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		System.out.println("Español");
    	    AnalizadorLexico.idioma = 1;
    	    
    	    m.BUNDLE_NAME = "IDE.messages_es_MX";
    	    cambiarIdioma();
    	    //jMenuEditar.setText("EDITAR");
    	    //this = new Pantalla();
    	}
    });
    
    
    jMenuLangSpanish.setText(m.getString("Pantalla.jMenuLangSpanish.text")); //$NON-NLS-1$
    
    jMenuLanguage.add(jMenuLangSpanish);
    
    jMenuLangEnglish.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		 System.out.println("ENGLISH");
    		  AnalizadorLexico.idioma = 2;
    		  //Messages.BUNDLE_NAME = "IDE.messages_en_US";
    		  //Messages m = new Messages();
    		  m.BUNDLE_NAME = "IDE.messages_en_US";
    		  cambiarIdioma();
    		  
    		  
    	}
    });
    jMenuLangEnglish.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuLangEnglish.setText(m.getString("Pantalla.jMenuLangEnglish.text")); //$NON-NLS-1$
    
    jMenuLanguage.add(jMenuLangEnglish);
//    jSplitPane2.add(simulador, JSplitPane.BOTTOM);
    jSplitPane1.setDividerLocation(400);
    validar(0);
    editor.initPantalla(this);
  }
  
  void cambiarIdioma(){	    
	    bStart.setToolTipText(m.getString("Pantalla.bStart.toolTipText"));	    
	    bStart.setText(m.getString("Pantalla.bStart.text")); //$NON-NLS-1$	    
	    bAbrir.setText(m.getString("Pantalla.bAbrir.text")); //$NON-NLS-1$;
	    bAbrir.setToolTipText(m.getString("Pantalla.bAbrir.toolTipText"));
	    bAyuda.setText(m.getString("Pantalla.bAyuda.text")); //$NON-NLS-1$
	    bAyuda.setToolTipText(m.getString("Pantalla.bAyuda.text")); //$NON-NLS-1$
	    bBorrar.setText(m.getString("Pantalla.bBorrar.text")); //$NON-NLS-1$
	    bBorrar.setToolTipText(m.getString("Pantalla.bBorrar.toolTipText"));
	    bBorrarPared.setText(m.getString("Pantalla.bBorrarPared.text")); //$NON-NLS-1$
	    bBorrarPared.setToolTipText(m.getString("Pantalla.bBorrarPared.toolTipText"));
	    bCerrar.setText(m.getString("Pantalla.bCerrar.text")); //$NON-NLS-1$
	    bCerrar.setToolTipText(m.getString("Pantalla.bCerrar.toolTipText"));
	    bCompilar.setText(m.getString("Pantalla.bCompilar.text")); //$NON-NLS-1$
	    bCompilar.setToolTipText(m.getString("Pantalla.bCompilar.toolTipText"));
	    bCopiar.setText(m.getString("Pantalla.bCopiar.text")); //$NON-NLS-1$
	    bCopiar.setToolTipText(m.getString("Pantalla.bCopiar.toolTipText"));
	    bCortar.setText(m.getString("Pantalla.bCortar.text")); //$NON-NLS-1$
	    bCortar.setToolTipText(m.getString("Pantalla.bCortar.toolTipText"));
	    bDeshacer.setText(m.getString("Pantalla.bDeshacer.text")); //$NON-NLS-1$
	    bDeshacer.setToolTipText(m.getString("Pantalla.bDeshacer.toolTipText"));
	    bGuardar.setText(m.getString("Pantalla.bGuardar.text")); //$NON-NLS-1$
	    bGuardar.setToolTipText(m.getString("Pantalla.bGuardar.toolTipText"));
	    bGuardarTodo.setText(m.getString("Pantalla.bGuardarTodo.text")); //$NON-NLS-1$
	    bGuardarTodo.setToolTipText(m.getString("Pantalla.bGuardarTodo.toolTipText"));
	    bImprimir.setText(m.getString("Pantalla.bImprimir.text")); //$NON-NLS-1$
	    bImprimir.setToolTipText(m.getString("Pantalla.bImprimir.toolTipText"));
	    bLimpiar.setText(m.getString("Pantalla.bLimpiar.text")); //$NON-NLS-1$
	    bLimpiar.setToolTipText(m.getString("Pantalla.bLimpiar.toolTipText"));
	    bLuz.setText(m.getString("Pantalla.bLuz.text")); //$NON-NLS-1$
	    bLuz.setToolTipText(m.getString("Pantalla.bLuz.toolTipText")); //$NON-NLS-1$
	    bNuevo.setText(m.getString("Pantalla.bNuevo.text")); //$NON-NLS-1$
	    bNuevo.setToolTipText(m.getString("Pantalla.bNuevo.toolTipText"));
	    bObstaculo.setText(m.getString("Pantalla.bObstaculo.text")); //$NON-NLS-1$
	    bObstaculo.setToolTipText(m.getString("Pantalla.bObstaculo.toolTipText"));
	    bPared.setText(m.getString("Pantalla.bPared.text")); //$NON-NLS-1$
	    bPared.setToolTipText(m.getString("Pantalla.bPared.toolTipText"));
	    bPegar.setText(m.getString("Pantalla.bPegar.text")); //$NON-NLS-1$
	    bPegar.setToolTipText(m.getString("Pantalla.bPegar.toolTipText"));
	    bRedo.setText(m.getString("Pantalla.bRedo.text")); //$NON-NLS-1$
	    bRedo.setToolTipText(m.getString("Pantalla.bRedo.toolTipText"));
	    bRehacer.setText(m.getString("Pantalla.bRehacer.text")); //$NON-NLS-1$
	    bRehacer.setToolTipText(m.getString("Pantalla.bRehacer.toolTipText"));
	    bStop.setText(m.getString("Pantalla.bStop.text")); //$NON-NLS-1$
	    bStop.setToolTipText(m.getString("Pantalla.bStop.toolTipText"));
	    bTemp.setText(m.getString("Pantalla.bTemp.text")); //$NON-NLS-1$
	    bTemp.setToolTipText(m.getString("Pantalla.bTemp.toolTipText"));
	    bUndo.setText(m.getString("Pantalla.bUndo.text")); //$NON-NLS-1$
	    bUndo.setToolTipText(m.getString("Pantalla.bUndo.toolTipText"));
	    jCheckBoxVerErrores.setText(m.getString("Pantalla.jCheckBoxVerErrores.text")); //$NON-NLS-1$
	    jMenuAbrirMundo.setText(m.getString("Pantalla.jMenuAbrirMundo.text")); //$NON-NLS-1$
	    jMenuComp.setText(m.getString("Pantalla.jMenuComp.text")); //$NON-NLS-1$
	    jMenuCompCompilar.setText(m.getString("Pantalla.jMenuCompCompilar.text")); //$NON-NLS-1$
	    jMenuEditar.setText(m.getString("Pantalla.jMenuEditar.text")); //$NON-NLS-1$
	    jMenuEditarCopiar.setText(m.getString("Pantalla.jMenuEditarCopiar.text")); //$NON-NLS-1$
	    jMenuEditarCortar.setText(m.getString("Pantalla.jMenuEditarCortar.text")); //$NON-NLS-1$
	    jMenuEditarDesh.setText(m.getString("Pantalla.jMenuEditarDesh.text")); //$NON-NLS-1$
	    jMenuEditarEliminar.setText(m.getString("Pantalla.jMenuEditarEliminar.text")); //$NON-NLS-1$
	    jMenuEditarPegar.setText(m.getString("Pantalla.jMenuEditarPegar.text")); //$NON-NLS-1$
	    jMenuEditarReh.setText(m.getString("Pantalla.jMenuEditarReh.text")); //$NON-NLS-1$
	    jMenuEditarSelTodo.setText(m.getString("Pantalla.jMenuEditarSelTodo.text")); //$NON-NLS-1$
	    jMenuFile.setText(m.getString("Pantalla.jMenuFile.text")); //$NON-NLS-1$
	    jMenuFileAbrir.setText(m.getString("Pantalla.jMenuFileAbrir.text")); //$NON-NLS-1$
	    jMenuFileCerrar.setText(m.getString("Pantalla.jMenuFileCerrar.text")); //$NON-NLS-1$
	    jMenuFileCerrarTodo.setText(m.getString("Pantalla.jMenuFileCerrarTodo.text")); //$NON-NLS-1$
	    jMenuFileExit.setText(m.getString("Pantalla.jMenuFileExit.text")); //$NON-NLS-1$
	    jMenuFileGuardar.setText(m.getString("Pantalla.jMenuFileGuardar.text")); //$NON-NLS-1$
	    jMenuFileGuardarComo.setText(m.getString("Pantalla.jMenuFileGuardarComo.text")); //$NON-NLS-1$
	    jMenuFileGuardarTodo.setText(m.getString("Pantalla.jMenuFileGuardarTodo.text")); //$NON-NLS-1$
	    jMenuFileImprimir.setText(m.getString("Pantalla.jMenuFileImprimir.text")); //$NON-NLS-1$
	    jMenuFileNuevo.setText(m.getString("Pantalla.jMenuFileNuevo.text")); //$NON-NLS-1$
	    jMenuGuardar.setText(m.getString("Pantalla.jMenuGuardar.text")); //$NON-NLS-1$
	    jMenuGuardarMundo.setText(m.getString("Pantalla.jMenuGuardarMundo.text")); //$NON-NLS-1$
	    jMenuHelp.setText(m.getString("Pantalla.jMenuHelp.text")); //$NON-NLS-1$
	    jMenuHelpAbout.setText(m.getString("Pantalla.jMenuHelpAbout.text")); //$NON-NLS-1$
	    jMenuHelpAyuda.setText(m.getString("Pantalla.jMenuHelpAyuda.text")); //$NON-NLS-1$
	    jMenuMV.setText(m.getString("Pantalla.jMenuMV.text")); //$NON-NLS-1$
	    jMenuMVCerrar.setText(m.getString("Pantalla.jMenuMVCerrar.text")); //$NON-NLS-1$
	    jMenuMVStart.setText(m.getString("Pantalla.jMenuMVStart.text")); //$NON-NLS-1$
	    jMenuMVStop.setText(m.getString("Pantalla.jMenuMVStop.text")); //$NON-NLS-1$
	    jMenuNuevoMundo.setText(m.getString("Pantalla.jMenuNuevoMundo.text")); //$NON-NLS-1$
	    jMenuVer.setText(m.getString("Pantalla.jMenuVer.text")); //$NON-NLS-1$
	    jMenuTraduceTraducir.setText(m.getString("Pantalla.jMenuTraduceTraducir.text")); //$NON-NLS-1$
	    bTraducir.setToolTipText(m.getString("Pantalla.bTraducir.toolTipText"));
	    bAyuda.setToolTipText(m.getString("Pantalla.bAyuda.toolTipText"));
	    jMenuLanguage.setText(m.getString("Pantalla.jMenuLanguage.text")); //$NON-NLS-1$
	    jMenuLangSpanish.setText(m.getString("Pantalla.jMenuLangSpanish.text")); //$NON-NLS-1$
	    jMenuLangEnglish.setText(m.getString("Pantalla.jMenuLangEnglish.text")); //$NON-NLS-1$
  }
  //File | Exit action performed
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    editor.cerrarTodo();
    if(editor.getCountProgramas()==0){
      System.exit(0);
    }
  }
  //Help | About action performed
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    AboutBox dlg = new AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  
  //Overridden so we can exit when window is closed
/*  protected void processWindowEvent(WindowEvent e) {
    if(editor.getCountProgramas() == 0){
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        jMenuFileExit_actionPerformed(null);
      }
    }
  }
*/
  void jMenuFileNuevo_actionPerformed(ActionEvent e) {
  if(!editor.isShowing()){
    editor.setVisible(true);
    jSplitPane1.add(editor, JSplitPane.LEFT);
//    setStatusBarInsert();
  }
  statusBar.setVisible(true);
  editor.nuevoPrograma();
  jSplitPane1.setDividerLocation(400);
  validar(1);
  }

  void jMenuFileAbrir_actionPerformed(ActionEvent e) {
  if(!editor.isShowing()){
    editor.setVisible(true);
    jSplitPane1.add(editor, JSplitPane.LEFT);
//    setStatusBarInsert();
  }
  statusBar.setVisible(true);
  editor.abrirPrograma();
  validar(1);
  }

  void jMenuFileCerrar_actionPerformed(ActionEvent e) {
  editor.cerrarPrograma();
  validar(5);
  }

// Aparece/Desaparece la ventana de mensajes de error según sea el caso
  void jCheckBoxVerErrores_actionPerformed(ActionEvent e) {
//  viewMsg.setVisible(jCheckBoxVerErrores.isSelected());
  scroll.setVisible(jCheckBoxVerErrores.isSelected());
  jSplitPane1.setDividerLocation(400);
  }

  private void guardarProg(){
  editor.guardarPrograma();
  validar(2);
  }

  void jMenuFileGuardar_actionPerformed(ActionEvent e) {
  guardarProg();
  }

  void jMenuFileGuardarComo_actionPerformed(ActionEvent e) {
  editor.guardarComoPrograma();
  }

  void jMenuFileCerrarTodo_actionPerformed(ActionEvent e) {
  editor.cerrarTodo();
//  validar(5);
//  validar(16); // Cierra la ventana de Errores y Warnings
  }

  void jMenuFileGuardarTodo_actionPerformed(ActionEvent e) {
  editor.guardarTodo();
  validar(2);
  }

  void jMenuEditarPegar_actionPerformed(ActionEvent e) {
  editor.pegar();
  }

  void jMenuEditarCopiar_actionPerformed(ActionEvent e) {
  editor.copiar();
  validar(12);
  }

  void jMenuEditarCortar_actionPerformed(ActionEvent e) {
  editor.cortar();
  validar(11);
  validar(12);
  }

  void jMenuEditarSelTodo_actionPerformed(ActionEvent e) {
  editor.seleccionarTodo();
  }

  void jMenuEditarEliminar_actionPerformed(ActionEvent e) {
  editor.eliminar();
  }

  private void botonExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonExited
  javax.swing.JButton temp = (javax.swing.JButton) evt.getComponent();
  if(temp.isEnabled()){
      temp.setBorder(BorderFactory.createRaisedBevelBorder());
      temp.setBorderPainted(false);
  }
  }//GEN-LAST:event_botonExited

  private void botonEnter(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEnter
  javax.swing.JButton temp = (javax.swing.JButton) evt.getComponent();
  if(temp.isEnabled()){
        temp.setBorder(BorderFactory.createRaisedBevelBorder());
        temp.setBorderPainted(true);
  }
  }///GEN-LAST:event_botonEnter

  private void botonPressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonExited
  javax.swing.JButton temp = (javax.swing.JButton) evt.getComponent();
  if(temp.isEnabled())  temp.setBorder(BorderFactory.createLoweredBevelBorder());
  }///GEN-LAST:event_botonExited

  private void botonReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonExited
  javax.swing.JButton temp = (javax.swing.JButton) evt.getComponent();
  if(temp.isEnabled())  temp.setBorder(BorderFactory.createRaisedBevelBorder());
  }

  void jMenuEditarDesh_actionPerformed(ActionEvent e) {
  editor.deshacer();
  }

  void jMenuEditarReh_actionPerformed(ActionEvent e) {
  editor.rehacer();
  }

  void jMenuFileImprimir_actionPerformed(ActionEvent e) {
  editor.imprimirPrograma();
  }

  public int countProgramas(){
    return editor.countProgramas();
  }

  public void validar(int fase){
  switch(fase){
    case 0:// VALORES INICIALES DE VALIDACIÓN - NO SOBRE BOTÓN
            Enabled(NUEVO, true);
            Enabled(CERRAR, false);
            Enabled(CERRARTODO, false);
            Enabled(GUARDAR, false);
            Enabled(GUARDARCOMO, false);
            Enabled(GUARDARTODO, false);
            Enabled(IMPRIMIR, false);
            Enabled(DESHACER, false);
            Enabled(REHACER, false);
            Enabled(COPIAR, false);
            Enabled(PEGAR, false);
            Enabled(CORTAR, false);
            Enabled(ELIMINAR, false);
            Enabled(SELECCIONARTODO, false);
            Enabled(VERERRORES, false);
            Enabled(VIEWERRORES, false);
            Enabled(EJECUTAR, false);
            Enabled(SIMULAR, false);
            Enabled(TRADUCIR, false);
            Enabled(MUNDO, false);
            Enabled(OBJETOS, false);
            Enabled(DESHGRAFICO, false);
            Enabled(REHGRAFICO, false);
            break;
    case 1:// VALORES DE UN NUEVO PROGRAMA DE VALIDACIÓN - NO SOBRE BOTÓN
            Enabled(CERRAR, true);
            Enabled(CERRARTODO, true);
            Enabled(GUARDAR, false);
            Enabled(GUARDARCOMO, true);
            Enabled(IMPRIMIR, true);
            Enabled(SELECCIONARTODO, true);
            Enabled(VERERRORES, true);
            Enabled(EJECUTAR, true);
//            Enabled(SIMULAR, false);
            break;
    case 2:// VALORES CUANDO EXISTEN CAMBIOS Y CLICK SOBRE BOTÓN GUARDAR
            if(!editor.getModificado()){
                Enabled(GUARDAR, false);
                bGuardar.setBorderPainted(false);
                if(!editor.getGuardarTodo()){
                    Enabled(GUARDARTODO, false);
                    bGuardarTodo.setBorderPainted(false);
                }
            }
            break;
    case 3:// VALORES CUANDO MODIFICADO = TRUE - NO SOBRE BOTÓN
            Enabled(GUARDAR, true);
            Enabled(GUARDARTODO, true);
            Enabled(SIMULAR, false);
            break;
    case 4:// VALORES CUANDO EXISTEN CAMBIOS - NO SOBRE BOTÓN
            if(editor.getModificado()){
                Enabled(GUARDAR, true);
            }
            else{// VALORES CUANDO LOS CAMBIOS SE GUARDAN
                Enabled(GUARDAR, false);
            }
            break;
    case 5:// VALORES CUANDO SE CIERRAN PROGRAMAS - CLICK SOBRE BOTÓN CERRAR
            if(editor.getCountProgramas()==0){
                Enabled(CERRAR, false);
                bCerrar.setBorderPainted(false);
                Enabled(CERRARTODO, false);
                Enabled(GUARDAR, false);
                Enabled(GUARDARTODO, false);
                Enabled(GUARDARCOMO, false);
                Enabled(IMPRIMIR, false);
                Enabled(DESHACER, false);
                Enabled(REHACER, false);
                Enabled(COPIAR, false);
                Enabled(PEGAR, false);
                Enabled(CORTAR, false);
                Enabled(ELIMINAR, false);
                Enabled(SELECCIONARTODO, false);
                Enabled(EJECUTAR, false);
                Enabled(STATUSBAR, false);
                Enabled(SIMULAR, false);
                Enabled(TRADUCIR, false);
                Enabled(MUNDO, false);
                Enabled(OBJETOS, false);
                Enabled(DESHGRAFICO, false);
                Enabled(REHGRAFICO, false);
            }
            OVERTYPEMODE = false;
            break;
    case 6:// VALORES DE MENU EDITAR DESHACER - TRUE
            Enabled(DESHACER, true);
            break;
    case 7:// VALORES DE MENU EDITAR DESHACER - FALSE
            Enabled(DESHACER, false);
            bDeshacer.setBorderPainted(false);
            break;
    case 8:// VALORES DE MENU EDITAR REHACER - TRUE
            Enabled(REHACER, true);
            break;
    case 9:// VALORES DE MENU EDITAR RESHACER - FALSE
            Enabled(REHACER, false);
            bRehacer.setBorderPainted(false);
            break;
    case 10:// VALORES DE MENU EDITAR COPIAR - CORTAR
            Enabled(COPIAR, true);
            Enabled(CORTAR, true);
            Enabled(ELIMINAR, true);
            break;
    case 11:// VALORES DE MENU EDITAR COPIAR - CORTAR
            Enabled(COPIAR, false);
            bCopiar.setBorderPainted(false);
            Enabled(CORTAR, false);
            bCortar.setBorderPainted(false);
            Enabled(ELIMINAR, false);
            break;
    case 12:// VALORES DE MENU EDITAR COPIAR - PEGAR - CORTAR
            Enabled(PEGAR, true);
            break;
    case 13:// VALORES DE MENU EDITAR COPIAR - PEGAR - CORTAR
            Enabled(PEGAR, false);
            bPegar.setBorderPainted(false);
            break;
    case 14:// VALORES CUANDO SE CARGA POR PRIMERA VEZ LA SIMULACIÓN DE UN PROGRAMA EN AGE
            Enabled(SIMULAR, true);
            Enabled(MUNDO, true);
            Enabled(OBJETOS, true);
            Enabled(DESHGRAFICO, false);
            Enabled(REHGRAFICO, false);
            break;
    case 15:// VALORES QUE CIERRAN LA POSIBILIDAD DE SIMULAR -- O INICIA EL PROGRAMA InAGE
            Enabled(SIMULAR, false);
            Enabled(MUNDO, false);
            Enabled(OBJETOS, false);
            Enabled(DESHGRAFICO, false);
            Enabled(REHGRAFICO, false);
            break;
    case 16:
            Enabled(VIEWERRORES, false);
            break;
    case 17:
            Enabled(VIEWERRORES, true);
            break;
    case 18:
            viewViewMsg(2);
/*            viewMsg.setText("");
            if(editor.getCountWarning()!=0) setWarnings();
            if(editor.getCountErrores()!=0) setErrores();
*/
            break;
    case 19:// DESHACER GRAFICO
            Enabled(DESHGRAFICO, true);
            break;
    case 20:
            Enabled(DESHGRAFICO, false);
            break;
    case 21:// REHACER GRAFICO
            Enabled(REHGRAFICO, true);
            break;
    case 22:
            Enabled(REHGRAFICO, false);
            break;
    case 25:
    	Enabled(TRADUCIR,true);
  	}
  }


  private void Enabled(int boton, boolean valor){
  switch(boton){
    case NUEVO: // NUEVO
            bNuevo.setEnabled(valor);
            jMenuFileNuevo.setEnabled(valor);
            break;
    case CERRAR: // CERRAR
            bCerrar.setEnabled(valor);
            jMenuFileCerrar.setEnabled(valor);
            break;
    case CERRARTODO: // CERRAR
            jMenuFileCerrarTodo.setEnabled(valor);
            break;
    case GUARDAR: // GUARDAR
            bGuardar.setEnabled(valor);
            jMenuFileGuardar.setEnabled(valor);
            break;
    case GUARDARTODO: // GUARDAR TODO
            bGuardarTodo.setEnabled(valor);
            jMenuFileGuardarTodo.setEnabled(valor);
            break;
    case GUARDARCOMO: // GUARDAR COMO...
            jMenuFileGuardarComo.setEnabled(valor);
            break;
    case IMPRIMIR: // GUARDAR TODO
            bImprimir.setEnabled(valor);
            jMenuFileImprimir.setEnabled(valor);
            break;
    case DESHACER: // DESHACER
            bDeshacer.setEnabled(valor);
            jMenuEditarDesh.setEnabled(valor);
            break;
    case REHACER: // REHACER
            bRehacer.setEnabled(valor);
            jMenuEditarReh.setEnabled(valor);
            break;
    case COPIAR: // COPIAR
            bCopiar.setEnabled(valor);
            jMenuEditarCopiar.setEnabled(valor);
            break;
    case PEGAR: // PEGAR
            bPegar.setEnabled(valor);
            jMenuEditarPegar.setEnabled(valor);
            break;
    case CORTAR: // CORTAR
            bCortar.setEnabled(valor);
            jMenuEditarCortar.setEnabled(valor);
            break;
    case ELIMINAR: // CORTAR
            jMenuEditarEliminar.setEnabled(valor);
            break;
    case SELECCIONARTODO:
            jMenuEditarSelTodo.setEnabled(valor);
            break;
    case VERERRORES:
            jCheckBoxVerErrores.setEnabled(valor);
            break;
    case VIEWERRORES:
            scroll.setVisible(valor);
            jCheckBoxVerErrores.setSelected(valor);
            if(valor)
              jSplitPane1.setDividerLocation(400);
            break;
    case EJECUTAR:
            bCompilar.setEnabled(valor);
            jMenuCompCompilar.setEnabled(valor);
            break;
    case SIMULAR:
            jMenuMVStart.setEnabled(valor);
            bStart.setEnabled(valor);
            jMenuMVStop.setEnabled(valor);
            bStop.setEnabled(valor);
            break;
    case TRADUCIR:
        bTraducir.setEnabled(valor);
        jMenuTraduceTraducir.setEnabled(valor);
        break;
    case MUNDO:
            jMenuNuevoMundo.setEnabled(valor);
            jMenuAbrirMundo.setEnabled(valor);
            jMenuGuardarMundo.setEnabled(valor);
            jMenuMVCerrar.setEnabled(valor);
            break;
    case OBJETOS:
            bPared.setEnabled(valor);
            bObstaculo.setEnabled(valor);
            bLuz.setEnabled(valor);
            bTemp.setEnabled(valor);
            bBorrar.setEnabled(valor);
            bBorrarPared.setEnabled(valor);
            bLimpiar.setEnabled(valor);
            break;
    case REHGRAFICO:
            bRedo.setEnabled(valor);
            break;
    case DESHGRAFICO:
            bUndo.setEnabled(valor);
            break;
    case AYUDA:// AYUDA
            bAyuda.setEnabled(valor);
            jMenuHelpAyuda.setEnabled(valor);
            break;
    case STATUSBAR:// AYUDA
                  statusBar.setVisible(valor);
                  text1.setText("");
                  text2.setText("");
                  text3.setText("");
                  break;
  }
  }

  void this_windowClosing(WindowEvent e) {
    editor.cerrarTodo();
    System.exit(0);
  }
  
  void jMenuTraduceTraducir_actionPerformed(ActionEvent e) {
        	try {
				traductor.traduce();
				
				JOptionPane.showMessageDialog(null, "La traducción se realizó de manera correcta", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  }

  void jMenuCompCompilar_actionPerformed(ActionEvent e) {
    guardarProg();
    if(editor.isRuta()){
        viewMsg.setText("");
        if(editor.ejecutar()==0){ // si al compilar no existen errores
            
        	/* *************************************
             * Sección Máquina Virtual : Habilita, despliega, carga sociedad
             */
        	
        	
            validar(14); // habilita la opción de MV
            if(simulador==null) initEspacioSimular();
            simulador.abrirNuevoArcAge(editor.getNombrePrograma(),editor.getDirectorio());
            /* ************************************* */
            
            /**************************
             * Sección Código objeto Lejos
             * 
             */
        	validar(25);
        	traductor = new Traductor(editor.getNombrePrograma(),editor.getDirectorio());
        	
        	
        	
        	
            if(editor.getCountWarning()!=0){
                validar(17);// muestra la ventana de errores y warnings
                //setWarnings();
                viewViewMsg(1);
            }
            if(AnalizadorLexico.idioma==1)
            	JOptionPane.showMessageDialog(null, "El proceso de compilación fué exitoso", "Información",
                                      JOptionPane.INFORMATION_MESSAGE);
            else{JOptionPane.showMessageDialog(null, "the compilation process was successful", "Information",
                    JOptionPane.INFORMATION_MESSAGE);}
        }
        else{
            validar(15); // deshabilita la opción de MV
            validar(17);// muestra la ventana de errores y warnings
            viewViewMsg(2);
            /*
            if(editor.getCountWarning()!=0) setWarnings();
            if(editor.getCountErrores()!=0) setErrores();
            */
        }
    }
  }

  public int isPR(String palabra){
    return colorPR.buscar(palabra, palabra.length() );
  }

  public void setCleanViewMsg(){
    viewMsg.setText("");
  }

  private void viewViewMsg(int op){
  try{
    int countW = editor.getCountWarning();
    int countE = 0;
    String temp = "";
    viewMsg.setText("");
    temp = setWarnings(temp, countW);
    if(op > 1){ // Si va a desplegar también los Errores
      countE = editor.getCountErrores();
      temp = setErrores(temp, countW, countE);
    }
    temp = temp.substring(0,temp.length()-1);
    viewMsg.setText(temp);
    for (int i = 0; i < countW; i++) {
      doc.insertString(iWarning[i][0], "W", styleWarning); // Coloca la imagen
    }
    for (int i = 0; i < countE; i++) {
      doc.insertString(iError[i][0], "E", styleError); // Coloca la imagen
    }
    viewMsg.setCaretPosition(0); // Muestra los primeros Errores/Warnings, el scroll hasta arriba
  } catch (BadLocationException ee)  {}
   catch(java.lang.StringIndexOutOfBoundsException er) {}

  }

  private String setWarnings(String cadTemp, int count){
//    int indWarning[][] = new int[count+1][2]; // indice [imagen][renglon]
    iWarning[0][0] = 0; // Ubicación de la imagen de error/warning
//    iWarning[0][1] = 0; // Ubicación del renglón en donde se encuentra el error/warning
    for (int i = 1; i < count + 1; i++) { // numEW es la cantidad de errores/warnings que se conocen
      cadTemp = cadTemp + editor.getWarning(i) + "\n";
      iWarning[i][0] = cadTemp.length() + i; // Ubicación de la imagen de error/warning
      iWarning[i-1][1] = editor.getRowWarning(i); // Ubicación del renglón en donde se encuentra el error/warning
    }
    return cadTemp;
}


  private String setErrores(String cadTemp, int countW, int countE){
//   int indError[][] = new int[count+1][2]; // indice [imagen][renglon]
   iError[0][0] = cadTemp.length() + countW; // Ubicación de la imagen de error/warning
//   iError[0][1] = 0; // Ubicación del renglón en donde se encuentra el error/warning
   for (int i = 1; i < countE + 1; i++) { // numEW es la cantidad de errores/warnings que se conocen
     cadTemp = cadTemp + editor.getError(i) + "\n";
     iError[i][0] = cadTemp.length() + i + countW; // Ubicación de la imagen de error/warning
     iError[i-1][1] = editor.getRowError(i); // Ubicación del renglón en donde se encuentra el error/warning
   }
   return cadTemp;
  }

  public void setStatusBarArchivo(String cadena){
    // La posición 1 corresponde al nombre del archivo que se edita actualmente
      text1.setText(cadena);
  }

  public void setStatusBarInsert(boolean valor){
    // La posición 3 corresponde a la lìnea y columna que el cursor ocupa en el programa
    OVERTYPEMODE = valor;
    if(OVERTYPEMODE){
    	if(AnalizadorLexico.idioma==1)
    		text2.setText("SOBREESCRIBIR");
    	else{text2.setText("OVERWRITE");}    	
    }
    else{
    	if(AnalizadorLexico.idioma==1)
    		text2.setText("INSERTAR");
    	else{text2.setText("INSERT");}
    }
  }

  public boolean isOverTypeMode(){
    return OVERTYPEMODE;
  }

  public void setStatusBarLinea(String cadena){
    // La posición 3 corresponde a la lìnea y columna que el cursor ocupa en el programa
      text3.setText(cadena);
  }

  void viewMsg_mouseClicked(MouseEvent e) {
    /*
     Las siguientes instrucciones colocan las coordenadas (renglon, columna) en las que
     se encuentra el programador.
     */
    int posCaret = viewMsg.getCaretPosition();
    int currentLine = 1;
    try{
      Rectangle current = viewMsg.modelToView( posCaret );
      if (current == null)
        throw new BadLocationException("null Rectangle", posCaret);
      Rectangle one = viewMsg.modelToView(0);
      Rectangle two = viewMsg.modelToView(1);
      int width = two.x - one.x;
      if (width == 0)
        throw new BadLocationException("Empty document", 0);
      currentLine = ( (current.y - one.y) / current.height ) + 1;
    }
    catch(BadLocationException ble) {}
    finally{
      int countW = editor.getCountWarning();
      int countE = editor.getCountErrores();
      if(currentLine <= countW  && currentLine >0)
        editor.setCaretCodigo(iWarning[currentLine-1][1]); // iWarning[renglon en viewMsg][renglón en el programa]
      else
        if(currentLine - countW <= countE && currentLine - countW > 0)
          editor.setCaretCodigo(iError[currentLine-countW-1][1]); // iError[renglon en viewMsg][renglón en el programa]

    }
  }

  void initEspacioSimular(){
    scroll.setVisible(false);
//    simulador = new Simulador(jSplitPane2.getWidth()-450, jSplitPane2.getHeight());
    simulador = new Simulador();
    jSplitPane2.setDividerLocation(450);
    jSplitPane2.add(simulador, JSplitPane.BOTTOM);
    simulador.setVisible(true);
  }

  void jMenuAbrirMundo_actionPerformed(ActionEvent e) {
    simulador.abrirMundo();
  }

  void jMenuGuardarMundo_actionPerformed(ActionEvent e) {
    simulador.guardarMundoComo();
  }

  void jMenuNuevoMundo_actionPerformed(ActionEvent e) {
    simulador.nuevoMundo();
  }

  void bStart_actionPerformed(ActionEvent e) {
    simulador.startSociedadAgentes();
  }

  void bStop_actionPerformed(ActionEvent e) {
    simulador.stopSociedadAgentes();
  }

  void bPared_actionPerformed(ActionEvent e) {
    simulador.setPared();
    validar(19);
  }

  void bObstaculo_actionPerformed(ActionEvent e) {
    simulador.setObstaculo();
    validar(19);
  }

  void bLuz_actionPerformed(ActionEvent e) {
    simulador.setLuz();
    validar(19);
  }

  void bTemp_actionPerformed(ActionEvent e) {
    simulador.setTemperatura();
    validar(19);
  }

  void jMenuMVCerrar_actionPerformed(ActionEvent e) {
//    scroll.setVisible(false);
    jSplitPane2.remove(simulador);
    simulador.finalize();
    simulador = null;
  }

  void bBorrar_actionPerformed(ActionEvent e) {
    simulador.quitarObjeto();
    validar(19);
  }

  void bBorrarPared_actionPerformed(ActionEvent e) {
    simulador.quitarPared();
    validar(19);
  }

  void bUndo_actionPerformed(ActionEvent e) {
    int i=0;
    i = simulador.undo();
    validar(21);
    if(i==0) validar(20);

  }

  void bRedo_actionPerformed(ActionEvent e) {
    int i=0;
    i = simulador.redo();
    validar(19);
    if(i==0) validar(22);
  }

  void jMenuGuardar_actionPerformed(ActionEvent e) {
    simulador.guardarMundo();
  }



}// FIN JPANTALLA
