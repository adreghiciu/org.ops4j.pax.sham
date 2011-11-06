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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Dictionary;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.ops4j.pax.sham.core.foo.HelloWorld;
import org.ops4j.pax.sham.core.foo.internal.Activator;
import org.ops4j.pax.sham.core.foo.internal.HelloWorldImpl;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @since 1.0.0, November 07, 2011
 */
public class ShamFrameworkMockingTest
{

    @Test
    public void activatorRegisteringAService()
        throws Exception
    {
        final ShamBundleContext bundleContext = new ShamFramework().getBundleContext();
        final ServiceRegistration serviceRegistration = mock( ServiceRegistration.class );

        when( bundleContext.registerService( anyString(), any(), Matchers.<Dictionary>any() ) )
            .thenReturn( serviceRegistration );

        final Activator activator = new Activator();
        activator.start( bundleContext );
        activator.stop( bundleContext );

        final ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass( String.class );
        final ArgumentCaptor<HelloWorld> serviceCaptor = ArgumentCaptor.forClass( HelloWorld.class );

        verify( bundleContext ).registerService(
            typeCaptor.capture(), serviceCaptor.capture(), Matchers.<Dictionary>any()
        );

        assertThat( typeCaptor.getValue(), is( equalTo( HelloWorld.class.getName() ) ) );
        assertThat( serviceCaptor.getValue(), is( instanceOf( HelloWorldImpl.class ) ) );

        verify( serviceRegistration ).unregister();
    }

}
