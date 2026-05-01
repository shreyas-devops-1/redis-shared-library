import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.adHoc(
        'localhost',
        config.inventory,
        'test -f /etc/redis/redis.conf && cp /etc/redis/redis.conf /etc/redis/redis.conf.bak || true'
    )
}

