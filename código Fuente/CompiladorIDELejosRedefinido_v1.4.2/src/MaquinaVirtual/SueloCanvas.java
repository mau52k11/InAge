package MaquinaVirtual;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.GradientPaint;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.Math;
import Herramientas.NodoRedoUndo;
import Herramientas.ListaRedoUndo;

public class SueloCanvas extends Canvas {

  static final int NORTE = 1;
  static final int SUR = 2;
  static final int ESTE = 3;
  static final int OESTE = 4;
  static final int NORESTE = 5;
  static final int NOROESTE = 6;
  static final int SURESTE = 7;
  static final int SUROESTE = 8;

  static int PARED0 = 10;
  static int PARED1 = 11;
  static int OBSTACULO = 12;
  static int LUZ = 13;
  static int TEMPERATURA = 14;
  static int AGENTE = 15;

  //  Orientaci�n
  static final int IZQUIERDA = 4; //tactil
  static final int DERECHA = 5;  //tactil
  static final int ATRAS = 7;  //tactil
  static final int FRENTE = 2;

  static final int ATRASDER = 8; // luz
  static final int ATRASIZQ = 6; // luz
  static final int FRENTEDER = 1; // luz
  static final int FRENTEIZQ = 3; // luz

  static Color colorAgente[];
  static Color colorSuelo = new Color(128,191,255);
  static Color colorLineas = new Color(215,215,215);
  static Color colorPared = new Color(128,64,0);
  static Color colorObstaculo = Color.magenta;
  static Color colorLuz = new Color(255,255,168);
  static Color colorTemperatura = new Color(255,81,81);

  int xx, yy; // coordenadas de todo el canvas con respecto al componente que lo contendr�
  int paso;
  int paredH[][];
  int paredV[][];
  int numAge;
  static int agentes[][];
  static int celda[][];
  static int medidaLuz[][];
  static int medidaTemp[][];
  static int tempPos[];
  int valor;
  int espacio;
  GradientPaint gradiente;
  BufferedImage imgFoco;
  BufferedImage imgFuego;
  BufferedImage imgObst;
  ListaRedoUndo listaRedo;
  ListaRedoUndo listaUndo;
  boolean undo;

  public SueloCanvas(int x, int y, int w, int h, int paso){
    int i,j;
//    setLocation(x,y);
    xx= x;
    yy = y;
//    setBackground(colorSuelo);
    setSize(w+1,h+1);
    this.paso = paso;
    espacio = 4;
    tempPos = new int[4];
    tempPos[0]=-1;
    tempPos[1]=-1;
    tempPos[2]=-1;
    tempPos[3]=-1;

    /*
     *  Definici�n de los colores de los agentes
     */
    colorAgente = new Color[10];
    colorAgente[0]= Color.magenta;
    colorAgente[1]= Color.red;
    colorAgente[2]= Color.green;
    colorAgente[3]= Color.pink;
    colorAgente[4]= Color.cyan;
    colorAgente[5]= Color.white;
    colorAgente[6]= Color.orange;
    colorAgente[7]= Color.gray;
    colorAgente[8]= Color.yellow;
    colorAgente[9]= Color.blue;

    // im�genes
    File fileFoco = new File("imagen/foco16.gif");
    File fileFuego = new File("imagen/termometro16.gif");
    File fileObst = new File("imagen/cubo16.gif");
    try{
      imgFoco = ImageIO.read(fileFoco);
      imgFuego = ImageIO.read(fileFuego);
      imgObst = ImageIO.read(fileObst);
    } catch (IOException ee) {
    }

    valor = 1;
/*    agentes = new int[2][3];  // []--> n�m. de agentes, [2]---> orientacion y coordenadas (x,y)
    numAge = 2;*/

    celda = new int[w/paso][h/paso];
    medidaLuz = new int[w/paso][h/paso];
    medidaTemp = new int[w/paso][h/paso];

    paredH = new int[w/paso][(h/paso)+1];
    paredV = new int[(w/paso)+1][h/paso];

    // Coloca paredes en la periferia del suelo
    for(i=0; i<w/paso; i++) paredH[i][0]=1;
    for(i=0; i<w/paso; i++) paredH[i][h/paso]=1;
    for(i=0; i<h/paso; i++) paredV[0][i]=1;
    for(i=0; i<h/paso; i++) paredV[w/paso][i]=1;

    // Limpia las celdas
    for(i=0; i<this.getWidth()/paso; i++){ // avanza las y's
      for (j = 0; j < this.getHeight() / paso; j++) { // avanza las x's
        celda[i][j]=0;
        medidaLuz[i][j]=0;
        medidaTemp[i][j]=0;
      }
    }
    listaRedo = new ListaRedoUndo();
    listaUndo = new ListaRedoUndo();
    undo = true;
  }

  public void setDimension(int w, int h){
    setSize(w,h);
  }

  public void initSuelo(){
    setLocation(xx+100,yy);
  }

  public int getPaso(){
    return paso;
  }

  /*
   *  Los siguientes m�todos se refieren a colocar al Agente (pol�gono - tri�ngulo)
   *  sobre el suelo con una orientaci�n
   */

  public void setAgentes(int num){
    agentes = new int[num][3];  // []--> n�m. de agentes, [2]---> orientacion y coordenadas (x,y)
    numAge = num;
    // TEMPORAL
    for(int i=0; i< num; i++){
       setAgenteXY(i,NORTE,4,2+i); // CORREGIR -- BUSCAR UNA CELDA VAC�A
    }
    repaint();
  }

  public void setAgenteXY(int id, int x, int y){
    x = convertir(x);
    y = convertir(y);
    if(!isOcupada(x,y)){
      setAgenteOnlyXY(id,x,y);
      repaint();
   }else{
     if(agentes[id][1]!=x && agentes[id][2]!=y){
       JOptionPane.showMessageDialog(null,
                                     "La celda que ha elegido se encuentra ocupada",
                                     "Alerta",
                                     JOptionPane.ERROR_MESSAGE);
     }
     }
  }

  private void setAgenteOnlyXY(int id, int x, int y){
    if(undo)listaUndo.insertar(AGENTE,id,agentes[id][1],agentes[id][2],x,y);
    agentes[id][1] = x;
    agentes[id][2] = y;
  }

  public void setAgenteOrientacion(int id, int orientacion){
    if(agentes[id][0]!=orientacion){
      if(undo)listaUndo.insertar(1,id,agentes[id][0],0,orientacion,0);
      agentes[id][0] = orientacion;
      repaint();
    }
  }

  public int getAgenteOrientacion(int id){
    return agentes[id][0];
  }

  public void setAgenteXY(int id, int or, int x, int y){
    agentes[id][0] = or;
    agentes[id][1] = x;
    agentes[id][2] = y;
    repaint();
  }

  public int isAgente(int x, int y){
    //checa si no existe ya en alg�n otra celda
    int i;
    x = convertir(x);
    y = convertir(y);

    // verifica si hay un agente en esa celda
    for(i=0; i<numAge; i++){
      if(agentes[i][1]==x && agentes[i][2]==y){
        return i; // regresa el identificador del agente, en caso de que exista
      }
    }
    return -1;
  }

  public void colocaAgente(int x, int y){
    x = convertir(x);
    y = convertir(y);
    if(tempPos[0]!=x || tempPos[1]!=y){
      if(tempPos[0]==-1 && tempPos[1]==-1){
        tempPos[2] = x;
        tempPos[3] = y;
      }else{
        tempPos[2] = tempPos[0];
        tempPos[3] = tempPos[1];
      }
      tempPos[0] = x;
      tempPos[1] = y;
      repaint();
    }
  }

