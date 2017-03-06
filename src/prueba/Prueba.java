
package prueba;

import errores.Errores;
import maquinaP.MaquinaP;
import procesamientos.comprobaciontipos.ComprobacionTipos;
import procesamientos.comprobaciontipos.Vinculacion;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.Etiquetado;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
import programa.Programa;


public class Prueba extends Programa {
   private Prog programa;
   public Prueba() {
     programa = prog(new Dec[]{decvar(tipoInt(),"x","<dec1>"),
                               decvar(tipoInt(),"y","<dec2>"),
                               decvar(tipoBool(),"z","<dec3>")
                              }, 
                     ibloque(
                          new Inst[]{iasig("z", ctebool(true)),iwhile(ctebool(true),
                                     ibloque(new Inst[]{
                                          iasig("x",  
                                                suma(suma(cteint(5),cteint(6),"<asig 1>"),
                                                 cteint(25),"<asig 1>"),"<asig 1>"),
                                          iasig("y",  
                                                suma(suma(var("x","<asig 2>"),cteint(6),"<asig 2>"),
                                                cteint(25),"<asig 2>"), "<asig 2>"),
                                          iasig("z", ctebool(false))}),"<while>")}));
   }
   public Prog raiz() {
      return programa; 
   } 
   public static void main(String[] args) {
      Prueba programa = new Prueba();
      Impresion impresion = new Impresion();
      programa.raiz().procesaCon(impresion);
      Errores errores = new Errores();
      Vinculacion vinculacion = new Vinculacion(errores);
      programa.raiz().procesaCon(vinculacion);
      if (! vinculacion.error()) {
        ComprobacionTipos ctipos = new ComprobacionTipos(programa,errores); 
        programa.raiz().procesaCon(ctipos);
        if (programa.raiz().tipo().equals(programa.tipoOk())) {
           AsignacionDirecciones asignaciondirs = new AsignacionDirecciones();
           programa.raiz().procesaCon(asignaciondirs);
           Etiquetado etiquetado = new Etiquetado();
           programa.raiz().procesaCon(etiquetado);
           impresion = new Impresion(true);
           programa.raiz().procesaCon(impresion);
           MaquinaP maquina = new MaquinaP(asignaciondirs.tamanioDatos());
           GeneracionDeCodigo generacioncod = new GeneracionDeCodigo(maquina);
           programa.raiz().procesaCon(generacioncod);
           maquina.muestraCodigo(); 
           maquina.ejecuta();
           maquina.muestraEstado();
           maquina.ejecuta();
        }
        
      }
      
   }
}
  