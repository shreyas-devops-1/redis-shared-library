package org.opstree

class SSMDeploy implements Serializable {

    def steps

    SSMDeploy(steps) {
        this.steps = steps
    }

    def runCommand(String instanceId, String repoUrl, String playbook, String region="us-east-1") {

        steps.echo "Starting SSM deployment to instance: ${instanceId}"

        steps.sh """
        aws ssm send-command \
          --instance-ids ${instanceId} \
          --document-name "AWS-RunShellScript" \
          --comment "Deploy Redis via Ansible" \
          --parameters commands='[
            "cd /home/ubuntu",
            "rm -rf redis-cd",
            "git clone ${repoUrl}",
            "cd redis-cd",
            "ansible-playbook ${playbook}"
          ]' \
          --region ${region}
        """
    }
}
