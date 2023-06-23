# cnc-devcontainer-templates

## Templates
| Codespace Type | Link |
| ---- | ---- |
| Single Image: | [![Open in Github Codespaces Single Image](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates?devcontainer_path=.devcontainer%2Fbasic-image%2Fdevcontainer.json) |
| Docker Compose: | [![Open in GitHub Codespaces Docker Compose](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates?devcontainer_path=.devcontainer%2Fbasic-compose%2Fdevcontainer.json) |
| Kubernetes (k3s via k3d) | [![Open in GitHub Codespaces Kubernetes (k3s via k3d)](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates?devcontainer_path=.devcontainer%2Fbasic-k3s%2Fdevcontainer.json) |

## Common

All include

* tools-deps
* babashka
* lein

## Accessing Private JARs

## Github actions, issues, etc.

## Minimal VSCode setup

If you don't use vscode, I recommend setting up a minimal profile that emulates your favorite editor, and turning on settings sync.  That way you will always have a minimally functional editor when you use a codespace.

Note that you can configure your preference between the web editor, local VSCode, and other editors. Configure at [Your codespace settings](https://github.com/settings/codespaces)

## Dotfiles

You can specify a dotfile repository, that will be injected into your devcontainer.

[Docs](https://docs.github.com/en/codespaces/customizing-your-codespace/personalizing-github-codespaces-for-your-account#dotfiles)

Configure at [Your codespace settings](https://github.com/settings/codespaces)

## Prebuilds

Github actions can be automatically setup to pre-build your devcontainer docker images, dramatically speeding up startup.

https://docs.github.com/en/codespaces/prebuilding-your-codespaces/configuring-prebuilds

## Differences between devcontainers on your local machine and Github Codespaces


## Setting a minimum machine specification

[codespaces - minimum machine settings](https://docs.github.com/en/enterprise-cloud@latest/codespaces/setting-up-your-project-for-codespaces/configuring-dev-containers/setting-a-minimum-specification-for-codespace-machines)

## K3d commands

    k3d cluster create

    k3d cluster create --registry-create myregistry  > k3d.log 2>&1

    k3d cluster list

    k3d cluster start

Add the following to auto deploy manifests?

    --volume /tmp/test-manifests:/var/lib/rancher/k3s/server/manifests/test