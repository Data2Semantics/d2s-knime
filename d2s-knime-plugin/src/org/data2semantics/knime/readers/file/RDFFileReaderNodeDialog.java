package org.data2semantics.knime.readers.file;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentFileChooser;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * <code>NodeDialog</code> for the "RDFReader" Node.
 * RDFReader reading from file.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Adianto Wibisono
 */
public class RDFFileReaderNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring RDFReader node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected RDFFileReaderNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentFileChooser(
        		new SettingsModelString(
        				RDFFileReaderNodeModel.CFGKEY_FILENAME, ""),
        				"historyID???", ".n3|.ttl|.rdf") // Clueless about history ID parameter.
                );
                    
    }
}

