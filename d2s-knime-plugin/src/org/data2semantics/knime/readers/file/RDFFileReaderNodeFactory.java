package org.data2semantics.knime.readers.file;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RDFReader" Node.
 * RDFReader reading from file.
 *
 * @author Adianto Wibisono
 */
public class RDFFileReaderNodeFactory 
        extends NodeFactory<RDFFileReaderNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RDFFileReaderNodeModel createNodeModel() {
        return new RDFFileReaderNodeModel();
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
    public NodeView<RDFFileReaderNodeModel> createNodeView(final int viewIndex,
            final RDFFileReaderNodeModel nodeModel) {
        return new RDFFileReaderNodeView(nodeModel);
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
        return new RDFFileReaderNodeDialog();
    }

}

