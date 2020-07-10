/*******************************************************************************
 * Copyright (c) 2020, 2020 IBM Corp. and others
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution and is available at https://www.eclipse.org/legal/epl-2.0/
 * or the Apache License, Version 2.0 which accompanies this distribution and
 * is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following
 * Secondary Licenses when the conditions for such availability set
 * forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
 * General Public License, version 2 with the GNU Classpath
 * Exception [1] and GNU General Public License, version 2 with the
 * OpenJDK Assembly Exception [2].
 *
 * [1] https://www.gnu.org/software/classpath/license.html
 * [2] http://openjdk.java.net/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
 *******************************************************************************/

GITHUB_REPO = 'https://github.com/eclipse/openj9-website.git'
ECLIPSE_REPO = 'ssh://genie.openj9@git.eclipse.org:29418/www.eclipse.org/openj9.git'
BRANCH = 'staging'
GITHUB_DIR = 'github_repo'
ECLIPSE_DIR = 'staging_repo'
SSH_CREDENTIAL_ID = 'git.eclipse.org-bot-ssh'

timeout(time: 3, unit: 'HOURS') {
    timestamps {
        node('hw.arch.x86&&sw.tool.docker') {
            try {
                stage('Docker Build') {
                    checkout scm

                    my_image = docker.build "openj9-website"

                    my_image.inside {
                        sh """
                        git config --global user.email "genie-openj9@eclipse.com"
                        git config --global user.name "genie-openj9"
                        git status
                        git fetch origin
                        git checkout -B vnext
                        git merge origin/vnext
                        """
                        sh "npm install"
                        sh "npm run deploy"
                    }
                }
            } finally {
                cleanWs()
                sh "docker system prune -af"
            }
        }
    }
}