package org.data2semantics.knime.readers.sparql;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sparql.SPARQLRepository;


/**
 * This is the model implementation of RDFReader.
 * 
 *
 * @author Adianto Wibisono
 */
public class SPARQLReaderNodeModel extends NodeModel {
    
    // the logger instance
    private static final NodeLogger logger = NodeLogger
            .getLogger(SPARQLReaderNodeModel.class);
        
    // Settings keys which is used to retrieve and store the settings 
    // (from the dialog or from a settings file)    
    // (package visibility to be usable from the dialog). 
	static final String CFGKEY_COLUMN_COUNT = "CFG_COLUMN_COUNT";
	static final String CFGKEY_ROW_COUNT = "CFG_ROW_COUNT";
	static final String CFG_SPARQL_ENDPOINT = "CFG_SPARQL_ENDPOINT";
	static final String CFG_SPARQL_QUERY = "CFG_SPARQL_QUERY";
	

    /** initial default values. */
    static final int DEFAULT_COLUMN_COUNT = 3;
    static final int MIN_COLUMN_COUNT = 1;
    static final int MAX_COLUMN_COUNT = 10000;
	
    static final int DEFAULT_ROW_COUNT = 100;
    static final int MIN_ROW_COUNT = 1;
    static final int MAX_ROW_COUNT = Integer.MAX_VALUE;

    static final int N_INPUT_PORT =0, N_OUTPUT_PORT=1;
    
    static final String DEFAULT_SPARQL_ENDPOINT = 
    			"http://nl.dbpedia.org/sparql";
	static final String DEFAULT_SPARQL_QUERY = 
			"SELECT * where {?s ?p ?o } limit " + DEFAULT_ROW_COUNT;
	
    // Model variables filled from the dialog and used in the models execution method. 
	// The default components of the dialog work with "SettingsModels".
    private final SettingsModelIntegerBounded m_column_count =
        new SettingsModelIntegerBounded(CFGKEY_COLUMN_COUNT,
                    DEFAULT_COLUMN_COUNT,
                    MIN_COLUMN_COUNT, 
                    MAX_COLUMN_COUNT);
    
    private final SettingsModelIntegerBounded m_row_count =
            new SettingsModelIntegerBounded(CFGKEY_ROW_COUNT,
                        DEFAULT_ROW_COUNT,
                        MIN_ROW_COUNT, 
                        MAX_ROW_COUNT);
        
    private final SettingsModelString m_sparql_endpoint = 
    		new SettingsModelString(CFG_SPARQL_ENDPOINT, DEFAULT_SPARQL_ENDPOINT);

    private final SettingsModelString m_sparql_query = 
    		new SettingsModelString(CFG_SPARQL_QUERY, DEFAULT_SPARQL_QUERY);

	
    
	/**
     * Constructor for the node model.
     */
    public SPARQLReaderNodeModel() {
    
        // TODO one incoming port and one outgoing port is assumed
        super(N_INPUT_PORT, N_OUTPUT_PORT);
    }

    /**
     * {@inheritDoc}
     */
    @Override

    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

    	TupleQueryResult queryResult = getQueryResult();
    	
        String [] columnNames = queryResult.getBindingNames().toArray(new String[0]);
        
        // the data table spec of the single output table, 
        // the table will have three columns:
        DataColumnSpec[] columnSpecs = new DataColumnSpec[columnNames.length];
        
        for(int i=0;i<columnNames.length;i++){
            columnSpecs[i] = new DataColumnSpecCreator(columnNames[i], StringCell.TYPE).createSpec();
        }
        
        DataTableSpec outputSpec = new DataTableSpec(columnSpecs);
        
        
        // the execution context will provide us with storage capacity, in this
        // case a data container to which we will add rows sequentially
        // Note, this container can also handle arbitrary big data tables, it
        // will buffer to disc if necessary.
        BufferedDataContainer container = exec.createDataContainer(outputSpec);
        int i=0;
        // let's add m_count rows to it
        
        // I am going to make the second column unique by adding ID(counts), for now, think about it later.
        Map<String, Integer> uniqueIDPredicate = new HashMap<String,Integer>();
        logger.info("Watch out, predicates are unique");
        while(queryResult.hasNext()){
        	i++;
        	BindingSet current = queryResult.next();
        	RowKey key = new RowKey(current.toString());
            
            // the cells of the current row, the types of the cells must match
            // the column spec (see above)
            DataCell[] cells = new DataCell[current.size()];
            
            for(int j=0;j<cells.length;j++){
            	String newValue =current.getValue(columnNames[j]).toString();
            	if(j == 1) 
            	{
            		if(!uniqueIDPredicate.keySet().contains(newValue)){
            			uniqueIDPredicate.put(newValue, 0);
                    } 
            		uniqueIDPredicate.put(newValue, uniqueIDPredicate.get(newValue)+1);
            		newValue = newValue + "-" + uniqueIDPredicate.get(newValue);
            	}
            	cells[j] = new StringCell(newValue);
            	
            }
            DataRow row = new DefaultRow(key, cells);
            container.addRowToTable(row);
            
            // check if the execution monitor was canceled
            exec.checkCanceled();
            exec.setProgress(i / (double)m_row_count.getIntValue(), 
                "Adding row " + i);
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
        m_sparql_endpoint.saveSettingsTo(settings);
        m_sparql_query.saveSettingsTo(settings);
        m_column_count.saveSettingsTo(settings);
        m_row_count.saveSettingsTo(settings);
        

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
        m_sparql_endpoint.loadSettingsFrom(settings);
        m_sparql_query.loadSettingsFrom(settings);
        m_column_count.loadSettingsFrom(settings);
        m_row_count.loadSettingsFrom(settings);

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
    	m_row_count.validateSettings(settings);
        m_column_count.validateSettings(settings);
        m_sparql_endpoint.validateSettings(settings);
        m_sparql_query.validateSettings(settings);
        

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

    public TupleQueryResult getQueryResult(){
    	System.out.println("Getting query from SPARQL Endpoint "+m_sparql_endpoint.getStringValue());
		SPARQLRepository sparql_repository = new SPARQLRepository(m_sparql_endpoint.getStringValue());
		TupleQueryResult result  = null;
		RepositoryConnection conn = null;
		try {
			sparql_repository.initialize();
			
			conn = sparql_repository.getConnection();
			TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, m_sparql_query.getStringValue());
			result = query.evaluate();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
    }
    
    
}

