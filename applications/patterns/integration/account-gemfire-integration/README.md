

Start GemFire

```start
deployments/local/scripts/gemfire/start.sh
```


Create Region

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "connect"  -e "create region --name=Items --type=PARTITION --enable-statistics=true"
```


run mySQL

```shell
podman run -it --rm \
  --name mysql \
  --publish 3306:3306 \
  --volume mysql-data:/var/lib/mysql \
  -e MYSQL_ROOT_PASSWORD='CHANGEME' \
  -e MYSQL_DATABASE='appdb' \
  mysql:latest
```


CLI

```shell
podman exec -it mysql mysql -u root -pCHANGEME appdb
```


```sql
-- Drop table if it already exists to ensure a clean setup
DROP TABLE IF EXISTS item;

-- Create the item table matching the Spring Integration adapter fields
CREATE TABLE item (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    status INT NOT NULL DEFAULT 2,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_item_status (status) -- Added index to optimize the adapter's polling query
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```


```sql
-- Clear old test records
TRUNCATE TABLE item;

-- Insert records with status = 2 (Ready to be polled by Spring Integration)
INSERT INTO item (name, status) VALUES 
('Laptop Stand', 2),
('USB-C Cable', 2),
('Mechanical Keyboard', 2);

-- Insert records with other status values (Will be ignored by 'where status=2')
INSERT INTO item (name, status) VALUES 
('Wireless Mouse', 1),   -- Pending review
('Monitors', 10),        -- Already processed
('Gaming Chair', 0);     -- Inactive

-- Verify inserted data
SELECT id, name, status, created_at FROM item WHERE status = 2;
```


```sql
update item set status = 2;
```

```gfsh
query --query="select value.get('name') from /Items.entries"
```