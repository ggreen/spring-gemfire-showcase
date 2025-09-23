cd $GEMFIRE_HOME/bin

rm -rf g5dn-*

./gfsh -e "start locator --name=g5dn-locator1 --port=10334 --initial-heap=1g --max-heap=1g --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7777 --bind-address=127.0.0.1 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s" &

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7777/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

curl http://localhost:7777/metrics
./gfsh -e "connect" -e "configure pdx --read-serialized=true --auto-serializable-classes=.* --disk-store"
./gfsh -e "start server --name=g5dn-server1 --locators=localhost[10334] --initial-heap=5g --max-heap=5g --server-port=1880 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7778 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1" &
./gfsh -e "start server --name=g5dn-server2 --locators=localhost[10334]  --initial-heap=5g --max-heap=5g --server-port=1882 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7772 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1" &
./gfsh -e "start server --name=g5dn-server3 --locators=localhost[10334]  --initial-heap=5g --max-heap=5g --server-port=1883 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7773 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1" &
./gfsh -e "start server --name=g5dn-server4 --locators=localhost[10334]  --initial-heap=5g --max-heap=5g --server-port=1884 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7774 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1" &
./gfsh -e "start server --name=g5dn-server5 --locators=localhost[10334]  --initial-heap=5g --max-heap=5g --server-port=1885 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7775 --J=-Dgemfire.prometheus.metrics.host=localhost --J=-Dgemfire.prometheus.metrics.interval=15s --bind-address=127.0.0.1" &

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7775/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7774/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7773/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7772/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7778/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

until [ "$(curl -s -o /dev/null -w '%{http_code}' http://localhost:7777/metrics)" -eq 200 ]; do
  echo "Waiting for service..."
  sleep 2
done

./gfsh -e "connect" -e "connect"  -e "create region --name=Account --type=PARTITION --enable-statistics=true"
./gfsh -e "connect" -e "connect"  -e "create region --name=Location --type=PARTITION --enable-statistics=true"