package procesamientos.comprobaciontipos;

import errores.Errores;
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
import programa.Programa.Suma;
import programa.Programa.Tipo;
import programa.Programa.And;
import programa.Programa.CambioSigno;
import programa.Programa.ConversionBool;
import programa.Programa.ConversionChar;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.ILee;
import programa.Programa.IWhile;
import programa.Programa.Igual;
import programa.Programa.Inst;
import programa.Programa.Mayor;
import programa.Programa.MayorIgual;
import programa.Programa.Menor;
import programa.Programa.MenorIgual;
import programa.Programa.Prog;
import programa.Programa.Resta;
import programa.Programa.Var;


public class ComprobacionTipos extends Procesamiento { 
   private final static String ERROR_TIPO_OPERANDOS="Los tipos de los operandos no son correctos";
   private final static String ERROR_ASIG="Tipos no compatibles en asignacion";
   private final static String ERROR_COND="Tipo erroneo en condicion";
   private Programa programa;
   private Errores errores;
   public ComprobacionTipos(Programa programa, Errores errores) {
     this.programa = programa;  
     this.errores = errores;
   }

   public void procesa(Var exp) {
      exp.ponTipo(exp.declaracion().tipoDec());
   } 
   public void procesa(CteInt exp) {
      exp.ponTipo(programa.tipoInt());
   } 
   public void procesa(CteBool exp) {
      exp.ponTipo(programa.tipoBool());
   } 
   public void procesa(CteReal exp) {
	      exp.ponTipo(programa.tipoReal());
   } 
   public void procesa(CteChar exp) {
	      exp.ponTipo(programa.tipoChar());
   } 
   public void procesa(CteCadenaChar exp) {
	      exp.ponTipo(programa.tipoCadena());
   } 
   
   public void procesa(Suma exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
         exp.opnd2().tipo().equals(programa.tipoInt())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else if((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
    		  (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal()))){
    	  exp.ponTipo(programa.tipoReal()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoCadena()) &&
    	         exp.opnd2().tipo().equals(programa.tipoCadena())) {
          exp.ponTipo(programa.tipoCadena()); 
       }
      else{
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   } 
   
   public void procesa(Resta exp) {
	      exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      Tipo t1 = exp.opnd1().tipo();
	      Tipo t2 = exp.opnd2().tipo();
	      
	      // T1::Real y T2::Real o Int
	      if(t1.equals(programa.tipoReal()) &&
	    		  (t2.equals(programa.tipoInt()) || t2.equals(programa.tipoReal()))) {
	         exp.ponTipo(programa.tipoReal()); 
	      }
	      // T1::Real o Int y T2::Real
	      else if (t2.equals(programa.tipoReal()) &&
	    		  (t1.equals(programa.tipoInt()) || t1.equals(programa.tipoReal()))) {
		         exp.ponTipo(programa.tipoReal());
	      }
	      // T1::Int y T2::Int
	      else if (t1.equals(programa.tipoInt()) && t2.equals(programa.tipoInt())) {
	    	  exp.ponTipo(programa.tipoInt());
	      }
	      else {
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
    } 
   
   public void procesa(Multiplicacion exp) {
	      exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
	         exp.opnd2().tipo().equals(programa.tipoInt())) {
	         exp.ponTipo(programa.tipoInt()); 
	      }
	      else if((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	    		  (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal()))){
	    	  exp.ponTipo(programa.tipoReal()); 
	      }
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   } 
   
   public void procesa(Division exp) {
	      exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
	         exp.opnd2().tipo().equals(programa.tipoInt())) {
	         exp.ponTipo(programa.tipoInt()); 
	      }
	      else if((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	    		  (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal()))){
	    	  exp.ponTipo(programa.tipoReal()); 
	      }
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   } 
   
