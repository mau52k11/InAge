package IDE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class IDEAge2000 extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  Pantalla compiladorIDE;

  public IDEAge2000() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    this.setIconImage(null);
    this.setLocale(java.util.Locale.getDefault());
    this.setResizable(false);
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(800,600);
    this.setTitle("");
    this.addWindowListener(new IDEAge2000_this_windowAdapter(this));
    compiladorIDE = new Pantalla();
    compiladorIDE.setVisible(true);
    this.getContentPane().add(compiladorIDE, null);
    this.setUndecorated(true);
  }


}

class IDEAge2000_this_windowAdapter extends java.awt.event.WindowAdapter {
  IDEAge2000 adaptee;

  IDEAge2000_this_windowAdapter(IDEAge2000 adaptee) {
    this.adaptee = adaptee;
  }
}
