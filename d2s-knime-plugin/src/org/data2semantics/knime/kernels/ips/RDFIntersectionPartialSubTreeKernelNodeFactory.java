package org.data2semantics.knime.kernels.ips;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RDFIntersectionPartialSubTreeKernel" Node.
 * 
 *
 * @author Adianto Wibisono
 */
public class RDFIntersectionPartialSubTreeKernelNodeFactory 
        extends NodeFactory<RDFIntersectionPartialSubTreeKernelNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RDFIntersectionPartialSubTreeKernelNodeModel createNodeModel() {
        return new RDFIntersectionPartialSubTreeKernelNodeModel();
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
    public NodeView<RDFIntersectionPartialSubTreeKernelNodeModel> createNodeView(final int viewIndex,
            final RDFIntersectionPartialSubTreeKernelNodeModel nodeModel) {
        return new RDFIntersectionPartialSubTreeKernelNodeView(nodeModel);
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
        return new RDFIntersectionPartialSubTreeKernelNodeDialog();
    }

}

