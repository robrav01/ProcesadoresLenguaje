package procesamientos;

import programa.Programa.CadenaChar;
import programa.Programa.Char;
import programa.Programa.ConversionEntero;
import programa.Programa.ConversionReal;
import programa.Programa.CteCadenaChar;
import programa.Programa.CteChar;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.CteReal;
import programa.Programa.ElementoDeCadena;
import programa.Programa.Modulo;
import programa.Programa.Multiplicacion;
import programa.Programa.Real;
import programa.Programa.Resta;
import programa.Programa.Var;
import programa.Programa.Suma;
import programa.Programa.Prog;
import programa.Programa.DecVar;
import programa.Programa.Division;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.IWhile;
import programa.Programa.And;
import programa.Programa.Int;
import programa.Programa.Bool;
import programa.Programa.Error;
import programa.Programa.Ok;

public class Procesamiento {

   /*
    * CONSTANTES	
    */
   public void procesa(CteInt exp) {} 
   public void procesa(CteBool exp) {} 
   public void procesa(CteChar cteChar) {}
   public void procesa(CteReal cteReal) {}
   public void procesa(CteCadenaChar cteCadenaChar) {}
   
   /*
    * OPERACIONES
    */
   public void procesa(Suma exp) {} 
   public void procesa(Resta resta) {} 
   public void procesa(Multiplicacion multiplicacion) {}
   public void procesa(Division division) {}
   public void procesa(Modulo modulo) {}
   public void procesa(ElementoDeCadena elementoDeCadena) {}
   public void procesa(And exp) {} 
   public void procesa(Var exp) {} 
   
   /*
    * TIPOS
    */
   public void procesa(Int t) {}     
   public void procesa(Bool t) {}   
   public void procesa(CadenaChar cadenaChar) {}
   public void procesa(Real real) {}
   public void procesa(Char c) {}
   public void procesa(Ok t) {}     
   public void procesa(Error t) {} 
   
   /*
    * INSTRUCCIONES
    */
   public void procesa(Prog p) {}     
   public void procesa(DecVar d) {}     
   public void procesa(IAsig i) {}     
   public void procesa(IBloque i) {}     
   public void procesa(IWhile i) {}
   
   /*
    * CONVERSIONES DE TIPOS
    */
   public void procesa(ConversionEntero conversionEntero) {}
   public void procesa(ConversionReal conversionReal) {}
      
}