  public void limpiaTemporal(){
    tempPos[0]=-1;
    tempPos[1]=-1;
    tempPos[2]=-1;
    tempPos[3]=-1;
    repaint();
  }

  /*
   *  Sobrecarga del m�todo update para eliminar el parpadeo
   */

  public void update(Graphics g){
    paint(g);
  }


  /*
   *  Sobrecarga del m�todo paint propio del Canvas
   */

  public void paint(Graphics g){
    int i, x,y, radial;
    Graphics2D g2 = (Graphics2D) g;

    if(tempPos[0]==-1 && tempPos[1]==-1){
      g2.setColor(colorSuelo); // especifica el color del espacio gr�fico
      g2.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight()); // Hace un rect�nculo, y lo colorea de azul
    }

    // Coloca tentativamente la sombra de la celda destino anterior de un agente
    if(tempPos[0]!=-1 && tempPos[1]!=-1){
      g2.setColor(colorSuelo);
      g2.fillRect(tempPos[2] * paso + 2, tempPos[3] * paso + 2, 16, 16); //(x,y,width,height)
    }

    // OBST�CULOS
    // Coloca las sombras
    for(x=0; x<this.getWidth()/paso; x++){ // avanza las y's
      for (y = 0; y < this.getHeight() / paso; y++) { // avanza las x's
        if(celda[x][y]==LUZ){
          radial = medidaLuz[x][y];
          g2.setColor(colorLuz);
          g2.fillRoundRect(x*paso-(paso*radial)+(espacio/2), y*paso-(paso*radial)+(espacio/2), ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio);
        }
        if(celda[x][y]==TEMPERATURA){
          radial = medidaTemp[x][y];
          g2.setColor(colorTemperatura);
          g2.fillRoundRect(x*paso-(paso*radial)+(espacio/2), y*paso-(paso*radial)+(espacio/2), ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio, ((2*radial+1)*paso)-espacio);
        }
      }
    }

    // Ciclos para hacer los cuadros del suelo
    g2.setColor(colorLineas); // especifica el color del espacio gr�fico

    for(i=0; i<=this.getWidth(); i= i+paso){ // dibuja las l�neas verticales
      g2.drawLine(i, 0, i, this.getHeight());
    }
    for(i=0; i<=this.getHeight(); i= i+paso){  // dibuja las l�neas horizontales
      g2.drawLine(0, i, this.getWidth(),i);
    }

    // Coloca tentativamente la sombra de la celda destino de un agente
    if(tempPos[0]!=-1 && tempPos[1]!=-1){
      g2.setColor(Color.yellow);
      g2.fillRect(tempPos[0] * paso + 2, tempPos[1] * paso + 2, 16, 16); //(x,y,width,height)
    }


    // Coloca las im�genes (es importante que sea despu�s de las sombras,
    // para que no borre ninguna
    // de las im�genes)
    for(x=0; x<this.getWidth()/paso; x++){ // avanza las y's
      for (y = 0; y < this.getHeight() / paso; y++) { // avanza las x's
        if(celda[x][y]==OBSTACULO){
          g2.drawImage(imgObst,x*paso+(espacio/2), y*paso+(espacio/2),this);
        }
        if(celda[x][y]==LUZ){
          g2.drawImage(imgFoco,x*paso+(espacio/2), y*paso+(espacio/2),this);
        }
        if(celda[x][y]==TEMPERATURA){
          g2.drawImage(imgFuego,x*paso+(espacio/2), y*paso+(espacio/2),this);
        }
        if(celda[x][y]==PARED0 || celda[x][y]==PARED1){
          g2.setColor(colorPared);
          g2.fillPolygon(diagonal(x,y,celda[x][y]));
        }
      }
    }

    if(tempPos[0]!=-1 && tempPos[1]!=-1){
      g2.setColor(Color.yellow);
      g2.fillRect(tempPos[0] * paso + 2, tempPos[1] * paso + 2, 16, 16); //(x,y,width,height)
    }

    // AGENTES  (es importante que sea la �ltima en dibujarse en el panel)
    for(x=0; x<numAge; x++){
      g2.setColor(colorAgente[x]);
      g2.fillPolygon(posicionObjeto(agentes[x][0],agentes[x][1],agentes[x][2]));
    }

    // PAREDES
    g2.setColor(colorPared);

