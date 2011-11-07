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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.io.InputStream;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ops4j.pax.sham.core.ShamBundle;
import org.ops4j.pax.sham.core.ShamBundleContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * Configures bundle context to handle install methods.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 08, 2011
 */
public class InstallBundleBehavior
{

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Stub {@link BundleContext#installBundle(String)} and
     * {@link BundleContext#installBundle(String, java.io.InputStream)}.
     *
     * @param bundleContext to be stubbed
     * @return provided bundle context, for fluent api usage
     */
    public static ShamBundleContext applyInstallBundleBehavior( final ShamBundleContext bundleContext )
    {
        try
        {
            doAnswer(
                new Answer<ShamBundle>()
                {
                    @Override
                    public ShamBundle answer( final InvocationOnMock invocation )
                        throws Throwable
                    {
                        return bundleContext.getFramework().installBundle();
                    }
                }
            ).when( bundleContext ).installBundle( anyString() );

            doAnswer(
                new Answer<Bundle>()
                {
                    @Override
                    public Bundle answer( final InvocationOnMock invocation )
                        throws Throwable
                    {
                        return bundleContext.getFramework().installBundle();
                    }
                }
            ).when( bundleContext ).installBundle( anyString(), Matchers.<InputStream>any() );
        }
        catch ( BundleException ignore )
        {
            // we are mocking so it will not happen
        }
        return bundleContext;
    }

}
