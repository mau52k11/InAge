package IDE;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Lejos.Traductor;

import java.awt.event.*;
import java.io.*;


public class Simulador extends javax.swing.JPanel {

  static final int NORTE = 1;
  static final int SUR = 2;
  static final int ESTE = 3;
  static final int OESTE = 4;

  int PARED = 1;
  int OBSTACULO = 2;
  int LUZ = 3;
  int TEMPERATURA = 4;
  int AGENTE = 5;
  int ELIMINAR_CELDA = 6;
  int ELIMINAR_PARED = 7;

  ImageIcon imgAge2000;
  String directorio;
  String nombreArchivo;
  String archivoMundo;
  MaquinaVirtual.SociedadAgentes sociedad;
  MaquinaVirtual.SueloCanvas suelo;
  MaquinaVirtual.SueloInfo info;

  int x, y, width, height, paso;
  int colocar, x_ini, y_ini, x_fin, y_fin, id_o;

  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel areaSuelo = new JPanel();
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  JScrollPane scroll = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  PopupMenu popMenuOrientacion = new PopupMenu();
  PopupMenu popMenuRadial = new PopupMenu();
  CheckboxMenuItem checkItem1 = new CheckboxMenuItem();
  CheckboxMenuItem checkItem2 = new CheckboxMenuItem();
  CheckboxMenuItem checkItem3 = new CheckboxMenuItem();
  CheckboxMenuItem checkItem4 = new CheckboxMenuItem();
  CheckboxMenuItem checkItemRadio1 = new CheckboxMenuItem();
  CheckboxMenuItem checkItemRadio2 = new CheckboxMenuItem();

