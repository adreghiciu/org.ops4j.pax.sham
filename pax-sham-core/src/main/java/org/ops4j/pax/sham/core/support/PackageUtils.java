package org.ops4j.pax.sham.core.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.ops4j.pax.sham.core.ExecutionEnvironment;
import org.ops4j.pax.sham.core.OSGiFramework;
import org.ops4j.pax.sham.core.internal.IOUtils;

/**
 * Helper for exported packages.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class PackageUtils
{

    // ----------------------------------------------------------------------
    // Constants
    // ----------------------------------------------------------------------

    /**
     * Prefix path for execution environments packages properties files.
     */
    private static final String EE_PATH = ExecutionEnvironment.class.getPackage().getName().replace( ".", "/" ) + "/";

    /**
     * Prefix path for system packages properties files.
     */
    private static final String OF_PATH = OSGiFramework.class.getPackage().getName().replace( ".", "/" ) + "/";

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Load packages from a properties file.<br/>
     * Properties file should contain a property named "packages" with a value equal with exported packages in the
     * format compliant with OSGi "Export-Packages" (comma delimited exported packages).
     *
     * @param file file containing exported packages
     * @return array of exported packages
     */
    public static String[] packagesFrom( final File file )
    {
        InputStream in = null;
        try
        {
            in = new FileInputStream( file );
            return packagesFrom( in );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        finally
        {
            IOUtils.close( in );
        }
    }

    /**
     * Load packages from a properties file, specified by an url.<br/>
     * Properties file, denoted by url, should contain a property named "packages" with a value equal with exported
     * packages in the format compliant with OSGi "Export-Packages" (comma delimited exported packages).
     *
     * @param url url of a properties file containing exported packages
     * @return array of exported packages
     */
    public static String[] packagesFrom( final URL url )
    {
        InputStream in = null;
        try
        {
            in = url.openStream();
            return packagesFrom( in );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        finally
        {
            IOUtils.close( in );
        }
    }

    /**
     * Load packages from an inputs stream of properties file.<br/>
     * Properties file should contain a property named "packages" with a value equal with exported packages in the
     * format compliant with OSGi "Export-Packages" (comma delimited exported packages).
     *
     * @param stream stream of a file containing exported packages
     * @return array of exported packages
     */
    public static String[] packagesFrom( final InputStream stream )
    {
        try
        {
            final Properties properties = new Properties();
            properties.load( stream );
            return packagesFrom( properties );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Load packages from a Java Properties.<br/>
     * Properties should contain a property named "packages" with a value equal with exported packages in the
     * format compliant with OSGi "Export-Packages" (comma delimited exported packages).
     *
     * @param properties containing exported packages
     * @return array of exported packages
     */
    public static String[] packagesFrom( final Properties properties )
    {
        final String packages = properties.getProperty( "packages" );
        return packages.split( "," );
    }

    /**
     * Load packages related to an execution environment as specified by OSGi core specification.
     *
     * @param environment execution environment
     * @return array of exported packages
     */
    public static String[] packagesOf( final ExecutionEnvironment environment )
    {
        final URL resource = PackageUtils.class.getClassLoader().getResource(
            EE_PATH + environment.forFS() + ".properties"
        );
        return packagesFrom( resource );
    }

    /**
     * Load packages related to a system bundle as specified by OSGi core specification.
     *
     * @param framework osgi framework
     * @return array of exported packages
     */
    public static String[] packagesOf( final OSGiFramework framework )
    {
        final URL resource = PackageUtils.class.getClassLoader().getResource(
            OF_PATH + framework.forFS() + ".properties"
        );
        return packagesFrom( resource );
    }

}
