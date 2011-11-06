package org.ops4j.pax.sham.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.ops4j.pax.sham.core.support.PackageUtils.packagesOf;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

/**
 * TODO
 *
 * @since 1.0
 */
public class ShamFrameworkTest
{

    @Test
    public void executionEnvironment()
    {
        final ShamFramework framework = new ShamFramework()
            .withExecutionEnvironment( ExecutionEnvironment.J2SE_1_3 )
            .withExecutionEnvironment( ExecutionEnvironment.J2SE_1_4 )
            .withExecutionEnvironment( ExecutionEnvironment.J2SE_1_5 )
            .withExecutionEnvironment( ExecutionEnvironment.JavaSE_1_6 );

        final String ee = framework.getBundleContext().getProperty( Constants.FRAMEWORK_EXECUTIONENVIRONMENT );
        assertThat( ee, containsString( ExecutionEnvironment.J2SE_1_3.forFS() ) );
        assertThat( ee, containsString( ExecutionEnvironment.J2SE_1_4.forFS() ) );
        assertThat( ee, containsString( ExecutionEnvironment.J2SE_1_5.forFS() ) );
        assertThat( ee, containsString( ExecutionEnvironment.JavaSE_1_6.forFS() ) );
    }

    @Test
    public void frameworkPackages()
    {
        final ShamFramework framework = new ShamFramework()
            .withSystemPackages( packagesOf( OSGiFramework.OSGI_4_2 ) );

        final String exports = (String) framework.getSystemBundle().getHeaders().get( Constants.EXPORT_PACKAGE );
        assertThat( exports, containsString( "org.osgi.framework; version=1.5.0" ) );
    }

    @Test
    public void installBundleWithExportsViaShamFramework()
    {
        final ShamBundle foo = new ShamFramework().installBundle()
            .withPackages( "org.foo; version=1.0" );

        final String exports = (String) foo.getHeaders().get( Constants.EXPORT_PACKAGE );
        assertThat( exports, containsString( "org.foo; version=1.0" ) );
    }

    @Test
    public void installBundleWithExportsViaBundleContext()
        throws BundleException
    {
        final ShamBundle foo = new ShamFramework().getBundleContext().installBundle( "foo" )
            .withPackages( "org.foo; version=1.0" );

        final String exports = (String) foo.getHeaders().get( Constants.EXPORT_PACKAGE );
        assertThat( exports, containsString( "org.foo; version=1.0" ) );
    }

    @Test
    public void bundleState()
        throws BundleException
    {
        final ShamBundle foo = new ShamFramework().installBundle();

        assertThat( foo.getState(), is( equalTo( Bundle.INSTALLED ) ) );

        foo.start();
        assertThat( foo.getState(), is( equalTo( Bundle.ACTIVE ) ) );

        foo.stop();
        assertThat( foo.getState(), is( equalTo( Bundle.INSTALLED ) ) );

        foo.setState( Bundle.RESOLVED );
        assertThat( foo.getState(), is( equalTo( Bundle.RESOLVED ) ) );
    }

}
