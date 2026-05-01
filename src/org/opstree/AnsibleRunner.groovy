package org.opstree

class AnsibleRunner implements Serializable {

    def steps

    AnsibleRunner(steps) {
        this.steps = steps
    }

    def runPlaybook(String playbook, String inventory, String extraVars = '') {
        steps.sh """
            export ANSIBLE_HOST_KEY_CHECKING=False
            ansible-playbook ${playbook} \
              -i ${inventory} \
              --extra-vars "${extraVars}" \
              -v
        """
    }

    def syntaxCheck(String playbook, String inventory) {
        steps.sh """
            export ANSIBLE_HOST_KEY_CHECKING=False
            ansible-playbook ${playbook} -i ${inventory} --syntax-check
            ansible-inventory -i ${inventory} --list
        """
    }

    def dryRun(String playbook, String inventory) {
        steps.sh """
            export ANSIBLE_HOST_KEY_CHECKING=False
            ansible-playbook ${playbook} \
              -i ${inventory} \
              --check -v
        """
    }

    def adHoc(String hosts, String inventory, String command) {
        steps.sh """
            export ANSIBLE_HOST_KEY_CHECKING=False
            ansible ${hosts} \
              -i ${inventory} \
              -m shell \
              -a '${command}' \
              --become
        """
    }
}