    // Paredes Horizontales
    for(y=0; y<this.getHeight()/paso+1; y++){ // avanza las y's
      for(x=0; x<this.getWidth()/paso; x++){ // avanza las x's
        if(paredH[x][y]==1)
          g2.fillRect(x*paso,y*paso-1,paso,3); //(x,y,width,height)
      }
    }
    // Paredes Verticales
    for(x=0; x<this.getWidth()/paso+1; x++){ // avanza las y's
      for(y=0; y<this.getHeight()/paso; y++){ // avanza las x's
        if(paredV[x][y]==1)
          g2.fillRect(x*paso-1,y*paso,3,paso);
      }
    }


  }

  /*
   *  Coloca los diferentes objetos sobre el suelo
   *  objetos = pared, obst�culo, luz, temperatura
   */

  public void setPared(int x1, int y1, int x2, int y2){
    int temp=0;
    if(x1!= x2 & y1==y2){ // paredes horizontales
      if(x1>x2){  // identifica qu� punto es el menor
        temp = x1;
        x1 = x2;
        x2 = temp;
      }
      if(undo)listaUndo.insertar(2,0,x1,y1,x2,y2);
      for(; x1<x2; x1++){
        paredH[x1][y1] = 1;
      }
    }else{ // paredes verticales
         if (x1 == x2 & y1 != y2) { // se valida que haga referencia a una l�nea, y no a un punto
           if(y1>y2){ // identifica qu� punto es el menor
             temp = y1;
             y1 = y2;
             y2 = temp;
           }
           if(undo)listaUndo.insertar(3,0,x1,y1,x2,y2);
           for (; y1 < y2; y1++) {
             paredV[x1][y1] = 1;
           }
         }
    }
    repaint();
  }

  public void setPared(int x1, int y1, int x2, int y2, int pared){
    int i;
    if(pared==PARED0){
      for(i=0; i<x1-x2;i++){
        if(celda[x1-i-1][y1+i]==0)
          celda[x1-i-1][y1+i]=PARED0;
      }
    }

    if(pared==PARED1){
      for(i=0; i<x2-x1;i++){
        if(celda[x1+i][y1+i]==0)
          celda[x1+i][y1+i]=PARED1;
      }
    }
    if(undo)listaUndo.insertar(pared,0,x1,y1,x2,y2);
    repaint();
  }

  public void setNvaPared(int x_ini, int y_ini, int x_fin, int y_fin){

    int tempC, tempR;
    tempC= x_ini/paso;
    tempR = x_ini%paso;
    if(paso/2 <= tempR) tempC++;
    x_ini = tempC;

    tempC= y_ini/paso;
    tempR = y_ini%paso;
    if(paso/2 <= tempR) tempC++;
    y_ini = tempC;

    tempC= x_fin/paso;
    tempR = x_fin%paso;
    if(paso/2 <= tempR) tempC++;
    x_fin = tempC;

    tempC= y_fin/paso;
    tempR = y_fin%paso;
    if(paso/2 <= tempR) tempC++;
    y_fin = tempC;

    // Valida el que se encuentre dentro del suelo
      if(x_ini>=0 && x_fin >=0 &&
         x_ini <= this.getWidth()/paso && x_fin <= this.getWidth()/paso &&
         y_ini>=0 && y_fin >=0 &&
         y_ini <= this.getHeight()/paso && y_fin <= this.getHeight()/paso){

        // Coloca paredes verticales u horizontales
        if ( (x_ini != x_fin && y_ini == y_fin) ||
            (x_ini == x_fin && y_ini != y_fin)) {
          setPared(x_ini, y_ini, x_fin, y_fin);
        }
        else {
          int pared;
          // Coloca paredes diagonales
          if ( (x_ini > x_fin && y_ini > y_fin) ||
              (x_fin > x_ini && y_fin > y_ini)) {
            if (x_ini > x_fin) {
              tempC = x_ini;
              tempR = y_ini;
              x_ini = x_fin;
              y_ini = y_fin;
              x_fin = tempC;
              y_fin = tempR;
            }
            pared = PARED1;
          }
          else {
            if (x_ini < x_fin) {
              tempC = x_ini;
              tempR = y_ini;
              x_ini = x_fin;
              y_ini = y_fin;
              x_fin = tempC;
              y_fin = tempR;
            }
            pared = PARED0;
          }
          if (Math.abs(x_ini - x_fin) == Math.abs(y_ini - y_fin)) { //  Valida que se refiere a diagonales cuadradas
            setPared(x_ini, y_ini, x_fin, y_fin, pared);
          }
        }
      }else{
      JOptionPane.showMessageDialog(null,
               "Debe de trazar la pared dentro del suelo", "Alerta",
                                         JOptionPane.ERROR_MESSAGE);
    }


    repaint();
  }

  // OBST�CULOS

  public void setObstaculo(int x, int y){
    if(undo)listaUndo.insertar(OBSTACULO,0,x,y,0,0);
    celda[x][y]=OBSTACULO;
    repaint();
  }

  public void setNvoObstaculo(int x, int y){
    // Reciben las coordenadas en pixeles, y se deben de convertir a
    // puntos de coordenadas de acuerdo a la celda
    x = convertir(x);
    y = convertir(y);

     if(!isOcupada(x,y)){
       setObstaculo(x,y);
       repaint();
     }else{
       JOptionPane.showMessageDialog(null,
               "La celda que ha elegido se encuentra ocupada", "Alerta",
                                         JOptionPane.ERROR_MESSAGE);
     }
  }

  // LUZ

  public void setLuz(int x, int y, int radial){
    celda[x][y]=LUZ;
    setRadial(x,y,radial,medidaLuz);
    if(undo)listaUndo.insertar(LUZ,0,x,y,radial,0);
    repaint();
  }

  public void setNvaLuz(int x, int y){
    // Reciben las coordenadas en pixeles, y se deben de convertir a
    // puntos de coordenadas de acuerdo a la celda
    x = convertir(x);
    y = convertir(y);

     if(!isOcupada(x,y)){
       setLuz(x,y,1);
       repaint();
     }else{
       JOptionPane.showMessageDialog(null,
               "La celda que ha elegido se encuentra ocupada", "Alerta",
                                         JOptionPane.ERROR_MESSAGE);
     }
  }

  public int isRadial(int x, int y){
    x = convertir(x);
    y = convertir(y);
    return celda[x][y];
  }

  public int getRadial(int x, int y){
    x = convertir(x);
    y = convertir(y);
    if(celda[x][y]==LUZ){
      return medidaLuz[x][y];
    }
    if(celda[x][y]==TEMPERATURA){
      return medidaTemp[x][y];
    }
    return 0;
  }

  public void setRadial(int x, int y, int radial){
    x = convertir(x);
    y = convertir(y);
    if(celda[x][y]==LUZ && medidaLuz[x][y]!=radial){
      if(undo) listaUndo.insertar(4,0,x,y,medidaLuz[x][y],radial);
      setRadial(x,y,radial,medidaLuz);
    }
    if(celda[x][y]==TEMPERATURA && medidaTemp[x][y]!=radial){
      if(undo) listaUndo.insertar(4,0,x,y,medidaTemp[x][y],radial);
      setRadial(x, y, radial, medidaTemp);
    }
    repaint();
  }

  // TEMPERATURA

  public void setTemperatura(int x, int y, int radial){
    celda[x][y]=TEMPERATURA;
    setRadial(x,y,radial,medidaTemp);
    if(undo)listaUndo.insertar(TEMPERATURA,0,x,y,radial,0);
    repaint();
  }

  public void setNvaTemperatura(int x, int y){
    // Reciben las coordenadas en pixeles, y se deben de convertir a
    // puntos de coordenadas de acuerdo a la celda
    x = convertir(x);
    y = convertir(y);

     if(!isOcupada(x,y)){
       setTemperatura(x,y,1);
       repaint();
     }else{
       JOptionPane.showMessageDialog(null,
               "La celda que ha elegido se encuentra ocupada", "Alerta",
                                         JOptionPane.ERROR_MESSAGE);
     }
  }

  private boolean isOcupada(int x, int y){
    //checa si no existe ya en alg�n otra celda
    int i;
    // verifica si hay un agente en esa celda
    for(i=0; i<numAge; i++){
      if(agentes[i][1]==x && agentes[i][2]==y){
        return true;
      }
    }
    // verifica en la celdas si existe alg�n objeto
    if(celda[x][y]!=0){
      return true;
    }
    return false;
  }

  private int isAgenteCelda(int x, int y){
    //checa si no existe ya en alg�n otra celda
    int i;
    // verifica si hay un agente en esa celda
    for (i = 0; i < numAge; i++) {
      if (agentes[i][1] == x && agentes[i][2] == y) {
        return i;
      }
    }
    return -1;
  }

  // QUITAR OBJETOS : OBST�CULOS, LUZ, TEMPERATURA
  public void quitarObjeto(int x, int y){
    // Reciben las coordenadas en pixeles, y se deben de convertir a
    // puntos de coordenadas de acuerdo a la celda
    x = convertir(x);
    y = convertir(y);

    if (isAgenteCelda(x, y) == -1) {
      if(celda[x][y]!=0){
        int radial =0;
        if(celda[x][y]==LUZ){
          radial = medidaLuz[x][y];
          quitarRadial(x,y,medidaLuz[x][y],medidaLuz);
          if(undo)listaUndo.insertar(celda[x][y]*10,0,x,y,radial,0);
        }
        if(celda[x][y]==TEMPERATURA){
          radial = medidaTemp[x][y];
          quitarRadial(x,y,medidaTemp[x][y],medidaTemp);
          if(undo)listaUndo.insertar(celda[x][y]*10,0,x,y,radial,0);
        }
        if(celda[x][y]==OBSTACULO){
          if(undo)listaUndo.insertar(celda[x][y]*10,0,x,y,0,0);
        }
        if(celda[x][y]==PARED0){
          if(undo)listaUndo.insertar(celda[x][y]*10,0,x+1,y,x,y+1);
        }
        if(celda[x][y]==PARED1){
          if(undo)listaUndo.insertar(celda[x][y]*10,0,x,y,x+1,y+1);
        }
        celda[x][y]=0;
        repaint();
      }
      else {
        JOptionPane.showMessageDialog(null,
                                      "La celda ya se encuentra vacia",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    else {
      JOptionPane.showMessageDialog(null,
                                    "No puede borrar Agentes",
                                    "Alerta",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void quitarPared(int x, int y){
    int rx, ry; // residuos
    int holgura=2;
    boolean bandx = false, bandy = false;
    // Se calculan los residuos, para conocer la celda a la que se refiere
    rx = x%paso;
    ry = y%paso;
    x = x/paso;
    y = y/paso;

    if(ry<=holgura || ry>=paso-holgura){ // condici�n que hace referencia
      bandy =true;                       // a la pared de acuerdo al grosor que tiene.
      if(ry>=paso-holgura) y++;
    }
    if(rx<=holgura || rx>=paso-holgura){
      bandx =true;
      if(rx>=paso-holgura) x++;
    }

    if(!bandx && bandy){ // L�nea Horizontal
      if(paredH[x][y]==1) quitarParedH(x,y); // si existe pared Horizontal
      else
        JOptionPane.showMessageDialog(null,
                                      "No existe pared en esta �rea",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
    }

    if(bandx && !bandy){ // L�nea / Pared Vertical
      if(paredV[x][y]==1) quitarParedV(x,y); // si existe pared Vertical
      else
        JOptionPane.showMessageDialog(null,
                                      "No existe pared en esta �rea",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
    }

    if(bandx && bandy){  // Existen dos paredes (vertical y horizontal) encontradas en un punto
      if(paredV[x][y]==1 && paredH[x][y]==1){
        JOptionPane.showMessageDialog(null,
                                      "Especifique con mayor precisi�n la pared a borrar",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
      }else{
        if(paredV[x][y]==1) quitarParedV(x,y);
        if(paredH[x][y]==1) quitarParedH(x,y);
      }
    }

    if(!bandx && !bandy){// No se se�al� un �rea valida donde se encuentre dibujada la pared
      if(celda[x][y]==PARED0 || celda[x][y]==PARED1){
        quitarParedDiagonal(x,y);
      }else{
        JOptionPane.showMessageDialog(null,
                                      "Especifique con mayor precisi�n la pared a borrar",
                                      "Alerta",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    repaint();
  }

  public void quitarPared(int x1, int y1, int x2, int y2, int pared){
    int i;
    if(pared==PARED0){
      for(i=0; i<Math.abs(x1-x2);i++){
        if(celda[x1-i-1][y1+i]!=0)
          celda[x1-i-1][y1+i]=0;
      }
    }

    if(pared==PARED1){
      for(i=0; i<Math.abs(x2-x1);i++){
        if(celda[x1+i][y1+i]!=0)
          celda[x1+i][y1+i]=0;
      }
    }
    if(undo)listaUndo.insertar(pared*4,0,x1,y1,x2,y2);
    repaint();

  }

  public void quitarParedDiagonal(int x, int y){
    if(celda[x][y]==PARED0){ // Diagonal "7"
     int i=0, j=0;
     for(i=1; x+i<this.getWidth()/paso && y-i>=0;i++){
       if(celda[x+i][y-i]!=PARED0) break;
     }
     i--;
     for(j=1; x-j>=0 && y+j<this.getHeight()/paso;j++){
       if(celda[x-j][y+j]!=PARED0) break;
     }
     j--;
     quitarPared(x+i+1,y-i,x-j,y+j+1, PARED0);
    }
    if(celda[x][y]==PARED1){ // Diagonal "\"
      int i=0, j=0;
      for(i=1; x-i>=0 && y-i>=0;i++){
        if(celda[x-i][y-i]!=PARED1) break;
      }
      i--;
      for(j=1; x+j<this.getWidth()/paso && y+j<this.getHeight()/paso;j++){
        if(celda[x+j][y+j]!=PARED1){
          break;
        }
      }
      j--;
      quitarPared(x-i,y-i,x+j+1,y+j+1,PARED1);
    }
  }

  public void quitarParedH(int x, int y){
    int a, b;
    for(a=x; a<this.getWidth()/paso && paredH[a][y]==1; a++){
      paredH[a][y]=0;
    }
    b = a;
    for(a=x-1; a>=0 && paredH[a][y]==1; a--){
      paredH[a][y]=0;
    }
    if(undo)listaUndo.insertar(22,0,a+1,y,b,y);
  }

  public void quitarParedH(int x1, int y1, int x2){
    int a;
    for(a=x1; a<this.getWidth()/paso && paredH[a][y1]==1 && a<=x2; a++){
      paredH[a][y1]=0;
    }
    if(undo)listaUndo.insertar(22,0,x1,y1,x2,y1);

  }

  public void quitarParedV(int x, int y){
    int a, b;
    for(a=y; a<this.getHeight()/paso && paredV[x][a]==1; a++){
      paredV[x][a]=0;
    }
    b = a;
    for(a=y-1; a>=0 && paredV[x][a]==1; a--){
      paredV[x][a]=0;
    }
    if(undo)listaUndo.insertar(33,0,x,a+1,x,b);
  }

  public void quitarParedV(int x1, int y1, int y2){
    int a;
    for(a=y1; a<this.getHeight()/paso && paredV[x1][a]==1; a++){
      paredV[x1][a]=0;
    }
    if(undo)listaUndo.insertar(33,0,x1,y1,x1,y2);
  }

  private void quitarRadial(int x, int y, int radial, int temp[][]){
    for(int i=radial*(-1); i<=radial;i++){
      for(int j=radial*(-1); j<=radial;j++){
        if(x+i>=0 && y+j>=0 && x+i<this.getWidth()/paso && y+j<this.getHeight()/paso){
          temp[x + i][y + j] = 0;
        }
      }
    }
  }

  private void setRadial(int x, int y, int radial, int temp[][]){
    for(int i=radial*(-1); i<=radial;i++){
      for(int j=radial*(-1); j<=radial;j++){
        if(x+i>=0 && y+j>=0 && x+i<this.getWidth()/paso && y+j<this.getHeight()/paso){
          if(i==0 && j==0){
            temp[x][y] = radial;
          }else{
            temp[x + i][y + j] = 1;
          }
        }
      }
    }
  }


  /*
   * Limpia el espacio del suelo
   * Inicia un nuevo mundo
   */
  public void limpia(){
    int i, j;
    // Limpia las l�neas existentes
    for(i=0; i<this.getWidth()/paso; i++){
      for (j = 0; j <= this.getHeight()/paso; j++) {
        paredH[i][j]=0;
    }}
    for(i=0; i<= this.getWidth()/paso; i++){
      for (j = 0; j < this.getHeight()/paso; j++) {
        paredV[i][j]=0;
    }}
    // Coloca paredes en la periferia del suelo
    for(i=0; i<this.getWidth()/paso; i++) paredH[i][0]=1;
    for(i=0; i<this.getWidth()/paso; i++) paredH[i][this.getHeight()/paso]=1;
    for(i=0; i<this.getHeight()/paso; i++) paredV[0][i]=1;
    for(i=0; i<this.getHeight()/paso; i++) paredV[this.getWidth()/paso][i]=1;

    // Limpia las sombras de las temperaturas
    // OBST�CULOS
// Coloca las sombras
    for(i=0; i<this.getWidth()/paso; i++){ // avanza las y's
      for (j = 0; j < this.getHeight() / paso; j++) { // avanza las x's
        celda[i][j]=0;
        medidaLuz[i][j]=0;
        medidaTemp[i][j]=0;
      }
    }
/*    for(i=0; i<this.getWidth()/paso; i++){ // avanza las y's
      for (j = 0; j < this.getHeight() / paso; j++) { // avanza las x's
        celda[i][j]=0;
      }
    }*/
    listaUndo = new ListaRedoUndo();
    listaRedo = new ListaRedoUndo();
    repaint();
  }

  /*
   * Los siguientes m�todos abren un mundo.
   * Y adem�s, guardan el mundo dise�ado sobre el suelo.
   */

  public void abrirMundo(String ruta){
    int i, j, c, temp[], pared;
    ArchivoObjeto in = new ArchivoObjeto(ruta);
   temp = new int[4];
   limpia();
   // Carga las l�neas del archivo *.mdo
   c = in.getCodigo();

   // PAREDES
   while(c=='L'){
     for(i=0; i<4; i++){
       temp[i]=in.getNumeroInt();
     }
     setPared(temp[0],temp[1],temp[2],temp[3]);
     c = in.getCodigo();
   }

   // OBSTACULOS
   while(c=='O'){
     for(i=0; i<2; i++){
       temp[i]=in.getNumeroInt();
     }
     setObstaculo(temp[0],temp[1]);
     c = in.getCodigo();
   }

   // LUZ
   while(c=='Z'){
     for(i=0; i<3; i++){
       temp[i]=in.getNumeroInt();
     }
     setLuz(temp[0],temp[1],temp[2]);
     c = in.getCodigo();
   }

   while(c=='T'){
     for(i=0; i<3; i++){
       temp[i]=in.getNumeroInt();
     }
     setTemperatura(temp[0],temp[1],temp[2]);
     c = in.getCodigo();
   }

   while(c=='D' || c=='E'){
     if(c=='D'){
       pared = PARED0;
     }
     else{
       pared = PARED1;
     }
     for(i=0; i<2; i++){
       temp[i]=in.getNumeroInt();
     }
     if(pared==PARED1){
       setPared(temp[0], temp[1], temp[0]+1, temp[1]+1, pared);
     }
     else{
       setPared(temp[0]+1, temp[1], temp[0], temp[1]+1, pared);
     }
     c = in.getCodigo();
   }
   listaUndo = new ListaRedoUndo();
   listaRedo = new ListaRedoUndo();
   repaint();
  }

  public String guardarMundo(String ruta){
    int x, y, x1, y1, x2, y2;
    String cadTemp="", cadLuz="", cadObs="", cadDiag="";
    x1=-1; y1=-1;
    x2=-1; y2=-1;
      try{
        String extension = ruta.substring(ruta.length() - 4);
        if (extension.compareTo(".mdo")!=0) {
          ruta = ruta + ".mdo";
        }
        DataOutputStream output;
        output = new DataOutputStream(new FileOutputStream(ruta));

        // Verifica todas las paredes existentes horizontales
        // Paredes Horizontales
        for(y=1; y<this.getHeight()/paso; y++){ // avanza las y's  - No contempla las paredes de la periferia
          y1=y2=y;
          for(x=0; x<this.getWidth()/paso; x++){ // avanza las x's
            if(paredH[x][y]==1){ // Si encuentra pared
              if(x1>-1){
                x2=x+1;
              }else {
                x1=x;
                x2= x+1;
              }
            }
            else{// si no encuentra pared
              if(x1>-1){
                cadTemp = cadTemp + "L#" + x1 + "##" + y1 + "##" + x2 + "##" + y2 +
                    '#';
                x1=-1;
              }
            }
          }// Termina de recorrer las x's
          if(x1>-1){
            cadTemp = cadTemp + "L#" + x1 + "##" + y1 + "##" + x2 + "##" + y2 +
                '#';
            x1=-1;
          }
        }// Termina de recorrer las y's
        output.writeBytes(cadTemp);
        cadTemp="";
        y1=y2=-1;
        // Verifica todas las paredes existentes Verticales
        // Paredes Verticales
        for(x=1; x<this.getWidth()/paso; x++){ // avanza las x's  - No contempla las paredes de la periferia
          x1=x2=x;
          for(y=0; y<this.getHeight()/paso; y++){ // avanza las y's
            if(paredV[x][y]==1){ // Si encuentra pared
              if(y1>-1){
                y2=y+1;
              }else {
                y1=y;
                y2= y+1;
              }
            }
            else{// si no encuentra pared
              if(y1>-1){
                cadTemp = cadTemp + "L#" + x1 + "##" + y1 + "##" + x2 + "##" + y2 +
                    '#';
                y1=-1;
              }
            }
          }// Termina de recorrer las y's
          if(y1>-1){
            cadTemp = cadTemp + "L#" + x1 + "##" + y1 + "##" + x2 + "##" + y2 +
                '#';
            y1=-1;
          }
        }// Termina de recorrer las x's
        output.writeBytes(cadTemp);
        cadTemp="";
        // Coloca las im�genes (es importante que sea despu�s de las sombras,
        // para que no borre ninguna
        // de las im�genes)
        for(x=0; x<this.getWidth()/paso; x++){ // avanza las y's
          for (y = 0; y < this.getHeight() / paso; y++) { // avanza las x's
            if(celda[x][y]==OBSTACULO){
              cadObs = cadObs + "O#" + x + "##" + y + '#';
            }
            if(celda[x][y]==LUZ){
              cadLuz = cadLuz + "Z#" + x + "##" + y + "##" + medidaLuz[x][y] + '#';
            }
            if(celda[x][y]==TEMPERATURA){
              cadTemp = cadTemp + "T#" + x + "##" + y + "##" + medidaTemp[x][y] + '#';
            }
            if(celda[x][y]==PARED0){
              cadDiag = cadDiag + "D#" + x + "##" + y + '#';
            }
            if(celda[x][y]==PARED1){
              cadDiag = cadDiag + "E#" + x + "##" + y + '#';
            }
          }
        }
        output.writeBytes(cadObs);
        output.writeBytes(cadLuz);
        output.writeBytes(cadTemp);
        output.writeBytes(cadDiag);
        output.writeByte(0); // Buscar el colocar el fin de archivo
        output.close();
      }
      catch (IOException e){
      }
      return ruta;
   }

 /*
  *  Los siguientes m�todos se refieren a las primitivas del lenguaje Age2000
  *  (avanzar, retroceder, gira a la derecha, gira a la izquierda).
  *  Adem�s, el �ltimo m�todo, se refiere a dibujar un pol�gono (tri�ngulo)
  *  en la nueva orientaci�n o celda.
  */

  public void avanzar(int id){
    switch(agentes[id][0]){         // Mueve al agente al nuevo estado
     case NORTE:                    // modifica las y's
             if (agentes[id][2] > 0  && paredH[agentes[id][1]][agentes[id][2]]==0){
               if(celda[agentes[id][1]][agentes[id][2]-1]==0){
                 celda[agentes[id][1]][agentes[id][2]]=0;
                 agentes[id][2]--;
                 celda[agentes[id][1]][agentes[id][2]]=AGENTE;
               }
             }
             break;
     case SUR:                    // modifica las y's
             if(agentes[id][2] <= this.getHeight()/paso  &&  paredH[agentes[id][1]][agentes[id][2]+1]==0){
               if(celda[agentes[id][1]][agentes[id][2]+1]==0){
                 celda[agentes[id][1]][agentes[id][2]]=0;
                 agentes[id][2]++;
                 celda[agentes[id][1]][agentes[id][2]]=AGENTE;
               }
             }
             break;
     case ESTE:                    // modifica las x's
             if(agentes[id][1] <= this.getWidth()/paso  && paredV[agentes[id][1]+1][agentes[id][2]]==0){
               if(celda[agentes[id][1]+1][agentes[id][2]]==0){
                 celda[agentes[id][1]][agentes[id][2]]=0;
                 agentes[id][1]++;
                 celda[agentes[id][1]][agentes[id][2]]=AGENTE;
               }
             }
             break;
     case OESTE:                    // modifica las x's
             if(agentes[id][1] > 0  && paredV[agentes[id][1]][agentes[id][2]]==0){
               if(celda[agentes[id][1]-1][agentes[id][2]]==0){
                 celda[agentes[id][1]][agentes[id][2]]=0;
                 agentes[id][1]--;
                 celda[agentes[id][1]][agentes[id][2]]=AGENTE;
               }
             }
             break;
     }
     repaint();
  }

  public void retroceder(int id){
    switch(agentes[id][0]){         // Mueve al agente al nuevo estado
      case NORTE:
                 if(agentes[id][2] <= this.getHeight()/paso  & paredH[agentes[id][1]][agentes[id][2]+1]==0){
                   if(celda[agentes[id][1]][agentes[id][2]+1]==0){
                     celda[agentes[id][1]][agentes[id][2]]=0;
                     agentes[id][2]++;
                     celda[agentes[id][1]][agentes[id][2]]=AGENTE;
                   }
                 }
                 break;
      case SUR:
                 if(agentes[id][2] > 0  & paredH[agentes[id][1]][agentes[id][2]]==0){
                   if(celda[agentes[id][1]][agentes[id][2]-1]==0){
                     celda[agentes[id][1]][agentes[id][2]]=0;
                     agentes[id][2]--;
                     celda[agentes[id][1]][agentes[id][2]]=AGENTE;
                   }
                 }
                 break;
      case ESTE:
                 if(agentes[id][1] > 0  & paredV[agentes[id][1]][agentes[id][2]]==0){
                   if(celda[agentes[id][1]-1][agentes[id][2]]==0){
                     celda[agentes[id][1]][agentes[id][2]]=0;
                     agentes[id][1]--;
                     celda[agentes[id][1]][agentes[id][2]]=AGENTE;
                   }
                 }
                 break;
      case OESTE:
                 if(agentes[id][1] <= this.getWidth()/paso  & paredV[agentes[id][1]+1][agentes[id][2]]==0){
                   if(celda[agentes[id][1]+1][agentes[id][2]]==0){
                     celda[agentes[id][1]][agentes[id][2]]=0;
                     agentes[id][1]++;
                     celda[agentes[id][1]][agentes[id][2]]=AGENTE;
                   }
                 }
                 break;
    }
    repaint();
  }

  public void giraIzq(int id){
    switch(agentes[id][0]){         // Mueve al agente al nuevo estado
      case NORTE:
                 agentes[id][0] = NOROESTE;
                 repaint();
                 agentes[id][0] = OESTE;
                 break;
      case SUR:
                 agentes[id][0] = SURESTE;
                 repaint();
                 agentes[id][0] = ESTE;
                 break;
      case ESTE:
                 agentes[id][0] = NORESTE;
                 repaint();
                 agentes[id][0] = NORTE;
                 break;
      case OESTE:
                 agentes[id][0] = SUROESTE;
                 repaint();
                 agentes[id][0] = SUR;
                 break;
    }
    repaint();
  }

  public void giraDer(int id){
    switch(agentes[id][0]){         // Mueve al agente al nuevo estado
      case NORTE:
                 agentes[id][0] = NORESTE;
                 repaint();
                 agentes[id][0] = ESTE;
                 break;
      case SUR:
                 agentes[id][0] = SUROESTE;
                 repaint();
                 agentes[id][0] = OESTE;
                 break;
      case ESTE:
                 agentes[id][0] = SURESTE;
                 repaint();
                 agentes[id][0] = SUR;
                 break;
      case OESTE:
                 agentes[id][0] = NOROESTE;
                 repaint();
                 agentes[id][0] = NORTE;
                 break;
    }
    repaint();
  }

  public Polygon posicionObjeto(int orientacion, int a, int b){
    Polygon objeto = new Polygon();
    switch(orientacion){
      case NORTE:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+paso-espacio);
                 objeto.addPoint((a*paso)+(paso/2),(b*paso)+espacio);
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+paso-espacio);
                 break;
      case SUR:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+espacio);
                 objeto.addPoint((a*paso)+(paso/2),(b*paso)+paso-espacio);
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+espacio);
                 break;
      case ESTE:// -------->
                 objeto.addPoint((a*paso)+espacio,(b*paso)+espacio);
                 objeto.addPoint((a*paso)+espacio,(b*paso)+paso-espacio);
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+(paso/2));
                 break;
      case OESTE:// <--------
                 objeto.addPoint((a*paso)+espacio,(b*paso)+(paso/2));
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+espacio);
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+paso-espacio);
                 break;
      case NORESTE:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+paso-(2*espacio));
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+espacio); // punta
                 objeto.addPoint((a*paso)+paso-(2*espacio),(b*paso)+paso-espacio);
                 break;
      case NOROESTE:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+espacio); // punta
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+paso-(2*espacio));
                 objeto.addPoint((a*paso)+paso-(3*espacio),(b*paso)+paso-espacio);
                 break;
      case SURESTE:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+(2*espacio));
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+paso-espacio); // punta
                 objeto.addPoint((a*paso)+paso-(2*espacio),(b*paso)+espacio);
                 break;
      case SUROESTE:
                 objeto.addPoint((a*paso)+espacio,(b*paso)+paso-espacio); // punta
                 objeto.addPoint((a*paso)+paso-espacio,(b*paso)+(2*espacio));
                 objeto.addPoint((a*paso)+paso-(3*espacio),(b*paso)+espacio);
                 break;

    }
    return objeto;
  }


