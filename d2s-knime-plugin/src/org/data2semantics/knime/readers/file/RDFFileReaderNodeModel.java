package org.data2semantics.knime.readers.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.nodes.data.RDFFileDataSet;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;


/**
 * This is the model implementation of RDFReader.
 * RDFReader reading from file.
 *
 * @author Adianto Wibisono
 */
public class RDFFileReaderNodeModel extends NodeModel {
    
    // the logger instance
    private static final NodeLogger logger = NodeLogger
            .getLogger(RDFFileReaderNodeModel.class);
        
    /** the settings key which is used to retrieve and 
        store the settings (from the dialog or from a settings file)    
       (package visibility to be usable from the dialog). */
	static final String CFGKEY_FILENAME = "FileName";


    private final SettingsModelString m_filename = 
    		new SettingsModelString(CFGKEY_FILENAME, "");
    
    /**
     * Constructor for the node model.
     */
    protected RDFFileReaderNodeModel() {
    	// No input for this module, producing only tables of S P O
        super(0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

    	DataColumnSpec[] SPOSpecs = new DataColumnSpec[3];
        SPOSpecs[0] = 
            new DataColumnSpecCreator("S", StringCell.TYPE).createSpec();
        SPOSpecs[1] = 
            new DataColumnSpecCreator("P", StringCell.TYPE).createSpec();
        SPOSpecs[2] = 
            new DataColumnSpecCreator("O", StringCell.TYPE).createSpec();
        
        DataTableSpec outputSpec = new DataTableSpec(SPOSpecs);

        BufferedDataContainer container = exec.createDataContainer(outputSpec);
        
        RDFFormat format = RDFFormat.forFileName(m_filename.getStringValue());
        
        logger.info("Start reading all dataset");
        RDFFileDataSet dataset = new RDFFileDataSet(new File(m_filename.getStringValue()), format);
        List<Statement> allStatement = dataset.getFullGraph();
        
        int rowNum=0;
        for(Statement statement : allStatement){
        	RowKey key = new RowKey(""+rowNum++);
        	
        	DataCell [] cells = new DataCell[3];
        	cells[0] = new StringCell(statement.getSubject().toString());
        	cells[1] = new StringCell(statement.getPredicate().toString());
        	cells[2] = new StringCell(statement.getObject().toString());

        	DataRow row = new DefaultRow(key, cells);
        	container.addRowToTable(row);
        	
        	  exec.checkCanceled();
              exec.setProgress(1.0*rowNum / allStatement.size(), 
                  "Adding row " + rowNum);
        }
  
        // once we are done, we close the container and return its table
        container.close();
        BufferedDataTable out = container.getTable();
        return new BufferedDataTable[]{out};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        // TODO Code executed on reset.
        // Models build during execute are cleared here.
        // Also data handled in load/saveInternals will be erased here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
        
        // TODO: check if user settings are available, fit to the incoming
        // table structure, and the incoming types are feasible for the node
        // to execute. If the node can execute in its current state return
        // the spec of its output data table(s) (if you can, otherwise an array
        // with null elements), or throw an exception with a useful user message

        return new DataTableSpec[]{null};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {

        // TODO save user settings to the config object.
        
    	m_filename.saveSettingsTo(settings);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO load (valid) settings from the config object.
        // It can be safely assumed that the settings are valided by the 
        // method below.
        
    	m_filename.loadSettingsFrom(settings);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO check if the settings could be applied to our model
        // e.g. if the count is in a certain range (which is ensured by the
        // SettingsModel).
        // Do not actually set any values of any member variables.

        m_filename.validateSettings(settings);

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        
        // TODO load internal data. 
        // Everything handed to output ports is loaded automatically (data
        // returned by the execute method, models loaded in loadModelContent,
        // and user settings set through loadSettingsFrom - is all taken care 
        // of). Load here only the other internals that need to be restored
        // (e.g. data used by the views).

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
       
        // TODO save internal models. 
        // Everything written to output ports is saved automatically (data
        // returned by the execute method, models saved in the saveModelContent,
        // and user settings saved through saveSettingsTo - is all taken care 
        // of). Save here only the other internals that need to be preserved
        // (e.g. data used by the views).

    }

}

