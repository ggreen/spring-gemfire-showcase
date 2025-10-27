package spring.gemfire.showcase.remove.search.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

class ToTextConverterTest {

    private ToTextConverter subject;

    public static RestTemplate createUnsafeRestTemplate() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Disable hostname verification
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @BeforeEach
    void setUp() throws Exception {
        subject = new ToTextConverter(createUnsafeRestTemplate());
    }

    @Test
    void convert() {
        var expected ="expected";
        var actual = subject.convert(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenUrlThenReturnText() {

        var input ="https://www.timeanddate.com/worldclock/";
        var actual = subject.convert(input);
        assertThat(actual).isNotEmpty().isNotEqualTo(input);

        System.out.println(actual);
    }
}