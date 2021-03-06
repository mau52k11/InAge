package MaquinaVirtual;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SueloInfo extends Canvas {


  static Color colorAgente[];
  static String nombreAgente[];
  int numAge;

  public SueloInfo(int x, int y, int w, int h, int numAge){
  setLocation(x,y);
  setSize(w,h);
  this.numAge = numAge;

  nombreAgente = new String [numAge];

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

  }

public void setDimension(int w, int h){
  setSize(w,h);
}


/*
 *  Sobrecarga del m�todo paint propio del Canvas
 */

public void paint(Graphics g){
  Graphics2D g2 = (Graphics2D) g;
  int xx, yy, coc, res;
  for(int i=0; i<numAge; i++){
    coc = i / 2;
    res = i % 2;
    xx = 80 * res;
    yy = (coc + 1) * 15;
    g2.setColor(colorAgente[i]); // especifica el color del agente
    g2.fillPolygon(posicionObjeto(xx, yy));
    g2.setColor(Color.black); // especifica el color del agente
    g2.drawString(nombreAgente[i], xx + 20, yy + 10);
  }
}

/*
 *  Sobrecarga del m�todo update para eliminar el parpadeo
 */

public void update(Graphics g){
  paint(g);
}


public Polygon posicionObjeto(int a, int b){
  Polygon objeto = new Polygon();
  objeto.addPoint(a,b+10);
  objeto.addPoint(a+5,b);
  objeto.addPoint(a+10,b+10);
  return objeto;
}

public void setAgente(int i, String nombre){
  nombreAgente[i]=nombre;
  repaint();
}

}
