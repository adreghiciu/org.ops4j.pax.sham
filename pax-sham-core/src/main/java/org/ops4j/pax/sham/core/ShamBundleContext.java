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

import java.io.InputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * Extension of {@link BundleContext} with covariant return types for mocked bundles.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public abstract class ShamBundleContext
    implements BundleContext
{

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    /**
     * Framework that created this bundle context. Injected by {@link ShamFramework} on bundle context creation.
     */
    private ShamFramework framework;

    /**
     * Associated bundle. Injected by {@link ShamFramework} on bundle context creation.
     */
    private ShamBundle bundle;

    /**
     * Returns the framework that created this bundle context.
     * @return the framework that created this bundle context.
     */
    public ShamFramework getFramework()
    {
        return framework;
    }

    @Override
    public ShamBundle getBundle()
    {
        return bundle;
    }

    @Override
    public ShamBundle getBundle( final long id ){
        return framework.getBundles().get(0);
    }

    @Override
    public ShamBundle[] getBundles(){
        return framework.getBundles().toArray(new ShamBundle[framework.getBundles().size()]);
}

    @Override
    public abstract ShamBundle installBundle( final String location )
        throws BundleException;

    @Override
    public abstract ShamBundle installBundle( final String location, final InputStream inputStream )
        throws BundleException;

    // ----------------------------------------------------------------------
    // Implementation methods
    // ----------------------------------------------------------------------

    ShamBundleContext setFramework(final ShamFramework framework)
    {
        this.framework = framework;
        return this;
    }

    ShamBundleContext setBundle(final ShamBundle bundle)
    {
        this.bundle = bundle;
        return this;
    }

}
