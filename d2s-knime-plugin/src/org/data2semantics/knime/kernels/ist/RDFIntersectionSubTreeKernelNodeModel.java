package org.data2semantics.knime.kernels.ist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.data2semantics.knime.kernels.RDFDataModelAbstractClass;
import org.data2semantics.mustard.experiments.modules.kernels.RDFIntersectionSubTreeKernelModule;
import org.data2semantics.mustard.kernels.data.RDFData;
import org.data2semantics.mustard.rdf.RDFDataSet;
import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;


/**
 * This is the model implementation of RDFIntersectionSubTreeKernel.
 * Intersection Sub Tree Kernel
 *
 * @author Adianto Wibisono
 */
public class RDFIntersectionSubTreeKernelNodeModel extends RDFDataModelAbstractClass {
    
    // the logger instance
    private static final NodeLogger logger = NodeLogger
            .getLogger(RDFIntersectionSubTreeKernelNodeModel.class);
        
    /** the settings key which is used to retrieve and 
        store the settings (from the dialog or from a settings file)    
       (package visibility to be usable from the dialog). */
    static final String CFG_DEPTH  				= "Depth";
	static final String CFG_DISCOUNT_FACTOR		= "DiscountFactor";
	static final String CFG_INFERENCE 			= "Inference";
	static final String CFG_NORMALIZE  			= "Normalize";
	
	static final int 	DEFAULT_DEPTH 			= 2; 
	static final double DEFAULT_DISCOUNT_FACTOR = 1.0;

	static final boolean DEFAULT_INFERENCE		=true, 
					 	 DEFAULT_NORMALIZE 		=false; 


	private final SettingsModelIntegerBounded depth  =    
			new SettingsModelIntegerBounded(RDFIntersectionSubTreeKernelNodeModel.CFG_DEPTH,
					RDFIntersectionSubTreeKernelNodeModel.DEFAULT_DEPTH,
	            0, Integer.MAX_VALUE);
	
	private final SettingsModelDouble discount_factor = 
			new SettingsModelDouble(CFG_DISCOUNT_FACTOR, DEFAULT_DISCOUNT_FACTOR);
	

	private final SettingsModelBoolean inference =    new SettingsModelBoolean(RDFIntersectionSubTreeKernelNodeModel.CFG_INFERENCE,
			RDFIntersectionSubTreeKernelNodeModel.DEFAULT_INFERENCE);
	    
    private final SettingsModelBoolean normalize =    new SettingsModelBoolean(RDFIntersectionSubTreeKernelNodeModel.CFG_NORMALIZE,
    		RDFIntersectionSubTreeKernelNodeModel.DEFAULT_NORMALIZE);
    
    /**
     * Constructor for the node model.
     */
    protected RDFIntersectionSubTreeKernelNodeModel() {
    
        super(3, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

    	 // TODO do something here
        logger.info("We should have three buffered data table here " + inData.length);
        RDFDataSet dataset= extractRDFDataSet(inData[0]);
        List<Resource > instances = extractInstances(inData[1]);
        List<Statement> blacklist = extractBlackList(inData[2]);
        
        RDFData graphData =new RDFData(dataset, instances, blacklist);
        RDFIntersectionSubTreeKernelModule module = new RDFIntersectionSubTreeKernelModule(
        		depth.getIntValue(), 
        		discount_factor.getDoubleValue(),
        		inference.getBooleanValue(),  
        		normalize.getBooleanValue(), 
        		graphData);
        
        //Computing matrix
        module.compute();
        
        double [][] featureMatrix = module.getMatrix();
        
        
        int nRows = featureMatrix.length;
        int nCols = featureMatrix[0].length;
        
        logger.info("Number of rows and columns for output " + nRows +" " + nCols);
        System.out.println("Number of rows and columns for output " + nRows +" " + nCols);
        
        DataColumnSpec[] columnSpecs = new DataColumnSpec[nCols];
        
        for(int i=0;i<nCols;i++){
        	// Column names is just C[i]
            columnSpecs[i] = new DataColumnSpecCreator("C"+i, DoubleCell.TYPE).createSpec();
        }
        
        DataTableSpec outputSpec = new DataTableSpec(columnSpecs);
        
        
        // the execution context will provide us with storage capacity, in this
        // case a data container to which we will add rows sequentially
        // Note, this container can also handle aritrary big data tables, it
        // will buffer to disc if necessary.
        BufferedDataContainer container = exec.createDataContainer(outputSpec);
        for(int i=0;i<nRows;i++){
        	RowKey rowKey = new RowKey("Row"+i);
        	DataCell [] rowCells = new DataCell[nCols];
        	for(int j=0;j<nCols;j++){
        		rowCells[j] = new DoubleCell(featureMatrix[i][j]);
        	}
        	DataRow newRow = new DefaultRow(rowKey, rowCells);
        	container.addRowToTable(newRow);
        	exec.checkCanceled();
            exec.setProgress(i / (double)nRows, "Adding row " + i);
        }
        container.close();
        
        return new BufferedDataTable[]{container.getTable()};
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
        
        depth.saveSettingsTo(settings);
        discount_factor.saveSettingsTo(settings);
        inference.saveSettingsTo(settings);
        normalize.saveSettingsTo(settings);
        

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
        
        depth.loadSettingsFrom(settings);
        discount_factor.loadSettingsFrom(settings);
        inference.loadSettingsFrom(settings);
        normalize.loadSettingsFrom(settings);
        
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
        depth.validateSettings(settings);
        discount_factor.validateSettings(settings);
        inference.validateSettings(settings);
        normalize.validateSettings(settings);
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

