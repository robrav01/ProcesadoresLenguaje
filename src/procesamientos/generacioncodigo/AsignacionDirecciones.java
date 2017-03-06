package procesamientos.generacioncodigo;

import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.Prog;
import programa.Programa.DecVar;


public class AsignacionDirecciones extends Procesamiento {
   private int dir; 
   public AsignacionDirecciones() {
       dir = 0;
   }
   public void procesa(Prog p) {
      for(Programa.Dec d: p.decs()) 
          d.procesaCon(this);
   }     
   public void procesa(DecVar d) {
       d.ponDir(dir++);
   }     
   public int tamanioDatos() {return dir;}
}
