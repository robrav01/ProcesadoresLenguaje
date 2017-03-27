package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.omg.Messaging.SyncScopeHelper;

import maquinaP.MaquinaP.Instruccion;



public class MaquinaP {
   private final static String W_ACCESO="**** WARNING: Acceso a memoria sin inicializar";
   private final static String E_ASIGNA="**** ERROR: Los tipos de la expresion y la variable no coinciden";
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
   
   private ICambioSignoInt ICAMBIO_SIGNO_INT;
   private class ICambioSignoInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd = pilaEvaluacion.pop();
		   Valor result;
		   if (opnd == UNKNOWN) 
	           result = UNKNOWN;
		   else
			   result = new ValorInt(opnd.valorInt()*(-1));
		   pilaEvaluacion.push(result);
	       pc++;    
	   }
	   public String toString() {return "cambioSigno";}
   }
   private ICambioSignoReal ICAMBIO_SIGNO_REAL;
   private class ICambioSignoReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd = pilaEvaluacion.pop();
		   Valor result;
		   if (opnd == UNKNOWN) 
	           result = UNKNOWN;
		   else
			   result = new ValorReal(opnd.valorReal()*(-1.0));
		   pilaEvaluacion.push(result);
	       pc++;    
	   }
	   public String toString() {return "cambioSigno";}
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
   private IMultiplicacionInt IMULTIPLICACION_INT;
   private class IMultiplicacionInt implements Instruccion {
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
      public String toString() {return "multiplicación";};
   }
   
   private IMultiplicacionReal IMULTIPLICACION_REAL;
   private class IMultiplicacionReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else{
        	 
             	resul = new ValorReal(opnd1.valorReal()*opnd2.valorReal());
         }
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "multiplicación";};
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
   
   private IIgualInt IIGUAL_INT;
   private class IIgualInt implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()==opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compIgual";};
   }
   
   private IIgualReal IIGUAL_REAL;
   private class IIgualReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorReal()==opnd2.valorReal());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compIgual";};
   }
   
   private IIgualBool IIGUAL_BOOL;
   private class IIgualBool implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorBool()==opnd2.valorBool());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compIgual";};
   }
   
   private IIgualChar IIGUAL_CHAR;
   private class IIgualChar implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()==opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compIgual";};
   }
   
   private IIgualString IIGUAL_STRING;
   private class IIgualString implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorString()==opnd2.valorString());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compIgual";};
   }
   
   private IMenorInt IMENOR_INT;
   private class IMenorInt implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()<opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorReal IMENOR_REAL;
   private class IMenorReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorReal()<opnd2.valorReal());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorBool IMENOR_BOOL;
   private class IMenorBool implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	 if (opnd1.valorBool() == true)
	        		 resul = new ValorBool(false);
	        	 else
	        		 resul = new ValorBool(opnd2.valorBool());  // Programacion Ninja
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorChar IMENOR_CHAR;
   private class IMenorChar implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()<opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorString IMENOR_STRING;
   private class IMenorString implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	  String v = opnd1.valorString();
	        	  String o = opnd2.valorString();
	        	  
	        	  int l = Math.min(v.length(), o.length());
	       	  
	        	  int i = 0;
	       	  
	        	  while (i < l && v.charAt(i)==o.charAt(i))
	        		  i++;
	       	  
	        	  resul = new ValorBool(v.charAt(i) < o.charAt(i));
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMayorInt IMAYOR_INT;
   private class IMayorInt implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()>opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMayorReal IMAYOR_REAL;
   private class IMayorReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorReal()>opnd2.valorReal());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorBool IMAYOR_BOOL;
   private class IMayorBool implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	 if (opnd1.valorBool() == false)
	        		 resul = new ValorBool(false);
	        	 else
	        		 resul = new ValorBool(!opnd2.valorBool());  // Programacion Ninja
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorChar IMAYOR_CHAR;
   private class IMayorChar implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()>opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorString IMAYOR_STRING;
   private class IMayorString implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	  String v = opnd1.valorString();
	        	  String o = opnd2.valorString();
	        	  
	        	  int l = Math.min(v.length(), o.length());
	       	  
	        	  int i = 0;
	       	  
	        	  while (i < l && v.charAt(i)==o.charAt(i))
	        		  i++;
	       	  
	        	  resul = new ValorBool(v.charAt(i) > o.charAt(i));
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorIgualInt IMAYOR_IGUAL_INT;
   private class IMayorIgualInt implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()>=opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMayorIgualReal IMAYOR_IGUAL_REAL;
   private class IMayorIgualReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorReal()>=opnd2.valorReal());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorIgualBool IMAYOR_IGUAL_BOOL;
   private class IMayorIgualBool implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	 if (opnd1.valorBool() == true)
	        		 resul = new ValorBool(true);
	        	 else
	        		 resul = new ValorBool(!opnd2.valorBool());  // Programacion Ninja
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorIgualChar IMAYOR_IGUAL_CHAR;
   private class IMayorIgualChar implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()>=opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMayor";};
   }
   
   private IMayorIgualString IMAYOR_IGUAL_STRING;
   private class IMayorIgualString implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	  String v = opnd1.valorString();
	        	  String o = opnd2.valorString();
	        	  
	        	  int l = Math.min(v.length(), o.length());
	       	  
	        	  int i = 0;
	       	  
	        	  while (i < l && v.charAt(i)==o.charAt(i))
	        		  i++;
	       	  
	        	  resul = new ValorBool(v.charAt(i) >= o.charAt(i));
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
   }
   
   private IMenorIgualInt IMENOR_IGUAL_INT;
   private class IMenorIgualInt implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()<=opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorIgualReal IMENOR_IGUAL_REAL;
   private class IMenorIgualReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorReal()<=opnd2.valorReal());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorIgualBool IMENOR_IGUAL_BOOL;
   private class IMenorIgualBool implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	 if (opnd1.valorBool() == false)
	        		 resul = new ValorBool(true);
	        	 else
	        		 resul = new ValorBool(opnd2.valorBool());  // Programacion Ninja
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorIgualChar IMENOR_IGUAL_CHAR;
   private class IMenorIgualChar implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	             resul = new ValorBool(opnd1.valorInt()<=opnd2.valorInt());
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
   }
   
   private IMenorIgualString IMENOR_IGUAL_STRING;
   private class IMenorIgualString implements Instruccion {
	      public void ejecuta() {
	         Valor opnd2 = pilaEvaluacion.pop(); 
	         Valor opnd1 = pilaEvaluacion.pop();
	         Valor resul;
	         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
	             resul = UNKNOWN;
	         else{
	        	  String v = opnd1.valorString();
	        	  String o = opnd2.valorString();
	        	  
	        	  int l = Math.min(v.length(), o.length());
	       	  
	        	  int i = 0;
	       	  
	        	  while (i < l && v.charAt(i)==o.charAt(i))
	        		  i++;
	       	  
	        	  resul = new ValorBool(v.charAt(i) <= o.charAt(i));
	         }
	         pilaEvaluacion.push(resul);
	         pc++;
	      } 
	      public String toString() {return "compMenor";};
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
      public String toString() {return "módulo";};
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
   
   private class ILeeInt implements Instruccion {
	   private Scanner sc;
	   private int dirVar;
	   
	   ILeeInt(int dir) {
		   dirVar = dir;
		   sc = new Scanner(System.in);
	   }
	@Override
	public void ejecuta() {
		Valor v = null;
		if(sc.hasNextInt()) 
			v = new ValorInt(sc.nextInt());
		else if (sc.hasNextDouble()) 
			v = new ValorReal(sc.nextDouble());
		else if (sc.hasNextBoolean())
			v = new ValorBool(sc.nextBoolean());
		else
			System.err.println(E_ASIGNA);
		
		if (v != null)
			datos[dirVar] = new ValorInt(v.valorInt());
        pc++;
	}
   }
	
	private class ILeeReal implements Instruccion {
		   private Scanner sc;
		   private int dirVar;
		   
		   ILeeReal(int dir) {
			   dirVar = dir;
			   sc = new Scanner(System.in);
		   }
		@Override
		public void ejecuta() {
			Valor v = null;
			if(sc.hasNextDouble()) 
				v = new ValorReal(sc.nextDouble());
			else if (sc.hasNextInt()) 
				v = new ValorReal(sc.nextInt());
			
			if (v != null)
				datos[dirVar] = new ValorReal(v.valorReal());
	        pc++;
		}
   }
	
	private class ILeeChar implements Instruccion {
		   private Scanner sc;
		   private int dirVar;
		   
		   ILeeChar(int dir) {
			   dirVar = dir;
			   sc = new Scanner(System.in);
		   }
		@Override
		public void ejecuta() {
			ValorChar v = new ValorChar(sc.next().charAt(0));
			
			if (v != null)
				datos[dirVar] = new ValorChar(v.valorChar());
	        pc++;
		}
	}
	
	private class ILeeBool implements Instruccion {
		   private Scanner sc;
		   private int dirVar;
		   
		   ILeeBool(int dir) {
			   dirVar = dir;
			   sc = new Scanner(System.in);
		   }
		@Override
		public void ejecuta() {
			Valor v = null;
			if (sc.hasNextInt()) 
				v = new ValorInt(sc.nextInt());
			else if (sc.hasNextBoolean())
				v = new ValorBool(sc.nextBoolean());
			else
				System.err.println(E_ASIGNA);
			
			if (v != null)
				datos[dirVar] = new ValorBool(v.valorBool());
	        pc++;
		}
	}
   
	private class ILeeString implements Instruccion {
		   private Scanner sc;
		   private int dirVar;
		   
		   ILeeString(int dir) {
			   dirVar = dir;
			   sc = new Scanner(System.in);
		   }
		@Override
		public void ejecuta() {
			Valor v = null;
			if (sc.hasNext()) 
				v = new ValorString(sc.next());
			else
				System.err.println(E_ASIGNA);
			
			if (v != null)
				datos[dirVar] = new ValorString(v.valorString());
	        pc++;
		}
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
   public Instruccion multiplicacionInt(){return IMULTIPLICACION_INT;}
   public Instruccion multiplicacionReal(){return IMULTIPLICACION_REAL;}
   public Instruccion divisionInt() {return IDIVISION_INT;}
   public Instruccion divisionReal() {return IDIVISION_REAL;}
   public Instruccion igualInt(){return IIGUAL_INT;}
   public Instruccion igualReal(){return IIGUAL_REAL;}
   public Instruccion igualChar(){return IIGUAL_CHAR;}
   public Instruccion igualBool(){return IIGUAL_BOOL;}
   public Instruccion igualString(){return IIGUAL_STRING;}
   public Instruccion menorInt(){return IMENOR_INT;}
   public Instruccion menorReal(){return IMENOR_REAL;}
   public Instruccion menorChar(){return IMENOR_CHAR;}
   public Instruccion menorBool(){return IMENOR_BOOL;}
   public Instruccion menorString(){return IMENOR_STRING;}
   public Instruccion mayorInt(){return IMAYOR_INT;}
   public Instruccion mayorReal(){return IMAYOR_REAL;}
   public Instruccion mayorChar(){return IMAYOR_CHAR;}
   public Instruccion mayorBool(){return IMAYOR_BOOL;}
   public Instruccion mayorString(){return IMAYOR_STRING;}
   public Instruccion menorIgualInt(){return IMENOR_IGUAL_INT;}
   public Instruccion menorIgualReal(){return IMENOR_IGUAL_REAL;}
   public Instruccion menorIgualChar(){return IMENOR_IGUAL_CHAR;}
   public Instruccion menorIgualBool(){return IMENOR_IGUAL_BOOL;}
   public Instruccion menorIgualString(){return IMENOR_IGUAL_STRING;}
   public Instruccion mayorIgualInt(){return IMAYOR_IGUAL_INT;}
   public Instruccion mayorIgualReal(){return IMAYOR_IGUAL_REAL;}
   public Instruccion mayorIgualChar(){return IMAYOR_IGUAL_CHAR;}
   public Instruccion mayorIgualBool(){return IMAYOR_IGUAL_BOOL;}
   public Instruccion mayorIgualString(){return IMAYOR_IGUAL_STRING;}
   
   public Instruccion leeInt(int dir) {return new ILeeInt(dir);}
   public Instruccion leeReal(int dir) {return new ILeeReal(dir);}
   public Instruccion leeChar(int dir) {return new ILeeChar(dir);}
   public Instruccion leeBool(int dir) {return new ILeeBool(dir);}
   public Instruccion leeCadena(int dir) {return new ILeeString(dir);}
   
   public Instruccion modulo(){return IMODULO;}
   public Instruccion elementoDeCadena(){return IELEMENTODECADENA;}
   public Instruccion conversionInt(){return ICONVERSIONINT;}
   public Instruccion conversionReal(){return ICONVERSIONREAL;}
   public Instruccion cambioSignoInt(){return ICAMBIO_SIGNO_INT;}
   public Instruccion cambioSignoReal(){return ICAMBIO_SIGNO_REAL;}
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
      IMULTIPLICACION_INT = new IMultiplicacionInt();
      IMULTIPLICACION_REAL = new IMultiplicacionReal();
      IDIVISION_INT = new IDivisionInt();
      IDIVISION_REAL = new IDivisionReal();
      ICAMBIO_SIGNO_INT = new ICambioSignoInt();
      ICAMBIO_SIGNO_REAL = new ICambioSignoReal();
      ICONVERSIONINT = new IConversionInt();
      ICONVERSIONREAL = new IConversionReal();
      IMODULO = new IModulo();
      UNKNOWN = new ValorUnknown();
      IIGUAL_INT = new IIgualInt();
      IIGUAL_REAL = new IIgualReal();
      IIGUAL_CHAR = new IIgualChar();
      IIGUAL_BOOL = new IIgualBool();
      IIGUAL_STRING = new IIgualString();
      IMENOR_INT = new IMenorInt();
      IMENOR_REAL = new IMenorReal();
      IMENOR_CHAR = new IMenorChar();
      IMENOR_BOOL= new IMenorBool();
      IMENOR_STRING = new IMenorString();
      IMAYOR_INT= new IMayorInt();
      IMAYOR_REAL= new IMayorReal();
      IMAYOR_CHAR= new IMayorChar();
      IMAYOR_BOOL= new IMayorBool();
      IMAYOR_STRING= new IMayorString();
      IMENOR_IGUAL_INT= new IMenorIgualInt();
      IMENOR_IGUAL_REAL= new IMenorIgualReal();
      IMENOR_IGUAL_CHAR= new IMenorIgualChar();
      IMENOR_IGUAL_BOOL= new IMenorIgualBool();
      IMENOR_IGUAL_STRING= new IMenorIgualString();
      IMAYOR_IGUAL_REAL= new IMayorIgualReal();
      IMAYOR_IGUAL_CHAR= new IMayorIgualChar();
      IMAYOR_IGUAL_BOOL= new IMayorIgualBool();
      IMAYOR_IGUAL_STRING= new IMayorIgualString();
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
