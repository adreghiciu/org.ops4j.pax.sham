package org.ops4j.pax.sham.core;

/**
 * Enumeration of OSGi versions.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public enum OSGiFramework
{

    OSGI_4_2( "OSGi-4.2" );

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    private final String fsName;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    OSGiFramework( final String fsName )
    {
        this.fsName = fsName;
    }

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * File system compliant execution environment name.
     *
     * @return file system compliant execution environment name
     */
    public String forFS()
    {
        return fsName;
    }

}
