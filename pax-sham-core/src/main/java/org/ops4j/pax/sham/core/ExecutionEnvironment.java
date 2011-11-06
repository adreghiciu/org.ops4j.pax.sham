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
