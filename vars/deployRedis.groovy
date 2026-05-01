import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.runPlaybook(
        config.playbook,
        config.inventory,
        'redis_port=6379 redis_bind=0.0.0.0 redis_maxmemory=256mb'
    )
}