// Dibuja Paredes Diagonales
  public Polygon diagonal(int a, int b, int pared){
    Polygon objeto = new Polygon();
    // '\'
    if(pared == PARED1){
      objeto.addPoint( (a * paso) + 2, b * paso); // Forman un tri�ngulo en la punta (cada 3 puntos)
      objeto.addPoint( a * paso -1, b * paso -1);
      objeto.addPoint(a * paso, (b * paso) + 2);
      objeto.addPoint( (a + 1) * paso - 2, (b + 1) * paso);
      objeto.addPoint( (a + 1) * paso +1 , (b + 1) * paso +1);
      objeto.addPoint( (a + 1) * paso, (b + 1) * paso - 2);
    }
    // '/'
    if(pared == PARED0){
      objeto.addPoint( a * paso, (b+1) * paso -2);
      objeto.addPoint( a * paso -1, (b+1) * paso +1);
      objeto.addPoint(a * paso+2, (b+1) * paso);
      objeto.addPoint( (a + 1) * paso, b* paso +2);
      objeto.addPoint( (a + 1) * paso + 1, b* paso -1);
      objeto.addPoint( (a + 1) * paso -2, b* paso);
    }
    return objeto;
    }

// Convierte un pixel a la celda que corresponde -- en este caso a la columna que corresponda
  public int convertir(int pixel){
    return pixel/paso;
  }

