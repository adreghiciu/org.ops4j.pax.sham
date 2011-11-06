/*
 * Copyright 2011 Alin Dreghiciu.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
