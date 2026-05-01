import org.opstree.AnsibleRunner

def call(Map config) {
    def runner = new AnsibleRunner(this)
    runner.syntaxCheck(config.playbook, config.inventory)
}
