package org.data2semantics.knime.kernels.wl;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

/**
 * <code>NodeDialog</code> for the "RDFWLSubTreeKernel" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Adianto Wibisono
 */
public class RDFWLSubTreeKernelNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring RDFWLSubTreeKernel node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected RDFWLSubTreeKernelNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentNumber(
                new SettingsModelIntegerBounded(
                    RDFWLSubTreeKernelNodeModel.CFG_ITERATIONS,
                    RDFWLSubTreeKernelNodeModel.DEFAULT_ITERATION,
                    0, Integer.MAX_VALUE),
                    "Iteration:", /*step*/ 1, /*componentwidth*/ 5));
        
        addDialogComponent(new DialogComponentNumber(
        		new SettingsModelIntegerBounded(
                            RDFWLSubTreeKernelNodeModel.CFG_DEPTH,
                            RDFWLSubTreeKernelNodeModel.DEFAULT_DEPTH,
                            0, Integer.MAX_VALUE),
                            "Depth:", /*step*/ 1, /*componentwidth*/ 5));
                    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(RDFWLSubTreeKernelNodeModel.CFG_INFERENCE,
        								 RDFWLSubTreeKernelNodeModel.DEFAULT_INFERENCE),
        		"Inference"));
                
    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(RDFWLSubTreeKernelNodeModel.CFG_REVERSE,
        								 RDFWLSubTreeKernelNodeModel.DEFAULT_REVERSE),
        		"Reverse"));
    
        
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(RDFWLSubTreeKernelNodeModel.CFG_WEIGHTING,
        								 RDFWLSubTreeKernelNodeModel.DEFAULT_WEIGHTING),
        		"Iteration Weighting"));
    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(RDFWLSubTreeKernelNodeModel.CFG_NORMALIZE,
        								 RDFWLSubTreeKernelNodeModel.DEFAULT_NORMALIZE),
        		"Normalize"));
    
    }
    
}

