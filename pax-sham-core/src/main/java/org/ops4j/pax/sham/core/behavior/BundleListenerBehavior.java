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

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ops4j.pax.sham.core.ShamBundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * TODO
 *
 * @since 1.0
 */
public class BundleListenerBehavior
{

    public static ShamBundle applyBundleListenerBehavior( final ShamBundle bundle )
    {
        final BundleListenerProxy bundleListenerProxy = new BundleListenerProxy();

        final BundleContext bundleContext = bundle.getBundleContext();
        Mockito.doAnswer(
            new Answer<Void>()
            {
                @Override
                public Void answer( final InvocationOnMock invocationOnMock )
                    throws Throwable
                {
                    final BundleListener bundleListener = (BundleListener) invocationOnMock.getArguments()[0];
                    bundleListenerProxy.listeners.add( bundleListener );
                    return null;
                }
            }
        ).when( bundleContext ).addBundleListener( Mockito.<BundleListener>any() );
        Mockito.doAnswer(
            new Answer<Void>()
            {
                @Override
                public Void answer( final InvocationOnMock invocationOnMock )
                    throws Throwable
                {
                    final BundleListener bundleListener = (BundleListener) invocationOnMock.getArguments()[0];
                    bundleListenerProxy.listeners.remove( bundleListener );
                    return null;
                }
            }
        ).when( bundleContext ).removeBundleListener( Mockito.<BundleListener>any() );

        bundle.getBundleListeners().add( bundleListenerProxy );

        return bundle;
    }

    private static class BundleListenerProxy
        implements BundleListener
    {

        private List<BundleListener> listeners;

        BundleListenerProxy()
        {
            listeners = new ArrayList<BundleListener>();
        }

        @Override
        public void bundleChanged( final BundleEvent bundleEvent )
        {
            for ( final BundleListener bundleListener : listeners )
            {
                bundleListener.bundleChanged( bundleEvent );
            }
        }

    }

}
