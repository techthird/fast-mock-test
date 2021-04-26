package fast.mock.test.maven.plugin;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class MockPluginTests {

    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();

    @Test
    public void testInvalidProject() throws Exception {
        /*File projectCopy = this.resources.getBasedir( "project--invalid" );
        File pom = new File( projectCopy, "pom.xml" );
        Assert.assertNotNull( pom );
        Assert.assertTrue( pom.exists());*/

        /*ValidateMojo mojo = (ValidateMojo) this.rule.lookupMojo( "validate", pom );
        Assert.assertNotNull( mojo );
        mojo.execute();*/
        UnittestPlugin unittestPlugin = new UnittestPlugin();
        unittestPlugin.execute();
    }

}
