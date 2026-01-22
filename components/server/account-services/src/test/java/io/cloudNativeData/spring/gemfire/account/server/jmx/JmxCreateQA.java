package io.cloudNativeData.spring.gemfire.account.server.jmx;

import nyla.solutions.core.patterns.jmx.JMX;
import org.apache.geode.management.MemberMXBean;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Map;

public class JmxCreateQA {
    public static void main(String[] args) throws MalformedObjectNameException {

        final String host = "localhost";
        final int port = 1099;

        String user = "";
        char[] password = "".toCharArray();

        var jmx = JMX.connect(host,port,user,password);

        MemberMXBean locatorMember = jmx.newBean(MemberMXBean.class,
                new ObjectName("GemFire:type=Member,member=locator1"));

        locatorMember.processCommand("create region --name=MyRegion --type=PARTITION");
    }
}
