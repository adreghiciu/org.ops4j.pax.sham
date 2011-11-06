package org.ops4j.pax.sham.core.internal;

import java.io.Serializable;

import org.mockito.internal.stubbing.defaultanswers.ReturnsMoreEmptyValues;
import org.mockito.invocation.InvocationOnMock;

/**
 * TODO
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class PartialImplementation
    extends ReturnsMoreEmptyValues
    implements Serializable
{

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    /**
     * Class of partial implementation.
     */
    private Class<?> implementer;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    public PartialImplementation( final Class<?> implementer )
    {
        this.implementer = implementer;
    }

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    @Override
    public Object answer( final InvocationOnMock invocation )
        throws Throwable
    {
        try
        {
            final String name = invocation.getMethod().getName();
            final Class<?>[] parameterTypes = invocation.getMethod().getParameterTypes();
            // if method is not present will throw NoSuchMethodException and we get the usual way
            implementer.getDeclaredMethod( name, parameterTypes );
            return invocation.callRealMethod();
        }
        catch ( NoSuchMethodException ignore )
        {
            return super.answer( invocation );
        }

    }

}
