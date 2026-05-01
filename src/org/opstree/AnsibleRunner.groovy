package org.opstree

class AnsibleRunner implements Serializable {

    def steps
    static final String CREDENTIAL_ID = 'ansible-ssh-key'
    static final String AWS_REGION    = 'us-east-1'

    AnsibleRunner(steps) {
        this.steps = steps
    }

    def runPlaybook(String playbook, String inventory, String extraVars = '') {
        steps.withCredentials([steps.sshUserPrivateKey(
            credentialsId: CREDENTIAL_ID,
            keyFileVariable: 'SSH_KEY'
        )]) {
            steps.sh """
                export ANSIBLE_HOST_KEY_CHECKING=False
                export AWS_REGION=${AWS_REGION}
                ansible-playbook ${playbook} \
                  -i ${inventory} \
                  --private-key \$SSH_KEY \
                  --extra-vars "ansible_user=ubuntu ${extraVars}" \
                  -v
            """
        }
    }

    def syntaxCheck(String playbook, String inventory) {
        steps.withCredentials([steps.sshUserPrivateKey(
            credentialsId: CREDENTIAL_ID,
            keyFileVariable: 'SSH_KEY'
        )]) {
            steps.sh """
                export ANSIBLE_HOST_KEY_CHECKING=False
                export AWS_REGION=${AWS_REGION}
                ansible-playbook ${playbook} -i ${inventory} --syntax-check
                ansible-inventory  -i ${inventory} --list
            """
        }
    }

    def dryRun(String playbook, String inventory) {
        steps.withCredentials([steps.sshUserPrivateKey(
            credentialsId: CREDENTIAL_ID,
            keyFileVariable: 'SSH_KEY'
        )]) {
            steps.sh """
                export ANSIBLE_HOST_KEY_CHECKING=False
                export AWS_REGION=${AWS_REGION}
                ansible-playbook ${playbook} \
                  -i ${inventory} \
                  --private-key \$SSH_KEY \
                  --extra-vars "ansible_user=ubuntu" \
                  --check -v
            """
        }
    }

    def adHoc(String hosts, String inventory, String command) {
        steps.withCredentials([steps.sshUserPrivateKey(
            credentialsId: CREDENTIAL_ID,
            keyFileVariable: 'SSH_KEY'
        )]) {
            steps.sh """
                export ANSIBLE_HOST_KEY_CHECKING=False
                export AWS_REGION=${AWS_REGION}
                ansible ${hosts} \
                  -i ${inventory} \
                  --private-key \$SSH_KEY \
                  --extra-vars "ansible_user=ubuntu" \
                  -m shell -a "${command}" \
                  --become
            """
        }
    }
}
