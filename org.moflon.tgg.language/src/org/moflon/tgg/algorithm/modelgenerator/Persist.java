package org.moflon.tgg.algorithm.modelgenerator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;

public class Persist
{

   public static void persist(DataContainer state)
   {

      List<EObject> src = new ArrayList<EObject>();
      src.addAll(state.getSrcTempOutputContainer().getPotentialRoots());

      List<EObject> trg = new ArrayList<EObject>();
      trg.addAll(state.getTrgTempOutputContainer().getPotentialRoots());

      String folderName = "instances/generatedModels_" + generateTimeStamp() + "/";

      if (src.size() > 1)
      {
         String fileName = folderName + state.getModelgenStats().getSrcElementCount() + "_" + state.getModelgenStats().getSrcConnectorCount()
               + "_SrcRootContainer.xmi";

         eMoflonEMFUtil.saveModel(state.getSrcTempOutputContainer(), fileName);
      } else if (src.size() == 1)
      {
         String fileName = folderName + state.getModelgenStats().getSrcElementCount() + "_" + state.getModelgenStats().getSrcConnectorCount()
               + "_SourceModel.xmi";

         eMoflonEMFUtil.saveModel(src.get(0), fileName);

      }

      if (trg.size() > 1)
      {

         String fileName = folderName + state.getModelgenStats().getTrgElementCount() + "_" + state.getModelgenStats().getTrgConnectorCount()
               + "_TrgRootContainer.xmi";
         eMoflonEMFUtil.saveModel(state.getTrgTempOutputContainer(), fileName);
      } else if (trg.size() == 1)
      {
         String fileName = folderName + state.getModelgenStats().getTrgElementCount() + "_" + state.getModelgenStats().getTrgConnectorCount()
               + "_TargetModel.xmi";
         eMoflonEMFUtil.saveModel(trg.get(0), fileName);

      }

      String fileName = folderName + state.getModelgenStats().getCorrElementCount() + "_" + state.getModelgenStats().getCorrConnectorCount()
            + "_CorrespondenceModel.xmi";

      eMoflonEMFUtil.saveModel(state.getCorrespondenceModel(), fileName);
   }

   private static String generateTimeStamp()
   {

      return "" + System.nanoTime();
   }

}
