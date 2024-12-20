package org.dede.com.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.awaitility.Awaitility;
import org.dede.com.camel.processor.FileProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class CamelPlayApplicationTests {

    private static final long DURATION_MILIS = 10000;
    private static final String SOURCE_FOLDER = "D:\\WORK\\IntellijWorkspace\\camelPlay\\camel-java-dsl-router\\src\\test\\resources\\source-folder";
    private static final String DESTINATION_FOLDER = "D:\\WORK\\IntellijWorkspace\\camelPlay\\camel-java-dsl-router\\src\\test\\resources\\destination-folder";

    @Test
    public void givenJavaDSLRoute_whenCamelStart_thenMoveFolderContent() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("file://" + SOURCE_FOLDER + "?delete=true")
                        .process(new FileProcessor())
                        .to("file://" + DESTINATION_FOLDER);
            }
        });

        camelContext.start();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        File destinationFile1 = new File(DESTINATION_FOLDER + "/" + dateFormat.format(date) + "Test1");
        File destinationFile2 = new File(DESTINATION_FOLDER + "/" + dateFormat.format(date) + "Test2");
        File destinationFile3 = new File(DESTINATION_FOLDER + "/" + dateFormat.format(date) + "Test3");

        Awaitility.await().atMost(Duration.ofMillis(DURATION_MILIS)).untilAsserted(() -> {
            assertThat(destinationFile1.exists(), is(true));
            assertThat(destinationFile2.exists(), is(true));
            assertThat(destinationFile3.exists(), is(true));
        });
        camelContext.stop();
    }

}
