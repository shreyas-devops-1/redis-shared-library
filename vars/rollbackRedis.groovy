import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.adHoc(
        'role_middleware',
        config.inventory,
        'test -f /etc/redis/redis.conf.bak && cp /etc/redis/redis.conf.bak /etc/redis/redis.conf && systemctl restart redis-server && /usr/bin/redis-cli ping || echo "No backup found, skipping rollback"'
    )
}
