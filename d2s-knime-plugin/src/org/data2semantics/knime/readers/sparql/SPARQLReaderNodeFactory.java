package org.data2semantics.knime.readers.sparql;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RDFReader" Node.
 * 
 *
 * @author Adianto Wibisono
 */
public class SPARQLReaderNodeFactory 
        extends NodeFactory<SPARQLReaderNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SPARQLReaderNodeModel createNodeModel() {
        return new SPARQLReaderNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<SPARQLReaderNodeModel> createNodeView(final int viewIndex,
            final SPARQLReaderNodeModel nodeModel) {
        return new SPARQLReaderNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new SPARQLReaderNodeDialog();
    }

}

