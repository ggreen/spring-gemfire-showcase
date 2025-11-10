package spring.gemfire.showcase.remove.search.service;

import lombok.SneakyThrows;
import nyla.solutions.core.patterns.creational.Maker;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import spring.gemfire.showcase.remove.search.HttpRestTemplateConfig;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HyperTextServiceTest {

    private HyperTextService subject;

    @Mock
    private Maker<String, LuceneQuery<Object, Object>> maker;
    private final static int MAX_SIZE = 100;

    @BeforeEach
    void setUp() throws Exception {
        subject = new HyperTextService(maker,new HttpRestTemplateConfig().createUnsafeRestTemplate(),MAX_SIZE);
    }

    @SneakyThrows
    @Test
    void getSummary() throws MalformedURLException {
        var url = new URI("https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/");

        var actual = subject.getSummary(url);
        System.out.println(actual);
        assertThat(actual).isNotEmpty();
        assertThat(actual.length()).isLessThan(MAX_SIZE);
    }

    @Test
    void getSummaryWithPdf() throws URISyntaxException {

        var url = new URI("https://techdocs.broadcom.com/content/dam/broadcom/techdocs/us/en/pdf/vmware-tanzu/data-solutions/tanzu-gemfire/10-0/gf/gf.pdf");
        var actual = subject.getSummary(url);
        System.out.println(actual);
        assertThat(actual).isNull();
    }

    @Test
    void youTubeTest() throws URISyntaxException {

        //
        var url = new URI("https://www.youtube.com/watch?v=MssjJaWTlm4");
        var actual = subject.getSummary(url);
        System.out.println(actual);
        assertThat(actual).isNotEmpty();
    }

    @Test
    void search() {
        LuceneQuery<Object, Object> expected = Mockito.mock();
        when(maker.make(anyString())).thenReturn(expected);

        var actual = subject.search("text");

        assertThat(actual).isEqualTo(expected);
    }
}