package org.ops4j.pax.sham.core;

/**
 * Enumeration of OSGi Execution Environments.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public enum ExecutionEnvironment
{

    J2SE_1_3( "J2SE-1.3" ),
    J2SE_1_4( "J2SE-1.4" ),
    J2SE_1_5( "J2SE-1.5" ),
    JavaSE_1_6( "JavaSE-1.6" );

    // ----------------------------------------------------------------------
    // Implementation fields
    // ----------------------------------------------------------------------

    private final String fsName;

    // ----------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------

    ExecutionEnvironment( final String fsName )
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
