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
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.ops4j.pax.sham.core.behavior.BundleListenerBehavior;
import org.ops4j.pax.sham.core.behavior.ExecutionEnvironmentBehavior;
import org.ops4j.pax.sham.core.behavior.InstallBundleBehavior;
import org.ops4j.pax.sham.core.internal.PartialImplementation;

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

    /**
     * Current installed bundles. Lazy initialized.
     */
    private List<ShamBundle> bundles;

    /**
     * Execution environments. Lazy initialized.
     */
    private List<ExecutionEnvironment> executionEnvironments;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    public ShamFramework()
    {
        executionEnvironments = new ArrayList<ExecutionEnvironment>();
        bundles = new ArrayList<ShamBundle>();

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
     * Helper getter for system bundle (bundle with id 0)
     *
     * @return mocked system bundle
     */
    public ShamBundle getSystemBundle()
    {
        return bundles.get( 0 );
    }

    /**
     * Installs (creates) a new mocked bundle
     *
     * @return installed (mock) bundle
     */
    public ShamBundle installBundle()
    {
        final ShamBundle bundle = mock( ShamBundle.class, new PartialImplementation( ShamBundle.class ) );

        bundle.setBundleId( bundles.size() - 1 );
        bundles.add( bundle );

        final ShamBundleContext bundleContext = mock(
            ShamBundleContext.class, new PartialImplementation( ShamBundleContext.class )
        );
        bundleContext.setFramework( this );
        bundleContext.setBundle( bundle );

        bundle.setBundleContext( bundleContext );

        applyBehavioursTo( bundle );
        applyBehavioursTo( bundleContext );

        return bundle;
    }

    /**
     * Applies default bundle context behaviours. Subclasses, as when used in a test, can decide to add addition ones or
     * do not apply them at all by not calling this method. <br/>
     * Default behaviors applied are:<br/>
     * * {@link #applyExecutionEnvironmentBehavior(ShamBundleContext)}<br/>
     * * {@link #applyInstallBundleBehavior(ShamBundleContext)}<br/>
     * * {@link #applyBundleListenerBehavior(ShamBundleContext)}
     *
     * @param bundleContext to apply to
     */
    protected void applyBehavioursTo( final ShamBundleContext bundleContext )
    {
        applyExecutionEnvironmentBehavior( bundleContext );
        applyInstallBundleBehavior( bundleContext );
        applyBundleListenerBehavior( bundleContext );
    }

    /**
     * Applies {@link ExecutionEnvironmentBehavior}. Subclasses, as when used in a test, can decide to do not apply
     * this behavior by not calling this method.
     *
     * @param bundleContext to apply to
     */
    protected void applyExecutionEnvironmentBehavior( final ShamBundleContext bundleContext )
    {
        ExecutionEnvironmentBehavior.applyExecutionEnvironmentBehavior( bundleContext );
    }

    /**
     * Applies {@link InstallBundleBehavior}. Subclasses, as when used in a test, can decide to do not apply
     * this behavior by not calling this method.
     *
     * @param bundleContext to apply to
     */
    protected void applyInstallBundleBehavior( final ShamBundleContext bundleContext )
    {
        InstallBundleBehavior.applyInstallBundleBehavior( bundleContext );
    }

    /**
     * Applies {@link BundleListenerBehavior}. Subclasses, as when used in a test, can decide to do not apply
     * this behavior by not calling this method.
     *
     * @param bundleContext to apply to
     */
    protected void applyBundleListenerBehavior( final ShamBundleContext bundleContext )
    {
        BundleListenerBehavior.applyBundleListenerBehavior( bundleContext );
    }

    /**
     * Applies default bundle behaviours. Subclasses, as when used in a test, can decide to add addition ones or
     * do not apply them at all by not calling this method. <br/>
     * Default behaviors applied are:<br/>
     * * none (yet)
     *
     * @param bundle to apply to
     */
    protected void applyBehavioursTo( final ShamBundle bundle )
    {
        // none yet
    }

    /**
     * Returns current installed bundle.
     *
     * @return current installed bundle
     */
    public List<ShamBundle> getBundles()
    {
        return bundles;
    }

    /**
     * Joins execution environments as a comma separated string.
     *
     * @return execution environments
     */
    public String getExecutionEnvironments()
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