// Funci�n temporal, est� por definirse su estado
  public void nuevoMundo(){
     int i, j;
    //LINEAS
    // Limpia las l�neas existentes
    for(i=0; i<this.getWidth()/paso; i++){
      for (j = 0; j <= this.getHeight()/paso; j++) {
        paredH[i][j]=0;
    }}
    for(i=0; i<= this.getWidth()/paso; i++){
      for (j = 0; j < this.getHeight()/paso; j++) {
        paredV[i][j]=0;
    }}
    // Coloca paredes en la periferia del suelo
    for(i=0; i<this.getWidth()/paso; i++) paredH[i][0]=1;
    for(i=0; i<this.getWidth()/paso; i++) paredH[i][this.getHeight()/paso]=1;
    for(i=0; i<this.getHeight()/paso; i++) paredV[0][i]=1;
    for(i=0; i<this.getHeight()/paso; i++) paredV[this.getWidth()/paso][i]=1;
    repaint();
   }

  public int undo(){
    int i = 0;
    NodoRedoUndo nodo;
    i = listaUndo.getCount();
    if(i>0){
      undo = false;
      nodo = listaUndo.getLastNodo();
      switch (nodo.getTipo()) {
        case 1:// ORIENTACION : Recuperar la orientaci�n original
          setAgenteOrientacion(nodo.getId(),nodo.getX1());
          break;
        case 2: // PAREDES: Pared Horizontal -- Coloca pared --> UNDO --> Quita pared
          quitarParedH(nodo.getX1(), nodo.getY1(), nodo.getX2());
          break;
        case 3:// Pared Vertical
          quitarParedV(nodo.getX1(), nodo.getY1(), nodo.getY2());
          break;
        case 4:// RADIAL
          setRadial(nodo.getX1()*paso,nodo.getY1()*paso,nodo.getX2());
          break;
        case 22: case 33:// Pared Horizontal -- Quita pared --> UNDO --> Lo vuelve a poner
          setPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2()); //REDO --> Vuelve a colocar la pared
          break;
        case 10: case 11:// PARED0 -- PARED1
          quitarPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2(),nodo.getTipo());
          break;
        case 40: case 44:
          setPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2(),nodo.getTipo()/4);
          break;
        case 100: case 110: // Pared0 como objeto
          setPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2(),nodo.getTipo()/10);
          break;
        case 12://OBSTACULOS
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 120:
          setObstaculo(nodo.getX1(),nodo.getY1());
          break;
        case 13://LUZ
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 130:
          setLuz(nodo.getX1(),nodo.getY1(),nodo.getX2());
          break;
        case 14://TEMPERATURA
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 140:
          setTemperatura(nodo.getX1(),nodo.getY1(),nodo.getX2());
          break;
        case 15:// AGENTE: Recuperar las coordenadas anteriores
          setAgenteOnlyXY(nodo.getId(),nodo.getX1(),nodo.getY1());
          break;
      }
      listaRedo.insertar(nodo.getTipo(),nodo.getId(),nodo.getX1(), nodo.getY1(),nodo.getX2(),nodo.getY2());
      nodo = null;
      undo = true;
      repaint();
      return i-1;
    }
    else
      return i;
  }

  public int redo(){
    int i = 0;
    NodoRedoUndo nodo;
    i = listaRedo.getCount();
    if(i>0){
      nodo = listaRedo.getLastNodo();
      switch (nodo.getTipo()) {
        case 1:// ORIENTACION: Recuperar la orientaci�n ejecutada
          setAgenteOrientacion(nodo.getId(),nodo.getX2());
          break;
        case 2: case 3 :// PAREDES: Pared Horizontal/Vertical -- Coloca pared --> UNDO -- Quita pared -->
          setPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2()); //REDO --> Vuelve a colocar la pared
          break;
        case 4:// RADIAL
          setRadial(nodo.getX1()*paso,nodo.getY1()*paso,nodo.getY2());
          break;
        case 10: case 11: // PARED0, PARED1
          setPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2(),nodo.getTipo());
          break;
        case 40: case 44:
          quitarPared(nodo.getX1(),nodo.getY1(),nodo.getX2(),nodo.getY2(),nodo.getTipo()/4);
          break;
        case 100: // Pared0 como objeto
          quitarObjeto((nodo.getX1()-1)*paso,(nodo.getY2()-1)*paso);
          break;
        case 110:
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 22:
          quitarParedH(nodo.getX1(), nodo.getY1(),nodo.getX2());
          break;
        case 33:
          quitarParedV(nodo.getX1(), nodo.getY1(),nodo.getY2());
          break;
        case 12:// OBSTACULOS
          setObstaculo(nodo.getX1(),nodo.getY1());
          break;
        case 120:
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 13://LUZ
          setLuz(nodo.getX1(),nodo.getY1(),nodo.getX2());
          break;
        case 130:
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 14://TEMPERATURA
          setTemperatura(nodo.getX1(),nodo.getY1(),nodo.getX2());
          break;
        case 140:
          quitarObjeto(nodo.getX1()*paso,nodo.getY1()*paso);
          break;
        case 15:// AGENTE : Recuperar las coordenadas anteriores
          setAgenteOnlyXY(nodo.getId(),nodo.getX2(),nodo.getY2());
          break;
      }
      nodo = null;
      repaint();
      return i-1;
    }
    else
      return i;
  }

  /*
   * Las siguientes funciones permiten el conocer en qu� posici�n se encuentra
   * el agente, y reportar obst�culos, paredes, luz, y temperatura en su camino.
   */

