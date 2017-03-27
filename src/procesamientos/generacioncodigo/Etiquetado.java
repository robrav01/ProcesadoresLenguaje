package procesamientos.generacioncodigo;

import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.ConversionEntero;
import programa.Programa.ConversionReal;
import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.Division;
import programa.Programa.CteBool;
import programa.Programa.CteCadenaChar;
import programa.Programa.CteChar;
import programa.Programa.ElementoDeCadena;
import programa.Programa.Modulo;
import programa.Programa.Multiplicacion;
import programa.Programa.Resta;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.CambioSigno;
import programa.Programa.Prog;
import programa.Programa.IBloque;
import programa.Programa.ILee;
import programa.Programa.IWhile;
import programa.Programa.Igual;
import programa.Programa.Mayor;
import programa.Programa.MayorIgual;
import programa.Programa.Menor;
import programa.Programa.MenorIgual;
import programa.Programa.IAsig;
import programa.Programa.Var;


public class Etiquetado extends Procesamiento {
   private int etq; 
   public Etiquetado() {
       etq = 0;
   } 
   
   public void procesa(Var exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaDir(...dir variable...)
      exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CteInt exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaInt(...)
      exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CteBool exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaBool(...)
      exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(CteChar exp) {
	  exp.ponDirPrimeraInstruccion(etq);
	  exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(CteReal exp) {
	  exp.ponDirPrimeraInstruccion(etq);
	  exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(CteCadenaChar exp) {
	  exp.ponDirPrimeraInstruccion(etq);  
	  exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(Suma exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // suma
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Resta exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // suma
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Multiplicacion exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Division exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(Modulo exp) {
	   exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   
   public void procesa(Igual exp) {
	   exp.ponDirPrimeraInstruccion(etq);
	   exp.opnd1().procesaCon(this);
	   exp.opnd2().procesaCon(this);
	   
	   exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Menor exp) {
	   exp.ponDirPrimeraInstruccion(etq);
	   exp.opnd1().procesaCon(this);
	   exp.opnd2().procesaCon(this);
	   
	   exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Mayor exp) {
	   exp.ponDirPrimeraInstruccion(etq);
	   exp.opnd1().procesaCon(this);
	   exp.opnd2().procesaCon(this);
	   
	   exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(MenorIgual exp) {
	   exp.ponDirPrimeraInstruccion(etq);
	   exp.opnd1().procesaCon(this);
	   exp.opnd2().procesaCon(this);
	   
	   exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(MayorIgual exp) {
	   exp.ponDirPrimeraInstruccion(etq);
	   exp.opnd1().procesaCon(this);
	   exp.opnd2().procesaCon(this);
	   
	   exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(ElementoDeCadena exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(And exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // and
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   
   public void procesa(ConversionEntero exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd().procesaCon(this);
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(ConversionReal exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd().procesaCon(this);
       
       exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(CambioSigno exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd().procesaCon(this);
       exp.ponDirInstruccionSiguiente(++etq);
   }
   
   public void procesa(Prog p) {
      p.inst().procesaCon(this);
   }     
   public void procesa(IAsig i) {
      i.ponDirPrimeraInstruccion(etq);
      i.exp().procesaCon(this);
      // desapilaDir
      i.ponDirInstruccionSiguiente(++etq);
   }     
   public void procesa(IBloque b) {
      b.ponDirPrimeraInstruccion(etq);
      for(Programa.Inst i: b.is())
          i.procesaCon(this);
      b.ponDirInstruccionSiguiente(etq);
   }     
   public void procesa(IWhile i) {
      i.ponDirPrimeraInstruccion(etq);
      i.exp().procesaCon(this);
      // ir_f(...)
      etq++;
      i.cuerpo().procesaCon(this);
      // ir_a(...)
      etq++;
      i.ponDirInstruccionSiguiente(etq);
   }     
   public void procesa(ILee i) {
	   i.ponDirPrimeraInstruccion(etq);
	   i.ponDirInstruccionSiguiente(++etq);
   }
}