   public void procesa(Igual exp) {
	   exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      if ((exp.opnd1().tipo().equals(programa.tipoInt()) &&
	           exp.opnd2().tipo().equals(programa.tipoInt()))
	    	   ||
	    	   ((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	 	       (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal())))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoBool()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoBool()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoChar()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoChar()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoCadena()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoCadena()))) {
	    	  	exp.ponTipo(programa.tipoBool()); 
	      }
	
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }         
   }
  
   public void procesa(Menor exp) {
	   	  exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      if ((exp.opnd1().tipo().equals(programa.tipoInt()) &&
	           exp.opnd2().tipo().equals(programa.tipoInt()))
	    	   ||
	    	   ((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	 	       (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal())))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoBool()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoBool()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoChar()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoChar()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoCadena()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoCadena()))) {
	    	  	exp.ponTipo(programa.tipoBool()); 
	      }
	
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }        
   }
   
   public void procesa(Mayor exp) {
	      exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      if ((exp.opnd1().tipo().equals(programa.tipoInt()) &&
	           exp.opnd2().tipo().equals(programa.tipoInt()))
	    	   ||
	    	   ((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	 	       (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal())))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoBool()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoBool()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoChar()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoChar()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoCadena()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoCadena()))) {
	    	  	exp.ponTipo(programa.tipoBool()); 
	      }
	
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   }
   
   public void procesa(MenorIgual exp) {
	   exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      if ((exp.opnd1().tipo().equals(programa.tipoInt()) &&
	           exp.opnd2().tipo().equals(programa.tipoInt()))
	    	   ||
	    	   ((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	 	       (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal())))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoBool()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoBool()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoChar()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoChar()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoCadena()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoCadena()))) {
	    	  	exp.ponTipo(programa.tipoBool()); 
	      }
	
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   }
   
   public void procesa(MayorIgual exp) {
	   exp.opnd1().procesaCon(this);
	      exp.opnd2().procesaCon(this);
	      
	      if ((exp.opnd1().tipo().equals(programa.tipoInt()) &&
	           exp.opnd2().tipo().equals(programa.tipoInt()))
	    	   ||
	    	   ((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
	 	       (exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal())))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoBool()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoBool()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoChar()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoChar()))
	    	   ||
	    	   (exp.opnd1().tipo().equals(programa.tipoCadena()) &&
	  	 	    exp.opnd2().tipo().equals(programa.tipoCadena()))) {
	    	  	exp.ponTipo(programa.tipoBool()); 
	      }
	
	      else{
	         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
	             ! exp.opnd2().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
	}
   
   public void procesa(CambioSigno exp) {
	      exp.opnd().procesaCon(this);
	      
	      if(exp.opnd().tipo().equals(programa.tipoInt())) {
	         exp.ponTipo(programa.tipoInt()); 
	      }
	      else if(exp.opnd().tipo().equals(programa.tipoReal())){
	    	  exp.ponTipo(programa.tipoReal()); 
	      }
	      else{
	         if (! exp.opnd().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   } 
   
   public void procesa(Modulo exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      
      if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
         exp.opnd2().tipo().equals(programa.tipoInt())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else{
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
	   
   }
   public void procesa(ElementoDeCadena exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoCadena()) &&
         exp.opnd2().tipo().equals(programa.tipoInt())) {
         exp.ponTipo(programa.tipoChar()); 
      }
      else{
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
	   
   }
   public void procesa(ConversionEntero exp) {
      exp.opnd().procesaCon(this);
      if(exp.opnd().tipo().equals(programa.tipoInt()) ||
         exp.opnd().tipo().equals(programa.tipoReal()) ||
         exp.opnd().tipo().equals(programa.tipoBool()) ||
         exp.opnd().tipo().equals(programa.tipoChar())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else{
         if (! exp.opnd().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
	   
   }
   public void procesa(ConversionReal exp) {
      exp.opnd().procesaCon(this);
      if(exp.opnd().tipo().equals(programa.tipoInt()) ||
         exp.opnd().tipo().equals(programa.tipoReal()) ||
         exp.opnd().tipo().equals(programa.tipoBool()) ||
         exp.opnd().tipo().equals(programa.tipoChar())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else{
         if (! exp.opnd().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
	   
  }
   public void procesa(ConversionChar exp) {
	      exp.opnd().procesaCon(this);
	      if(exp.opnd().tipo().equals(programa.tipoInt())) {
	         exp.ponTipo(programa.tipoChar()); 
	      }
	      else{
	         if (! exp.opnd().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   }
   public void procesa(ConversionBool exp) {
	      exp.opnd().procesaCon(this);
	      if(exp.opnd().tipo().equals(programa.tipoInt())) {
	         exp.ponTipo(programa.tipoBool()); 
	      }
	      else{
	         if (! exp.opnd().tipo().equals(programa.tipoError()))
	             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
	         exp.ponTipo(programa.tipoError());
	      }     
   }
   public void procesa(And exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool()) &&
         exp.opnd2().tipo().equals(programa.tipoBool())) {
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Prog p) {
     p.inst().procesaCon(this);
     p.ponTipo(p.inst().tipo());
   }     
   public void procesa(IAsig i) {
     i.exp().procesaCon(this);  
     if(! i.declaracion().tipoDec().equals(i.exp().tipo())) {
         if (! i.exp().tipo().equals(programa.tipoError()))
             errores.msg(i.enlaceFuente()+":"+ERROR_ASIG);
        i.ponTipo(programa.tipoError()); 
     }
     else {
        i.ponTipo(programa.tipoOk());  
     }
   }     
   public void procesa(IBloque b) {
      boolean ok=true;
      for (Inst i: b.is()) {
         i.procesaCon(this);
         ok = ok && i.tipo().equals(programa.tipoOk());
      }
      if (ok) 
        b.ponTipo(programa.tipoOk());
      else
       b.ponTipo(programa.tipoError());   
   }     
   public void procesa(IWhile i) {
       i.exp().procesaCon(this);
       if (! i.exp().tipo().equals(programa.tipoError()) &&
           ! i.exp().tipo().equals(programa.tipoBool())) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       
       }   
       i.cuerpo().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoBool()) &&
          i.cuerpo().tipo().equals(programa.tipoOk())) {
          i.ponTipo(programa.tipoOk()); 
       }
       else {
          i.ponTipo(programa.tipoError()); 
       }
   }     
   public void procesa(ILee i) {
	   i.ponTipo(programa.tipoOk());
   }
}
