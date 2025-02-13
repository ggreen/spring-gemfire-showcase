# ===============================================
# The Z Garbage collector is enabled by gfsh by default when starting members with Java 17.
# Customers have the options of using G1 or Z GC with Java 17.
# The Z garage collector may require more server resources than G1.
# See  https://gemfire.dev/blog/maximizing-gemfire-cache-performance-with-zgc-and-heap-lru-eviction/
#
# Customers migrating from the Java 11 CMS garbage typically find the server resources of G1 are closer to CMS.
# Customers can add the following gfsh arguments to enable G1GC on Java 17
#
# --J=-XX:-UseZGC --J=XX:+UseG1GC
# See https://knowledge.broadcom.com/external/article/294399/gemfire-using-g1gc-effectively-configur.html


# Example Script

cd $GEMFIRE_HOME/bin

./gfsh -e "start locator --name=g1-locator1 --port=10334 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7777 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1 --J=-XX:-UseZGC --J=-XX:+UseG1GC --J=-Dgemfire.statistic-archive-file=g1-locator1.gfs"

curl http://localhost:7777/metrics

./gfsh -e "connect" -e "configure pdx --read-serialized=true --auto-serializable-classes=.* --disk-store"

./gfsh -e "start server --name=g1-server1 --locators=localhost[10334] --server-port=1880 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7778 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1 --J=-Dgemfire.statistic-archive-file=g1-server1.gfs --J=-XX:-UseZGC --J=-XX:+UseG1GC"

curl http://localhost:7778/metrics

./gfsh -e "connect" -e "connect"  -e "create region --name=Account --type=PARTITION --enable-statistics=true"
./gfsh -e "connect" -e "connect"  -e "create region --name=Location --type=PARTITION --enable-statistics=true"

