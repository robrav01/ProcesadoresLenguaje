package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class MaquinaP {
   private final static String W_ACCESO="**** WARNING: Acceso a memoria sin inicializar";
   private final Valor UNKNOWN; 
   private class Valor {
      public int valorInt() {throw new UnsupportedOperationException();}  
      public boolean valorBool() {throw new UnsupportedOperationException();}
      public char valorChar() {throw new UnsupportedOperationException();}
      public double valorReal() {throw new UnsupportedOperationException();}
      public String valorString() {throw new UnsupportedOperationException();}
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public double valorReal(){
    	  return valor;
      }
      public boolean valorBool() {if(valor==0)return false; return true;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorChar extends Valor {
      private char valor;
      public ValorChar(char valor) {
         this.valor = valor; 
      }
      public int valorInt(){
    	  return valor;
      }
      public double valorReal(){
    	  return valor;
      }
      public char valorChar() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorReal extends Valor {
      private double valor;
      public ValorReal(double valor2) {
         this.valor = valor2; 
      }
      
      public int valorInt(){
    	  return (int) valor;
      }
      public double valorReal() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorString extends Valor {
      private String valor;
      public ValorString(String valor) {
         this.valor = valor; 
      }
      public String valorString() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public int valorInt() {if(valor) return 1; return 0;}
      public double valorReal() {if(valor) return 1; return 0;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorUnknown extends Valor {
      public String toString() {
        return "?";
      }
   }
   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   
   private ICambioSigno ICAMBIO_SIGNO;
   private class ICambioSigno implements Instruccion {
	   public void ejecuta() {
		   Valor opnd = pilaEvaluacion.pop();
		   Valor result;
		   if (opnd == UNKNOWN) 
	           result = UNKNOWN;
		   /*else
			   // !FUCK!
		    */ 
		   //pilaEvaluacion.push(result);
	       pc++;    
	   }
	   public String toString() {return "suma";}
   }
   private ISumaInt ISUMA_INT;
   private class ISumaInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorInt(opnd1.valorInt()+opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
   }
   private ISumaReal ISUMA_REAL;
   private class ISumaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorReal(opnd1.valorReal()+opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
   }
   
   private ISumaCadena ISUMA_CADENA;
   private class ISumaCadena implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorString(opnd1.valorString().concat(opnd2.valorString()));
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
   }
   
   private IRestaInt IRESTA_INT;
   private class IRestaInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorInt(opnd1.valorInt() - opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "resta";};
   }
   private IRestaReal IRESTA_REAL;
   private class IRestaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorReal(opnd1.valorReal() - opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "resta";};
   }
   private IMultiplicacion IMULTIPLICACION;
   private class IMultiplicacion implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else{
        	 
             	resul = new ValorInt(opnd1.valorInt()*opnd2.valorInt());
         }
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "multiplicaci�n";};
   }
   
   private IDivisionInt IDIVISION_INT;
   private class IDivisionInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else{
             resul = new ValorInt(opnd1.valorInt()/opnd2.valorInt());
         }
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "division";};
   }
   
   private IDivisionReal IDIVISION_REAL;
   private class IDivisionReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else{
             resul = new ValorReal(opnd1.valorReal()/opnd2.valorReal());
         }
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "division";};
   }
   
   private IModulo IMODULO;
   private class IModulo implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else{
        	 
             	resul = new ValorInt(opnd1.valorInt()%opnd2.valorInt());
         }
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "m�dulo";};
   }
   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorBool(opnd1.valorBool()&&opnd2.valorBool());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "and";};
   }
   private IElementoDeCadena IELEMENTODECADENA;
   private class IElementoDeCadena implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorChar(opnd1.valorString().charAt(opnd2.valorInt()));
         pilaEvaluacion.push(resul);
         pc++;
      }
	 public String toString() {return "and";};
   }
   private IConversionInt ICONVERSIONINT;
   private class IConversionInt implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop(); 
         Valor resul;
         if (opnd1 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorInt(opnd1.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
      }
	 public String toString() {return "conversionInt";};
   }
   private IConversionReal ICONVERSIONREAL;
   private class IConversionReal implements Instruccion {
	   public void ejecuta() {
	         Valor opnd1 = pilaEvaluacion.pop(); 
	         Valor resul;
	         if (opnd1 == UNKNOWN) 
	             resul = UNKNOWN;
	         else 
	             resul = new ValorReal(opnd1.valorReal());
	         pilaEvaluacion.push(resul);
	         pc++;
	      }
	 public String toString() {return "conversionReal";};
   }
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
   }
   private class IApilaChar implements Instruccion {
      private char valor;
      public IApilaChar(char valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorChar(valor)); 
         pc++;
      } 
      public String toString() {return "apilaChar("+valor+")";};
   }
   private class IApilaReal implements Instruccion {
      private double valor;
      public IApilaReal(double valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorReal(valor)); 
         pc++;
      } 
      public String toString() {return "apilaReal("+valor+")";};
   }
   private class IApilaString implements Instruccion {
      private String valor;
      public IApilaString(String valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorString(valor)); 
         pc++;
      } 
      public String toString() {return "apilaString("+valor+")";};
   }
   private class IDesapilaDir implements Instruccion {
      private int dir;
      public IDesapilaDir(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         datos[dir] = pilaEvaluacion.pop();
         pc++;
      } 
      public String toString() {return "desapilaDir("+dir+")";};
   }
   private class IApilaDir implements Instruccion {
      private int dir;
      private String enlaceFuente;
      public IApilaDir(int dir) {
        this(dir,null);  
      }
      public IApilaDir(int dir, String enlaceFuente) {
        this.enlaceFuente = enlaceFuente;  
        this.dir = dir;  
      }
      public void ejecuta() {
         if(datos[dir] == null) { 
             System.err.println(enlaceFuente+":"+W_ACCESO); 
             pilaEvaluacion.push(UNKNOWN); 
         }     
         else 
              pilaEvaluacion.push(datos[dir]);
         pc++;
      } 
      public String toString() {return "apilaDir("+dir+","+enlaceFuente+")";};
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }

   private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ira("+dir+")";};
   }

      private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "irf("+dir+")";};
   }

   
   public Instruccion sumaInt() {return ISUMA_INT;}
   public Instruccion sumaReal() {return ISUMA_REAL;}
   public Instruccion sumaCadena() {return ISUMA_CADENA;}
   public Instruccion restaInt() {return IRESTA_INT;}
   public Instruccion restaReal() {return IRESTA_REAL;}
   public Instruccion and() {return IAND;}
   public Instruccion multiplicacion(){return IMULTIPLICACION;}
   public Instruccion divisionInt() {return IDIVISION_INT;}
   public Instruccion divisionReal() {return IDIVISION_REAL;}
   public Instruccion modulo(){return IMODULO;}
   public Instruccion elementoDeCadena(){return IELEMENTODECADENA;}
   public Instruccion conversionInt(){return ICONVERSIONINT;}
   public Instruccion conversionReal(){return ICONVERSIONREAL;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilaChar(char val) {return new IApilaChar(val);}
   public Instruccion apilaReal(double val) {return new IApilaReal(val);}
   public Instruccion apilaString(String val) {return new IApilaString(val);}
   public Instruccion desapilaDir(int dir) {return new IDesapilaDir(dir);}
   public Instruccion apilaDir(int dir) {return new IApilaDir(dir);}
   public Instruccion apilaDir(int dir,String dinfo) {return new IApilaDir(dir,dinfo);}
   public Instruccion irA(int dir) {return new IIrA(dir);}
   public Instruccion irF(int dir) {return new IIrF(dir);}
   
   public void addInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   public MaquinaP(int tamdatos) {
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos];
      this.pc = 0;
      ISUMA_INT = new ISumaInt();
      ISUMA_REAL = new ISumaReal();
      ISUMA_CADENA = new ISumaCadena();
      IAND = new IAnd();
      IRESTA_INT = new IRestaInt();
      IRESTA_REAL = new IRestaReal();
      IMULTIPLICACION = new IMultiplicacion();
      IDIVISION_INT = new IDivisionInt();
      IDIVISION_REAL = new IDivisionReal();
      ICAMBIO_SIGNO = new ICambioSigno();
      ICONVERSIONINT = new IConversionInt();
      ICONVERSIONREAL = new IConversionReal();
      IMODULO = new IModulo();
      UNKNOWN = new ValorUnknown();
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
}
