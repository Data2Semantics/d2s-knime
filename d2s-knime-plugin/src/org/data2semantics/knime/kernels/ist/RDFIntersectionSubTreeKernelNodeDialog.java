package org.data2semantics.knime.kernels.ist;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentDoubleRange;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDoubleRange;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

/**
 * <code>NodeDialog</code> for the "RDFIntersectionSubTreeKernel" Node.
 * Intersection Sub Tree Kernel
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Adianto Wibisono
 */
public class RDFIntersectionSubTreeKernelNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring RDFIntersectionSubTreeKernel node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected RDFIntersectionSubTreeKernelNodeDialog() {
        super();
        addDialogComponent(new DialogComponentNumber(
        		new SettingsModelIntegerBounded(
        				RDFIntersectionSubTreeKernelNodeModel.CFG_DEPTH,
        				RDFIntersectionSubTreeKernelNodeModel.DEFAULT_DEPTH,
                            0, Integer.MAX_VALUE),
                            "Depth:", /*step*/ 1, /*componentwidth*/ 5));
        
        addDialogComponent(new DialogComponentDoubleRange(
        		new SettingsModelDoubleRange(
        				RDFIntersectionSubTreeKernelNodeModel.CFG_DISCOUNT_FACTOR, 
        				0, 1.0), 
        				0, 1.0, 0.001, "Discount Factor"));
        	             
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(
        				RDFIntersectionSubTreeKernelNodeModel.CFG_INFERENCE,
        				RDFIntersectionSubTreeKernelNodeModel.DEFAULT_INFERENCE),
        		"Inference"));

    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(
        				RDFIntersectionSubTreeKernelNodeModel.CFG_NORMALIZE,
        				RDFIntersectionSubTreeKernelNodeModel.DEFAULT_NORMALIZE),
        		"Normalize"));
                    
    }
}

