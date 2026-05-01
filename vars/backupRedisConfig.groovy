import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.adHoc(
        'role_middleware',
        config.inventory,
        'test -f /etc/redis/redis.conf && cp /etc/redis/redis.conf /etc/redis/redis.conf.bak || echo "No existing config, skipping backup"'
    )
}
