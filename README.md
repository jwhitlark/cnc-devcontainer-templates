# cnc-devcontainer-templates

## Templates

* basic-image
* basic-compose
* basic-k8s

## Common

All include

* tools-deps
* babashka
* lein

## Accessing Private JARs

## Github actions, issues, etc.

## Dotfiles

[Docs](https://docs.github.com/en/codespaces/customizing-your-codespace/personalizing-github-codespaces-for-your-account#dotfiles)

Configure at [Your codespace settings](https://github.com/settings/codespaces)

## Prebuilds

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