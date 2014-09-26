package org.data2semantics.knime.kernels.ist;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RDFIntersectionSubTreeKernel" Node.
 * Intersection Sub Tree Kernel
 *
 * @author Adianto Wibisono
 */
public class RDFIntersectionSubTreeKernelNodeFactory 
        extends NodeFactory<RDFIntersectionSubTreeKernelNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RDFIntersectionSubTreeKernelNodeModel createNodeModel() {
        return new RDFIntersectionSubTreeKernelNodeModel();
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
    public NodeView<RDFIntersectionSubTreeKernelNodeModel> createNodeView(final int viewIndex,
            final RDFIntersectionSubTreeKernelNodeModel nodeModel) {
        return new RDFIntersectionSubTreeKernelNodeView(nodeModel);
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
        return new RDFIntersectionSubTreeKernelNodeDialog();
    }

}