// TACTIL

  public boolean getTactil(int orientacion, int id){
    boolean band=false;
    switch(agentes[id][0]){
      case NORTE:if(orientacion==IZQUIERDA) band = getTactilAgente(IZQUIERDA,id);
                 if(orientacion==DERECHA) band = getTactilAgente(DERECHA,id);
                 if(orientacion==FRENTE) band = getTactilAgente(FRENTE,id);
                 if(orientacion==ATRAS) band = getTactilAgente(ATRAS,id);
                 break;
      case SUR:  if(orientacion==IZQUIERDA) band = getTactilAgente(DERECHA,id);
                 if(orientacion==DERECHA) band = getTactilAgente(IZQUIERDA,id);
                 if(orientacion==FRENTE) band = getTactilAgente(ATRAS,id);
                 if(orientacion==ATRAS) band = getTactilAgente(FRENTE,id);
                 break;
      case ESTE: if(orientacion==IZQUIERDA) band = getTactilAgente(FRENTE,id);
                 if(orientacion==DERECHA) band = getTactilAgente(ATRAS,id);
                 if(orientacion==FRENTE) band = getTactilAgente(DERECHA,id);
                 if(orientacion==ATRAS) band = getTactilAgente(IZQUIERDA,id);
                 break;
      case OESTE:if(orientacion==IZQUIERDA) band = getTactilAgente(ATRAS,id);
                 if(orientacion==DERECHA) band = getTactilAgente(FRENTE,id);
                 if(orientacion==FRENTE) band = getTactilAgente(IZQUIERDA,id);
                 if(orientacion==ATRAS) band = getTactilAgente(DERECHA,id);
                 break;
    }

    return band;
  }

  public boolean getTactilAgente(int opcion, int id){ // Funci�n tomando en cuenta al agente hacia el norte
    boolean band = false;
    switch(opcion){
      case IZQUIERDA:// Este caso se refiere a alg�n obst�culo, � pared del lado IZQUIERDO
                     // sin tomar en cuenta la orientaci�n del agente
                     if(paredV[agentes[id][1]][agentes[id][2]]==1) band = true;
                     if(band==false && agentes[id][1]>0){ // si hay celda del lado izquierdo
                        if(celda[agentes[id][1]-1][agentes[id][2]]!=0) band = true;
                     }
                     break;
      case DERECHA:  // Este caso se refiere a alg�n obst�culo, � pared del lado DERECHO
                     // sin tomar en cuenta la orientaci�n del agente
                     if(paredV[agentes[id][1]+1][agentes[id][2]]==1) band = true;
                     if(band==false && agentes[id][1]<this.getWidth()/paso-1){ // si hay celda del lado izquierdo
                        if(celda[agentes[id][1]+1][agentes[id][2]]!=0) band = true;
                     }
                     break;
      case FRENTE:   if(paredH[agentes[id][1]][agentes[id][2]]==1) band = true;
                     if(band==false && agentes[id][2]>0){ // si hay celda al frente
                        if(celda[agentes[id][1]][agentes[id][2]-1]!=0) band = true;
                     }
                     break;
      case ATRAS:   if(paredH[agentes[id][1]][agentes[id][2]+1]==1) band = true;
                     if(band==false && agentes[id][2]<this.getHeight()/paso-1){ // si hay celda al frente
                        if(celda[agentes[id][1]][agentes[id][2]+1]!=0) band = true;
                     }
                     break;
    }
    return band;
  }

