package Sintactico;

public class OpEntero{
  private Sintactico.AnalizadorSintactico sintactico;
 //&,|,op_aritm,inicio,entonces,hacer,si,mientras,repite,solicita,veces,variable,;,entero,decimal
  private int follow_set[]={6,7,19,22,21,25,5,4,3,1,16,17,12,8,13,9,15,11,124,108,122,129,125,127,131,22,135};
  private int header_set[]={113,114,102};// finConducta,finComportamiento,acciones

  public OpEntero(Sintactico.AnalizadorSintactico sint){
  sintactico = sint;
  }

  public void analisis()throws Exception{
  CpoEntero cpoEntero = new CpoEntero(sintactico);
//  sintactico.verifica_entrada(12, follow_set, header_set);
  if(sintactico.compara(16)==true){
    sintactico.comparar(16); // (
    sintactico.setCodigoObjeto('(');
    cpoEntero.analisis();
    sintactico.comparar(17);// )
    sintactico.setCodigoObjeto(')');
    }
  else
    cpoEntero.analisis();
  sintactico.verifica_entrada(12, follow_set, header_set);
  }//fin de analisis()

}// fin de la clase TInstrucciones
