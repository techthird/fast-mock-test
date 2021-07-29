package fast.mock.test.maven.plugin;

import fast.mock.test.maven.plugin.base.AbstractPlugin;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

@RunWith(PowerMockRunner.class)
public class BaseTest extends AbstractMojoTestCase {
    @Rule
    public MojoRule rule = new MojoRule();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void testInvalidProject() throws Exception {
        File pom = new File( "src/test/resources");
        Assert.assertNotNull( pom );
        Assert.assertTrue( pom.exists());

        UnittestPlugin mojo = (UnittestPlugin) this.rule.lookupConfiguredMojo( readMavenProject(pom,"pom2.xml"), "test");
        Assert.assertNotNull( mojo );
        mojo.execute();
    }

    public MavenProject readMavenProject(File basedir, String pomFileName) throws Exception {
        File pom = new File(basedir, pomFileName);
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory(basedir);
        ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        configuration.setRepositorySession(new DefaultRepositorySystemSession());
        MavenProject project = ((ProjectBuilder)this.lookup(ProjectBuilder.class)).build(pom, configuration).getProject();
        Assert.assertNotNull(project);
        return project;
    }

}