    public Simulador() {
//        this.setSize(w,h);
        initComponents();
    try {
      jbInit();

    }
    catch(Exception e) {
      e.printStackTrace();
    }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   private void initComponents() {//GEN-BEGIN:initComponents
      imgAge2000 = new ImageIcon("imagen/age2000.gif");
//      directorio = "D:\\AAA-programas\\otros\\Sociedades\\";
      archivoMundo = "";
      directorio = "";
//      this.getContentPane().setLayout(null);
    }

  private void jbInit() throws Exception {
    x= 0; //coordenadas con respecto al frame (this.getGraphics)
    y = 0;
    // "paso", es la diferencia en pixeles entre un mosaico y otro
    paso = 20; // tama�o de los cuadros del piso

    // Alto y ancho del �rea de graficos
    width= 300;
    height = 300;

    colocar = 0;

    titledBorder1 = new TitledBorder("");

//    this.getContentPane().setLayout(null);
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    this.setBackground(SystemColor.control);
    this.setBorder(BorderFactory.createEtchedBorder());
    this.setLayout(borderLayout2);
    this.addComponentListener(new Simulador_this_componentAdapter(this));
    areaSuelo.setBackground(SystemColor.control);
    areaSuelo.setForeground(Color.black);
    areaSuelo.setDebugGraphicsOptions(0);
    areaSuelo.setLayout(null);
    //suelo.setBounds(new Rectangle(0, 0, 10, 10));
    suelo = new MaquinaVirtual.SueloCanvas(x,y,width,height, paso);
    suelo.addMouseListener(new Simulador_suelo_mouseAdapter(this));
    suelo.addKeyListener(new Simulador_suelo_keyAdapter(this));
    suelo.addMouseMotionListener(new Simulador_suelo_mouseMotionAdapter(this));

    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    checkItem1.setLabel("Norte");
    checkItem1.addItemListener(new Simulador_checkItem1_itemAdapter(this));
    checkItem2.setLabel("Sur");
    checkItem2.addItemListener(new Simulador_checkItem2_itemAdapter(this));
    checkItem3.setLabel("Este");
    checkItem3.addItemListener(new Simulador_checkItem3_itemAdapter(this));
    checkItem4.setLabel("Oeste");
    checkItem4.addItemListener(new Simulador_checkItem4_itemAdapter(this));
    checkItemRadio1.setLabel("radio 1");
    checkItemRadio1.addItemListener(new Simulador_checkItemRadio1_itemAdapter(this));
    checkItemRadio2.setLabel("radio 2");
    checkItemRadio2.addItemListener(new Simulador_checkItemRadio2_itemAdapter(this));

    this.add(scroll,  BorderLayout.CENTER);
    scroll.getViewport().add(areaSuelo, null);
    areaSuelo.add(suelo, null);
    popMenuOrientacion.add(checkItem1);
    popMenuOrientacion.add(checkItem2);
    popMenuOrientacion.add(checkItem3);
    popMenuOrientacion.add(checkItem4);
    popMenuRadial.add(checkItemRadio1);
    popMenuRadial.add(checkItemRadio2);

  }

  public void abrirNuevoArcAge(){
    File archivo = null;
    JFileChooser aux = new JFileChooser();
    aux.setAcceptAllFileFilterUsed(false);
    // Configura el directorio donde va a buscar los programas .age
    if(directorio != ""){
      File directory = new File(directorio);
      aux.setCurrentDirectory(directory);
    }
    // Configurar filtros "txt" y "age"
    ExampleFileFilter filter = new ExampleFileFilter("age", "C�digo objeto");
    aux.addChoosableFileFilter(filter);
    int valor=aux.showOpenDialog(this);
    if(valor==aux.APPROVE_OPTION){
      archivo = aux.getSelectedFile();
      directorio = archivo.getParent() + "\\";
      if(existeArchivo(directorio + getOnlyName(archivo.getName())+".Sociedad.Age2000.obj" )){
        if (!existeArchivoAbierto(archivo.getPath())) {
          nombreArchivo = getOnlyName(archivo.getName());
          initSociedad();
        }
        else {
          JOptionPane.showMessageDialog(null,
                                        "El archivo especificado ya se encuentra abierto",
                                        "Alerta",
                                        JOptionPane.INFORMATION_MESSAGE);
        }
      }
      else{
        JOptionPane.showMessageDialog(null,
                                      "El archivo especificado ("+ archivo.getPath() +") no ha sido compilado.",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  //public void inicializaSueloTraductor(Traductor trad){
	//  trad.inicializa(suelo);
  //}
  public void abrirNuevoArcAge(String nombre, String direct){
    nombreArchivo = nombre;
    directorio = direct;
    // FALTA QUIAR (NULL) SI EXIST�A UNA SOCIEDAD PREVIA (LIBERAR MEMORIA)
    initSociedad();
  }

  private String getOnlyName(String archivo){
      String e;
      int i=0;
      i = archivo.lastIndexOf('.');
      if(i>0 && i<archivo.length()){
          e = archivo.substring(0, i);
          return e;
      }
      else
        return archivo;
  }

  public boolean existeArchivo(String ruta){
    boolean band = false;
    BufferedReader in;
    try{
      in = new BufferedReader(
           new InputStreamReader(
           new FileInputStream(ruta)));
      band = true;
      try {in.close();}
        catch (IOException e) {} // "No cerr� bien el archivo"
    }
    catch (IOException e){
      band = false;// "No se abri� bien el archivo fuente"
    }
    finally{
       return band;
    }
  }

  private boolean existeArchivoAbierto(String archivo){
    if(archivo == directorio+nombreArchivo+".age"){
      return true;
    }
    return false;
  }

  public void initSociedad(){
//    suelo.setDimension(300,300); // CHECAR PENDIENTE PENDIENTE PENDIENTE
    sociedad = new MaquinaVirtual.SociedadAgentes(directorio, nombreArchivo, suelo);
/*    info = new MaquinaVirtual.SueloInfo(0,suelo.getHeight(),this.getWidth()-20,this.getHeight()-suelo.getHeight()-20,sociedad.countAgentes());
    info.setVisible(true);
    areaSuelo.add(info, null);
    for(int i=0; i<sociedad.countAgentes(); i++){
      info.setAgente(i, sociedad.getNombreAgente(i));
    }*/
  }

  public void startSociedadAgentes(){
    sociedad.startAgentes();
  }

  public void stopSociedadAgentes(){
    sociedad.stopAgentes();
  }

  public void suspendSociedadAgentes(){
    sociedad.suspendAgentes();
  }

  public void resumeSociedadAgentes(){
    sociedad.resumeAgentes();
  }

  public void nuevoMundo(){
    suelo.limpia();
  }

  public void abrirMundo(){
    File archivo = null;
    JFileChooser aux = new JFileChooser();
    aux.setAcceptAllFileFilterUsed(false);
  // Configura el directorio donde va a buscar los programas .mdo
  if(directorio != ""){
    File directory = new File(directorio);
    aux.setCurrentDirectory(directory);
  }
  // Configurar filtros "txt" y "mdo"
  ExampleFileFilter filter = new ExampleFileFilter("mdo", "C�digo objeto");
  aux.addChoosableFileFilter(filter);
  int valor=aux.showOpenDialog(this);
  if(valor==aux.APPROVE_OPTION){
    archivo = aux.getSelectedFile();
    directorio = archivo.getParent() + "\\";
    if (archivo.getPath()!=archivoMundo) {
      archivoMundo = archivo.getPath();
      suelo.abrirMundo(archivoMundo);
    }
    else {
      JOptionPane.showMessageDialog(null,
                                    "El archivo especificado ya se encuentra abierto",
                                    "Alerta",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
  }
  }

  public void guardarMundoComo(){
    File archivo = null;
    JFileChooser aux = new JFileChooser();
    aux.setDialogTitle("Guardar como...");
    // Especificar el directorio sobre el que se est� trabajando
    if(directorio != ""){
      File directory = new File(directorio);
      aux.setCurrentDirectory(directory);
    }
//    aux.setSelectedFile(new File(archivoMundo));
    // Especificar el filtro .age
    aux.setAcceptAllFileFilterUsed(false);
    ExampleFileFilter filter = new ExampleFileFilter("mdo", "Mundo");
    aux.addChoosableFileFilter(filter);
    int valor = aux.showSaveDialog(this);
    if(valor==aux.APPROVE_OPTION){
          archivo = aux.getSelectedFile();
          archivoMundo = suelo.guardarMundo(archivo.getPath());
   }

  }

  public void guardarMundo(){
    if(archivoMundo!=""){
      archivoMundo = suelo.guardarMundo(archivoMundo);
    }else
      guardarMundoComo();
  }

  void setPared(){
    colocar = PARED;
  }

  void setObstaculo(){
    colocar = OBSTACULO;
  }

  void setLuz(){
    colocar = LUZ;
  }

  void setTemperatura(){
    colocar = TEMPERATURA;
  }

  public int undo(){
    return suelo.undo();
  }

  public int redo(){
    return suelo.redo();
  }

  void quitarObjeto(){
    colocar = ELIMINAR_CELDA;
  }

  void quitarPared(){
    colocar = ELIMINAR_PARED;
  }

  void suelo_mouseReleased(MouseEvent e) {
    if(e.getButton()==3){ // bot�n DERECHO / BOTON 3
      id_o = suelo.isAgente(e.getX(),e.getY());
      if(id_o!=-1){ // si existe Agente en esa celda
        checkOrientacion(suelo.getAgenteOrientacion(id_o));
        suelo.add(popMenuOrientacion);
        popMenuOrientacion.show(suelo, e.getX(), e.getY());
      }
      x_fin = e.getX();
      y_fin = e.getY();
      if(suelo.isRadial(x_fin,y_fin)==13 || suelo.isRadial(x_fin,y_fin)==14){ // Si es temperatura o Luz
        checkRadio(suelo.getRadial(x_fin, y_fin));
        suelo.add(popMenuRadial);
        popMenuRadial.show(suelo, e.getX(), e.getY());
      }
    }else{
      switch (colocar) {
        case 1:
          x_fin = e.getX();
          y_fin = e.getY();
          suelo.setNvaPared(x_ini, y_ini, x_fin, y_fin);
          break;
        case 2:
          suelo.setNvoObstaculo(e.getX(), e.getY());
          break;
        case 3:
          suelo.setNvaLuz(e.getX(), e.getY());
          break;
        case 4:
          suelo.setNvaTemperatura(e.getX(), e.getY());
          break;
        case 5:
          suelo.setAgenteXY(x_ini, e.getX(), e.getY());
          suelo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          suelo.limpiaTemporal();
          colocar = 0;
          break;
        case 6:
          suelo.quitarObjeto(e.getX(), e.getY());
          break;
        case 7:
          suelo.quitarPared(e.getX(), e.getY());
          break;
      }
    }
  }

  void suelo_mousePressed(MouseEvent e) {
    if(e.getButton()!=3){ // bot�n DERECHO / BOTON 3
      switch (colocar) {
        case 1: // Guarda las coordenadas iniciales de una pared
          x_ini = e.getX();
          y_ini = e.getY();
          break;
      }
      if (colocar == 0 && suelo.isAgente(e.getX(), e.getY()) >= 0) { // Simulaci�n para re-colocar a un agente
        x_ini = suelo.isAgente(e.getX(), e.getY());
        suelo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        colocar = AGENTE;
      }
    }
  }

  void suelo_keyReleased(KeyEvent e) {
    if(e.getKeyCode()==27){// C�digo del "ESC"
      colocar = 0;
      suelo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  void suelo_mouseEntered(MouseEvent e) {
    if(colocar==0){
     suelo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   }
   else
     suelo.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
  }

  public void finalize(){
    suelo = null;
    sociedad = null;
  }

  void suelo_mouseExited(MouseEvent e) {
    if(colocar==5){
      colocar=0;
      suelo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      suelo.limpiaTemporal();
    }
  }

  void suelo_mouseDragged(MouseEvent e) {
    if(colocar==5){
      suelo.colocaAgente(e.getX(), e.getY());
    }
  }


  void this_componentResized(ComponentEvent e) {
    if(info == null){
      info = new MaquinaVirtual.SueloInfo(0, suelo.getHeight(),
                                          this.getWidth() - 20,
                                          this.getHeight() - suelo.getHeight() -
                                          20, sociedad.countAgentes());
      areaSuelo.add(info, null);
    }else{
//      areaSuelo.remove(info);
      info = null;
      info = new MaquinaVirtual.SueloInfo(0, suelo.getHeight(),
                                          this.getWidth() - 20,
                                          this.getHeight() - suelo.getHeight() -
                                          20, sociedad.countAgentes());
    }
    for(int i=0; i<sociedad.countAgentes(); i++){
      info.setAgente(i, sociedad.getNombreAgente(i));
    }

//    areaSuelo.add(info, null);
//    info.setVisible(true);
//    areaSuelo.setSize(this.getWidth()-5,this.getHeight()-5);
//    areaSuelo.setSize(suelo.getWidth(), 400);
  }

  private void checkOrientacion(int or){
    checkItem1.setState(false);
    checkItem2.setState(false);
    checkItem3.setState(false);
    checkItem4.setState(false);
    switch(or){
      case NORTE:checkItem1.setState(true);
        break;
      case SUR:checkItem2.setState(true);
        break;
      case ESTE:checkItem3.setState(true);
        break;
      case OESTE:checkItem4.setState(true);
        break;
    }
  }

  private void checkRadio(int radio){
    switch (radio) {
      case 1:
        checkItemRadio1.setState(true);
        checkItemRadio2.setState(false);
        break;
      case 2:
        checkItemRadio1.setState(false);
        checkItemRadio2.setState(true);
        break;
    }
  }

  void checkItem1_itemStateChanged(ItemEvent e) {
    suelo.setAgenteOrientacion(id_o, NORTE);
  }

  void checkItem2_itemStateChanged(ItemEvent e) {
    suelo.setAgenteOrientacion(id_o, SUR);
  }

  void checkItem3_itemStateChanged(ItemEvent e) {
    suelo.setAgenteOrientacion(id_o, ESTE);
  }

  void checkItem4_itemStateChanged(ItemEvent e) {
    suelo.setAgenteOrientacion(id_o, OESTE);
  }

  void checkItemRadio1_itemStateChanged(ItemEvent e) {
    suelo.setRadial(x_fin,y_fin,1);
  }

  void checkItemRadio2_itemStateChanged(ItemEvent e) {
    suelo.setRadial(x_fin,y_fin,2);
  }


}

class Simulador_suelo_mouseAdapter extends java.awt.event.MouseAdapter {
  Simulador adaptee;

  Simulador_suelo_mouseAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseReleased(MouseEvent e) {
    adaptee.suelo_mouseReleased(e);
  }
  public void mousePressed(MouseEvent e) {
    adaptee.suelo_mousePressed(e);
  }
  public void mouseEntered(MouseEvent e) {
    adaptee.suelo_mouseEntered(e);
  }
  public void mouseExited(MouseEvent e) {
    adaptee.suelo_mouseExited(e);
  }
}

class Simulador_suelo_keyAdapter extends java.awt.event.KeyAdapter {
  Simulador adaptee;

  Simulador_suelo_keyAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e) {
    adaptee.suelo_keyReleased(e);
  }
}

class Simulador_suelo_mouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
  Simulador adaptee;

  Simulador_suelo_mouseMotionAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseDragged(MouseEvent e) {
    adaptee.suelo_mouseDragged(e);
  }
}

class Simulador_this_componentAdapter extends java.awt.event.ComponentAdapter {
  Simulador adaptee;

  Simulador_this_componentAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void componentResized(ComponentEvent e) {
    adaptee.this_componentResized(e);
  }
}


class Simulador_checkItem1_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItem1_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItem1_itemStateChanged(e);
  }
}

class Simulador_checkItem2_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItem2_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItem2_itemStateChanged(e);
  }
}

class Simulador_checkItem3_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItem3_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItem3_itemStateChanged(e);
  }
}

class Simulador_checkItem4_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItem4_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItem4_itemStateChanged(e);
  }
}

class Simulador_checkItemRadio1_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItemRadio1_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItemRadio1_itemStateChanged(e);
  }
}

class Simulador_checkItemRadio2_itemAdapter implements java.awt.event.ItemListener {
  Simulador adaptee;

  Simulador_checkItemRadio2_itemAdapter(Simulador adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkItemRadio2_itemStateChanged(e);
  }
}
