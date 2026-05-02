def call(Map config) {

    def deployer = new org.opstree.SSMDeploy(this)

    deployer.runCommand(
        config.instanceId,
        config.repoUrl,
        config.playbook ?: "redis_setup.yml",
        config.region ?: "us-east-1"
    )
}
