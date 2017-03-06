package procesamientos.impresion;

import procesamientos.Procesamiento;
import programa.Programa.ConversionEntero;
import programa.Programa.ConversionReal;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.ElementoDeCadena;
import programa.Programa.Modulo;
import programa.Programa.Multiplicacion;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.Dec;
import programa.Programa.DecVar;
import programa.Programa.Exp;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.Inst;
import programa.Programa.Prog;
import programa.Programa.Var;
import programa.Programa.IWhile;


public class Impresion extends Procesamiento {
   private boolean atributos;
   private int identacion;
   public Impresion(boolean atributos) {
     this.atributos = atributos; 
     identacion = 0;
   }
   public Impresion() {
     this(false);
   }
    
   private void imprimeAtributos(Exp exp) {
       if(atributos) {
          System.out.print("@{t:"+exp.tipo()+"}"); 
       }
   }
   private void imprimeAtributos(Prog prog) {
       if(atributos) {
          System.out.print("@{t:"+prog.tipo()+"}"); 
       }
   }
   private void imprimeAtributos(Inst i) {
       if(atributos) {
          System.out.print("@{t:"+i.tipo()+",dirc:"+i.dirPrimeraInstruccion()+",dirs:"+
                           i.dirInstruccionSiguiente()+"}");       
     }
   }
       
   private void identa() {
      for (int i=0; i < identacion; i++)
          System.out.print(" ");
   }
     
   public void procesa(CteInt exp) {
     System.out.print(exp.valEntero());
     imprimeAtributos(exp);
   } 
   public void procesa(CteBool exp) {
     System.out.print(exp.valBool());
     imprimeAtributos(exp);
   } 
   public void procesa(Var exp) {
     System.out.print(exp.var());
     imprimeAtributos(exp);
   }
   public void procesa(Modulo exp) {
	   System.out.print('('); 
	     exp.opnd1().procesaCon(this);
	     System.out.print('%');
	     imprimeAtributos(exp);
	     exp.opnd2().procesaCon(this);
	     System.out.print(')'); 
   }
   public void procesa(Suma exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('+');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void procesa(Multiplicacion exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('*');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void procesa(And exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("&&");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void procesa(ElementoDeCadena exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print(",");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void procesa(ConversionEntero exp) {
     System.out.print('('); 
     exp.opnd().procesaCon(this);
     imprimeAtributos(exp);
     System.out.print(')'); 
   }
   public void procesa(ConversionReal exp) {
     System.out.print('('); 
     exp.opnd().procesaCon(this);
     imprimeAtributos(exp);
     System.out.print(')'); 
   }
   public void procesa(Prog p) {
      for(Dec d: p.decs()) 
          d.procesaCon(this);
      p.inst().procesaCon(this);
      imprimeAtributos(p);
      System.out.println();
   }     
   public void procesa(DecVar t) {
      System.out.print(t.tipoDec()+" "+t.var());    
      System.out.println();
   }     
   public void procesa(IAsig i) {
      identa(); 
      System.out.print(i.var()+"=");
      i.exp().procesaCon(this);
      imprimeAtributos(i);
      System.out.println(); 
   }     
   public void procesa(IBloque b) {
      identa(); 
      System.out.println("{");
      identacion += 3;
      for(Inst i: b.is())
          i.procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   }     
   public void procesa(IWhile b) {
      identa(); 
      System.out.print("while ");
      b.exp().procesaCon(this);
      System.out.println(" do {");
      identacion += 3;
      b.cuerpo().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   }     
}   