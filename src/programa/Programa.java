package programa;

import procesamientos.Procesamiento;

public abstract class Programa {
   private final Tipo TENT;
   private final Tipo TBOOL;
   private final Tipo TOK;
   private final Tipo TERROR;
   private final Tipo TCHAR;
   private final Tipo TREAL;
   private final Tipo TSTRING;
   
   public Programa() {
      TENT = new Int();
      TBOOL = new Bool();
      TOK = new Ok();
      TERROR = new Error();
      TCHAR = new Char();
      TREAL = new Real();
      TSTRING = new CadenaChar();
   }
   
   public interface Tipo {
     void acepta(Procesamiento p);  
   }
   
   public class Int implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }     
       public String toString() {return "INT";}
   }
   
   public class Bool implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "BOOL";}
   }
   
   public class Ok implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "OK";}
   }
   
   public class Error implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "ERROR";}
   }
   
   public class Char implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "OK";}
   }
   
   public class Real implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "OK";}
   }
   
   public class CadenaChar implements Tipo {
       public void acepta(Procesamiento p) {
          p.procesa(this); 
       }         
       public String toString() {return "OK";}
   }
   
   public class Prog {
     private Dec[] decs;
     private Inst i;
     private Tipo tipo;
     public Prog(Dec[] decs, Inst i) {
       this.decs = decs;
       this.i = i;
       this.tipo = null;
     }
     public Dec[] decs() {return decs;}
     public Inst inst() {return i;}
     public Tipo tipo() {return tipo;}
     public void ponTipo(Tipo tipo) {this.tipo = tipo;}
     public void procesaCon(Procesamiento p) {
       p.procesa(this);
     }
   }

      
   public abstract class Dec {
     public abstract void procesaCon(Procesamiento p);      
   }
   
   public class DecVar extends Dec {
     private String enlaceFuente;
     private String var;
     private Tipo tipoDec;
     private int dir;
     public DecVar(Tipo tipo,String var) {
       this(tipo,var,null);
     }
     public DecVar(Tipo tipo,String var, String enlaceFuente) {
       this.tipoDec = tipo;  
       this.enlaceFuente = enlaceFuente;
       this.var= var;
     }
     public Tipo tipoDec() {return tipoDec;}
     public String var() {
         return var;
     }
     public int dir() {return dir;}
     public void ponDir(int dir) {this.dir = dir;}
     public String enlaceFuente() {
         return enlaceFuente;  
     }
     public void procesaCon(Procesamiento p) {
         p.procesa(this);
     }
   }
   
   public abstract class Inst  {
      private Tipo tipo;
      private int dirPrimeraInstruccion;
      private int dirInstruccionSiguiente;
      public Inst() {
       tipo = null;   
      }
     public Tipo tipo() {return tipo;}
     public void ponTipo(Tipo tipo) {this.tipo = tipo;}
     public int dirPrimeraInstruccion() {
         return dirPrimeraInstruccion;
     }
     public void ponDirPrimeraInstruccion(int dir) {
        dirPrimeraInstruccion = dir;
     }
     public int dirInstruccionSiguiente() {
         return dirInstruccionSiguiente;
     }
     public void ponDirInstruccionSiguiente(int dir) {
        dirInstruccionSiguiente = dir;
     }
     
     public abstract void procesaCon(Procesamiento p); 
   }

   
   public class IAsig extends Inst {
       private String var;
       private Exp exp;
       private String enlaceFuente;
       private DecVar declaracion;
       public IAsig(String var, Exp exp, String enlaceFuente) {
          this.var = var;
          this.exp = exp;
          this.declaracion = null;
          this.enlaceFuente = enlaceFuente;
       }
       public IAsig(String var, Exp exp) {
          this(var,exp,null); 
       }
       public String var() {return var;}
       public Exp exp() {return exp;}
       public DecVar declaracion() {
           return declaracion;
       }
       public String enlaceFuente() {
           return enlaceFuente;
       }
       public void ponDeclaracion(DecVar d) {
          declaracion = d; 
       }
       public void procesaCon(Procesamiento p) {
         p.procesa(this);
       }
   }   

   public class IBloque extends Inst {
       private Inst[] is;
       public IBloque(Inst[] is) {
          this.is = is;
       }
       public Inst[] is() {return is;}
       
       public void procesaCon(Procesamiento p) {
         p.procesa(this);
       }
   }   

   public class IWhile extends Inst {
       private Exp exp;
       private Inst cuerpo;
       private String enlaceFuente;
       public IWhile(Exp exp, Inst cuerpo) {
          this(exp,cuerpo,null); 
       }
       public IWhile(Exp exp, Inst cuerpo, String enlaceFuente) {
          this.exp = exp;
          this.cuerpo = cuerpo;
          this.enlaceFuente = enlaceFuente;
       }
       public Exp exp() {return exp;}
       public Inst cuerpo() {return cuerpo;}
       public String enlaceFuente() {return enlaceFuente;}
      
       public void procesaCon(Procesamiento p) {
         p.procesa(this);
       }
   }   
   

   public abstract class Exp {
     private Tipo tipo;
      private int dirPrimeraInstruccion;
      private int dirInstruccionSiguiente;
     public Exp() {
       tipo = null;
     }
     public void ponTipo(Tipo tipo) {
        this.tipo = tipo;  
     }
     public Tipo tipo() {
         return tipo;  
     }
     public int dirPrimeraInstruccion() {
         return dirPrimeraInstruccion;
     }
     public void ponDirPrimeraInstruccion(int dir) {
        dirPrimeraInstruccion = dir;
     }
     public int dirInstruccionSiguiente() {
         return dirInstruccionSiguiente;
     }
     public void ponDirInstruccionSiguiente(int dir) {
        dirInstruccionSiguiente = dir;
     }
     public abstract void procesaCon(Procesamiento p); 
   }

   public class Var extends Exp {
       private String var;
       private DecVar declaracion;
       private String enlaceFuente;
       public Var(String var) {
         this(var,null);  
       }
       public Var(String var, String enlaceFuente) {
         this.var = var;
         declaracion = null;
         this.enlaceFuente = enlaceFuente;
       }
       public String var() {return var;}
       public DecVar declaracion() {return declaracion;}
       public void ponDeclaracion(DecVar dec) {
           declaracion = dec;
       }
       public String enlaceFuente() {return enlaceFuente;}
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class CteInt extends Exp {
       private int val;
       public CteInt(int val) {
         this.val = val;
       }
       public int valEntero() {return val;}   
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class CteBool extends Exp {
       private boolean val;
       public CteBool(boolean val) {
         this.val = val;
       }
       public boolean valBool() {return val;}      
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   
   public class CteChar extends Exp {
       private char val;
       public CteChar(char val) {
         this.val = val;
       }
       public char valChar() {return val;}      
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   
   public class CteReal extends Exp {
       private double val;
       public CteReal(double val) {
         this.val = val;
       }
       public double valReal() {return val;}      
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   
   public class CteCadenaChar extends Exp {
       private String val;
       public CteCadenaChar(String val) {
         this.val = val;
       }
       public String valCteCadenaChar() {return val;}      
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class ConversionEntero extends Exp {
	   private Exp opnd;
       private String enlaceFuente;
       public ConversionEntero(Exp opnd) {
    	   this(opnd,null);
       }
       public ConversionEntero(Exp opnd,String enlaceFuente) {
	      this.enlaceFuente = enlaceFuente; 
	      this.opnd = opnd;
	     }
       public Exp opnd() {return opnd;}
       public String enlaceFuente() {return enlaceFuente;}     
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class ConversionReal extends Exp {
	   private Exp opnd;
       private String enlaceFuente;
       public ConversionReal(Exp opnd) {
    	   this(opnd,null);
       }
       public ConversionReal(Exp opnd,String enlaceFuente) {
	      this.enlaceFuente = enlaceFuente; 
	      this.opnd = opnd;
	     }
       public Exp opnd() {return opnd;}
       public String enlaceFuente() {return enlaceFuente;}     
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   
   private abstract class ExpBin extends Exp {
       private Exp opnd1;
       private Exp opnd2;
       private String enlaceFuente;
       public ExpBin(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public ExpBin(Exp opnd1, Exp opnd2,String enlaceFuente) {
         this.enlaceFuente = enlaceFuente; 
         this.opnd1 = opnd1;
         this.opnd2 = opnd2;
       }
     public Exp opnd1() {return opnd1;}
     public Exp opnd2() {return opnd2;}
     public String enlaceFuente() {return enlaceFuente;}
   }

   public class Suma extends ExpBin {
       public Suma(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public Suma(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class Resta extends ExpBin {
       public Resta(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public Resta(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class Modulo extends ExpBin {
       public Modulo(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public Modulo(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class Multiplicacion extends ExpBin {
       public Multiplicacion(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public Multiplicacion(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class ElementoDeCadena extends ExpBin {
       public ElementoDeCadena(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public ElementoDeCadena(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }
   public class And extends ExpBin {
       public And(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public And(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
       public void procesaCon(Procesamiento p) {
          p.procesa(this); 
       }
   }

   public Prog prog(Dec[] decs, Inst i) {
      return new Prog(decs,i);  
   }
   
   public Dec decvar(Tipo t, String v) {
      return new DecVar(t,v);  
   }
   
   public Dec decvar(Tipo t, String v, String enlaceFuente) {
      return new DecVar(t,v,enlaceFuente);  
   }
   
   public Inst iasig(String v, Exp e) {
      return new IAsig(v,e);  
   }
   
   public Inst iasig(String v, Exp e, String enlaceFuente) {
      return new IAsig(v,e,enlaceFuente);  
   }

   public Inst ibloque(Inst[] is) {
      return new IBloque(is);  
   }
   public Inst iwhile(Exp exp, Inst cuerpo) {
      return new IWhile(exp,cuerpo);  
   }
   public Inst iwhile(Exp exp, Inst cuerpo, String enlaceFuente) {
      return new IWhile(exp,cuerpo,enlaceFuente);  
   }

   public Exp var(String id) {
      return new Var(id);  
   }
   public Exp var(String id, String enlaceFuente) {
      return new Var(id,enlaceFuente);  
   }
  public Exp cteint(int val) {
      return new CteInt(val);  
   }
   public Exp ctebool(boolean val) {
      return new CteBool(val);  
   }
   public Exp conversionEntero(Exp exp1) {
      return new ConversionEntero(exp1);  
   }
   public Exp conversionReal(Exp exp1) {
      return new ConversionReal(exp1);  
   }
   public Exp suma(Exp exp1, Exp exp2) {
      return new Suma(exp1, exp2);  
   }
   public Exp multiplicacion(Exp exp1, Exp exp2) {
      return new Multiplicacion(exp1, exp2);  
   }
   public Exp modulo(Exp exp1, Exp exp2) {
      return new Modulo(exp1, exp2);  
   }
   public Exp elementoDeCadena(Exp exp1, Exp exp2) {
      return new ElementoDeCadena(exp1, exp2);  
   }
   public Exp and(Exp exp1, Exp exp2) {
      return new And(exp1, exp2);  
   }
   public Exp suma(Exp exp1, Exp exp2, String enlaceFuente) {
      return new Suma(exp1, exp2, enlaceFuente);  
   }
   public Exp resta(Exp exp1, Exp exp2) {
	      return new Resta(exp1, exp2);  
	   }
   public Exp and(Exp exp1, Exp exp2, String enlaceFuente) {
      return new And(exp1, exp2, enlaceFuente);  
   }
   public Exp modulo(Exp exp1, Exp exp2, String enlaceFuente) {
      return new Modulo(exp1, exp2, enlaceFuente);  
   }
   public Exp multiplicacion(Exp exp1, Exp exp2, String enlaceFuente) {
      return new Multiplicacion(exp1, exp2, enlaceFuente);  
   }
   public Exp elementoDeCadena(Exp exp1, Exp exp2, String enlaceFuente) {
      return new ElementoDeCadena(exp1, exp2, enlaceFuente);  
   }
   public Tipo tipoInt() {return TENT;}
   public Tipo tipoBool() {return TBOOL;}
   public Tipo tipoOk() {return TOK;}
   public Tipo tipoChar() {return TCHAR;}
   public Tipo tipoReal() {return TREAL;}
   public Tipo tipoString() {return TSTRING;}
   public Tipo tipoError() {return TERROR;}
   
   public abstract Prog raiz();
   
}
