using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using System.Runtime.InteropServices;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui
{
    public interface IExpressionProvider
    {

        /// <summary>
        /// this method must return expressions as a string list.
        /// </summary>
        /// <returns>all possible expression types as Strings which can be displayed in the gui</returns>
        [ComVisibleAttribute(false)]
        List<String> getExpressionStringList();

        /// <summary>
        /// this method computes all (for the current selected expression) possible target objects and
        /// saves them internally.
        /// </summary>
        /// <param name="selectedExpressionType">The expression type for which the target objects should be computed</param>
        /// <returns>all target object names as a list</returns>
        [ComVisibleAttribute(false)]
        List<String> getFirstStringList(String selectedExpressionType);

        /// <summary>
        /// computes all (for the current selected expression and target object) possible source objects and
        /// saves them internally
        /// </summary>
        /// <param name="selectedTargetObjectIndex"></param>
        /// <returns></returns>
        [ComVisibleAttribute(false)]
        List<String> getSecondStringList(int selectedTargetObjectIndex);

        /// <summary>
        /// 
        /// </summary>
        /// <returns>the container element for the current provider (for computing of source and target objects)</returns>
        SQLElement getContainerElement();

        /// <summary></summary>
        /// <returns>the expression which underlies the current provider</returns>
        Expression getProviderExpression();



        /// <summary>
        /// computes the expression from the current selected data
        /// </summary>
        /// <param name="typedInValues">the data to compute from (selected data)</param>
        /// <returns></returns>
        Expression getExpression();


        void setSelectedSecondString(int selectedSourceIndex);

        IExpressionProvider getMainProvider();


        void setParameterTypeFilter(int parameterTypeFilterId);


        SecondObject getSelectedSecond();

        void setExpression(Expression expression);
    }
}
