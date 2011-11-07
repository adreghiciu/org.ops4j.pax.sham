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

package org.ops4j.pax.sham.core.behavior;

import static org.mockito.Mockito.doAnswer;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ops4j.pax.sham.core.ShamBundle;
import org.ops4j.pax.sham.core.ShamBundleContext;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;

/**
 * Configures bundle context to automatically send bundle events on bundle state changes to registered listeners.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class BundleListenerBehavior
{

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Stub {@link BundleContext#addBundleListener(BundleListener)} and
     * {@link BundleContext#removeBundleListener(BundleListener)}.
     *
     * @param bundleContext to be stubbed
     * @return provided bundle context, for fluent api usage
     */
    public static ShamBundleContext applyBundleListenerBehavior( final ShamBundleContext bundleContext )
    {
        final ShamBundle bundle = bundleContext.getBundle();

        doAnswer(
            new Answer<Void>()
            {
                @Override
                public Void answer( final InvocationOnMock invocationOnMock )
                    throws Throwable
                {
                    final BundleListener bundleListener = (BundleListener) invocationOnMock.getArguments()[0];
                    bundle.getBundleListeners().add( bundleListener );
                    return null;
                }
            }
        ).when( bundleContext ).addBundleListener( Mockito.<BundleListener>any() );

        doAnswer(
            new Answer<Void>()
            {
                @Override
                public Void answer( final InvocationOnMock invocationOnMock )
                    throws Throwable
                {
                    final BundleListener bundleListener = (BundleListener) invocationOnMock.getArguments()[0];
                    bundle.getBundleListeners().remove( bundleListener );
                    return null;
                }
            }
        ).when( bundleContext ).removeBundleListener( Mockito.<BundleListener>any() );

        return bundleContext;
    }

}
