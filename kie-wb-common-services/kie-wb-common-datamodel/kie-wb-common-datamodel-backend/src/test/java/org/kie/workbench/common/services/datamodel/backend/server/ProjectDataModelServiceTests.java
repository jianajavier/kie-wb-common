package org.kie.workbench.common.services.datamodel.backend.server;

import java.net.URL;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.drools.workbench.models.datamodel.oracle.ProjectDataModelOracle;
import org.jboss.weld.environment.se.StartMain;
import org.junit.Before;
import org.junit.Test;
import org.kie.commons.java.nio.fs.file.SimpleFileSystemProvider;
import org.kie.workbench.common.services.datamodel.backend.server.service.DataModelService;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;

import static org.junit.Assert.*;
import static org.kie.workbench.common.services.datamodel.backend.server.ProjectDataModelOracleTestUtils.*;

/**
 * Tests for DataModelService
 */
public class ProjectDataModelServiceTests {

    private final SimpleFileSystemProvider fs = new SimpleFileSystemProvider();
    private BeanManager beanManager;
    private Paths paths;

    @Before
    public void setUp() throws Exception {
        //Bootstrap WELD container
        StartMain startMain = new StartMain( new String[ 0 ] );
        beanManager = startMain.go().getBeanManager();

        //Instantiate Paths used in tests for Path conversion
        final Bean pathsBean = (Bean) beanManager.getBeans( Paths.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( pathsBean );
        paths = (Paths) beanManager.getReference( pathsBean,
                                                  Paths.class,
                                                  cc );

        //Ensure URLs use the default:// scheme
        fs.forceAsDefault();
    }

    @Test
    public void testProjectDataModelOracle() throws Exception {
        final Bean dataModelServiceBean = (Bean) beanManager.getBeans( DataModelService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( dataModelServiceBean );
        final DataModelService dataModelService = (DataModelService) beanManager.getReference( dataModelServiceBean,
                                                                                               DataModelService.class,
                                                                                               cc );

        final URL packageUrl = this.getClass().getResource( "/DataModelBackendTest1/src/main/java/t3p1" );
        final org.kie.commons.java.nio.file.Path nioPackagePath = fs.getPath( packageUrl.toURI() );
        final Path packagePath = paths.convert( nioPackagePath );

        final ProjectDataModelOracle oracle = dataModelService.getProjectDataModel( packagePath );

        assertNotNull( oracle );

        assertEquals( 2,
                      oracle.getProjectModelFields().size() );
        assertContains( "t3p1.Bean1",
                        oracle.getProjectModelFields().keySet() );
        assertContains( "t3p2.Bean2",
                        oracle.getProjectModelFields().keySet() );

        assertEquals( 3,
                      oracle.getProjectModelFields().get( "t3p1.Bean1" ).length );
        assertContains( "this",
                        oracle.getProjectModelFields().get( "t3p1.Bean1" ) );
        assertContains( "field1",
                        oracle.getProjectModelFields().get( "t3p1.Bean1" ) );
        assertContains( "field2",
                        oracle.getProjectModelFields().get( "t3p1.Bean1" ) );

        assertEquals( 2,
                      oracle.getProjectModelFields().get( "t3p2.Bean2" ).length );
        assertContains( "this",
                        oracle.getProjectModelFields().get( "t3p2.Bean2" ) );
        assertContains( "field1",
                        oracle.getProjectModelFields().get( "t3p2.Bean2" ) );
    }

    @Test
    public void testProjectDataModelOracleJavaDefaultPackage() throws Exception {
        final Bean dataModelServiceBean = (Bean) beanManager.getBeans( DataModelService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( dataModelServiceBean );
        final DataModelService dataModelService = (DataModelService) beanManager.getReference( dataModelServiceBean,
                                                                                               DataModelService.class,
                                                                                               cc );

        final URL packageUrl = this.getClass().getResource( "/DataModelBackendTest2/src/main/java" );
        final org.kie.commons.java.nio.file.Path nioPackagePath = fs.getPath( packageUrl.toURI() );
        final Path packagePath = paths.convert( nioPackagePath );

        final ProjectDataModelOracle oracle = dataModelService.getProjectDataModel( packagePath );

        assertNotNull( oracle );

        assertEquals( 1,
                      oracle.getProjectModelFields().size() );
        assertContains( "Bean1",
                        oracle.getProjectModelFields().keySet() );

        assertEquals( 3,
                      oracle.getProjectModelFields().get( "Bean1" ).length );
        assertContains( "this",
                        oracle.getProjectModelFields().get( "Bean1" ) );
        assertContains( "field1",
                        oracle.getProjectModelFields().get( "Bean1" ) );
        assertContains( "field2",
                        oracle.getProjectModelFields().get( "Bean1" ) );
    }

}