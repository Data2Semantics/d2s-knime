package org.data2semantics.knime.kernels.ips;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentDoubleRange;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDoubleRange;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

/**
 * <code>NodeDialog</code> for the "RDFIntersectionPartialSubTreeKernel" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Adianto Wibisono
 */
public class RDFIntersectionPartialSubTreeKernelNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring RDFIntersectionPartialSubTreeKernel node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected RDFIntersectionPartialSubTreeKernelNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentNumber(
        		new SettingsModelIntegerBounded(
        				RDFIntersectionPartialSubTreeKernelNodeModel.CFG_DEPTH,
        				RDFIntersectionPartialSubTreeKernelNodeModel.DEFAULT_DEPTH,
                            0, Integer.MAX_VALUE),
                            "Depth:", /*step*/ 1, /*componentwidth*/ 5));
        
        addDialogComponent(new DialogComponentDoubleRange(
        		new SettingsModelDoubleRange(
        				RDFIntersectionPartialSubTreeKernelNodeModel.CFG_DISCOUNT_FACTOR, 
        				0, 1.0), 
        				0, 1.0, 0.001, "Discount Factor"));
        	             
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(
        				RDFIntersectionPartialSubTreeKernelNodeModel.CFG_INFERENCE,
        				RDFIntersectionPartialSubTreeKernelNodeModel.DEFAULT_INFERENCE),
        		"Inference"));

    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(
        				RDFIntersectionPartialSubTreeKernelNodeModel.CFG_NORMALIZE,
        				RDFIntersectionPartialSubTreeKernelNodeModel.DEFAULT_NORMALIZE),
        		"Normalize"));
                    
    }
}

