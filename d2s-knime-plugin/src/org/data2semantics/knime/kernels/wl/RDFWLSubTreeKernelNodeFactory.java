package org.data2semantics.knime.kernels.wl;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RDFWLSubTreeKernel" Node.
 * 
 *
 * @author Adianto Wibisono
 */
public class RDFWLSubTreeKernelNodeFactory 
        extends NodeFactory<RDFWLSubTreeKernelNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RDFWLSubTreeKernelNodeModel createNodeModel() {
        return new RDFWLSubTreeKernelNodeModel();
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
    public NodeView<RDFWLSubTreeKernelNodeModel> createNodeView(final int viewIndex,
            final RDFWLSubTreeKernelNodeModel nodeModel) {
        return new RDFWLSubTreeKernelNodeView(nodeModel);
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
        return new RDFWLSubTreeKernelNodeDialog();
    }

}

