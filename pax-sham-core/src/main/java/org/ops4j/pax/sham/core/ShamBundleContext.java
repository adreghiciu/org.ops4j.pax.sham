package org.ops4j.pax.sham.core;

import java.io.InputStream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * Extension of {@link BundleContext} with covariant return types for mocked bundles.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public interface ShamBundleContext
    extends BundleContext
{

    @Override
    ShamBundle getBundle( long id );

    @Override
    ShamBundle[] getBundles();

    @Override
    ShamBundle installBundle( String location )
        throws BundleException;

    @Override
    ShamBundle installBundle( String location, InputStream inputStream )
        throws BundleException;

}
