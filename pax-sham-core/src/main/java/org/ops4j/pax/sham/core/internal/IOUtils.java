package org.ops4j.pax.sham.core.internal;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO related utilities.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class IOUtils
{

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Safe close of a closable.
     *
     * @param closeable to be closed
     */
    public static void close( Closeable closeable )
    {
        if ( closeable != null )
        {
            try
            {
                closeable.close();
            }
            catch ( IOException e )
            {
                throw new RuntimeException( e );
            }
        }
    }

}
