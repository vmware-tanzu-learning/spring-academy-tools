#!/bin/bash

set -e
set -x
set -o pipefail
set -u

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
cd $SCRIPT_DIR/..

setup() {
    if [ -d ./tmp ]; then
        rm -rf ./tmp
    fi

    mkdir -p ./tmp/code-server
    mkdir -p ./tmp/package/User

    cp resources/* ./tmp/package/User/

    docker run --rm \
        -v "$PWD/tmp/code-server:/home/coder/.local/share/code-server" \
        -u "$(id -u):$(id -g)" \
        -e "DOCKER_USER=$USER" \
        codercom/code-server:${CODE_SERVER_VERSION} --install-extension vscjava.vscode-java-pack@${VSCODE_JAVA_EXTENSION_VERSION}

    cp -r ./tmp/code-server/extensions ./tmp/package/
    rm ./tmp/package/extensions/extensions.json
}

publish() {
    imgpkg push \
    -i ghcr.io/${GITHUB_REPOSITORY_OWNER}/vscode-java-tools-${PLATFORM_ARCH}-files:latest \
    -f ./tmp/package

    crane tag ghcr.io/${GITHUB_REPOSITORY_OWNER}/vscode-java-tools-${PLATFORM_ARCH}-files:latest ${TAG} 
}

"$@"