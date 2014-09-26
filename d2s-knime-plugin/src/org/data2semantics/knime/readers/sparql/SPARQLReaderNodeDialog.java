package org.data2semantics.knime.readers.sparql;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentMultiLineString;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * <code>NodeDialog</code> for the "RDFReader" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Adianto Wibisono
 */
public class SPARQLReaderNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring RDFReader node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected SPARQLReaderNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentNumber(
                new SettingsModelIntegerBounded(
                    SPARQLReaderNodeModel.CFGKEY_COLUMN_COUNT,
                    SPARQLReaderNodeModel.DEFAULT_COLUMN_COUNT,
                    0, SPARQLReaderNodeModel.MAX_COLUMN_COUNT),
                    "Column Counts:", /*step*/ 1, /*componentwidth*/ 5)
        		);

        addDialogComponent(new DialogComponentNumber(
                new SettingsModelIntegerBounded(
                    SPARQLReaderNodeModel.CFGKEY_ROW_COUNT,
                    SPARQLReaderNodeModel.DEFAULT_ROW_COUNT,
                    SPARQLReaderNodeModel.MIN_ROW_COUNT,
                    SPARQLReaderNodeModel.MAX_ROW_COUNT),
                    "Row Count (Limit):", /*step*/ 1, /*componentwidth*/ 5)
        		);
        
        addDialogComponent(new DialogComponentString(
        		new SettingsModelString(
        			SPARQLReaderNodeModel.CFG_SPARQL_ENDPOINT, 
        			SPARQLReaderNodeModel.DEFAULT_SPARQL_ENDPOINT), 
        			"SPARQL End point"));
                    
        addDialogComponent(new DialogComponentMultiLineString(
        		new SettingsModelString(
        			SPARQLReaderNodeModel.CFG_SPARQL_QUERY, 
        			SPARQLReaderNodeModel.DEFAULT_SPARQL_QUERY), 
        			"SPARQL Query"));
    }
}

