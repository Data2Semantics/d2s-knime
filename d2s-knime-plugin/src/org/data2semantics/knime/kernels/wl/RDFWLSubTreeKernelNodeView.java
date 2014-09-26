package org.data2semantics.knime.kernels.wl;

import org.data2semantics.knime.kernels.RDFDataModelAbstractClass;
import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "RDFWLSubTreeKernel" Node.
 * 
 *
 * @author Adianto Wibisono
 */
public class RDFWLSubTreeKernelNodeView extends NodeView<RDFWLSubTreeKernelNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link RDFWLSubTreeKernelNodeModel})
     */
    protected RDFWLSubTreeKernelNodeView(final RDFWLSubTreeKernelNodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and 
        // update the view.
        RDFDataModelAbstractClass nodeModel = 
            (RDFDataModelAbstractClass)getNodeModel();
        assert nodeModel != null;
        
        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}

