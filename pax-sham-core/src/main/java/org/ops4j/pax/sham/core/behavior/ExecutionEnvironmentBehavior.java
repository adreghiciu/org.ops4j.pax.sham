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

package org.ops4j.pax.sham.core.behavior;

import static org.mockito.Mockito.when;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ops4j.pax.sham.core.ShamBundleContext;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * Configures bundle context to return framework execution environments property
 * {@link Constants#FRAMEWORK_EXECUTIONENVIRONMENT}.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 08, 2011
 */
public class ExecutionEnvironmentBehavior
{

    // ----------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------

    /**
     * Stub {@link BundleContext#getProperty(String)} for {@link Constants#FRAMEWORK_EXECUTIONENVIRONMENT}.
     *
     * @param bundleContext to be stubbed
     * @return provided bundle context, for fluent api usage
     */
    public static ShamBundleContext applyExecutionEnvironmentBehavior( final ShamBundleContext bundleContext )
    {
        when( bundleContext.getProperty( Constants.FRAMEWORK_EXECUTIONENVIRONMENT ) ).thenAnswer(
            new Answer<String>()
            {
                @Override
                public String answer( final InvocationOnMock invocation )
                    throws Throwable
                {
                    return bundleContext.getFramework().getExecutionEnvironments();
                }
            }
        );

        return bundleContext;
    }

}