// LUZ

  public int getLuz(int orientacion, int id){
    int valor=0;
    switch(agentes[id][0]){
      case NORTE:if(orientacion==IZQUIERDA) valor = getLuzAgente(IZQUIERDA,id);
                 if(orientacion==DERECHA) valor = getLuzAgente(DERECHA,id);
                 if(orientacion==FRENTE) valor = getLuzAgente(FRENTE,id);
                 if(orientacion==ATRAS) valor = getLuzAgente(ATRAS,id);
                 if(orientacion==FRENTEIZQ) valor = getLuzAgente(FRENTEIZQ,id);
                 if(orientacion==FRENTEDER) valor = getLuzAgente(FRENTEDER,id);
                 if(orientacion==ATRASIZQ) valor = getLuzAgente(ATRASIZQ,id);
                 if(orientacion==ATRASDER) valor = getLuzAgente(ATRASDER,id);
                 break;
      case SUR:  if(orientacion==IZQUIERDA) valor = getLuzAgente(DERECHA,id);
                 if(orientacion==DERECHA) valor = getLuzAgente(IZQUIERDA,id);
                 if(orientacion==FRENTE) valor = getLuzAgente(ATRAS,id);
                 if(orientacion==ATRAS) valor = getLuzAgente(FRENTE,id);
                 if(orientacion==FRENTEIZQ) valor = getLuzAgente(ATRASDER,id);
                 if(orientacion==FRENTEDER) valor = getLuzAgente(ATRASIZQ,id);
                 if(orientacion==ATRASIZQ) valor = getLuzAgente(FRENTEDER,id);
                 if(orientacion==ATRASDER) valor = getLuzAgente(FRENTEIZQ,id);
                 break;
      case ESTE: if(orientacion==IZQUIERDA) valor = getLuzAgente(FRENTE,id);
                 if(orientacion==DERECHA) valor = getLuzAgente(ATRAS,id);
                 if(orientacion==FRENTE) valor = getLuzAgente(DERECHA,id);
                 if(orientacion==ATRAS) valor = getLuzAgente(IZQUIERDA,id);
                 if(orientacion==FRENTEIZQ) valor = getLuzAgente(FRENTEDER,id);
                 if(orientacion==FRENTEDER) valor = getLuzAgente(ATRASDER,id);
                 if(orientacion==ATRASIZQ) valor = getLuzAgente(FRENTEIZQ,id);
                 if(orientacion==ATRASDER) valor = getLuzAgente(ATRASIZQ,id);
                 break;
      case OESTE:if(orientacion==IZQUIERDA) valor = getLuzAgente(ATRAS,id);
                 if(orientacion==DERECHA) valor = getLuzAgente(FRENTE,id);
                 if(orientacion==FRENTE) valor = getLuzAgente(IZQUIERDA,id);
                 if(orientacion==ATRAS) valor = getLuzAgente(DERECHA,id);
                 if(orientacion==FRENTEIZQ) valor = getLuzAgente(ATRASIZQ,id);
                 if(orientacion==FRENTEDER) valor = getLuzAgente(FRENTEIZQ,id);
                 if(orientacion==ATRASIZQ) valor = getLuzAgente(ATRASDER,id);
                 if(orientacion==ATRASDER) valor = getLuzAgente(FRENTEDER,id);
                 break;
    }

    return valor;
  }

  public int getLuzAgente(int opcion, int id){
    switch(opcion){
      case IZQUIERDA:// Lado IZQUIERDO viendo hacia el NORTE
                     if(agentes[id][1]>0){ // si hay celda del lado izquierdo
                        if(celda[agentes[id][1]-1][agentes[id][2]]==LUZ) return 1;
                        else return medidaLuz[agentes[id][1]-1][agentes[id][2]];
                     }
                     break;
      case DERECHA:  if(agentes[id][1]<this.getWidth()/paso-1){
                       if(celda[agentes[id][1]+1][agentes[id][2]]==LUZ) return 1;
                       else return medidaLuz[agentes[id][1]+1][agentes[id][2]];
                     }
                     break;
      case FRENTE:   if(agentes[id][2]>0){
                       if(celda[agentes[id][1]][agentes[id][2]-1]==LUZ) return 1;
                       else return medidaLuz[agentes[id][1]][agentes[id][2]-1];
                     }
                     break;
      case ATRAS:   if(agentes[id][2]<this.getHeight()/paso-1){
                       if(celda[agentes[id][1]][agentes[id][2]+1]==LUZ) return 1;
                       else return medidaLuz[agentes[id][1]][agentes[id][2]+1];
                    }
                     break;
      case FRENTEIZQ:if(agentes[id][1]>0 && agentes[id][2]>0){
                        if(celda[agentes[id][1]-1][agentes[id][2]-1]==LUZ) return 1;
                        return medidaLuz[agentes[id][1]-1][agentes[id][2]-1];
                     }
                     break;
      case FRENTEDER:if(agentes[id][1]<this.getWidth()/paso-1 && agentes[id][2]>0){
                        if(celda[agentes[id][1]+1][agentes[id][2]-1]==LUZ) return 1;
                        return medidaLuz[agentes[id][1]+1][agentes[id][2]-1];
                     }
                     break;
      case ATRASIZQ: if(agentes[id][1]>0 && agentes[id][2]<this.getHeight()/paso-1){
                        if(celda[agentes[id][1]-1][agentes[id][2]+1]==LUZ) return 1;
                        return medidaLuz[agentes[id][1]-1][agentes[id][2]+1];
                     }
                     break;
      case ATRASDER: if(agentes[id][1]<this.getWidth()/paso-1 && agentes[id][2]<this.getHeight()/paso-1){
                        if(celda[agentes[id][1]+1][agentes[id][2]+1]==LUZ) return 1;
                        return medidaLuz[agentes[id][1]+1][agentes[id][2]+1];
                     }
                     break;
    }
    return 0;
  }

