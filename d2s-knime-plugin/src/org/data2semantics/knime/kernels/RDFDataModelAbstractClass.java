package org.data2semantics.knime.kernels;

import java.util.ArrayList;
import java.util.List;

import org.data2semantics.mustard.rdf.RDFDataSet;
import org.data2semantics.mustard.rdf.RDFSingleDataSet;
import org.knime.core.data.DataRow;
import org.knime.core.data.RowIterator;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.NodeModel;
import org.knime.core.node.port.PortType;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
/**
 * Generic abstract class for nodes processing dataset, instances, blacklist i.e RDFData from original Mustard
 * 
 * @author Adianto
 *
 */
public abstract class RDFDataModelAbstractClass extends NodeModel {

	public RDFDataModelAbstractClass(int nrInDataPorts, int nrOutDataPorts) {
		super(nrInDataPorts, nrOutDataPorts);
	}

	public RDFDataModelAbstractClass(PortType[] inPortTypes,
			PortType[] outPortTypes) {
		super(inPortTypes, outPortTypes);
	}

	protected List<Statement> extractBlackList(BufferedDataTable dataTable) {
		RDFDataSet 		dataSet = new RDFSingleDataSet();
		List<Statement> result  = new ArrayList<Statement>();
		
		for(RowIterator rowIt = dataTable.iterator(); rowIt.hasNext();){
			DataRow dataRow = rowIt.next();
			
			Statement newStatement = extractStatementFromDataRow(dataSet, dataRow);
			result.add(newStatement);
		}
		
		return result;
	}

	protected List<Resource> extractInstances(BufferedDataTable dataTable) {
		List<Resource> result = new ArrayList<Resource> ();
		RDFDataSet dataSet = new RDFSingleDataSet();
		
		for(RowIterator rowIt = dataTable.iterator(); rowIt.hasNext();){
				DataRow dataRow = rowIt.next();
				StringCell instance = (StringCell) dataRow.getCell(0);
				URI instanceURI = dataSet.createURI(instance.getStringValue());
				result.add(instanceURI);
		}
		return result;
	}

	protected RDFDataSet extractRDFDataSet(BufferedDataTable dataTable) {
		RDFDataSet dataSet = new RDFSingleDataSet();
		
		//For each row on the buffered data table, extract S,P,O, create URI, create statement, add.
		List<Statement> newStatements = new ArrayList<Statement>();
		
		for(RowIterator rowIt = dataTable.iterator(); rowIt.hasNext();){
			DataRow dataRow = rowIt.next();
		
			Statement newStatement = extractStatementFromDataRow(dataSet, dataRow);
			
			newStatements.add(newStatement);
		}		
		
		dataSet.addStatements(newStatements);
		
		return dataSet;
	}

	private Statement extractStatementFromDataRow(RDFDataSet dataSet, DataRow dataRow) {
		StringCell subject   = (StringCell) dataRow.getCell(0);
		StringCell predicate = (StringCell) dataRow.getCell(1);
		StringCell object    = (StringCell) dataRow.getCell(2);
		
		Statement newStatement  = dataSet.createStatement(
				dataSet.createURI(subject.getStringValue()), 
				dataSet.createURI(predicate.getStringValue()), 
				dataSet.createURI(object.getStringValue()));
		
		return newStatement;
	}

}