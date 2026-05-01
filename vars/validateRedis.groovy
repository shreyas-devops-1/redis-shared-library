import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.adHoc(
        'role_middleware',
        config.inventory,
        '/usr/bin/redis-cli -p 6379 ping | grep -q PONG && echo "Redis healthy" || exit 1'
    )
}
