cd $GEMFIRE_HOME/bin

rm -rf g2dn-*

./gfsh -e "start locator --name=g2dn-locator1 --port=10334 --initial-heap=1g --max-heap=1g --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7777 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1"

curl http://localhost:7777/metrics
./gfsh -e "connect" -e "configure pdx --read-serialized=true --auto-serializable-classes=.* --disk-store"
./gfsh -e "start server --name=g2dn-server1 --locators=localhost[10334] --initial-heap=5g --max-heap=5g --server-port=1880 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7778 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1"
curl http://localhost:7778/metrics

./gfsh -e "start server --name=g2dn-server2 --locators=localhost[10334]  --initial-heap=5g --max-heap=5g --server-port=1882 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7772 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1"
curl http://localhost:7772/metrics

./gfsh -e "connect" -e "connect"  -e "create region --name=Account --type=PARTITION --enable-statistics=true"
./gfsh -e "connect" -e "connect"  -e "create region --name=Location --type=PARTITION --enable-statistics=true"