package org.moflon.ide.texteditor.modules.folding;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.moflon.ide.texteditor.editors.MoflonTextEditor;
import org.moflon.ide.texteditor.helpers.MocaNodeIndexUtil;

import MocaTree.Node;
import MocaTree.Text;

public class MoflonFoldingHandler
{

   ProjectionViewer pViewer;

   ProjectionAnnotationModel annotationModel;

   MoflonTextEditor textEditor;

   ArrayList<ProjectionAnnotation> oldAnnotations;

   HashMap<ProjectionAnnotation, Position> newAnnotations;
   
   public MoflonFoldingHandler(MoflonTextEditor textEditor)
   {
      this.textEditor = textEditor;
      this.oldAnnotations = new ArrayList<ProjectionAnnotation>();
      this.newAnnotations = new HashMap<ProjectionAnnotation, Position>();
   }

   public void refreshAnnotations(Node mocaTree)
   {
      if (annotationModel == null)
      {
         ISourceViewer sourceViewer = textEditor.getSourceViewerPublic();
         if (sourceViewer instanceof ProjectionViewer)
         {
            pViewer = (ProjectionViewer) sourceViewer;
            annotationModel = pViewer.getProjectionAnnotationModel();
         }
      }
      if (annotationModel != null)
      {
         annotationModel.removeAllAnnotations();
         getAnnotations(mocaTree);
      }
      
      
   }

   private void getAnnotations(Node mocaNode)
   {
      for (Text text : mocaNode.getChildren())
      {
         if (text instanceof Node)
         {
            getAnnotations((Node) text);
         }
      }

      if (this.textEditor.getMoflonTextEditorConfiguration().foldNode(mocaNode))
      {
         ProjectionAnnotation annotation = new ProjectionAnnotation();
         Position position = MocaNodeIndexUtil.getIndexAlgorithm(mocaNode);
         if (position.getOffset() != Integer.MAX_VALUE && position.getLength() != Integer.MAX_VALUE && position.getLength() > 0)
         {
            annotation.markCollapsed();
            annotationModel.addAnnotation(annotation, position);
         }
      }
   }
}