// TEMPERATURA

  public int getTemperatura(int orientacion, int id){
    switch(agentes[id][0]){
      case NORTE:if(orientacion==FRENTE) return getTemperaturaAgente(FRENTE,id);
                 break;
      case SUR:  if(orientacion==FRENTE) return getTemperaturaAgente(ATRAS,id);
                 break;
      case ESTE: if(orientacion==FRENTE) return getTemperaturaAgente(DERECHA,id);
                 break;
      case OESTE:if(orientacion==FRENTE) return getTemperaturaAgente(IZQUIERDA,id);
                 break;
    }
    return 0;
  }

  public int getTemperaturaAgente(int opcion, int id){ // Funci�n tomando en cuenta al agente hacia el norte
    switch(opcion){
      case IZQUIERDA:// Este caso se refiere a alg�n obst�culo, � pared del lado IZQUIERDO
                     if(agentes[id][1]>0){ // si hay celda del lado izquierdo
                       if(celda[agentes[id][1]-1][agentes[id][2]]==TEMPERATURA) return 1;
                        else return medidaTemp[agentes[id][1]-1][agentes[id][2]];
                     }
                     break;
      case DERECHA:  // Este caso se refiere a alg�n obst�culo, � pared del lado DERECHO
                     if(agentes[id][1]<this.getWidth()/paso-1){ // si hay celda del lado izquierdo
                       if(celda[agentes[id][1]+1][agentes[id][2]]==TEMPERATURA) return 1;
                        else return medidaTemp[agentes[id][1]+1][agentes[id][2]];
                     }
                     break;
      case FRENTE:
                     if(agentes[id][2]>0){ // si hay celda al frente
                       if(celda[agentes[id][1]][agentes[id][2]-1]==TEMPERATURA) return 1;
                        else return medidaTemp[agentes[id][1]][agentes[id][2]-1];
                     }
                     break;
      case ATRAS:
                     if(agentes[id][2]<this.getHeight()/paso-1){ // si hay celda al frente
                       if(celda[agentes[id][1]][agentes[id][2]+1]==TEMPERATURA) return 1;
                        else return medidaTemp[agentes[id][1]][agentes[id][2]+1];
                     }
                     break;
    }
    return 0;
  }


}


