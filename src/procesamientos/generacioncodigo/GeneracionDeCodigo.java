package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.ConversionEntero;
import programa.Programa.ConversionReal;
import programa.Programa.CteChar;
import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.Division;
import programa.Programa.CteBool;
import programa.Programa.CteCadenaChar;
import programa.Programa.ElementoDeCadena;
import programa.Programa.Modulo;
import programa.Programa.Multiplicacion;
import programa.Programa.Resta;
import programa.Programa.Suma;
import programa.Programa.Tipo;
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


public class GeneracionDeCodigo extends Procesamiento {
   private MaquinaP maquina;
   private Programa programa;
   public GeneracionDeCodigo(MaquinaP maquina, Programa programa) {
      this.maquina = maquina; 
      this.programa = programa;
   }
   public void procesa(Var exp) {
      maquina.addInstruccion(maquina.apilaDir(exp.declaracion().dir(),exp.enlaceFuente()));         
   } 
   public void procesa(CteInt exp) {
       maquina.addInstruccion(maquina.apilaInt(exp.valEntero()));         
   } 
   public void procesa(CteBool exp) {
       maquina.addInstruccion(maquina.apilaBool(exp.valBool()));         
   }
   public void procesa(CteChar exp){
	   maquina.addInstruccion(maquina.apilaChar(exp.valChar()));
   }
   public void procesa(CteReal exp){
	   maquina.addInstruccion(maquina.apilaReal(exp.valReal()));
   }
   public void procesa(CteCadenaChar exp){
	   maquina.addInstruccion(maquina.apilaString(exp.valString()));
   }
   public void procesa(Modulo exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.modulo());         
   } 
   public void procesa(Suma exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       if (exp.tipo().equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.sumaInt()); 
       else if (exp.tipo().equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.sumaReal()); 
       else if (exp.tipo().equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.sumaCadena());
   }
   public void procesa(Resta exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       if (exp.tipo().equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.restaInt()); 
       else if (exp.tipo().equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.restaReal());
   }
   public void procesa(Multiplicacion exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       if (exp.tipo().equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.multiplicacionInt()); 
       else if (exp.tipo().equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.multiplicacionReal());        
   }
   
   public void procesa(Division exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       if (exp.tipo().equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.divisionInt()); 
       else if (exp.tipo().equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.divisionReal()); 
   }
   
   public void procesa(Igual exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       Tipo t1 = exp.opnd1().tipo();
       Tipo t2 = exp.opnd2().tipo();
       
       if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.igualInt()); 
       else if (t1.equals(programa.tipoReal()) || t2.equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.igualReal()); 
       else if (t1.equals(programa.tipoBool()) && t2.equals(programa.tipoBool()))
    	   maquina.addInstruccion(maquina.igualBool()); 
       else if (t1.equals(programa.tipoChar()) && t2.equals(programa.tipoChar()))
    	   maquina.addInstruccion(maquina.igualChar());
       else if (t1.equals(programa.tipoCadena()) && t2.equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.igualString());
   }
   
   public void procesa(Menor exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       Tipo t1 = exp.opnd1().tipo();
       Tipo t2 = exp.opnd2().tipo();
       
       if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.menorInt()); 
       else if (t1.equals(programa.tipoReal()) || t2.equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.menorReal()); 
       else if (t1.equals(programa.tipoBool()) && t2.equals(programa.tipoBool()))
    	   maquina.addInstruccion(maquina.menorBool()); 
       else if (t1.equals(programa.tipoChar()) && t2.equals(programa.tipoChar()))
    	   maquina.addInstruccion(maquina.menorChar());
       else if (t1.equals(programa.tipoCadena()) && t2.equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.menorString());
   }
   
   public void procesa(Mayor exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       Tipo t1 = exp.opnd1().tipo();
       Tipo t2 = exp.opnd2().tipo();
       
       if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.mayorInt()); 
       else if (t1.equals(programa.tipoReal()) || t2.equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.mayorReal()); 
       else if (t1.equals(programa.tipoBool()) && t2.equals(programa.tipoBool()))
    	   maquina.addInstruccion(maquina.mayorBool()); 
       else if (t1.equals(programa.tipoChar()) && t2.equals(programa.tipoChar()))
    	   maquina.addInstruccion(maquina.mayorChar());
       else if (t1.equals(programa.tipoCadena()) && t2.equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.mayorString());
   }
   
   public void procesa(MenorIgual exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       Tipo t1 = exp.opnd1().tipo();
       Tipo t2 = exp.opnd2().tipo();
       
       if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.menorIgualInt()); 
       else if (t1.equals(programa.tipoReal()) || t2.equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.menorIgualReal()); 
       else if (t1.equals(programa.tipoBool()) && t2.equals(programa.tipoBool()))
    	   maquina.addInstruccion(maquina.menorIgualBool()); 
       else if (t1.equals(programa.tipoChar()) && t2.equals(programa.tipoChar()))
    	   maquina.addInstruccion(maquina.menorIgualChar());
       else if (t1.equals(programa.tipoCadena()) && t2.equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.menorIgualString());
   }
   
   public void procesa(MayorIgual exp) {
	   exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       
       Tipo t1 = exp.opnd1().tipo();
       Tipo t2 = exp.opnd2().tipo();
       
       if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt()))
    	   maquina.addInstruccion(maquina.mayorIgualInt()); 
       else if (t1.equals(programa.tipoReal()) || t2.equals(programa.tipoReal()))
    	   maquina.addInstruccion(maquina.mayorIgualReal()); 
       else if (t1.equals(programa.tipoBool()) && t2.equals(programa.tipoBool()))
    	   maquina.addInstruccion(maquina.mayorIgualBool()); 
       else if (t1.equals(programa.tipoChar()) && t2.equals(programa.tipoChar()))
    	   maquina.addInstruccion(maquina.mayorIgualChar());
       else if (t1.equals(programa.tipoCadena()) && t2.equals(programa.tipoCadena()))
    	   maquina.addInstruccion(maquina.mayorIgualString());
   }
   
   public void procesa(ElementoDeCadena exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.elementoDeCadena());         
   } 
   public void procesa(And exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.and());         
   }
   public void procesa(ConversionEntero exp) {
       exp.opnd().procesaCon(this);
       maquina.addInstruccion(maquina.conversionInt());         
   }
   public void procesa(ConversionReal exp) {
       exp.opnd().procesaCon(this);
       maquina.addInstruccion(maquina.conversionReal());         
   }
   public void procesa(CambioSigno exp) {
	   exp.opnd().procesaCon(this);
	   
	   if (exp.tipo().equals(programa.tipoInt()))
		   maquina.addInstruccion(maquina.cambioSignoInt());
	   else if (exp.tipo().equals(programa.tipoReal()))
		   maquina.addInstruccion(maquina.cambioSignoReal());
   }
   public void procesa(Prog p) {
      p.inst().procesaCon(this);
   }     
   public void procesa(IAsig i) {
      i.exp().procesaCon(this);
      maquina.addInstruccion(maquina.desapilaDir(i.declaracion().dir()));
   }     
   public void procesa(IBloque b) {
      for(Programa.Inst i: b.is())
          i.procesaCon(this);
   }     
   public void procesa(IWhile i) {
      i.exp().procesaCon(this);
      maquina.addInstruccion(maquina.irF(i.dirInstruccionSiguiente()));
      i.cuerpo().procesaCon(this);
      maquina.addInstruccion(maquina.irA(i.dirPrimeraInstruccion()));      
   }     
   public void procesa(ILee i) {
	   Tipo tvar = i.declaracion().tipoDec();
	   
	   if (tvar.equals(programa.tipoInt()))
		maquina.addInstruccion(maquina.leeInt(i.declaracion().dir()));
	   else if (tvar.equals(programa.tipoReal()))
		   maquina.addInstruccion(maquina.leeReal(i.declaracion().dir()));
	   else if (tvar.equals(programa.tipoChar()))
		   maquina.addInstruccion(maquina.leeChar(i.declaracion().dir()));
	   else if (tvar.equals(programa.tipoBool()))
		   maquina.addInstruccion(maquina.leeBool(i.declaracion().dir()));
	   else if (tvar.equals(programa.tipoCadena()))
		   maquina.addInstruccion(maquina.leeCadena(i.declaracion().dir()));
   }
}
