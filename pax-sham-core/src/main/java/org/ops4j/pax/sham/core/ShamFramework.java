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

package org.ops4j.pax.sham.core;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ops4j.pax.sham.core.internal.PartialImplementation;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

/**
 * TODO
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class ShamFramework
{

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    private List<ShamBundle> bundles;

    private ShamBundleContext bundleContext;

    private List<ExecutionEnvironment> executionEnvironments;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    public ShamFramework()
    {
        executionEnvironments = new ArrayList<ExecutionEnvironment>();
        bundles = new ArrayList<ShamBundle>();

        bundleContext = mock( ShamBundleContext.class );
        when( bundleContext.getProperty( Constants.FRAMEWORK_EXECUTIONENVIRONMENT ) ).thenAnswer(
            new Answer<String>()
            {
                @Override
                public String answer( final InvocationOnMock invocation )
                    throws Throwable
                {
                    return executionEnvironments();
                }
            }
        );
        when( bundleContext.getBundles() ).thenReturn( bundles.toArray( new ShamBundle[bundles.size()] ) );
        when( bundleContext.getBundle( anyLong() ) ).thenAnswer(
            new Answer<ShamBundle>()
            {
                @Override
                public ShamBundle answer( final InvocationOnMock invocation )
                    throws Throwable
                {
                    return bundles.get( ( (Long) invocation.getArguments()[0] ).intValue() );
                }
            }
        );
        try
        {
            when( bundleContext.installBundle( anyString() ) ).thenAnswer(
                new Answer<ShamBundle>()
                {
                    @Override
                    public ShamBundle answer( final InvocationOnMock invocation )
                        throws Throwable
                    {
                        return installBundle();
                    }
                }
            );
            when( bundleContext.installBundle( anyString(), Matchers.<InputStream>any() ) ).thenAnswer(
                new Answer<Bundle>()
                {
                    @Override
                    public Bundle answer( final InvocationOnMock invocation )
                        throws Throwable
                    {
                        return installBundle();
                    }
                }
            );
        }
        catch ( BundleException ignore )
        {
            // we are mocking so it will not happen
        }
        installBundle().withBundleSymbolicName( "system" );
    }

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Builder method for specifying execution environments.
     *
     * @param environments execution environments simulated by mock framework
     * @return itself, for fluent api usage
     */
    public ShamFramework withExecutionEnvironment( final ExecutionEnvironment... environments )
    {
        executionEnvironments.addAll( asList( environments ) );
        return this;
    }

    /**
     * Builder method for specifying packages exported by system bundle.
     *
     * @param exports packages exported by system bundle
     * @return itself, for fluent api usage
     */
    public ShamFramework withSystemPackages( final String... exports )
    {
        getSystemBundle().withPackages( exports );
        return this;
    }

    /**
     * Returns the mocked bundle context.
     *
     * @return mocked bundle context
     */
    public ShamBundleContext getBundleContext()
    {
        return bundleContext;
    }

    /**
     * Helper getter for system bundle (bundle with id 0)
     *
     * @return mocked system bundle
     */
    public ShamBundle getSystemBundle()
    {
        return getBundleContext().getBundle( 0 );
    }

    /**
     * Installs (creates) a new mocked bundle
     *
     * @return installed (mock) bundle
     */
    public ShamBundle installBundle()
    {
        final ShamBundle bundle = mock( ShamBundle.class, new PartialImplementation( ShamBundle.class ) );
        bundles.add( bundle );
        return bundle.setBundleId( bundles.size() - 1 ).setBundleContext( getBundleContext() );
    }

    // ----------------------------------------------------------------------
    // Implementation methods
    // ----------------------------------------------------------------------

    /**
     * Joins execution environments as a comma separated string.
     *
     * @return execution environments
     */
    private String executionEnvironments()
    {
        if ( executionEnvironments.size() == 0 )
        {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for ( ExecutionEnvironment executionEnvironment : executionEnvironments )
        {
            if ( sb.length() > 0 )
            {
                sb.append( "," );
            }
            sb.append( executionEnvironment.forFS() );
        }
        return sb.toString();
    }

}
