package org.ops4j.pax.sham.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

/**
 * TODO
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public abstract class ShamBundle
    implements Bundle
{

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    /**
     * Bundle headers. Lazy initialized.
     */
    private Properties headers;

    /**
     * Bundle context of this bundle. Injected by {@link ShamFramework} on bundle creation.
     */
    private ShamBundleContext bundleContext;

    /**
     * Id of this bundle. Injected by {@link ShamFramework} on bundle creation.
     */
    private long id;

    /**
     * Bundle exports. Lazy initialized.
     */
    private List<String> exports;

    /**
     * Bundle state. Defaults to {@link Bundle#INSTALLED}.
     */
    private int state;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    /**
     * Only {@link ShamFramework} should create sham bundles.
     */
    ShamBundle()
    {

    }

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Builder method for specifying bundle symbolic name.
     *
     * @param bsn bundle symbolic name
     * @return itself, for fluent api usage
     */
    public ShamBundle withBundleSymbolicName( final String bsn )
    {
        getHeadersAsProperties().setProperty( Constants.BUNDLE_SYMBOLICNAME, bsn );
        return this;
    }

    /**
     * Builder method for specifying exported packages.
     *
     * @param exports exported packages
     * @return itself, for fluent api usage
     */
    public ShamBundle withPackages( final String... exports )
    {
        this.getExports().addAll( Arrays.asList( exports ) );
        getHeadersAsProperties().setProperty( Constants.EXPORT_PACKAGE, exports() );
        return this;
    }

    /**
     * Builder method for specifying bundle state.
     *
     * @param state bundle state
     * @return itself, for fluent api usage
     */
    public ShamBundle setState( int state )
    {
        this.state = state;
        return this;
    }

    @Override
    public long getBundleId()
    {
        return id;
    }

    @Override
    public ShamBundleContext getBundleContext()
    {
        return bundleContext;
    }

    @Override
    public Dictionary getHeaders()
    {
        return getHeadersAsProperties();
    }

    @Override
    public int getState()
    {
        if ( state == 0 )
        {
            state = Bundle.INSTALLED;
        }
        return state;
    }

    @Override
    public void start()
        throws BundleException
    {
        setState( Bundle.ACTIVE );
    }

    @Override
    public void start( final int i )
        throws BundleException
    {
        start();
    }

    @Override
    public void stop()
        throws BundleException
    {
        setState( Bundle.INSTALLED );
    }

    @Override
    public void stop( final int i )
        throws BundleException
    {
        stop();
    }

    // ----------------------------------------------------------------------
    // Implementation methods
    // ----------------------------------------------------------------------

    ShamBundle setBundleContext( final ShamBundleContext bundleContext )
    {
        this.bundleContext = bundleContext;
        return this;
    }

    ShamBundle setBundleId( final long id )
    {
        this.id = id;
        return this;
    }

    private String exports()
    {
        final StringBuilder sb = new StringBuilder();
        for ( final String export : getExports() )
        {
            if ( sb.length() > 0 )
            {
                sb.append( "," );
            }
            sb.append( export );
        }
        return sb.toString();
    }

    private List<String> getExports()
    {
        if ( exports == null )
        {
            exports = new ArrayList<String>();
        }
        return exports;
    }

    private Properties getHeadersAsProperties()
    {
        if ( headers == null )
        {
            headers = new Properties();
        }
        return headers;
    }

}
